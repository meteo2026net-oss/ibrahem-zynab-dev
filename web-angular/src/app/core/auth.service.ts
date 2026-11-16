import { HttpClient } from '@angular/common/http';
import { Injectable, computed, inject, signal } from '@angular/core';
import { firstValueFrom } from 'rxjs';

export interface AuthUser {
  uid: string;
  name: string;
  email: string;
  role: 'user';
}

interface FirebaseAuthResponse {
  localId: string;
  email: string;
  displayName?: string;
  idToken: string;
  refreshToken: string;
  expiresIn: string;
}

interface RuntimeAppConfig {
  firebaseApiKey?: string;
}

type RuntimeGlobal = typeof globalThis & { __APP_CONFIG__?: RuntimeAppConfig };

/**
 * Firebase Authentication REST integration.
 * The API key is public Firebase web configuration, not a server secret.
 * ID tokens are kept in memory only; no browser storage is trusted for identity.
 */
@Injectable({ providedIn: 'root' })
export class AuthService {
  private readonly http = inject(HttpClient);
  private readonly apiKey =
    (globalThis as RuntimeGlobal).__APP_CONFIG__?.firebaseApiKey?.trim() ?? '';
  private idToken: string | null = null;
  private refreshToken: string | null = null;
  private tokenExpiresAt = 0;
  private authGeneration = 0;
  private refreshInFlight: Promise<string | null> | null = null;

  private readonly userSignal = signal<AuthUser | null>(null);

  readonly user = this.userSignal.asReadonly();
  readonly isLoggedIn = computed(() => this.userSignal() !== null);
  readonly isConfigured = this.apiKey.length > 0;

  async login(email: string, password: string): Promise<boolean> {
    this.logout();
    if (!this.isConfigured || !email.trim() || !password) return false;

    try {
      const response = await firstValueFrom(
        this.http.post<FirebaseAuthResponse>(
          `https://identitytoolkit.googleapis.com/v1/accounts:signInWithPassword?key=${encodeURIComponent(this.apiKey)}`,
          { email: email.trim(), password, returnSecureToken: true }
        )
      );
      return this.acceptAuthResponse(response);
    } catch {
      this.logout();
      return false;
    }
  }

  async register(email: string, password: string): Promise<boolean> {
    this.logout();
    if (!this.isConfigured || !email.trim() || password.length < 6) return false;

    try {
      const response = await firstValueFrom(
        this.http.post<FirebaseAuthResponse>(
          `https://identitytoolkit.googleapis.com/v1/accounts:signUp?key=${encodeURIComponent(this.apiKey)}`,
          { email: email.trim(), password, returnSecureToken: true }
        )
      );
      return this.acceptAuthResponse(response);
    } catch {
      this.logout();
      return false;
    }
  }

  async sendEmailVerification(): Promise<boolean> {
    const token = await this.getValidIdToken();
    if (!token || !this.isConfigured) return false;
    try {
      await firstValueFrom(this.http.post(
        `https://identitytoolkit.googleapis.com/v1/accounts:sendOobCode?key=${encodeURIComponent(this.apiKey)}`,
        { requestType: 'VERIFY_EMAIL', idToken: token }
      ));
      return true;
    } catch {
      return false;
    }
  }

  async requestPasswordReset(email: string): Promise<boolean> {
    if (!this.isConfigured || !email.trim()) return false;
    try {
      await firstValueFrom(this.http.post(
        `https://identitytoolkit.googleapis.com/v1/accounts:sendOobCode?key=${encodeURIComponent(this.apiKey)}`,
        { requestType: 'PASSWORD_RESET', email: email.trim() }
      ));
      return true;
    } catch {
      return false;
    }
  }

  private acceptAuthResponse(response: FirebaseAuthResponse): boolean {
    const expirySeconds = Number(response.expiresIn);
    if (
      !response.idToken || !response.localId || !response.email || !response.refreshToken ||
      !Number.isFinite(expirySeconds) || expirySeconds <= 0
    ) {
      this.logout();
      return false;
    }
    this.authGeneration += 1;
    this.refreshInFlight = null;
    this.idToken = response.idToken;
    this.refreshToken = response.refreshToken;
    this.tokenExpiresAt = Date.now() + expirySeconds * 1000;
    this.userSignal.set({
      uid: response.localId,
      name: response.displayName?.trim() || response.email.split('@')[0],
      email: response.email,
      role: 'user',
    });
    return true;
  }

  getIdToken(): string | null {
    return this.idToken;
  }

  async getValidIdToken(): Promise<string | null> {
    if (!this.idToken || !this.refreshToken || !this.isConfigured) return null;
    if (Date.now() < this.tokenExpiresAt - 60_000) return this.idToken;
    if (this.refreshInFlight) return this.refreshInFlight;

    const expectedUid = this.userSignal()?.uid;
    const expectedGeneration = this.authGeneration;
    const refreshToken = this.refreshToken;
    if (!expectedUid) return null;

    let refreshPromise!: Promise<string | null>;
    refreshPromise = (async () => {
      try {
        const response = await firstValueFrom(
          this.http.post<{
            id_token: string;
            refresh_token: string;
            expires_in: string;
            user_id: string;
          }>(
            `https://securetoken.googleapis.com/v1/token?key=${encodeURIComponent(this.apiKey)}`,
            { grant_type: 'refresh_token', refresh_token: refreshToken }
          )
        );

        // A logout or a new sign-in may happen while the network request is pending.
        // Never let an older response overwrite a newer authentication session.
        if (expectedGeneration !== this.authGeneration) return null;

        const refreshedExpirySeconds = Number(response.expires_in);
        if (
          this.userSignal()?.uid !== expectedUid ||
          response.user_id !== expectedUid ||
          !response.id_token ||
          !response.refresh_token ||
          !Number.isFinite(refreshedExpirySeconds) ||
          refreshedExpirySeconds <= 0
        ) {
          this.logout();
          return null;
        }

        this.idToken = response.id_token;
        this.refreshToken = response.refresh_token;
        this.tokenExpiresAt = Date.now() + refreshedExpirySeconds * 1000;
        return this.idToken;
      } catch {
        if (expectedGeneration === this.authGeneration) this.logout();
        return null;
      } finally {
        if (this.refreshInFlight === refreshPromise) this.refreshInFlight = null;
      }
    })();

    this.refreshInFlight = refreshPromise;
    return refreshPromise;
  }

  logout(): void {
    this.authGeneration += 1;
    this.refreshInFlight = null;
    this.idToken = null;
    this.refreshToken = null;
    this.tokenExpiresAt = 0;
    this.userSignal.set(null);
  }
}
