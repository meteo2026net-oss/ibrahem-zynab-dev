import { Injectable, computed, signal } from '@angular/core';

export interface AuthUser {
  name: string;
  email: string;
  role: string;
}

/**
 * Authentication is deliberately fail-closed until a trusted server-backed
 * identity provider is configured. A browser localStorage value is not proof
 * of identity and must never grant an application role.
 */
@Injectable({ providedIn: 'root' })
export class AuthService {
  private readonly userSignal = signal<AuthUser | null>(null);

  readonly user = this.userSignal.asReadonly();
  readonly isLoggedIn = computed(() => this.userSignal() !== null);
  readonly isConfigured = false;

  login(_email: string, _password: string, _name?: string): boolean {
    // No local/demo authentication: only a verified provider may establish a session.
    this.userSignal.set(null);
    return false;
  }

  logout(): void {
    this.userSignal.set(null);
  }
}
