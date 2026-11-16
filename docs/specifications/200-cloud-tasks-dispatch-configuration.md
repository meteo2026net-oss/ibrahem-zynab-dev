# 200 — Cloud Tasks dispatch configuration and operational contract

## Implemented in source
The `createPersistentJob` callable now attempts to create a Cloud Tasks HTTP task after the Firestore transaction commits. The task has a deterministic name derived from the job UUID, so repeated calls using the same idempotency key do not create duplicate tasks. The request body contains only the job ID. The task uses an OIDC token minted for the configured worker service account and targets the configured Cloud Run worker URL.

The callable records one of:
- `ENQUEUED`: task created or already existed.
- `NOT_CONFIGURED`: required Cloud Tasks / worker settings are absent; the job remains queued.
- `FAILED`: task creation failed; the job remains queued and the failure is logged without exposing credentials.

The job listing callable now exposes `dispatchStatus`, `progressPercent`, and `progressMessage`.

## Required server environment
Configure these values on the Firebase Functions runtime:
- `CLOUD_TASKS_PROJECT_ID` (may fall back to `GCLOUD_PROJECT`)
- `CLOUD_TASKS_LOCATION`
- `CLOUD_TASKS_QUEUE`
- `JOB_WORKER_URL` — HTTPS URL of the authenticated Cloud Run worker
- `JOB_WORKER_SERVICE_ACCOUNT` — service account whose OIDC token Cloud Tasks attaches

The Functions runtime service account needs permission to create tasks in the selected queue (typically `roles/cloudtasks.enqueuer`). The Cloud Tasks service agent must be able to mint OIDC tokens for the worker service account (including the required service-account token creation permission). Grant Cloud Run Invoker to the worker service account, and do not make the worker publicly invokable.

## Important deployment boundary
This change implements dispatch only. It does not create or deploy the Cloud Tasks queue, Cloud Run service, service accounts, IAM bindings, or worker code. Until those infrastructure resources exist and `JOB_WORKER_URL` is configured, dispatch status will be `NOT_CONFIGURED` and jobs will remain queued. A task can be delivered only after a real worker endpoint exists.

## Failure recovery
A task creation failure is visible as `FAILED` and is logged with a non-secret error code. Repeating the callable with the same idempotency key retries task creation because the job identifier is stable. The scheduled `reconcilePersistentJobDispatch` function now checks up to 50 failed dispatch records each minute, retries queued jobs, and records the new dispatch status. It uses deterministic task names so ambiguous create-task responses are safe to retry. Persistent failures still require operational alerting and inspection.

## Validation still required
- Functions CI compilation after the dependency update.
- Emulator or integration tests for task creation, duplicate task names, missing configuration, Cloud Tasks API failures, scheduled reconciliation, and callable retries.
- A deployed staging queue and authenticated Cloud Run worker to validate OIDC and IAM end-to-end.
- Worker lease claim, progress, cancellation, and finish integration tests.
