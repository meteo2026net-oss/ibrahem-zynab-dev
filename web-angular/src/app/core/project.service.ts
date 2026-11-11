import { Injectable, signal, computed } from '@angular/core';
import {
  Project,
  Requirement,
  DataEntity,
  TestCase,
  AgentTask,
  Platform,
} from './models';

@Injectable({ providedIn: 'root' })
export class ProjectService {
  readonly projects = signal<Project[]>([
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
  ]);

  readonly activeProjectId = signal<string>('p1');

  readonly activeProject = computed(() =>
    this.projects().find((p) => p.id === this.activeProjectId()) ?? this.projects()[0]
  );

  readonly ideaText = signal(
    'أريد تطبيق متجر إلكتروني بسيط. الزبون يتصفح المنتجات حسب التصنيف، يشاهد التفاصيل والسعر، يضيف للسلة، ثم يطلب بعد تسجيل الدخول. البائع يدير المنتجات ويرى الطلبات ويغيّر حالتها.'
  );

  readonly requirements = signal<Requirement[]>([
    { id: 'R-01', text: 'تصفح قائمة المنتجات', priority: 'high', approved: true },
    { id: 'R-02', text: 'إضافة منتج إلى السلة', priority: 'high', approved: true },
    { id: 'R-03', text: 'إتمام الطلب بعد تسجيل الدخول', priority: 'high', approved: true },
    { id: 'R-04', text: 'إدارة المنتجات من لوحة البائع', priority: 'high', approved: false },
    { id: 'R-05', text: 'عرض حالة الطلب للمستخدم', priority: 'medium', approved: false },
  ]);

  readonly analysisDone = signal(true);

  readonly entities = signal<DataEntity[]>([
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
  ]);

  readonly tests = signal<TestCase[]>([
    { id: 'TC-01', title: 'إضافة للسلة — مستخدم مسجل', expected: 'نجاح + زيادة العداد', result: 'pass' },
    { id: 'TC-02', title: 'إضافة للسلة — غير مسجل', expected: 'فتح تسجيل الدخول', result: 'pass' },
    { id: 'TC-03', title: 'طلب وسلة فارغة', expected: 'منع + رسالة', result: 'fail' },
    { id: 'TC-04', title: 'طلب ناجح', expected: 'طلب جديد + تفريغ السلة', result: 'pending' },
  ]);

  readonly agentTasks = signal<AgentTask[]>([
    { id: '#A-102', agent: 'Analysis', description: 'تحليل فكرة متجري', status: 'done' },
    { id: '#D-045', agent: 'Design', description: 'توليد شاشة قائمة المنتجات', status: 'review' },
    { id: '#B-012', agent: 'Build', description: 'توليد كود Android', status: 'queued' },
  ]);

  readonly buildLog = signal<string[]>([
    '[10:01] بدء توليد الكود — 8 شاشات',
    '[10:02] توليد نماذج البيانات — Product, Order, User...',
    '[10:03] توليد وظائف السلة والطلب',
    '[10:04] اكتمال التوليد — 42 ملفاً',
    '[10:05] بدء بناء APK',
    '[10:08] نجاح البناء — app-debug.apk (18.4 MB)',
  ]);

  readonly lastApk = signal<{ name: string; size: string; ready: boolean }>({
    name: 'app-debug.apk',
    size: '18.4 MB',
    ready: true,
  });

  selectProject(id: string): void {
    this.activeProjectId.set(id);
  }

  addProject(data: {
    name: string;
    description: string;
    platforms: Platform[];
  }): void {
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
    this.projects.update((list) => [p, ...list]);
    this.activeProjectId.set(p.id);
  }

  runAnalysis(): void {
    this.analysisDone.set(true);
    this.requirements.set([
      { id: 'R-01', text: 'تصفح قائمة المنتجات', priority: 'high', approved: false },
      { id: 'R-02', text: 'إضافة منتج إلى السلة', priority: 'high', approved: false },
      { id: 'R-03', text: 'إتمام الطلب بعد تسجيل الدخول', priority: 'high', approved: false },
      { id: 'R-04', text: 'إدارة المنتجات من لوحة البائع', priority: 'high', approved: false },
      { id: 'R-05', text: 'عرض حالة الطلب للمستخدم', priority: 'medium', approved: false },
      { id: 'R-06', text: 'البحث والفلترة حسب التصنيف', priority: 'medium', approved: false },
    ]);
  }

  toggleRequirement(id: string): void {
    this.requirements.update((list) =>
      list.map((r) => (r.id === id ? { ...r, approved: !r.approved } : r))
    );
  }

  approveAllRequirements(): void {
    this.requirements.update((list) => list.map((r) => ({ ...r, approved: true })));
    this.projects.update((list) =>
      list.map((p) =>
        p.id === this.activeProjectId()
          ? { ...p, phase: 2, progress: 25, updatedAt: 'الآن' }
          : p
      )
    );
  }

  setTestResult(id: string, result: TestCase['result']): void {
    this.tests.update((list) =>
      list.map((t) => (t.id === id ? { ...t, result } : t))
    );
  }

  runAllPendingTests(): void {
    this.tests.update((list) =>
      list.map((t) =>
        t.result === 'pending' ? { ...t, result: Math.random() > 0.3 ? 'pass' : 'fail' } : t
      )
    );
  }

  appendBuildLog(line: string): void {
    this.buildLog.update((lines) => [...lines, line]);
  }

  simulateBuild(): void {
    this.lastApk.set({ name: 'app-debug.apk', size: '—', ready: false });
    this.buildLog.set(['[بدء] توليد الكود...']);
    setTimeout(() => this.appendBuildLog('[..] توليد الشاشات والنماذج...'), 400);
    setTimeout(() => this.appendBuildLog('[..] توليد الوظائف...'), 900);
    setTimeout(() => this.appendBuildLog('[OK] اكتمال التوليد — 42 ملفاً'), 1400);
    setTimeout(() => this.appendBuildLog('[..] بناء APK...'), 1800);
    setTimeout(() => {
      this.appendBuildLog('[OK] نجاح البناء — app-debug.apk (18.6 MB)');
      this.lastApk.set({ name: 'app-debug.apk', size: '18.6 MB', ready: true });
    }, 2600);
  }
}
