# Signed Android release pipeline — implementation batch 195

Date: 2026-11-16

## Implemented

- Added a manual GitHub Actions workflow at `.github/workflows/android-release.yml`.
- It validates release keystore secrets before doing work, restores the upload keystore only in the runner's temporary directory, runs Android unit tests, builds a signed release AAB, and uploads the artifact.
- Firebase runtime settings are read from GitHub Actions Variables:
  - `FIREBASE_API_KEY`
  - `FIREBASE_APP_ID`
  - `FIREBASE_PROJECT_ID`
  - `FIREBASE_MESSAGING_SENDER_ID`
  - optional `FIREBASE_STORAGE_BUCKET`
  - optional `FIREBASE_DATABASE_URL`
  - `GOOGLE_WEB_CLIENT_ID`
- Release secrets required:
  - `KEYSTORE_BASE64`: base64-encoded upload keystore
  - `STORE_PASSWORD`
  - `KEY_PASSWORD`
- The workflow deliberately sets `VAULT_BACKEND_ENABLED=false` until the server backend and App Check have been deployed and verified.

## Important

- This workflow has not been run because the owner-provided release keystore and passwords are not present in GitHub Actions secrets.
- Do not upload the keystore to the repository or include passwords in workflow files.
- The AAB artifact is a release-signed package, but Play Store publication still requires owner review, Play Console configuration, app signing policy, privacy disclosures, and release approval.
- Firebase runtime integration still needs verification against the actual project.
