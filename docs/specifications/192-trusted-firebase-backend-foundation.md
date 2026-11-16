# Trusted Firebase backend foundation — implementation batch 192

Date: 2026-11-16

## Implemented in this batch

- Added a Node.js 22 / TypeScript Firebase Cloud Functions package under `functions/`.
- Registered the package in the root `firebase.json`.
- Added a CI workflow that installs the Functions dependencies and type-checks the TypeScript.
- Added App Check-enforced callable endpoints:
  - `putVaultSecret`: verifies authentication, App Check, project ownership, and allow-listed services/agents/operations; encrypts with Cloud KMS; stores ciphertext and masked metadata only; writes an immutable audit event.
  - `listVaultSecrets`: returns metadata only and never returns ciphertext.
  - `deleteVaultSecret`: verifies owner and deletes through a server transaction with an audit event.
  - `createApprovalRequest`: verifies project ownership and operation allow-list before creating a server-side approval request.
  - `decideApprovalRequest`: requires the `approvalOfficer=true` custom claim, rejects self-approval, checks expiry and optimistic version, then updates the decision and audit event transactionally.
- The Android client-side secret cipher and direct Firestore vault access remain disabled. The backend is being added first; the Android repository still needs callable integration before enabling the vault UI.

## Required project configuration before deployment

1. Enable the Cloud KMS API in the Firebase/Google Cloud project.
2. Create a symmetric encrypt/decrypt KMS key ring and key in the same region as the Functions deployment.
3. Configure `KMS_KEY_NAME` as the full key resource name for the Functions runtime.
4. Configure `FIRESTORE_DATABASE_ID` to the actual Firestore database ID. The repository currently references a named database rather than `(default)`.
5. Grant the Functions runtime service account only `cloudkms.cryptoKeyEncrypterDecrypter` on the selected key.
6. Enable Firebase App Check for the Android and web apps and configure the production providers.
7. Assign the `approvalOfficer=true` custom claim only to separately authorized approver accounts through a trusted administrative process.

## Explicit limitations / release gates

- No Functions deployment has been performed. The code is not live until the owner configures the cloud resources and deploys it.
- No plaintext secret is returned from a callable endpoint. A future server-to-server worker integration must resolve secrets inside the trusted backend only.
- The Android Secrets Vault screen remains disabled pending integration and successful deployment.
- The Android Orchestrator, lease/heartbeat, checkpoints, idempotency and external integration workflows still need to be moved to trusted backend operations; current Android-side orchestration is not production-safe.
- Functions CI must pass before this batch is accepted. Emulator/integration tests for the callable endpoints remain to be added.
