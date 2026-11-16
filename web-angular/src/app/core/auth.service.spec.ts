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

  it('creates an ordinary Firebase user account', async () => {
    const result = service.register('new-person@example.com', 'strong-pass-123');
    const request = http.expectOne((req) => req.url.includes('accounts:signUp'));
    expect(request.request.body).toEqual({
      email: 'new-person@example.com',
      password: 'strong-pass-123',
      returnSecureToken: true,
    });
    request.flush({
      localId: 'new-user-1',
      email: 'new-person@example.com',
      idToken: 'new-user-token',
      refreshToken: 'new-refresh-token',
      expiresIn: '3600',
    });

    expect(await result).toBeTrue();
    expect(service.user()?.role).toBe('user');
    expect(service.getIdToken()).toBe('new-user-token');
  });

  it('sends an email verification request using the authenticated ID token', async () => {
    const login = service.login('person@example.com', 'correct-password');
    http.expectOne((req) => req.url.includes('accounts:signInWithPassword')).flush({
      localId: 'firebase-user-1',
      email: 'person@example.com',
      idToken: 'verified-id-token',
      refreshToken: 'refresh-token',
      expiresIn: '3600',
    });
    expect(await login).toBeTrue();

    const result = service.sendEmailVerification();
    await Promise.resolve();
    const request = http.expectOne((req) => req.url.includes('accounts:sendOobCode'));
    expect(request.request.body).toEqual({
      requestType: 'VERIFY_EMAIL',
      idToken: 'verified-id-token',
    });
    request.flush({ email: 'person@example.com' });
    expect(await result).toBeTrue();
  });

  it('requests password reset without exposing account existence in the UI contract', async () => {
    const result = service.requestPasswordReset('person@example.com');
    const request = http.expectOne((req) => req.url.includes('accounts:sendOobCode'));
    expect(request.request.body).toEqual({
      requestType: 'PASSWORD_RESET',
      email: 'person@example.com',
    });
    request.flush({ email: 'person@example.com' });
    expect(await result).toBeTrue();
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
    expect(service.isLoggedIn()).toBeFalse();
    expect(service.getIdToken()).toBeNull();
  });

  it('refreshes an expiring Firebase ID token without browser storage', async () => {
    const clock = spyOn(Date, 'now').and.returnValue(0);
    const signIn = service.login('person@example.com', 'correct-password');
    const signInRequest = http.expectOne((req) => req.url.includes('accounts:signInWithPassword'));
    signInRequest.flush({
      localId: 'firebase-user-1',
      email: 'person@example.com',
      idToken: 'first-id-token',
      refreshToken: 'first-refresh-token',
      expiresIn: '3600',
    });
    expect(await signIn).toBeTrue();

    clock.and.returnValue(3_550_000);
    const refreshed = service.getValidIdToken();
    const refreshRequest = http.expectOne((req) => req.url.includes('securetoken.googleapis.com/v1/token'));
    expect(refreshRequest.request.body).toEqual({
      grant_type: 'refresh_token',
      refresh_token: 'first-refresh-token',
    });
    refreshRequest.flush({
      id_token: 'renewed-id-token',
      refresh_token: 'renewed-refresh-token',
      expires_in: '3600',
      user_id: 'firebase-user-1',
    });

    expect(await refreshed).toBe('renewed-id-token');
    expect(service.getIdToken()).toBe('renewed-id-token');
  });

  it('clears the in-memory ID token on logout', async () => {
    service.logout();
    expect(service.getIdToken()).toBeNull();
    expect(service.isLoggedIn()).toBeFalse();
  });
});
