import { TestBed } from '@angular/core/testing';
import { provideHttpClient } from '@angular/common/http';
import { HttpTestingController, provideHttpClientTesting } from '@angular/common/http/testing';
import { AuthService } from './auth.service';

describe('AuthService Firebase Authentication', () => {
  let service: AuthService;
  let http: HttpTestingController;

  beforeEach(() => {
    Object.defineProperty(globalThis, '__APP_CONFIG__', {
      configurable: true,
      value: { firebaseApiKey: 'test-public-firebase-api-key' },
    });
    TestBed.configureTestingModule({
      providers: [provideHttpClient(), provideHttpClientTesting()],
    });
    service = TestBed.inject(AuthService);
    http = TestBed.inject(HttpTestingController);
  });

  afterEach(() => {
    http.verify();
    service.logout();
    TestBed.resetTestingModule();
    delete (globalThis as typeof globalThis & { __APP_CONFIG__?: unknown }).__APP_CONFIG__;
  });

  it('authenticates through Firebase and grants only the ordinary user role', async () => {
    const result = service.login('person@example.com', 'correct-password');
    const request = http.expectOne((req) =>
      req.url.startsWith('https://identitytoolkit.googleapis.com/v1/accounts:signInWithPassword')
    );
    expect(request.request.method).toBe('POST');
    expect(request.request.body).toEqual({
      email: 'person@example.com',
      password: 'correct-password',
      returnSecureToken: true,
    });
    request.flush({
      localId: 'firebase-user-1',
      email: 'person@example.com',
      displayName: 'Person',
      idToken: 'verified-id-token',
      refreshToken: 'refresh-token',
      expiresIn: '3600',
    });

    expect(await result).toBeTrue();
    expect(service.isLoggedIn()).toBeTrue();
    expect(service.user()?.role).toBe('user');
    expect(service.getIdToken()).toBe('verified-id-token');
  });

  it('clears authentication state when Firebase rejects credentials', async () => {
    const result = service.login('person@example.com', 'incorrect-password');
    const request = http.expectOne((req) => req.url.includes('accounts:signInWithPassword'));
    request.flush({ error: { message: 'INVALID_PASSWORD' } }, { status: 400, statusText: 'Bad Request' });

    expect(await result).toBeFalse();
    expect(service.isLoggedIn()).toBeFalse();
    expect(service.getIdToken()).toBeNull();
  });

  it('does not restore an untrusted localStorage session', () => {
    localStorage.setItem('iad-auth-v1', JSON.stringify({
      name: 'Demo admin',
      email: 'admin@example.com',
      role: 'admin',
    }));
    const fresh = new AuthService();
    expect(fresh.isLoggedIn()).toBeFalse();
  });

  it('clears the in-memory ID token on logout', async () => {
    service.logout();
    expect(service.getIdToken()).toBeNull();
    expect(service.isLoggedIn()).toBeFalse();
  });
});
