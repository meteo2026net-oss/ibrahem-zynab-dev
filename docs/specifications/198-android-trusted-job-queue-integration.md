# Android trusted job queue integration — implementation batch 198

Date: 2026-11-16

## Changes applied

- Android job creation now calls the authenticated, App Check-enforced `createPersistentJob` callable instead of writing directly to Firestore or scheduling an untrusted local WorkManager worker.
- Android cancellation now calls `cancelPersistentJob`; pause/resume/retry remain explicitly unavailable until supported by the trusted worker.
- The client returns a clear queued/pending message: tasks are stored server-side but are not executed until the trusted worker and dispatcher are deployed.
- The Functions job record now includes `projectName`, `userPrompt`, and `progressPercent` display metadata while preserving the backend `prompt` and `progress` fields.
- Android parsing now recognizes backend uppercase states, maps `SUCCEEDED` to the app's completed state, and accepts Firestore Timestamp values.

## Verification

- Firebase Functions CI run 43864577107: TypeScript compile passed.
- Android CI run 43864553883: unit tests passed, debug APK assembled, and artifact 14224803870 uploaded.
- Firestore rules CI run 43863860808: 8/8 tests passed after backup collection hardening.

## Explicitly not complete

- There is still no deployed Cloud Tasks dispatcher or trusted worker runtime. Jobs remain queued and will not execute.
- Firebase Functions and Cloud KMS have not been deployed to a production project; IAM, App Check enforcement and real account integration still need live verification.
- Android release AAB is not built or signed because the owner's signing secrets are not configured.
- Web build artifact is not a deployed website. npm audit reports 44 vulnerabilities (2 critical, 15 high, 25 moderate, 2 low); major Angular dependency migration remains open.
