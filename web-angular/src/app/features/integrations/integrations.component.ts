import { Component, inject, signal } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { ToastService } from '../../core/toast.service';

interface Integration {
  id: string;
  name: string;
  type: 'github' | 'ai' | 'firebase' | 'other';
  status: 'connected' | 'disconnected' | 'error';
  note: string;
}

@Component({
  selector: 'app-integrations',
  standalone: true,
  imports: [FormsModule],
  templateUrl: './integrations.component.html',
  styleUrl: './integrations.component.scss',
})
export class IntegrationsComponent {
  private readonly toast = inject(ToastService);

  readonly items = signal<Integration[]>([
    {
      id: 'gh',
      name: 'GitHub',
      type: 'github',
      status: 'disconnected',
      note: 'ربط المستودعات والفروع وPR وفق المواصفات المعتمدة — يتطلب رمز وصول من المستخدم',
    },
    {
      id: 'fb',
      name: 'Firebase',
      type: 'firebase',
      status: 'disconnected',
      note: 'Auth + Firestore للمتجر السحابي',
    },
    {
      id: 'ai',
      name: 'مزود الذكاء الاصطناعي',
      type: 'ai',
      status: 'disconnected',
      note: 'مفاتيح API تُحفظ كأسرار ولا تُعرض للوكلاء مباشرة',
    },
  ]);

  githubToken = '';

  connectGithub(): void {
    if (!this.githubToken.trim()) {
      this.toast.error('أدخل Personal Access Token');
      return;
    }
    // لا نخزن التوكن في localStorage نصاً واضحاً في هذه النسخة — إشارة اتصال فقط
    this.items.update((list) =>
      list.map((i) =>
        i.id === 'gh'
          ? { ...i, status: 'connected', note: 'تم تسجيل حالة الربط محلياً (لا يُرفع التوكن للمستودع)' }
          : i
      )
    );
    this.githubToken = '';
    this.toast.success('تم تسجيل ربط GitHub (محلي)');
  }

  disconnect(id: string): void {
    this.items.update((list) =>
      list.map((i) => (i.id === id ? { ...i, status: 'disconnected' } : i))
    );
    this.toast.info('تم قطع الاتصال');
  }

  statusLabel(s: string): string {
    return s === 'connected' ? 'متصل' : s === 'error' ? 'خطأ' : 'غير متصل';
  }
  statusClass(s: string): string {
    return s === 'connected' ? 'badge-success' : s === 'error' ? 'badge-danger' : 'badge-muted';
  }
}
