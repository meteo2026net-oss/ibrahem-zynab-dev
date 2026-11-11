import { Injectable, signal, computed } from '@angular/core';

export interface AuthUser {
  name: string;
  email: string;
  role: string;
}

const KEY = 'iad-auth-v1';

@Injectable({ providedIn: 'root' })
export class AuthService {
  private readonly userSignal = signal<AuthUser | null>(this.load());

  readonly user = this.userSignal.asReadonly();
  readonly isLoggedIn = computed(() => !!this.userSignal());

  private load(): AuthUser | null {
    try {
      const raw = localStorage.getItem(KEY);
      return raw ? JSON.parse(raw) : null;
    } catch {
      return null;
    }
  }

  login(email: string, _password: string, name = 'أحمد'): boolean {
    if (!email.trim()) return false;
    const u: AuthUser = {
      name: name || email.split('@')[0],
      email: email.trim(),
      role: 'admin',
    };
    localStorage.setItem(KEY, JSON.stringify(u));
    this.userSignal.set(u);
    return true;
  }

  logout(): void {
    localStorage.removeItem(KEY);
    this.userSignal.set(null);
  }
}
