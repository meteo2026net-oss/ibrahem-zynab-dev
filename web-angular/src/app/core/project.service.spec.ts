import { TestBed } from '@angular/core/testing';
import { ProjectService } from './project.service';

describe('ProjectService honest local workflow behavior', () => {
  let service: ProjectService;

  beforeEach(() => {
    localStorage.clear();
    TestBed.configureTestingModule({});
    service = TestBed.inject(ProjectService);
  });

  afterEach(() => {
    TestBed.resetTestingModule();
    localStorage.clear();
  });

  it('starts with no claimed build, test, or release evidence', () => {
    expect(service.lastApk().ready).toBeFalse();
    expect(service.tests().every((test) => test.result === 'pending')).toBeTrue();
    expect(service.release().checklist.every((item) => !item.done)).toBeTrue();
  });

  it('creates draft steps from the supplied natural-language description', () => {
    service.setFunctionNatural('تحقق من تسجيل الدخول\nأضف المنتج للسلة\nعند النجاح اعرض رسالة\nعند الفشل اعرض الخطأ');
    service.convertNaturalToSteps();

    const steps = service.functionSteps();
    expect(steps.length).toBe(4);
    expect(steps[0].kind).toBe('check');
    expect(steps[1].kind).toBe('action');
    expect(steps[2].kind).toBe('success');
    expect(steps[3].kind).toBe('error');
    expect(steps.every((step) => step.detail.includes('مسودة'))).toBeTrue();
  });

  it('does not generate random pass/fail test outcomes', () => {
    const before = service.tests().map((test) => test.result);
    service.runAllPendingTests();
    expect(service.tests().map((test) => test.result)).toEqual(before);
  });

  it('does not report a build artifact as ready without a build provider', () => {
    service.simulateBuild();
    expect(service.lastApk().ready).toBeFalse();
    expect(service.buildLog().join(' ')).toContain('لم يتم إنشاء أي حزمة');
  });

  it('does not mark a local checklist as a deployed release', () => {
    expect(service.publishRelease()).toBeFalse();
    expect(service.release().lastPublished).toBeUndefined();
  });

  it('does not advance queued agents without an orchestration backend', () => {
    const before = service.agentTasks().map((task) => task.status);
    service.runQueuedAgents();
    expect(service.agentTasks().map((task) => task.status)).toEqual(before);
  });

  it('recovers safely from malformed persisted JSON', () => {
    localStorage.setItem('iad-app-state-v1', '{not-json');
    TestBed.resetTestingModule();
    TestBed.configureTestingModule({});
    service = TestBed.inject(ProjectService);

    expect(service.projects().length).toBeGreaterThan(0);
    expect(service.activeProject()).toBeDefined();
    expect(service.requirements().length).toBeGreaterThan(0);
  });

  it('normalizes invalid persisted collections and active project references', () => {
    localStorage.setItem('iad-app-state-v1', JSON.stringify({
      projects: null,
      activeProjectId: 'missing-project',
      requirements: null,
      tests: 'not-an-array',
      agentTasks: null,
    }));
    TestBed.resetTestingModule();
    TestBed.configureTestingModule({});
    service = TestBed.inject(ProjectService);

    expect(Array.isArray(service.projects())).toBeTrue();
    expect(service.activeProjectId()).toBe(service.projects()[0].id);
    expect(Array.isArray(service.requirements())).toBeTrue();
    expect(Array.isArray(service.tests())).toBeTrue();
    expect(Array.isArray(service.agentTasks())).toBeTrue();
  });

  it('ignores attempts to select a project that does not exist', () => {
    const activeId = service.activeProjectId();
    service.selectProject('missing-project');
    expect(service.activeProjectId()).toBe(activeId);
  });

  it('restores a default project when persisted project list is empty', () => {
    localStorage.setItem('iad-app-state-v1', JSON.stringify({
      projects: [],
      activeProjectId: 'missing-project',
    }));
    TestBed.resetTestingModule();
    TestBed.configureTestingModule({});
    service = TestBed.inject(ProjectService);

    expect(service.projects().length).toBeGreaterThan(0);
    expect(service.activeProject()).toBeDefined();
    expect(service.activeProjectId()).toBe(service.projects()[0].id);
  });
});
