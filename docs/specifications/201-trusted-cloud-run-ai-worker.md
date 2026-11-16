# 201 — Trusted Cloud Run AI worker and deployment contract

## Delivered source components
- `worker/server.js`: private HTTP worker with `/health` and `/tasks`.
- `worker/package.json`: Node.js 22 runtime with exact top-level dependency versions, including the current `@google/genai` 3.3.0 SDK.
- `worker/.env.example`: non-secret runtime configuration template.
- `.github/workflows/worker-ci.yml`: JavaScript syntax validation workflow.
- Firebase Functions now persist generated output through `finishPersistentJob`, and `getPersistentJobResult` returns it only to the authenticated owner.
- Android repository exposes `getJobResult(jobId)` through the authenticated callable.
- Claim handling acknowledges already-finished tasks and asks Cloud Tasks to retry when a different live lease currently owns the job.

## Execution behavior
1. Cloud Tasks sends an OIDC-authenticated POST to the private Cloud Run `/tasks` endpoint.
2. Cloud Run IAM verifies the caller. The service must not allow unauthenticated invocation.
3. The worker claims the job using the private Firebase Functions endpoint.
4. The worker renews its lease every 20 seconds and reports progress.
5. The worker uses `@google/genai` 3.3.0 with the `enterprise: true` backend and Gemini Enterprise Agent Platform, authenticated through the Cloud Run service identity (no AI key is embedded in the app). The deprecated `@google-cloud/vertexai` SDK is not used.
6. The result text, model name, summary and terminal state are saved through the lease-guarded finish endpoint.
7. The owner retrieves the result through `getPersistentJobResult`.

This is an initial server-side prompt-to-text execution adapter. It does **not** yet implement the complete 14-center autonomous application-design workflow, file generation, code editing, GitHub commits, builds, tests, approvals orchestration, or deployment automation. Those require dedicated typed executors and integration tests; a model text response is not proof that an application has been built.

## Required Google Cloud setup
Use one project and one region consistently. The examples below use `PROJECT_ID` and `us-central1` placeholders; replace them with the actual project and region.

Enable the required APIs:
```sh
gcloud services enable run.googleapis.com cloudtasks.googleapis.com aiplatform.googleapis.com --project=PROJECT_ID
```

Create a queue with bounded retry/backoff:
```sh
gcloud tasks queues create app-designer-jobs --location=us-central1 --project=PROJECT_ID --max-attempts=8 --min-backoff=60s --max-backoff=600s --max-doublings=5
```

Deploy the worker from source (no Dockerfile required):
```sh
gcloud run deploy app-designer-worker --source=worker --region=us-central1 --project=PROJECT_ID --service-account=WORKER_RUNTIME_SA --no-allow-unauthenticated --timeout=900 --set-env-vars=GCP_PROJECT_ID=PROJECT_ID,FUNCTIONS_BASE_URL=https://us-central1-PROJECT_ID.cloudfunctions.net,VERTEX_AI_LOCATION=us-central1,VERTEX_AI_MODEL=gemini-3.8-flash
```

Set `JOB_WORKER_URL` to the deployed service URL plus `/tasks`; set `JOB_WORKER_AUDIENCE` to the Cloud Run service URL without the path. Set `JOB_WORKER_SERVICE_ACCOUNT` to the service account whose OIDC identity Cloud Tasks will attach. Configure the same values in `functions/.env` (never commit that file).

## IAM boundaries
- The Functions runtime identity needs Cloud Tasks enqueue permission on the selected queue.
- The Cloud Tasks service agent must be able to mint OIDC tokens for the selected task service account.
- The task service account needs Cloud Run Invoker on this worker service.
- The worker runtime service account needs Vertex AI User and Cloud Run Invoker on the private Firebase Functions services it calls.
- Grant only the minimum service-level roles required; do not make the worker or lease endpoints public.
- App Check remains required for client-callable functions; the worker uses IAM-authenticated private HTTP endpoints.

## Operational limits and known gaps
- Cloud Tasks queue, Cloud Run service, service accounts, IAM bindings, Firebase Functions and Vertex AI have not been provisioned or deployed by this source change.
- No real Google Cloud credentials are available in this repository workflow, so end-to-end cloud execution is not verified.
- Worker CI validates JavaScript syntax; it does not prove Vertex AI permissions, Cloud Run IAM, Cloud Tasks delivery, lease race behavior or model output quality.
- The worker currently returns text output only. It does not safely create files, edit a repository, run builds, publish applications or perform irreversible operations.
- A future execution engine must route typed job plans through policy checks, approval gates, isolated build sandboxes, artifact storage, tests and audit logging before enabling those operations.
- Add an npm lockfile and dependency audit for the worker before production rollout.
