import { Injectable, signal, computed, effect, inject } from '@angular/core';
import { ProjectService } from './project.service';

export interface KnowledgeDoc {
  id: string;
  projectId: string;
  title: string;
  category:
    | 'idea'
    | 'requirements'
    | 'ui'
    | 'data'
    | 'api'
    | 'roles'
    | 'code'
    | 'decisions'
    | 'tests'
    | 'user-guide'
    | 'tech-guide';
  body: string;
  updatedAt: string;
  status: 'draft' | 'reviewed' | 'stale';
}

const KEY = 'iad-knowledge-v1';

const CATEGORY_LABELS: Record<KnowledgeDoc['category'], string> = {
  idea: 'الفكرة والأهداف',
  requirements: 'المتطلبات',
  ui: 'واجهات UI/UX',
  data: 'قواعد البيانات',
  api: 'واجهات API',
  roles: 'الأدوار والصلاحيات',
  code: 'بنية الكود',
  decisions: 'القرارات المعتمدة',
  tests: 'الاختبارات',
  'user-guide': 'دليل الاستخدام',
  'tech-guide': 'الدليل التقني',
};

@Injectable({ providedIn: 'root' })
export class KnowledgeService {
  private readonly projects = inject(ProjectService);
  private readonly docs = signal<KnowledgeDoc[]>(this.load());

  readonly categoryLabels = CATEGORY_LABELS;

  readonly projectDocs = computed(() => {
    const pid = this.projects.activeProjectId();
    return this.docs()
      .filter((d) => d.projectId === pid)
      .sort((a, b) => b.updatedAt.localeCompare(a.updatedAt));
  });

  readonly searchQuery = signal('');
  readonly filtered = computed(() => {
    const q = this.searchQuery().trim();
    const list = this.projectDocs();
    if (!q) return list;
    return list.filter(
      (d) => d.title.includes(q) || d.body.includes(q) || CATEGORY_LABELS[d.category].includes(q)
    );
  });

  constructor() {
    effect(() => {
      localStorage.setItem(KEY, JSON.stringify(this.docs()));
    });
    // seed for متجري if empty
    if (this.docs().length === 0) {
      this.seedMatjari();
    }
  }

  private load(): KnowledgeDoc[] {
    try {
      const raw = localStorage.getItem(KEY);
      return raw ? JSON.parse(raw) : [];
    } catch {
      return [];
    }
  }

  private seedMatjari(): void {
    const pid = 'p1';
    const now = new Date().toISOString();
    const seed: KnowledgeDoc[] = [
      {
        id: 'k1',
        projectId: pid,
        title: 'فكرة متجري',
        category: 'idea',
        body: 'تطبيق تجارة إلكترونية: تصفح منتجات، سلة، طلبات، ولوحة بائع لإدارة المخزون وحالات الطلب.',
        updatedAt: now,
        status: 'reviewed',
      },
      {
        id: 'k2',
        projectId: pid,
        title: 'المتطلبات الأساسية',
        category: 'requirements',
        body: 'R-01 تصفح المنتجات\nR-02 إضافة للسلة مع التحقق من المخزون\nR-03 إتمام الطلب بعد تسجيل الدخول\nR-04 إدارة المنتجات للبائع\nR-05 تتبع حالة الطلب',
        updatedAt: now,
        status: 'reviewed',
      },
      {
        id: 'k3',
        projectId: pid,
        title: 'نموذج البيانات',
        category: 'data',
        body: 'Product, Order, OrderItem, User, CartItem\nعلاقات: Order N-1 User، Order 1-N OrderItem، OrderItem N-1 Product',
        updatedAt: now,
        status: 'reviewed',
      },
      {
        id: 'k4',
        projectId: pid,
        title: 'الأدوار',
        category: 'roles',
        body: 'customer: تصفح وسلة وطلب\nseller: منتجات وطلبات\nadmin: كامل الصلاحيات',
        updatedAt: now,
        status: 'reviewed',
      },
      {
        id: 'k5',
        projectId: pid,
        title: 'دليل استخدام المتجر',
        category: 'user-guide',
        body: '1) ادخل /shop\n2) سجّل customer@matjari.demo / 123456\n3) أضف للسلة وأتمم الطلب\n4) ادخل كبائع لتغيير حالة الطلب',
        updatedAt: now,
        status: 'reviewed',
      },
    ];
    this.docs.set(seed);
  }

  add(partial: Omit<KnowledgeDoc, 'id' | 'updatedAt' | 'projectId' | 'status'> & { status?: KnowledgeDoc['status'] }): void {
    const doc: KnowledgeDoc = {
      id: 'k' + Date.now(),
      projectId: this.projects.activeProjectId(),
      title: partial.title,
      category: partial.category,
      body: partial.body,
      updatedAt: new Date().toISOString(),
      status: partial.status || 'draft',
    };
    this.docs.update((list) => [doc, ...list]);
  }

  update(id: string, partial: Partial<Pick<KnowledgeDoc, 'title' | 'body' | 'category' | 'status'>>): void {
    this.docs.update((list) =>
      list.map((d) =>
        d.id === id
          ? { ...d, ...partial, updatedAt: new Date().toISOString() }
          : d
      )
    );
  }

  markStaleOnProjectChange(): void {
    const pid = this.projects.activeProjectId();
    this.docs.update((list) =>
      list.map((d) =>
        d.projectId === pid && d.status === 'reviewed'
          ? { ...d, status: 'stale' as const, updatedAt: new Date().toISOString() }
          : d
      )
    );
  }

  exportMarkdown(): string {
    const lines = [`# معرفة المشروع: ${this.projects.activeProject()?.name || ''}`, ''];
    for (const d of this.projectDocs()) {
      lines.push(`## ${d.title} (${CATEGORY_LABELS[d.category]})`);
      lines.push(`*الحالة: ${d.status} — ${d.updatedAt}*`, '', d.body, '');
    }
    return lines.join('\n');
  }
}
