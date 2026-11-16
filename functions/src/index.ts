import { Buffer } from "node:buffer";
import { createHash, randomUUID } from "node:crypto";
import { KeyManagementServiceClient } from "@google-cloud/kms";
import { getApps, initializeApp } from "firebase-admin/app";
import { FieldValue, getFirestore } from "firebase-admin/firestore";
import { HttpsError, onCall, onRequest } from "firebase-functions/v2/https";

if (getApps().length === 0) initializeApp();

const databaseId = process.env.FIRESTORE_DATABASE_ID || "(default)";
const db = getFirestore(getApps()[0]!, databaseId);
const kms = new KeyManagementServiceClient();

const secretServices = new Set([
  "DATABASE_CONNECTOR", "GITHUB_SERVICE", "BACKEND_WORKER"
]);
const secretAgents = new Set(["CODE_DEVELOPER", "TESTING_QA"]);
const secretOperations = new Set(["READ_SCHEMA", "EXECUTE_QUERY", "CONNECT"]);
const approvalOperations = new Set([
  "PUSH_COMMIT", "DEPLOY", "CHANGE_SETTINGS", "DELETE_DATA", "ROTATE_SECRET"
]);

function requireUid(request: { auth?: { uid: string; token: Record<string, unknown> } | null }): string {
  const uid = request.auth?.uid;
  if (!uid) throw new HttpsError("unauthenticated", "Sign in is required.");
  return uid;
}

function requireText(value: unknown, field: string, maxLength = 200): string {
  if (typeof value !== "string" || value.trim().length === 0 || value.length > maxLength) {
    throw new HttpsError("invalid-argument", `Invalid ${field}.`);
  }
  return value.trim();
}

function requireKmsKeyName(): string {
  const name = process.env.KMS_KEY_NAME?.trim();
  if (!name) throw new HttpsError("failed-precondition", "The server-side KMS key is not configured.");
  return name;
}

async function assertProjectOwner(uid: string, projectId: string): Promise<void> {
  const project = await db.collection("projects").doc(projectId).get();
  if (!project.exists || project.get("userId") !== uid) {
    throw new HttpsError("permission-denied", "Project access denied.");
  }
}

async function encryptSecret(plaintext: string): Promise<string> {
  const [result] = await kms.encrypt({
    name: requireKmsKeyName(),
    plaintext: Buffer.from(plaintext, "utf8"),
  });
  if (!result.ciphertext) throw new HttpsError("internal", "KMS encryption returned no ciphertext.");
  return Buffer.from(result.ciphertext as Uint8Array).toString("base64");
}

export const putVaultSecret = onCall(
  { enforceAppCheck: true, timeoutSeconds: 30, memory: "256MiB" },
  async (request) => {
    const uid = requireUid(request);
    const projectId = requireText(request.data?.projectId, "projectId", 128);
    const secretName = requireText(request.data?.secretName, "secretName", 120);
    const secretType = requireText(request.data?.secretType, "secretType", 80);
    const description = typeof request.data?.description === "string"
      ? request.data.description.slice(0, 1000) : "";
    const plaintext = requireText(request.data?.secretValue, "secretValue", 8192);
    const services = request.data?.allowedServices;
    const agents = request.data?.allowedAgents;
    const operations = request.data?.allowedOperations;

    if (!Array.isArray(services) || services.length === 0 ||
        !services.every((x: unknown) => typeof x === "string" && secretServices.has(x))) {
      throw new HttpsError("invalid-argument", "Invalid allowedServices.");
    }
    if (!Array.isArray(agents) || agents.length === 0 ||
        !agents.every((x: unknown) => typeof x === "string" && secretAgents.has(x))) {
      throw new HttpsError("invalid-argument", "Invalid allowedAgents.");
    }
    if (!Array.isArray(operations) || operations.length === 0 ||
        !operations.every((x: unknown) => typeof x === "string" && secretOperations.has(x))) {
      throw new HttpsError("invalid-argument", "Invalid allowedOperations.");
    }

    await assertProjectOwner(uid, projectId);
    const encryptedSecretValue = await encryptSecret(plaintext);
    const secretId = randomUUID();
    const secretReferenceKey = `SEC_REF_${randomUUID().replaceAll("-", "").slice(0, 20).toUpperCase()}`;
    const expiresAt = typeof request.data?.expiresAt === "number" ? request.data.expiresAt : null;
    if (expiresAt !== null && (!Number.isSafeInteger(expiresAt) || expiresAt <= Date.now())) {
      throw new HttpsError("invalid-argument", "expiresAt must be a future Unix timestamp in milliseconds.");
    }

    await db.runTransaction(async (tx) => {
      tx.create(db.collection("project_secrets_vault").doc(secretId), {
        userId: uid,
        projectId,
        secretName,
        secretType,
        description,
        secretReferenceKey,
        encryptedSecretValue,
        maskedSecretValue: plaintext.length <= 4 ? "••••" : `••••••••${plaintext.slice(-4)}`,
        allowedServices: services,
        allowedAgents: agents,
        allowedOperations: operations,
        expiresAt,
        createdAt: FieldValue.serverTimestamp(),
        lastUsedAt: null,
      });
      tx.create(db.collection("audit_logs").doc(), {
        userId: uid,
        projectId,
        action: "VAULT_SECRET_CREATED",
        targetId: secretId,
        createdAt: FieldValue.serverTimestamp(),
        actor: uid,
      });
    });

    return { id: secretId, secretReferenceKey, maskedSecretValue: plaintext.length <= 4
      ? "••••" : `••••••••${plaintext.slice(-4)}` };
  }
);

export const listVaultSecrets = onCall(
  { enforceAppCheck: true, timeoutSeconds: 15, memory: "256MiB" },
  async (request) => {
    const uid = requireUid(request);
    const projectId = requireText(request.data?.projectId, "projectId", 128);
    await assertProjectOwner(uid, projectId);
    const snapshot = await db.collection("project_secrets_vault")
      .where("userId", "==", uid)
      .where("projectId", "==", projectId)
      .get();
    return {
      secrets: snapshot.docs.map((doc) => {
        const data = doc.data();
        return {
          id: doc.id,
          projectId: data.projectId,
          secretName: data.secretName,
          secretType: data.secretType,
          description: data.description,
          secretReferenceKey: data.secretReferenceKey,
          maskedSecretValue: data.maskedSecretValue,
          allowedServices: data.allowedServices,
          allowedAgents: data.allowedAgents,
          allowedOperations: data.allowedOperations,
          expiresAt: data.expiresAt ?? null,
          lastUsedAt: data.lastUsedAt ?? null,
        };
      }),
    };
  }
);

export const deleteVaultSecret = onCall(
  { enforceAppCheck: true, timeoutSeconds: 15, memory: "256MiB" },
  async (request) => {
    const uid = requireUid(request);
    const secretId = requireText(request.data?.secretId, "secretId", 128);
    await db.runTransaction(async (tx) => {
      const ref = db.collection("project_secrets_vault").doc(secretId);
      const snapshot = await tx.get(ref);
      if (!snapshot.exists || snapshot.get("userId") !== uid) {
        throw new HttpsError("not-found", "Secret not found.");
      }
      tx.delete(ref);
      tx.create(db.collection("audit_logs").doc(), {
        userId: uid,
        projectId: snapshot.get("projectId"),
        action: "VAULT_SECRET_DELETED",
        targetId: secretId,
        createdAt: FieldValue.serverTimestamp(),
        actor: uid,
      });
    });
    return { deleted: true };
  }
);

/**
 * Private IAM-invoked endpoint for trusted server workers only.
 * Cloud Run IAM must grant roles/run.invoker only to the worker service account.
 * This endpoint intentionally returns plaintext only to an authenticated
 * server-to-server caller; never expose it through a client callable.
 */
export const resolveVaultSecretForTrustedWorker = onRequest(
  { invoker: "private", timeoutSeconds: 30, memory: "256MiB" },
  async (request, response) => {
    if (request.method !== "POST") {
      response.status(405).json({ error: "method-not-allowed" });
      return;
    }
    const userId = requireText(request.body?.userId, "userId", 128);
    const projectId = requireText(request.body?.projectId, "projectId", 128);
    const referenceKey = requireText(request.body?.referenceKey, "referenceKey", 160);
    const requestingService = requireText(request.body?.requestingService, "requestingService", 80);
    const requestingAgent = requireText(request.body?.requestingAgent, "requestingAgent", 80);
    const operation = requireText(request.body?.operation, "operation", 80);

    if (!secretServices.has(requestingService) ||
        !secretAgents.has(requestingAgent) ||
        !secretOperations.has(operation)) {
      response.status(403).json({ error: "operation-not-allowed" });
      return;
    }

    const snapshot = await db.collection("project_secrets_vault")
      .where("userId", "==", userId)
      .where("projectId", "==", projectId)
      .where("secretReferenceKey", "==", referenceKey)
      .limit(1)
      .get();
    if (snapshot.empty) {
      response.status(404).json({ error: "secret-not-found" });
      return;
    }

    const secretDoc = snapshot.docs[0]!;
    const secret = secretDoc.data();
    const expiresAt = secret.expiresAt;
    if (typeof expiresAt === "number" && expiresAt <= Date.now()) {
      response.status(410).json({ error: "secret-expired" });
      return;
    }
    if (!Array.isArray(secret.allowedServices) || !secret.allowedServices.includes(requestingService) ||
        !Array.isArray(secret.allowedAgents) || !secret.allowedAgents.includes(requestingAgent) ||
        !Array.isArray(secret.allowedOperations) || !secret.allowedOperations.includes(operation)) {
      response.status(403).json({ error: "secret-scope-denied" });
      return;
    }
    if (typeof secret.encryptedSecretValue !== "string" || secret.encryptedSecretValue.length === 0) {
      response.status(500).json({ error: "secret-ciphertext-missing" });
      return;
    }

    const [decrypted] = await kms.decrypt({
      name: requireKmsKeyName(),
      ciphertext: Buffer.from(secret.encryptedSecretValue, "base64"),
    });
    if (!decrypted.plaintext) {
      response.status(500).json({ error: "kms-decryption-failed" });
      return;
    }

    await db.runTransaction(async (tx) => {
      const fresh = await tx.get(secretDoc.ref);
      if (!fresh.exists || fresh.get("userId") !== userId ||
          fresh.get("secretReferenceKey") !== referenceKey) {
        throw new HttpsError("failed-precondition", "Secret changed during resolution.");
      }
      tx.update(secretDoc.ref, { lastUsedAt: FieldValue.serverTimestamp() });
      tx.create(db.collection("audit_logs").doc(), {
        userId,
        projectId,
        action: "VAULT_SECRET_RESOLVED",
        targetId: secretDoc.id,
        actor: requestingService,
        agent: requestingAgent,
        operation,
        createdAt: FieldValue.serverTimestamp(),
      });
    });

    response.status(200).json({
      secretValue: Buffer.from(decrypted.plaintext as Uint8Array).toString("utf8"),
    });
  }
);

const jobStates = new Set(["SUCCEEDED", "FAILED", "CANCELLED"]);

function requireWorkerRequest(request: { body?: Record<string, unknown> }) {
  return {
    jobId: requireText(request.body?.jobId, "jobId", 128),
    workerId: requireText(request.body?.workerId, "workerId", 128),
    leaseToken: requireText(request.body?.leaseToken, "leaseToken", 128),
  };
}

async function verifyAndReadLease(
  tx: FirebaseFirestore.Transaction,
  jobId: string,
  workerId: string,
  leaseToken: string
) {
  const ref = db.collection("persistent_jobs").doc(jobId);
  const snapshot = await tx.get(ref);
  if (!snapshot.exists) throw new HttpsError("not-found", "Job not found.");
  const data = snapshot.data()!;
  if (data.status !== "RUNNING" || data.workerId !== workerId ||
      data.leaseToken !== leaseToken || typeof data.leaseExpiresAt !== "number" ||
      data.leaseExpiresAt <= Date.now()) {
    throw new HttpsError("failed-precondition", "Worker lease is invalid, expired, or no longer owned.");
  }
  return { ref, data };
}

export const createPersistentJob = onCall(
  { enforceAppCheck: true, timeoutSeconds: 15, memory: "256MiB" },
  async (request) => {
    const uid = requireUid(request);
    const projectId = requireText(request.data?.projectId, "projectId", 128);
    const jobType = requireText(request.data?.jobType, "jobType", 80);
    const title = requireText(request.data?.title, "title", 200);
    const prompt = requireText(request.data?.prompt, "prompt", 20000);
    const idempotencyKey = requireText(request.data?.idempotencyKey, "idempotencyKey", 128);
    await assertProjectOwner(uid, projectId);

    const idempotencyId = createHash("sha256")
      .update(`${uid}:${projectId}:${idempotencyKey}`)
      .digest("hex");
    const idempotencyRef = db.collection("idempotency_records").doc(idempotencyId);
    const jobId = randomUUID();
    const jobRef = db.collection("persistent_jobs").doc(jobId);
    const result = await db.runTransaction(async (tx) => {
      const existing = await tx.get(idempotencyRef);
      if (existing.exists) {
        return { jobId: existing.get("jobId") as string, reused: true };
      }
      tx.create(jobRef, {
        userId: uid,
        projectId,
        jobType,
        title,
        prompt,
        status: "QUEUED",
        progress: 0,
        currentStepIndex: 0,
        attempts: 0,
        cancelRequested: false,
        workerId: null,
        leaseToken: null,
        leaseExpiresAt: 0,
        createdAt: FieldValue.serverTimestamp(),
        updatedAt: FieldValue.serverTimestamp(),
      });
      tx.create(idempotencyRef, {
        userId: uid,
        projectId,
        actionType: "CREATE_PERSISTENT_JOB",
        idempotencyKey,
        jobId,
        createdAt: FieldValue.serverTimestamp(),
      });
      tx.create(db.collection("audit_logs").doc(), {
        userId: uid,
        projectId,
        action: "PERSISTENT_JOB_QUEUED",
        targetId: jobId,
        actor: uid,
        createdAt: FieldValue.serverTimestamp(),
      });
      return { jobId, reused: false };
    });
    return result;
  }
);

export const listPersistentJobs = onCall(
  { enforceAppCheck: true, timeoutSeconds: 15, memory: "256MiB" },
  async (request) => {
    const uid = requireUid(request);
    const projectId = requireText(request.data?.projectId, "projectId", 128);
    await assertProjectOwner(uid, projectId);
    const snapshot = await db.collection("persistent_jobs")
      .where("userId", "==", uid)
      .where("projectId", "==", projectId)
      .limit(50)
      .get();
    return {
      jobs: snapshot.docs.map((doc) => {
        const data = doc.data();
        return {
          id: doc.id,
          projectId: data.projectId,
          jobType: data.jobType,
          title: data.title,
          status: data.status,
          progress: data.progress ?? 0,
          currentStepIndex: data.currentStepIndex ?? 0,
          attempts: data.attempts ?? 0,
          cancelRequested: data.cancelRequested ?? false,
          createdAt: data.createdAt?.toMillis?.() ?? null,
          updatedAt: data.updatedAt?.toMillis?.() ?? null,
        };
      }),
    };
  }
);

export const cancelPersistentJob = onCall(
  { enforceAppCheck: true, timeoutSeconds: 15, memory: "256MiB" },
  async (request) => {
    const uid = requireUid(request);
    const jobId = requireText(request.data?.jobId, "jobId", 128);
    await db.runTransaction(async (tx) => {
      const ref = db.collection("persistent_jobs").doc(jobId);
      const snapshot = await tx.get(ref);
      if (!snapshot.exists || snapshot.get("userId") !== uid) {
        throw new HttpsError("not-found", "Job not found.");
      }
      const status = snapshot.get("status");
      if (status === "QUEUED") {
        tx.update(ref, { status: "CANCELLED", updatedAt: FieldValue.serverTimestamp() });
      } else if (status === "RUNNING") {
        tx.update(ref, { cancelRequested: true, updatedAt: FieldValue.serverTimestamp() });
      } else {
        throw new HttpsError("failed-precondition", "Job is already finished.");
      }
      tx.create(db.collection("audit_logs").doc(), {
        userId: uid,
        projectId: snapshot.get("projectId"),
        action: "PERSISTENT_JOB_CANCEL_REQUESTED",
        targetId: jobId,
        actor: uid,
        createdAt: FieldValue.serverTimestamp(),
      });
    });
    return { jobId, cancellationRequested: true };
  }
);

/** IAM-private lease claim; worker identity comes from its service configuration. */
export const claimPersistentJob = onRequest(
  { invoker: "private", timeoutSeconds: 20, memory: "256MiB" },
  async (request, response) => {
    if (request.method !== "POST") {
      response.status(405).json({ error: "method-not-allowed" });
      return;
    }
    const jobId = requireText(request.body?.jobId, "jobId", 128);
    const workerId = requireText(request.body?.workerId, "workerId", 128);
    const leaseToken = randomUUID();
    const now = Date.now();
    const leaseExpiresAt = now + 60_000;
    const result = await db.runTransaction(async (tx) => {
      const ref = db.collection("persistent_jobs").doc(jobId);
      const snapshot = await tx.get(ref);
      if (!snapshot.exists) throw new HttpsError("not-found", "Job not found.");
      const data = snapshot.data()!;
      const expired = typeof data.leaseExpiresAt !== "number" || data.leaseExpiresAt <= now;
      if (data.status !== "QUEUED" && !(data.status === "RUNNING" && expired)) {
        throw new HttpsError("failed-precondition", "Job is not available to claim.");
      }
      if (data.cancelRequested === true) {
        tx.update(ref, { status: "CANCELLED", updatedAt: FieldValue.serverTimestamp() });
        return { cancelled: true };
      }
      tx.update(ref, {
        status: "RUNNING",
        workerId,
        leaseToken,
        leaseExpiresAt,
        heartbeatAt: FieldValue.serverTimestamp(),
        attempts: (typeof data.attempts === "number" ? data.attempts : 0) + 1,
        updatedAt: FieldValue.serverTimestamp(),
      });
      return { cancelled: false, job: data };
    });
    if (result.cancelled) {
      response.status(409).json({ error: "job-cancelled" });
      return;
    }
    response.status(200).json({ jobId, workerId, leaseToken, leaseExpiresAt, job: result.job });
  }
);

export const renewPersistentJobLease = onRequest(
  { invoker: "private", timeoutSeconds: 15, memory: "256MiB" },
  async (request, response) => {
    if (request.method !== "POST") {
      response.status(405).json({ error: "method-not-allowed" });
      return;
    }
    const { jobId, workerId, leaseToken } = requireWorkerRequest(request);
    const leaseExpiresAt = Date.now() + 60_000;
    await db.runTransaction(async (tx) => {
      const { ref } = await verifyAndReadLease(tx, jobId, workerId, leaseToken);
      tx.update(ref, {
        leaseExpiresAt,
        heartbeatAt: FieldValue.serverTimestamp(),
        updatedAt: FieldValue.serverTimestamp(),
      });
    });
    response.status(200).json({ jobId, leaseExpiresAt });
  }
);

export const finishPersistentJob = onRequest(
  { invoker: "private", timeoutSeconds: 20, memory: "256MiB" },
  async (request, response) => {
    if (request.method !== "POST") {
      response.status(405).json({ error: "method-not-allowed" });
      return;
    }
    const { jobId, workerId, leaseToken } = requireWorkerRequest(request);
    const state = request.body?.state;
    if (typeof state !== "string" || !jobStates.has(state)) {
      response.status(400).json({ error: "invalid-job-state" });
      return;
    }
    const summary = typeof request.body?.summary === "string" ? request.body.summary.slice(0, 4000) : "";
    await db.runTransaction(async (tx) => {
      const { ref, data } = await verifyAndReadLease(tx, jobId, workerId, leaseToken);
      const finalState = data.cancelRequested === true ? "CANCELLED" : state;
      tx.update(ref, {
        status: finalState,
        summary,
        progress: finalState === "SUCCEEDED" ? 100 : (data.progress ?? 0),
        workerId: null,
        leaseToken: null,
        leaseExpiresAt: 0,
        finishedAt: FieldValue.serverTimestamp(),
        updatedAt: FieldValue.serverTimestamp(),
      });
      tx.create(db.collection("audit_logs").doc(), {
        userId: data.userId,
        projectId: data.projectId,
        action: `PERSISTENT_JOB_${finalState}`,
        targetId: jobId,
        actor: workerId,
        createdAt: FieldValue.serverTimestamp(),
      });
    });
    response.status(200).json({ jobId, state });
  }
);

export const createApprovalRequest = onCall(
  { enforceAppCheck: true, timeoutSeconds: 15, memory: "256MiB" },
  async (request) => {
    const uid = requireUid(request);
    const projectId = requireText(request.data?.projectId, "projectId", 128);
    const operationType = requireText(request.data?.operationType, "operationType", 80);
    const description = requireText(request.data?.description, "description", 2000);
    const targetEntityId = requireText(request.data?.targetEntityId, "targetEntityId", 128);
    if (!approvalOperations.has(operationType)) {
      throw new HttpsError("invalid-argument", "Unsupported approval operation.");
    }
    await assertProjectOwner(uid, projectId);
    const id = randomUUID();
    const expiresAt = Date.now() + 15 * 60 * 1000;
    await db.runTransaction(async (tx) => {
      tx.create(db.collection("approval_requests").doc(id), {
        userId: uid,
        projectId,
        operationType,
        description,
        targetEntityId,
        requestedBy: uid,
        status: "PENDING",
        version: 1,
        expiresAt,
        createdAt: FieldValue.serverTimestamp(),
      });
      tx.create(db.collection("audit_logs").doc(), {
        userId: uid,
        projectId,
        action: "APPROVAL_REQUEST_CREATED",
        targetId: id,
        createdAt: FieldValue.serverTimestamp(),
        actor: uid,
      });
    });
    return { id, status: "PENDING", expiresAt };
  }
);

export const decideApprovalRequest = onCall(
  { enforceAppCheck: true, timeoutSeconds: 15, memory: "256MiB" },
  async (request) => {
    const uid = requireUid(request);
    if (request.auth?.token.approvalOfficer !== true) {
      throw new HttpsError("permission-denied", "An approvalOfficer claim is required.");
    }
    const approvalId = requireText(request.data?.approvalId, "approvalId", 128);
    const decision = request.data?.decision;
    const expectedVersion = request.data?.expectedVersion;
    if (!["APPROVED", "REJECTED"].includes(decision) ||
        !Number.isSafeInteger(expectedVersion) || expectedVersion < 1) {
      throw new HttpsError("invalid-argument", "Invalid approval decision or version.");
    }

    await db.runTransaction(async (tx) => {
      const ref = db.collection("approval_requests").doc(approvalId);
      const snapshot = await tx.get(ref);
      if (!snapshot.exists) throw new HttpsError("not-found", "Approval request not found.");
      const data = snapshot.data()!;
      if (data.status !== "PENDING" || data.version !== expectedVersion ||
          typeof data.expiresAt !== "number" || data.expiresAt <= Date.now()) {
        throw new HttpsError("failed-precondition", "Approval is stale, expired, or already decided.");
      }
      if (data.requestedBy === uid) {
        throw new HttpsError("permission-denied", "The requester cannot approve their own request.");
      }
      tx.update(ref, {
        status: decision,
        approvedBy: uid,
        decidedAt: FieldValue.serverTimestamp(),
        version: expectedVersion + 1,
      });
      tx.create(db.collection("audit_logs").doc(), {
        userId: data.userId,
        projectId: data.projectId,
        action: `APPROVAL_${decision}`,
        targetId: approvalId,
        actor: uid,
        createdAt: FieldValue.serverTimestamp(),
      });
    });
    return { approvalId, decision, decidedBy: uid };
  }
);
