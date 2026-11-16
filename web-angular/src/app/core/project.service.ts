import { Injectable, signal, computed, effect } from '@angular/core';
import { AuthService } from './auth.service';
import {
  AppState,
  Project,
  Requirement,
  Platform,
  TestResult,
  FunctionStep,
  AppSettings,
} from './models';

const STORAGE_KEY = 'iad-app-state-v1';

function storageKeyForUser(userId: string): string {
  return `${STORAGE_KEY}:${userId}`;
}

function defaultState(): AppState {
  return {
    projects: [
      {
        id: 'p1',
        name: 'متجري',
        description: 'تطبيق تجارة إلكترونية — منتجات، سلة، طلبات، لوحة بائع',
        status: 'active',
        phase: 1,
        progress: 18,
        platforms: ['Android', 'Web'],
        updatedAt: 'اليوم',
      },
      {
        id: 'p2',
        name: 'نظام الحجوزات',
        description: 'حجز مواعيد عيادات مع تذكيرات',
        status: 'draft',
        phase: 0,
        progress: 5,
        platforms: ['Web'],
        updatedAt: 'أمس',
      },
      {
        id: 'p3',
        name: 'تطبيق التوصيل',
        description: 'تتبع طلبات التوصيل للسائقين',
        status: 'archived',
        phase: 4,
        progress: 42,
        platforms: ['Android'],
        updatedAt: 'قبل أسبوع',
      },
    ],
    activeProjectId: 'p1',
    ideaText:
      'أريد تطبيق متجر إلكتروني بسيط. الزبون يتصفح المنتجات حسب التصنيف، يشاهد التفاصيل والسعر، يضيف للسلة، ثم يطلب بعد تسجيل الدخول. البائع يدير المنتجات ويرى الطلبات ويغيّر حالتها.',
    requirements: [
      { id: 'R-01', text: 'تصفح قائمة المنتجات', priority: 'high', approved: true },
      { id: 'R-02', text: 'إضافة منتج إلى السلة', priority: 'high', approved: true },
      { id: 'R-03', text: 'إتمام الطلب بعد تسجيل الدخول', priority: 'high', approved: true },
      { id: 'R-04', text: 'إدارة المنتجات من لوحة البائع', priority: 'high', approved: false },
      { id: 'R-05', text: 'عرض حالة الطلب للمستخدم', priority: 'medium', approved: false },
    ],
    analysisDone: true,
    entities: [
      {
        name: 'Product',
        fields: [
          { name: 'id', type: 'UUID', required: true, unique: true, notes: 'مفتاح أساسي' },
          { name: 'name', type: 'String', required: true, unique: false, notes: 'اسم المنتج' },
          { name: 'price', type: 'Decimal', required: true, unique: false, notes: '> 0' },
          { name: 'stock', type: 'Integer', required: true, unique: false, notes: '≥ 0' },
          { name: 'categoryId', type: 'UUID (FK)', required: true, unique: false, notes: 'Category' },
          { name: 'imageUrl', type: 'String', required: false, unique: false, notes: 'رابط الصورة' },
          { name: 'isActive', type: 'Boolean', required: true, unique: false, notes: 'افتراضي true' },
        ],
      },
      {
        name: 'Order',
        fields: [
          { name: 'id', type: 'UUID', required: true, unique: true, notes: 'مفتاح أساسي' },
          { name: 'userId', type: 'UUID', required: true, unique: false, notes: 'المستخدم' },
          { name: 'status', type: 'String', required: true, unique: false, notes: 'جديد/تجهيز/شحن/مكتمل' },
          { name: 'total', type: 'Decimal', required: true, unique: false, notes: 'المجموع' },
          { name: 'createdAt', type: 'DateTime', required: true, unique: false, notes: 'تلقائي' },
        ],
      },
    ],
    tests: [
      { id: 'TC-01', title: 'إضافة للسلة — مستخدم مسجل', expected: 'نجاح + زيادة العداد', result: 'pending' },
      { id: 'TC-02', title: 'إضافة للسلة — غير مسجل', expected: 'فتح تسجيل الدخول', result: 'pending' },
      { id: 'TC-03', title: 'طلب وسلة فارغة', expected: 'منع + رسالة', result: 'pending' },
      { id: 'TC-04', title: 'طلب ناجح', expected: 'طلب جديد + تفريغ السلة', result: 'pending' },
    ],
    agentTasks: [
      { id: '#A-102', agent: 'Analysis', description: 'تحليل فكرة متجري', status: 'done' },
      { id: '#D-045', agent: 'Design', description: 'توليد شاشة قائمة المنتجات', status: 'review' },
      { id: '#B-012', agent: 'Build', description: 'توليد كود Android', status: 'queued' },
    ],
    buildLog: ['لم يتم تشغيل بناء بعد.'],
    lastApk: { name: 'لا توجد حزمة بناء', size: '—', ready: false },
    functionSteps: [
      { id: 1, title: 'التحقق من تسجيل الدخول', detail: 'إن لم يكن مسجلاً → افتح تسجيل الدخول', kind: 'check' },
      { id: 2, title: 'كمية افتراضية', detail: 'quantity = 1', kind: 'action' },
      { id: 3, title: 'استدعاء addToCart', detail: 'productId + quantity', kind: 'action' },
      { id: 4, title: 'عند النجاح', detail: 'رسالة نجاح + تحديث شارة السلة', kind: 'success' },
      { id: 5, title: 'عند الفشل', detail: 'عرض رسالة الخطأ', kind: 'error' },
    ],
    functionNatural:
      'عندما يضغط المستخدم على أضف للسلة: إن لم يكن مسجلاً افتح تسجيل الدخول. ثم أضف المنتج بكمية 1. عند النجاح أظهر رسالة وحدّث العداد.',
    designPages: [
      { id: 'd1', name: 'قائمة المنتجات', platform: 'Android' },
      { id: 'd2', name: 'تفاصيل المنتج', platform: 'Android' },
      { id: 'd3', name: 'السلة', platform: 'Android' },
      { id: 'd4', name: 'إتمام الطلب', platform: 'Web' },
    ],
    currentDesignPageId: 'd1',
    settings: {
      language: 'ar',
      timezone: 'Asia/Riyadh',
      notifyBuild: true,
      notifyRelease: true,
      notifyComments: false,
    },
    team: [
      { name: 'أحمد', role: 'مالك', badge: 'admin' },
      { name: 'سارة', role: 'مصمم', badge: 'designer' },
      { name: 'خالد', role: 'مطور', badge: 'developer' },
    ],
    comments: [
      {
        author: 'سارة',
        target: 'شاشة السلة',
        text: 'زر إتمام الطلب يجب أن يكون أوضح وأكبر على الهاتف.',
        status: 'open',
      },
    ],
    release: {
      version: '0.1.0-beta',
      notes: 'أول نسخة تجريبية — تصفح وسلة وطلب ولوحة بائع',
      checklist: [
        { label: 'نجاح البناء', done: false },
        { label: 'اجتياز اختبارات المسار الحرج', done: false },
        { label: 'مراجعة الأمان الأساسية', done: false },
      ],
    },
    monitorErrors: [
      { type: 'API', screen: 'إتمام الطلب', message: 'Timeout عند createOrder', count: 3 },
      { type: 'UI', screen: 'قائمة المنتجات', message: 'فشل تحميل صورة منتج', count: 2 },
    ],
    roles: [
      { role: 'customer', permissions: 'تصفح، سلة، طلب، عرض طلباته' },
      { role: 'seller', permissions: 'إدارة منتجاته، طلبات منتجاته، تغيير الحالة' },
      { role: 'admin', permissions: 'كامل الصلاحيات + المستخدمون والتصنيفات' },
    ],
  };
}

function isProject(value: unknown): value is Project {
  if (!value || typeof value !== 'object') return false;
  const project = value as Partial<Project>;
  return typeof project.id === 'string' && project.id.length > 0 &&
    typeof project.name === 'string' && typeof project.description === 'string' &&
    ['active', 'draft', 'archived'].includes(project.status ?? '') &&
    Number.isFinite(project.phase) && Number.isFinite(project.progress) &&
    Array.isArray(project.platforms) && typeof project.updatedAt === 'string';
}

function normalizeState(value: unknown): AppState {
  const defaults = defaultState();
  if (!value || typeof value !== 'object' || Array.isArray(value)) return defaults;
  const saved = value as Partial<AppState>;
  const restoredProjects = Array.isArray(saved.projects)
    ? saved.projects.filter(isProject)
    : [];
  const projects = restoredProjects.length > 0 ? restoredProjects : defaults.projects;
  const activeProjectId =
    typeof saved.activeProjectId === 'string' &&
    projects.some((project) => project.id === saved.activeProjectId)
      ? saved.activeProjectId
      : projects[0]?.id ?? '';

  return {
    ...defaults,
    ...saved,
    projects,
    activeProjectId,
    ideaText: typeof saved.ideaText === 'string' ? saved.ideaText : defaults.ideaText,
    requirements: Array.isArray(saved.requirements) ? saved.requirements : defaults.requirements,
    entities: Array.isArray(saved.entities) ? saved.entities : defaults.entities,
    tests: Array.isArray(saved.tests) ? saved.tests : defaults.tests,
    agentTasks: Array.isArray(saved.agentTasks) ? saved.agentTasks : defaults.agentTasks,
    buildLog: Array.isArray(saved.buildLog) ? saved.buildLog : defaults.buildLog,
    functionSteps: Array.isArray(saved.functionSteps) ? saved.functionSteps : defaults.functionSteps,
    designPages: Array.isArray(saved.designPages) ? saved.designPages : defaults.designPages,
    team: Array.isArray(saved.team) ? saved.team : defaults.team,
    comments: Array.isArray(saved.comments) ? saved.comments : defaults.comments,
    monitorErrors: Array.isArray(saved.monitorErrors) ? saved.monitorErrors : defaults.monitorErrors,
    roles: Array.isArray(saved.roles) ? saved.roles : defaults.roles,
    settings: saved.settings && typeof saved.settings === 'object' ? saved.settings : defaults.settings,
    release: saved.release && typeof saved.release === 'object' ? saved.release : defaults.release,
    lastApk: saved.lastApk && typeof saved.lastApk === 'object' ? saved.lastApk : defaults.lastApk,
  };
}

@Injectable({ providedIn: 'root' })
export class ProjectService {
  private readonly state = signal<AppState>(defaultState());
  private activeStorageOwner: string | null = null;

  readonly projects = computed(() => this.state().projects);
  readonly activeProjectId = computed(() => this.state().activeProjectId);
  readonly activeProject = computed(
    () => this.state().projects.find((p) => p.id === this.state().activeProjectId) ?? this.state().projects[0]
  );
  readonly ideaText = computed(() => this.state().ideaText);
  readonly requirements = computed(() => this.state().requirements);
  readonly analysisDone = computed(() => this.state().analysisDone);
  readonly entities = computed(() => this.state().entities);
  readonly tests = computed(() => this.state().tests);
  readonly agentTasks = computed(() => this.state().agentTasks);
  readonly buildLog = computed(() => this.state().buildLog);
  readonly lastApk = computed(() => this.state().lastApk);
  readonly functionSteps = computed(() => this.state().functionSteps);
  readonly functionNatural = computed(() => this.state().functionNatural);
  readonly designPages = computed(() => this.state().designPages);
  readonly currentDesignPage = computed(
    () => this.state().designPages.find((d) => d.id === this.state().currentDesignPageId) ?? this.state().designPages[0]
  );
  readonly settings = computed(() => this.state().settings);
  readonly team = computed(() => this.state().team);
  readonly comments = computed(() => this.state().comments);
  readonly release = computed(() => this.state().release);
  readonly monitorErrors = computed(() => this.state().monitorErrors);
  readonly roles = computed(() => this.state().roles);

  readonly approvedCount = computed(() => this.requirements().filter((r) => r.approved).length);
  readonly testsSummary = computed(() => {
    const t = this.tests();
    return {
      pass: t.filter((x) => x.result === 'pass').length,
      fail: t.filter((x) => x.result === 'fail').length,
      pending: t.filter((x) => x.result === 'pending').length,
      total: t.length,
    };
  });

  constructor(private readonly auth: AuthService) {
    effect(() => {
      const owner = this.auth.user()?.uid ?? 'anonymous';
      const currentState = this.state();

      // Switch storage namespaces atomically when the authenticated account changes.
      // Never copy the previous account's in-memory state into the next account's key.
      if (this.activeStorageOwner !== owner) {
        this.activeStorageOwner = owner;
        try {
          const raw = localStorage.getItem(storageKeyForUser(owner));
          this.state.set(raw ? normalizeState(JSON.parse(raw) as unknown) : defaultState());
        } catch {
          this.state.set(defaultState());
        }
        return;
      }

      try {
        localStorage.setItem(storageKeyForUser(owner), JSON.stringify(currentState));
      } catch {
        /* ignore storage quota or browser privacy-mode failures */
      }
    });
  }

  private patch(partial: Partial<AppState>): void {
    this.state.update((s) => ({ ...s, ...partial }));
  }

  private touchProject(id: string, extra: Partial<Project> = {}): void {
    this.state.update((s) => ({
      ...s,
      projects: s.projects.map((p) =>
        p.id === id ? { ...p, updatedAt: 'الآن', ...extra } : p
      ),
    }));
  }

  selectProject(id: string): void {
    if (!this.projects().some((project) => project.id === id)) return;
    this.patch({ activeProjectId: id });
  }

  replaceProjectsFromCloud(projects: Project[]): void {
    if (!Array.isArray(projects)) return;
    this.state.update((s) => {
      const valid = projects.filter(isProject);
      const activeProjectId = valid.some((project) => project.id === s.activeProjectId)
        ? s.activeProjectId
        : valid[0]?.id ?? '';
      return { ...s, projects: valid, activeProjectId };
    });
  }

  addCloudProject(project: Project): void {
    if (!isProject(project)) return;
    this.state.update((s) => ({
      ...s,
      projects: [project, ...s.projects.filter((item) => item.id !== project.id)],
      activeProjectId: project.id,
    }));
  }

  addProject(data: { name: string; description: string; platforms: Platform[] }): void {
    const p: Project = {
      id: 'p' + Date.now(),
      name: data.name,
      description: data.description,
      status: 'draft',
      phase: 0,
      progress: 0,
      platforms: data.platforms,
      updatedAt: 'الآن',
    };
    this.state.update((s) => ({
      ...s,
      projects: [p, ...s.projects],
      activeProjectId: p.id,
    }));
  }

  setIdeaText(text: string): void {
    this.patch({ ideaText: text });
  }

  runAnalysis(): void {
    // Analysis is unavailable until a real analysis provider is configured.
    // Preserve the user's idea and existing requirements; never fabricate results.
    this.patch({ analysisDone: false });
  }

  toggleRequirement(id: string): void {
    this.state.update((s) => ({
      ...s,
      requirements: s.requirements.map((r) =>
        r.id === id ? { ...r, approved: !r.approved } : r
      ),
    }));
  }

  approveAllRequirements(): void {
    this.state.update((s) => ({
      ...s,
      requirements: s.requirements.map((r) => ({ ...r, approved: true })),
    }));
    this.touchProject(this.state().activeProjectId, { phase: 2, progress: 30 });
  }

  setTestResult(id: string, result: TestResult): void {
    this.state.update((s) => ({
      ...s,
      tests: s.tests.map((t) => (t.id === id ? { ...t, result } : t)),
    }));
  }

  runAllPendingTests(): void {
    // Test execution must be performed by a real test runner, not random UI state.
    return;
  }

  appendBuildLog(line: string): void {
    this.state.update((s) => ({ ...s, buildLog: [...s.buildLog, line] }));
  }

  simulateBuild(): void {
    // Placeholder retained for compatibility; it must never claim an artifact exists.
    this.patch({
      lastApk: { name: 'غير متاح — لم يُنفّذ بناء فعلي', size: '—', ready: false },
      buildLog: ['[متوقف] خدمة توليد الكود والبناء غير موصولة. لم يتم إنشاء أي حزمة.'],
    });
  }

  setFunctionNatural(text: string): void {
    this.patch({ functionNatural: text });
  }

  convertNaturalToSteps(): void {
    const source = this.state().functionNatural.trim();
    if (!source) return;
    const lines = source
      .split(/[\n.!؟؛]+/)
      .map((line) => line.trim())
      .filter(Boolean)
      .slice(0, 20);
    const steps: FunctionStep[] = lines.map((line, index) => {
      const normalized = line.toLowerCase();
      const kind: FunctionStep['kind'] =
        /إذا|إن |تحقق|تأكد|قبل/.test(normalized) ? 'check' :
        /خطأ|فشل|تعذر|وإلا/.test(normalized) ? 'error' :
        /نجاح|بنجاح|عند اكتمال/.test(normalized) ? 'success' : 'action';
      return {
        id: index + 1,
        title: line.length > 72 ? line.slice(0, 69) + '…' : line,
        detail: 'مسودة مستخرجة محلياً من نص المستخدم — تحتاج مراجعة',
        kind,
      };
    });
    this.patch({ functionSteps: steps });
  }

  addFunctionStep(): void {
    this.state.update((s) => ({
      ...s,
      functionSteps: [
        ...s.functionSteps,
        {
          id: s.functionSteps.length + 1,
          title: 'خطوة جديدة',
          detail: 'حدّد التفاصيل',
          kind: 'action',
        },
      ],
    }));
  }

  selectDesignPage(id: string): void {
    this.patch({ currentDesignPageId: id });
  }

  addDesignPage(name: string): void {
    const id = 'd' + Date.now();
    this.state.update((s) => ({
      ...s,
      designPages: [...s.designPages, { id, name, platform: 'Android' }],
      currentDesignPageId: id,
    }));
  }

  updateSettings(partial: Partial<AppSettings>): void {
    this.state.update((s) => ({
      ...s,
      settings: { ...s.settings, ...partial },
    }));
  }

  setReleaseVersion(version: string, notes: string): void {
    this.state.update((s) => ({
      ...s,
      release: { ...s.release, version, notes },
    }));
  }

  publishRelease(): boolean {
    // A completed local checklist is not a deployment. Require a real release provider.
    return false;
  }

  setCommentStatus(index: number, status: 'open' | 'approved' | 'changes'): void {
    this.state.update((s) => ({
      ...s,
      comments: s.comments.map((c, i) => (i === index ? { ...c, status } : c)),
    }));
  }

  enqueueAgent(agent: string, description: string): void {
    const id = '#' + agent[0] + '-' + Date.now().toString().slice(-4);
    this.state.update((s) => ({
      ...s,
      agentTasks: [{ id, agent, description, status: 'queued' as const }, ...s.agentTasks],
    }));
  }

  runQueuedAgents(): void {
    // Agent orchestration requires a trusted backend; never simulate completion.
    return;
  }

  resetDemoData(): void {
    const owner = this.auth.user()?.uid ?? 'anonymous';
    localStorage.removeItem(storageKeyForUser(owner));
    this.state.set(defaultState());
  }
}
