# Firestore Emulator test harness — implementation batch 190

Date: 2026-11-16

## Implemented

- Added a dedicated Firestore Emulator test project under `firestore-tests/`.
- Added automated rules cases for:
  - authenticated owner create/read/update;
  - immutable `userId` ownership;
  - cross-user document and query isolation;
  - audit log read-only behavior;
  - client write denial for approvals, persistent jobs, checkpoints, and idempotency records;
  - client read/write denial for credential-bearing collections;
  - unauthenticated and unlisted collection denial.
- Added a dedicated GitHub Actions workflow, separate from Angular CI.
- Moved emulator configuration to the repository-root `firebase.json` because Firebase CLI rejects rules files outside the configured project directory.

## Verification status

- First emulator attempt failed before tests because the nested Firebase config pointed outside its project directory. This was a harness/configuration error, not a rules assertion failure.
- The configuration was corrected to use the root `firebase.json`; the obsolete nested emulator config was removed and the local test script was aligned with the root config.
- The corrected emulator workflow passed: 7/7 Firestore Emulator assertions succeeded on run 43832163309.
- Angular CI for the updated root Firebase configuration passed tests and production build.
- No production Firestore rules deployment was performed.

## Remaining

- Confirm all Emulator assertions pass.
- If failures occur, fix rules/test assumptions and rerun.
- Add additional cases for nested document paths, malformed/missing owner fields, and each protected collection.
- Keep production deploy blocked until security tests pass and the trusted backend replaces direct client access.
