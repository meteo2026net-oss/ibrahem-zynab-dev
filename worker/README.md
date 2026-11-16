# Trusted job worker

This is the private Cloud Run worker for persistent AI design jobs. It is not a public API.

## Runtime contract

- Cloud Tasks invokes `POST /tasks` using an OIDC identity.
- Cloud Run IAM must restrict invocation to the configured Cloud Tasks service account.
- The worker claims a Firestore-backed lease before processing a job.
- Lease renewal checks cancellation requests; a lost lease prevents the worker from publishing a result.
- The model is instructed to return a JSON project package:
  ```json
  {
    "summary": "Short project summary",
    "files": [
      { "path": "README.md", "content": "..." },
      { "path": "src/main.ts", "content": "..." }
    ]
  }
  ```
- `project-artifacts.js` rejects invalid JSON, absolute/traversal/hidden paths, credential-like filenames, duplicate paths, more than 40 files, files larger than 16 KiB, and packages larger than 160 KiB.
- Firebase Functions independently validates the files before storing them in the persistent job document. The authenticated owner retrieves them through `getPersistentJobResult`.

## Configuration

Copy `.env.example` values into the Cloud Run service configuration. The worker uses the Cloud Run service identity for Google API access; do not place service-account keys or provider secrets in this directory.

Required deployment configuration:
- `GCP_PROJECT_ID`
- `VERTEX_AI_LOCATION`
- `VERTEX_AI_MODEL`
- `FUNCTIONS_BASE_URL`
- Cloud Run IAM invoker binding for the Cloud Tasks OIDC service account
- Cloud Tasks queue and dispatcher environment variables in Firebase Functions

## Local validation

Run with Node.js 22:

```sh
npm install
npm run check
npm test
```

Local validation does not deploy Cloud Run, Firebase Functions, Cloud Tasks, IAM, or KMS. A successful test run is not evidence of a production deployment.
