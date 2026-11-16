import { Buffer } from "node:buffer";
import { randomUUID } from "node:crypto";
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
