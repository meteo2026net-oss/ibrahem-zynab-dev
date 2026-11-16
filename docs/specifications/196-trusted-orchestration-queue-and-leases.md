# Trusted orchestration queue and worker leases — implementation batch 196

Date: 2026-11-16

## Implemented in Firebase Functions

- `createPersistentJob`: authenticated, App Check-enforced, project-owner checked job creation; uses a transaction with an idempotency record and server-side audit event.
- `listPersistentJobs`: returns only the signed-in user's jobs for an owned project.
- `cancelPersistentJob`: cancels queued work or sets a cancellation request for a running worker, with audit.
- `claimPersistentJob`: IAM-private worker endpoint; atomically claims a queued job or a running job whose lease expired, assigns a random lease token, increments attempt count, and creates a 60-second lease.
- `renewPersistentJobLease`: IAM-private heartbeat; verifies worker identity, token, running state, and lease expiry before renewing.
- `finishPersistentJob`: IAM-private completion endpoint; verifies lease ownership, honors cancellation requests, finalizes status, clears lease credentials, and writes audit.

## Safety properties

- Job creation and idempotency are committed in one Firestore transaction.
- A worker cannot renew or finish a job without the current lease token and worker ID.
- Expired leases cannot be renewed; a job can be reclaimed after expiry.
- Client rules continue to deny direct writes to jobs, checkpoints, idempotency records, approvals, secrets, and audit logs.
- Worker endpoints use private Cloud Run invocation and must grant `roles/run.invoker` only to the dedicated worker service account.

## Not yet complete

- Functions TypeScript CI is running for this change; pass status must be confirmed.
- No Cloud Tasks dispatcher, worker runtime, or live IAM service account has been provisioned.
- Android `PersistentJobRepository` and `TaskOrchestrator` still need migration to these callables/private endpoints. Until then, the client-side orchestration path must not be considered production-ready.
- Emulator integration tests for queue races, expired leases, idempotency collisions, cancellation, and worker authorization are still required.
