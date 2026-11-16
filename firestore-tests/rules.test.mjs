import { before, after, beforeEach, describe, it } from 'node:test';
import assert from 'node:assert/strict';
import { readFile } from 'node:fs/promises';
import { initializeTestEnvironment, assertFails, assertSucceeds } from '@firebase/rules-unit-testing';
import { collection, deleteDoc, doc, getDoc, getDocs, query, setDoc, updateDoc, where } from 'firebase/firestore';

const projectId = 'demo-iad-rules';
let env;

before(async () => {
  env = await initializeTestEnvironment({
    projectId,
    firestore: {
      rules: await readFile(new URL('../firestore.rules', import.meta.url), 'utf8'),
      host: '127.0.0.1',
      port: 8080,
    },
  });
});
beforeEach(async () => env.clearFirestore());
after(async () => env.cleanup());

describe('Firestore owner and control-plane rules', () => {
  it('allows an authenticated owner to create and update owned project data', async () => {
    const db = env.authenticatedContext('user-a').firestore();
    const project = doc(db, 'projects/project-a');
    await assertSucceeds(setDoc(project, { userId: 'user-a', name: 'Initial' }));
    await assertSucceeds(updateDoc(project, { name: 'Updated' }));
    assert.equal((await getDoc(project)).data().name, 'Updated');
  });

  it('prevents changing userId to transfer document ownership', async () => {
    const db = env.authenticatedContext('user-a').firestore();
    const project = doc(db, 'projects/project-a');
    await assertSucceeds(setDoc(project, { userId: 'user-a', name: 'Owned' }));
    await assertFails(updateDoc(project, { userId: 'user-b' }));
  });

  it('prevents another authenticated user from reading or querying owner data', async () => {
    await env.withSecurityRulesDisabled(async (context) => {
      await setDoc(doc(context.firestore(), 'projects/project-a'), { userId: 'user-a', name: 'Private' });
    });
    const otherDb = env.authenticatedContext('user-b').firestore();
    await assertFails(getDoc(doc(otherDb, 'projects/project-a')));
    await assertFails(getDocs(query(collection(otherDb, 'projects'), where('userId', '==', 'user-a'))));
  });

  it('keeps audit logs readable by their owner but immutable from clients', async () => {
    await env.withSecurityRulesDisabled(async (context) => {
      await setDoc(doc(context.firestore(), 'audit_logs/log-a'), { userId: 'user-a', action: 'seeded' });
    });
    const db = env.authenticatedContext('user-a').firestore();
    const log = doc(db, 'audit_logs/log-a');
    await assertSucceeds(getDoc(log));
    await assertFails(setDoc(doc(db, 'audit_logs/log-b'), { userId: 'user-a' }));
    await assertFails(updateDoc(log, { action: 'tampered' }));
    await assertFails(deleteDoc(log));
  });

  it('denies client writes to approvals, jobs, checkpoints, and idempotency records', async () => {
    const db = env.authenticatedContext('user-a').firestore();
    for (const path of ['approval_requests/request-a', 'persistent_jobs/job-a', 'job_checkpoints/checkpoint-a', 'idempotency_records/idem-a']) {
      await assertFails(setDoc(doc(db, path), { userId: 'user-a' }));
    }
  });

  it('denies client reads and writes to credential-bearing collections', async () => {
    const db = env.authenticatedContext('user-a').firestore();
    for (const path of ['project_secrets_vault/secret-a', 'api_credentials/credential-a', 'github_accounts/github-a', 'google_accounts/google-a', 'telegram_bots/telegram-a', 'database_connections/database-a']) {
      await assertFails(getDoc(doc(db, path)));
      await assertFails(setDoc(doc(db, path), { userId: 'user-a', encryptedSecretValue: 'ciphertext' }));
    }
  });

  it('denies unauthenticated writes and unlisted collection access', async () => {
    const anonymousDb = env.unauthenticatedContext().firestore();
    await assertFails(setDoc(doc(anonymousDb, 'projects/anonymous'), { userId: 'anonymous' }));
    const ownerDb = env.authenticatedContext('user-a').firestore();
    await assertFails(setDoc(doc(ownerDb, 'unknown_collection/doc-a'), { userId: 'user-a' }));
  });
});
