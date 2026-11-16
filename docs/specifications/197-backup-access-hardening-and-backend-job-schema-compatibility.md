# Backup access hardening and trusted job schema compatibility — implementation batch 197

Date: 2026-11-16

## Changes applied

- Removed `project_backups` from every client-managed Firestore allow-list. Backup payload documents are now inaccessible to mobile/web clients until a trusted encrypted backup service is implemented.
- Added an Emulator test that seeds a backup document as an administrator and verifies the owner cannot read, forge, or delete it.
- Updated Android job deserialization to recognize backend status values (`QUEUED`, `RUNNING`, `SUCCEEDED`, `FAILED`, `CANCELLED`, and approval/retry states), while retaining compatibility with the app's lowercase enum IDs.
- Added support for Firestore `Timestamp` values and the backend's `prompt` / `progress` field names.
- Added a unit test for status and timestamp compatibility.

## Verification evidence

- Firestore Emulator CI run 43863860808: **8 tests passed, 0 failed**.
- Angular CI run 43863841528: unit tests and production build passed; web production artifact uploaded.
- Android CI run 43847995780: Android unit tests and debug APK build/upload passed for the preceding security batch. CI for this workpaper's Android parser change is triggered by the new commit and must be checked separately.
- npm audit in web CI still reports 44 vulnerabilities: 2 critical, 15 high, 25 moderate, 2 low. Most fixes require a major Angular toolchain update; no blind force upgrade was applied.

## Remaining constraints

- The trusted worker and dispatcher are not deployed. Android client-side job execution remains deliberately disabled.
- Production Firebase, Cloud KMS and IAM configuration are not verified/deployed.
- Signed Android release still requires owner-provided keystore secrets. The debug APK is not a production release.
- Web artifact is a production build artifact, not evidence of deployment to a hosting provider.
