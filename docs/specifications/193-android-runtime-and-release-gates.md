# Android runtime and release configuration gates — implementation batch 193

Date: 2026-11-16

## Build pipeline status

- Android CI run 43833854909 passed the Android unit-test task and is currently assembling the debug APK.
- The pipeline creates an ephemeral CI-only debug keystore. This is only for testing and local installation, not Play Store or production release signing.
- The application compiles without a checked-in `google-services.json` because Google sign-in now reads the optional `GOOGLE_WEB_CLIENT_ID` BuildConfig value and shows a configuration message when it is absent.

## Firebase runtime configuration is still missing

The repository's Android Firebase XML currently contains only a named Firestore database ID and an App Check site key. It does not provide the Firebase app ID, API key, project ID, or messaging sender ID needed to initialize the Firebase default app. The Android build therefore being successful does not prove that Firebase Authentication, Firestore, or callable Functions can connect at runtime.

Required owner-supplied configuration:
- the correct `google-services.json` for the production Android Firebase app;
- the matching OAuth web client ID (configure Gradle property or environment variable `GOOGLE_WEB_CLIENT_ID`);
- matching package name and SHA-1/SHA-256 signing fingerprints for Google sign-in;
- production App Check provider configuration.

Do not commit service-account credentials, production signing keys, or plaintext environment files.

## Release signing gate

The Android workflow generates only an ephemeral debug signing key. A Play-ready release requires the owner's persistent upload/release keystore and protected CI secrets:
- `KEYSTORE_PATH` (or a secure decoded keystore artifact),
- `STORE_PASSWORD`,
- `KEY_PASSWORD`.

The release signing configuration is present in Gradle but has not been tested with a production keystore. No release AAB is currently available.

## Other Android hardening still required

- Android Auto Backup has now been disabled (`allowBackup=false`) and backup-rule manifest references removed in commit 7f35ab67407ae88a681f0bacdfedbad317b62475; the resulting manifest change is pending CI re-verification.
- Integrate the callable Functions in the mobile repository before enabling the Secrets Vault UI.
- Move orchestration, job leases, heartbeats, approvals, idempotency, and external side effects to trusted server functions.
- Run end-to-end tests on a real configured Firebase project and supported Android devices.
