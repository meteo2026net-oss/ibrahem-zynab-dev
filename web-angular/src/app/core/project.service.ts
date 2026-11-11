import { Injectable, signal, computed, effect } from '@angular/core';
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
      { id: 'TC-01', title: 'إضافة للسلة — مستخدم مسجل', expected: 'نجاح + زيادة العداد', result: 'pass' },
      { id: 'TC-02', title: 'إضافة للسلة — غير مسجل', expected: 'فتح تسجيل الدخول', result: 'pass' },
      { id: 'TC-03', title: 'طلب وسلة فارغة', expected: 'منع + رسالة', result: 'fail' },
      { id: 'TC-04', title: 'طلب ناجح', expected: 'طلب جديد + تفريغ السلة', result: 'pending' },
    ],
    agentTasks: [
      { id: '#A-102', agent: 'Analysis', description: 'تحليل فكرة متجري', status: 'done' },
      { id: '#D-045', agent: 'Design', description: 'توليد شاشة قائمة المنتجات', status: 'review' },
      { id: '#B-012', agent: 'Build', description: 'توليد كود Android', status: 'queued' },
    ],
    buildLog: [
      '[10:01] بدء توليد الكود — 8 شاشات',
      '[10:04] اكتمال التوليد — 42 ملفاً',
      '[10:08] نجاح البناء — app-debug.apk (18.4 MB)',
    ],
    lastApk: { name: 'app-debug.apk', size: '18.4 MB', ready: true },
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
        { label: 'نجاح البناء', done: true },
        { label: 'اجتياز اختبارات المسار الحرج', done: true },
        { label: 'مراجعة الأمان الأساسية', done: true },
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

function loadState(): AppState {
  try {
    const raw = localStorage.getItem(STORAGE_KEY);
    if (!raw) return defaultState();
    return { ...defaultState(), ...JSON.parse(raw) };
  } catch {
    return defaultState();
  }
}

@Injectable({ providedIn: 'root' })
export class ProjectService {
  private readonly state = signal<AppState>(loadState());

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

  constructor() {
    effect(() => {
      const s = this.state();
      try {
        localStorage.setItem(STORAGE_KEY, JSON.stringify(s));
      } catch {
        /* ignore quota */
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
    this.patch({ activeProjectId: id });
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
    this.patch({
      analysisDone: true,
      requirements: [
        { id: 'R-01', text: 'تصفح قائمة المنتجات', priority: 'high', approved: false },
        { id: 'R-02', text: 'إضافة منتج إلى السلة', priority: 'high', approved: false },
        { id: 'R-03', text: 'إتمام الطلب بعد تسجيل الدخول', priority: 'high', approved: false },
        { id: 'R-04', text: 'إدارة المنتجات من لوحة البائع', priority: 'high', approved: false },
        { id: 'R-05', text: 'عرض حالة الطلب للمستخدم', priority: 'medium', approved: false },
        { id: 'R-06', text: 'البحث والفلترة حسب التصنيف', priority: 'medium', approved: false },
      ],
      agentTasks: [
        { id: '#A-' + Date.now().toString().slice(-4), agent: 'Analysis', description: 'تحليل محدّث', status: 'done' },
        ...this.state().agentTasks,
      ],
    });
    this.touchProject(this.state().activeProjectId, { phase: 1, progress: 20 });
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
    this.state.update((s) => ({
      ...s,
      tests: s.tests.map((t) =>
        t.result === 'pending'
          ? { ...t, result: Math.random() > 0.25 ? 'pass' : 'fail' }
          : t
      ),
    }));
  }

  appendBuildLog(line: string): void {
    this.state.update((s) => ({ ...s, buildLog: [...s.buildLog, line] }));
  }

  simulateBuild(): void {
    this.patch({
      lastApk: { name: 'app-debug.apk', size: '—', ready: false },
      buildLog: ['[بدء] توليد الكود...'],
    });
    setTimeout(() => this.appendBuildLog('[..] توليد الشاشات والنماذج...'), 400);
    setTimeout(() => this.appendBuildLog('[..] توليد الوظائف...'), 900);
    setTimeout(() => this.appendBuildLog('[OK] اكتمال التوليد — 42 ملفاً'), 1400);
    setTimeout(() => this.appendBuildLog('[..] بناء الحزمة...'), 1800);
    setTimeout(() => {
      this.appendBuildLog('[OK] نجاح البناء — app-debug.apk (18.6 MB)');
      this.patch({ lastApk: { name: 'app-debug.apk', size: '18.6 MB', ready: true } });
      this.touchProject(this.state().activeProjectId, { phase: 7, progress: 55 });
      this.state.update((s) => ({
        ...s,
        release: {
          ...s.release,
          checklist: s.release.checklist.map((c) =>
            c.label.includes('البناء') ? { ...c, done: true } : c
          ),
        },
      }));
    }, 2600);
  }

  setFunctionNatural(text: string): void {
    this.patch({ functionNatural: text });
  }

  convertNaturalToSteps(): void {
    const steps: FunctionStep[] = [
      { id: 1, title: 'التحقق من تسجيل الدخول', detail: 'مستخرج من الوصف', kind: 'check' },
      { id: 2, title: 'تنفيذ الإجراء الرئيسي', detail: 'addToCart / العملية المذكورة', kind: 'action' },
      { id: 3, title: 'معالجة النجاح', detail: 'رسالة وتحديث الواجهة', kind: 'success' },
      { id: 4, title: 'معالجة الفشل', detail: 'عرض الخطأ', kind: 'error' },
    ];
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
    const r = this.state().release;
    if (!r.checklist.every((c) => c.done)) return false;
    this.state.update((s) => ({
      ...s,
      release: {
        ...s.release,
        lastPublished: new Date().toLocaleString('ar-SA'),
      },
    }));
    this.touchProject(this.state().activeProjectId, { phase: 9, progress: 70 });
    return true;
  }

  setCommentStatus(index: number, status: 'open' | 'approved' | 'changes'): void {
    this.state.update((s) => ({
      ...s,
      comments: s.comments.map((c, i) => (i === index ? { ...c, status } : c)),
    }));
  }

  resetDemoData(): void {
    localStorage.removeItem(STORAGE_KEY);
    this.state.set(defaultState());
  }
}
