# Firestore security hardening — implementation batch 188

Date: 2026-11-16

## Changes applied

The Firestore rules were changed from broad owner-only CRUD to a fail-closed collection policy:

- For the explicitly client-managed project data collections, reads and writes require an authenticated owner.
- Updates must preserve the original `userId`; a user cannot transfer a document to another account by changing its owner field.
- Audit logs, approval requests, persistent jobs, job checkpoints, and idempotency records are read-only to clients. Client-side writes to these control-plane records are denied.
- Credential-bearing collections (including the secrets vault, API credentials, GitHub/Google accounts, Telegram bots, and database connections) are denied to web/mobile clients until a trusted server-side broker exists.
- Every unlisted collection/path is denied by default.

## Important compatibility consequence

This is an intentional security-first restriction. Existing Android code still attempts direct client access to some now server-only collections. Those operations will be denied until callable backend functions / trusted workers are implemented and the clients are migrated. Do not weaken the rules to restore the old behavior.

## Verification status

- Rules source updated on `main`.
- The Angular CI workflow is being expanded to trigger for Firestore rules/config changes and to preserve an npm audit JSON artifact.
- Firestore Emulator rule tests have **not** yet been executed. A passing Angular build does not validate Firestore rules.
- Production Firebase deployment has **not** been performed.
- This batch does not claim that the trusted backend, approval service, secrets broker, or production release is complete.

## Next required work

1. Add Firebase Emulator configuration and automated rules tests covering owner isolation, immutable `userId`, read-only audit/control records, and denied client access to credentials.
2. Implement authenticated callable backend functions for audit append, approval decisions, job leasing/checkpoints, idempotency, and secrets broker operations.
3. Migrate Android and Angular away from direct writes/reads to server-only collections.
4. Replace the Android client-shipped AES key and plaintext encryption fallback with backend-managed secret encryption; never return secret plaintext to the UI or an AI prompt.
5. Run emulator tests and deploy rules only after review and test evidence.

