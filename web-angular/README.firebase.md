# Firebase Web configuration

The browser configuration is loaded from `public/runtime-config.js` before Angular starts. Fill the public values from Firebase Console → Project settings → General → Your apps → Web app:

- `apiKey`, `authDomain`, `projectId`, `appId`, `messagingSenderId`
- `storageBucket` and `measurementId` are optional.
- `functionsRegion` must match the deployed callable region (defaults to `us-central1`).
- `appCheckSiteKey` is the reCAPTCHA v3 site key registered for this web app in Firebase App Check.

The page loads the Firebase compat App and App Check SDKs from Google's versioned CDN. The client initializes App Check with automatic token refresh and sends both the current Firebase ID token and App Check token on callable requests. Requests fail closed when configuration, authentication, SDK initialization, or App Check is unavailable.

## Firebase Console prerequisites

1. Enable the Email/Password provider in Authentication.
2. Register the production and development web domains in Authentication authorized domains.
3. Register a reCAPTCHA v3 provider in App Check for this web app and configure enforcement for Cloud Functions.
4. Deploy the callable functions to the configured region.
5. Configure API-key HTTP referrer restrictions for the actual hosting domains.

Only public web-app configuration belongs in this file. Never commit service-account JSON, private keys, refresh tokens, worker credentials, KMS material, or other server secrets. The existing REST Authentication flow remains in use; callable requests obtain its current ID token and an App Check token.

A successful local build does not prove that the production Firebase project, App Check provider, callable deployment, Firestore indexes/rules, Cloud Tasks queue, or worker are configured. End-to-end verification requires real project configuration and a deployed environment.
