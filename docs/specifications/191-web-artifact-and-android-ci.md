# Production web artifact and Android build pipeline — implementation batch 191

Date: 2026-11-16

## Web delivery

- Extended Angular CI to upload the production build output as the `intelligent-app-designer-web-production` artifact.
- CI run 43832649829 completed successfully: 14/14 Angular tests passed, production build succeeded, and both the web production bundle and npm audit report were uploaded.
- The web bundle artifact is available from the run's Actions artifacts for 30 days.

## Android delivery pipeline

- Added a separate Android CI workflow using JDK 21, Android SDK 36.1, Gradle 9.3.1, a CI-only debug signing key, Android unit tests, debug APK assembly, and APK artifact upload.
- The first attempt failed because the Android SDK setup action requested the removed legacy `tools` package. The workflow was corrected to install only `platform-tools`.
- The next attempt reached Gradle but failed resolving the unused Foojay resolver settings plugin. Since no project toolchain declarations require that plugin and CI supplies JDK 21, the unused plugin was removed from settings.
- The latest Android workflow is rerunning after that correction. Android unit tests and APK creation are not yet verified.
- A debug APK is not a store-release artifact. A production AAB/APK still requires the owner's release signing keystore and credentials.

## Remaining release gates

- Resolve the npm audit findings (44 total at the last audit: 2 critical, 15 high, 25 moderate, 2 low).
- Complete Android build/tests and produce the intended release artifact.
- Implement trusted backend functions for protected Firestore operations and secrets.
- Configure Firebase Web API Key, project/database identifiers, and enabled authentication providers.
- Perform integration, security, accessibility, and end-to-end verification before production deployment.
