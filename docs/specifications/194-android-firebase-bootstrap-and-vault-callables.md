# Android Firebase runtime bootstrap and callable Secrets Vault — implementation batch 194

Date: 2026-11-16

## Implemented

- Android can initialize Firebase from explicit build-time values when a `google-services.json` file is not supplied.
- Build values can be read from Gradle properties, environment variables, or a local ignored `.env` file:
  - `FIREBASE_API_KEY`
  - `FIREBASE_APP_ID`
  - `FIREBASE_PROJECT_ID`
  - `FIREBASE_MESSAGING_SENDER_ID`
  - optional `FIREBASE_STORAGE_BUCKET`
  - optional `FIREBASE_DATABASE_URL`
  - `GOOGLE_WEB_CLIENT_ID`
  - `VAULT_BACKEND_ENABLED` (defaults to false)
- If the required Firebase values are missing, Android displays a setup-required screen instead of calling Firebase APIs and crashing.
- App Check initialization now runs only after Firebase initialization succeeds.
- Added the Firebase Functions Android SDK and connected the Secrets Vault repository to:
  - `putVaultSecret`
  - `listVaultSecrets`
  - `deleteVaultSecret`
- Secret plaintext is sent only to the authenticated callable over TLS and encrypted server-side with Cloud KMS. The Android model never receives ciphertext or decrypted secret values.
- The Vault UI's add action is enabled only when `VAULT_BACKEND_ENABLED=true`; leave it false until Functions are deployed and App Check/KMS are configured.

## Verification and remaining gates

- The first Android build exposed empty BuildConfig fields when optional environment values were blank. The implementation now uses the existing Secrets Gradle Plugin with explicit non-secret CONFIGURE_* sentinels, and runtime initialization rejects those sentinels. The corrected Android CI run 43836075590 is still executing; unit tests and APK assembly must both pass before this batch is verified.
- The earlier Android run 43833854909 passed unit tests and produced a debug APK artifact, but it predates this Functions SDK integration.
- No live Firebase runtime credentials have been supplied to this repository. Firebase sign-in, Functions, and App Check cannot be validated against the actual production project yet.
- The Cloud Functions code has passed TypeScript CI, but no cloud deployment or KMS runtime test has been performed.
- Production signing and AAB generation remain separate release gates.
