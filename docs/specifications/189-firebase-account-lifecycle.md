# Firebase account lifecycle — implementation batch 189

Date: 2026-11-16

## Implemented

- Added Firebase Authentication email/password account creation through `accounts:signUp`.
- Added email verification delivery through `accounts:sendOobCode` with `VERIFY_EMAIL`.
- Added password reset email requests through `accounts:sendOobCode` with `PASSWORD_RESET`.
- Updated the Angular login screen to switch between sign-in, registration, and password-reset modes.
- Kept account role fixed to `user`; no browser-controlled admin role is accepted.
- Added service tests for registration, verification email request, and password reset request.

## Security and configuration notes

- Firebase Email/Password provider must be enabled in the Firebase Console.
- The Firebase Web API Key must be configured in `web-angular/public/runtime-config.js`.
- Email verification is requested on registration, but the current client flow does not yet enforce verified-email status on every login or Firestore rule. This remains a required follow-up.
- Password reset UI uses a generic response message so the interface does not reveal whether an email address is registered.
- Tokens remain in memory and are not persisted to browser storage.

## Verification

CI is triggered for each Angular source/test update. The final CI run for the latest commit must be checked before treating this batch as verified. Firebase Console configuration and live email delivery have not been exercised from this repository workflow.
