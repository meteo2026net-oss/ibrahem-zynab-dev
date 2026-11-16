import { HttpClient } from '@angular/common/http';
import { Injectable, computed, inject, signal } from '@angular/core';
import { firstValueFrom } from 'rxjs';

export interface AuthUser {
  name: string;
  email: string;
  role: 'user';
}

interface FirebasePasswordSignInResponse {
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

interface RuntimeGlobal extends typeof globalThis {
  __APP_CONFIG__?: RuntimeAppConfig;
}

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

  private readonly userSignal = signal<AuthUser | null>(null);

  readonly user = this.userSignal.asReadonly();
  readonly isLoggedIn = computed(() => this.userSignal() !== null);
  readonly isConfigured = this.apiKey.length > 0;

  async login(email: string, password: string, _name?: string): Promise<boolean> {
    this.logout();
    if (!this.isConfigured || !email.trim() || !password) return false;

    try {
      const response = await firstValueFrom(
        this.http.post<FirebasePasswordSignInResponse>(
          `https://identitytoolkit.googleapis.com/v1/accounts:signInWithPassword?key=${encodeURIComponent(this.apiKey)}`,
          { email: email.trim(), password, returnSecureToken: true }
        )
      );

      if (!response.idToken || !response.localId || !response.email) return false;

      this.idToken = response.idToken;
      this.userSignal.set({
        name: response.displayName?.trim() || response.email.split('@')[0],
        email: response.email,
        role: 'user',
      });
      return true;
    } catch {
      this.logout();
      return false;
    }
  }

  getIdToken(): string | null {
    return this.idToken;
  }

  logout(): void {
    this.idToken = null;
    this.userSignal.set(null);
  }
}
