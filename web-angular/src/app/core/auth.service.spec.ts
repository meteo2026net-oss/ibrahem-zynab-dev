import { TestBed } from '@angular/core/testing';
import { AuthService } from './auth.service';

describe('AuthService fail-closed authentication', () => {
  let service: AuthService;

  beforeEach(() => {
    localStorage.clear();
    TestBed.configureTestingModule({});
    service = TestBed.inject(AuthService);
  });

  afterEach(() => {
    TestBed.resetTestingModule();
    localStorage.clear();
  });

  it('does not treat a local or demo credential as an authenticated session', () => {
    expect(service.login('person@example.com', 'any-password')).toBeFalse();
    expect(service.isLoggedIn()).toBeFalse();
    expect(service.user()).toBeNull();
  });

  it('does not restore an untrusted localStorage session on service initialization', () => {
    TestBed.resetTestingModule();
    localStorage.setItem('iad-auth-v1', JSON.stringify({
      name: 'Demo admin',
      email: 'admin@example.com',
      role: 'admin',
    }));

    TestBed.configureTestingModule({});
    const fresh = TestBed.inject(AuthService);
    expect(fresh.isLoggedIn()).toBeFalse();
    expect(fresh.user()).toBeNull();
  });

  it('clears the in-memory session on logout', () => {
    service.logout();
    expect(service.isLoggedIn()).toBeFalse();
  });
});
