import { TestBed } from '@angular/core/testing';
import { AuthService } from './auth.service';
import { FirebaseClientService } from './firebase-client.service';

describe('FirebaseClientService', () => {
  const app = { name: '[DEFAULT]' };
  let activate: jasmine.Spy;
  let getToken: jasmine.Spy;
  let fetchSpy: jasmine.Spy;

  beforeEach(() => {
    Object.defineProperty(globalThis, '__APP_CONFIG__', {
      configurable: true,
      value: {
        apiKey: 'public-api-key',
        authDomain: 'sample.firebaseapp.com',
        projectId: 'sample-project',
        appId: '1:123:web:abc',
        messagingSenderId: '123',
        functionsRegion: 'europe-west2',
        appCheckSiteKey: 'public-recaptcha-site-key',
      },
    });
    activate = jasmine.createSpy('activate').and.resolveTo();
    getToken = jasmine.createSpy('getToken').and.resolveTo({ token: 'app-check-token' });
    (globalThis as typeof globalThis & { firebase?: unknown }).firebase = {
      apps: [app],
      initializeApp: jasmine.createSpy('initializeApp').and.returnValue(app),
      appCheck: jasmine.createSpy('appCheck').and.returnValue({ activate, getToken }),
    };
    fetchSpy = spyOn(globalThis, 'fetch').and.resolveTo(new Response(
      JSON.stringify({ result: { projectId: 'project-1' } }),
      { status: 200, headers: { 'Content-Type': 'application/json' } },
    ));
    TestBed.configureTestingModule({
      providers: [
        FirebaseClientService,
        { provide: AuthService, useValue: { getValidIdToken: () => Promise.resolve('firebase-id-token') } },
      ],
    });
  });

  afterEach(() => {
    TestBed.resetTestingModule();
    delete (globalThis as typeof globalThis & { __APP_CONFIG__?: unknown }).__APP_CONFIG__;
    delete (globalThis as typeof globalThis & { firebase?: unknown }).firebase;
  });

  it('initializes App Check and sends both identity tokens to the regional callable', async () => {
    const service = TestBed.inject(FirebaseClientService);
    const result = await service.call<{ name: string }, { projectId: string }>(
      'createProject',
      { name: 'Demo' },
    );

    expect(result.projectId).toBe('project-1');
    expect(activate).toHaveBeenCalledWith('public-recaptcha-site-key', true);
    expect(getToken).toHaveBeenCalled();
    const [url, options] = fetchSpy.calls.mostRecent().args;
    expect(url).toBe('https://europe-west2-sample-project.cloudfunctions.net/createProject');
    const headers = options?.headers as Record<string, string>;
    expect(headers.Authorization).toBe('Bearer firebase-id-token');
    expect(headers['X-Firebase-AppCheck']).toBe('app-check-token');
    expect(JSON.parse(String(options?.body))).toEqual({ data: { name: 'Demo' } });
  });

  it('fails closed when App Check cannot provide a token', async () => {
    getToken.and.resolveTo({ token: '' });
    const service = TestBed.inject(FirebaseClientService);
    await expectAsync(service.call('listProjects', {})).toBeRejected();
    expect(fetchSpy).not.toHaveBeenCalled();
  });

  it('rejects malformed callable function names before making a request', async () => {
    const service = TestBed.inject(FirebaseClientService);
    await expectAsync(service.call('../createProject', {})).toBeRejected();
    expect(fetchSpy).not.toHaveBeenCalled();
  });
});
