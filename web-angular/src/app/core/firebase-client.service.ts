import { Injectable, inject } from '@angular/core';
import { AuthService } from './auth.service';

interface FirebasePublicConfig {
  apiKey?: string;
  authDomain?: string;
  projectId?: string;
  appId?: string;
  messagingSenderId?: string;
  storageBucket?: string;
  measurementId?: string;
  functionsRegion?: string;
  appCheckSiteKey?: string;
}

interface FirebaseCompatApp {
  name: string;
}

interface FirebaseCompatAppCheck {
  activate(siteKey: string, isTokenAutoRefreshEnabled?: boolean): Promise<void> | void;
  getToken(forceRefresh?: boolean): Promise<{ token: string }>;
}

interface FirebaseCompatNamespace {
  apps: FirebaseCompatApp[];
  initializeApp(config: Record<string, string>): FirebaseCompatApp;
  appCheck(app?: FirebaseCompatApp): FirebaseCompatAppCheck;
}

type FirebaseGlobal = typeof globalThis & {
  __APP_CONFIG__?: FirebasePublicConfig;
  firebase?: FirebaseCompatNamespace;
};

export class FirebaseCallableError extends Error {
  constructor(
    message: string,
    readonly code: string,
    readonly details?: unknown,
  ) {
    super(message);
    this.name = 'FirebaseCallableError';
  }
}

/**
 * Firebase Web SDK bootstrap and authenticated callable transport.
 * App Check is fail-closed: no callable is sent unless a real App Check token
 * and a current Firebase Authentication ID token are both available.
 */
@Injectable({ providedIn: 'root' })
export class FirebaseClientService {
  private readonly auth = inject(AuthService);
  private readonly config = (globalThis as FirebaseGlobal).__APP_CONFIG__ ?? {};
  private ready: Promise<FirebaseCompatAppCheck> | null = null;

  get isConfigured(): boolean {
    return Boolean(
      this.config.apiKey?.trim() &&
      this.config.authDomain?.trim() &&
      this.config.projectId?.trim() &&
      this.config.appId?.trim() &&
      this.config.appCheckSiteKey?.trim()
    );
  }

  private initialize(): Promise<FirebaseCompatAppCheck> {
    if (this.ready) return this.ready;
    this.ready = (async () => {
      if (!this.isConfigured) {
        throw new Error('Firebase Web configuration or App Check site key is missing.');
      }
      const sdk = (globalThis as FirebaseGlobal).firebase;
      if (!sdk) throw new Error('Firebase Web SDK failed to load.');

      const app = sdk.apps.find((item) => item.name === '[DEFAULT]') ??
        sdk.initializeApp({
          apiKey: this.config.apiKey!,
          authDomain: this.config.authDomain!,
          projectId: this.config.projectId!,
          appId: this.config.appId!,
          messagingSenderId: this.config.messagingSenderId ?? '',
          ...(this.config.storageBucket ? { storageBucket: this.config.storageBucket } : {}),
          ...(this.config.measurementId ? { measurementId: this.config.measurementId } : {}),
        });

      const appCheck = sdk.appCheck(app);
      await appCheck.activate(this.config.appCheckSiteKey!, true);
      return appCheck;
    })().catch((error: unknown) => {
      this.ready = null;
      throw error;
    });
    return this.ready;
  }

  async call<TRequest extends Record<string, unknown>, TResponse>(
    functionName: string,
    data: TRequest,
  ): Promise<TResponse> {
    if (!/^[A-Za-z][A-Za-z0-9_]{0,99}$/.test(functionName)) {
      throw new Error('Invalid Firebase callable function name.');
    }
    const [appCheck, idToken] = await Promise.all([
      this.initialize(),
      this.auth.getValidIdToken(),
    ]);
    if (!idToken) throw new FirebaseCallableError('Authentication is required.', 'unauthenticated');

    const appCheckResult = await appCheck.getToken();
    if (!appCheckResult.token) {
      throw new FirebaseCallableError('App Check token is unavailable.', 'failed-precondition');
    }

    const region = this.config.functionsRegion?.trim() || 'us-central1';
    const projectId = this.config.projectId!;
    const response = await fetch(
      `https://${region}-${projectId}.cloudfunctions.net/${functionName}`,
      {
        method: 'POST',
        headers: {
          'Content-Type': 'application/json',
          Authorization: `Bearer ${idToken}`,
          'X-Firebase-AppCheck': appCheckResult.token,
        },
        body: JSON.stringify({ data }),
      },
    );

    const payload = await response.json().catch(() => null) as
      | { result?: TResponse; error?: { message?: string; status?: string; details?: unknown } }
      | null;
    if (!response.ok || !payload || payload.error) {
      const code = payload?.error?.status?.toLowerCase() || `http-${response.status}`;
      const message = payload?.error?.message || `Firebase callable failed (${response.status}).`;
      throw new FirebaseCallableError(message, code, payload?.error?.details);
    }
    if (!Object.prototype.hasOwnProperty.call(payload, 'result')) {
      throw new FirebaseCallableError('Firebase callable returned an invalid response.', 'internal');
    }
    return payload.result as TResponse;
  }
}
