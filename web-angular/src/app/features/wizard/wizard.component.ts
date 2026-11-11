import { Component, inject, signal } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { Router, RouterLink } from '@angular/router';
import { ProjectService } from '../../core/project.service';
import { KnowledgeService } from '../../core/knowledge.service';
import { AuditService } from '../../core/audit.service';
import { ToastService } from '../../core/toast.service';
import { Platform } from '../../core/models';

@Component({
  selector: 'app-wizard',
  standalone: true,
  imports: [FormsModule, RouterLink],
  templateUrl: './wizard.component.html',
  styleUrl: './wizard.component.scss',
})
export class WizardComponent {
  private readonly projects = inject(ProjectService);
  private readonly knowledge = inject(KnowledgeService);
  private readonly audit = inject(AuditService);
  private readonly toast = inject(ToastService);
  private readonly router = inject(Router);

  step = signal(1);
  idea = '';
  name = '';
  audience = '';
  platforms = { android: true, ios: false, web: true, desktop: false };
  analysisPreview = signal<{
    type: string;
    screens: string[];
    entities: string[];
    complexity: string;
  } | null>(null);

  next(): void {
    if (this.step() === 1 && !this.idea.trim()) {
      this.toast.error('اشرح فكرة التطبيق أولاً');
      return;
    }
    if (this.step() === 1) {
      this.name = this.name || this.guessName(this.idea);
      this.analysisPreview.set(this.analyze(this.idea));
    }
    if (this.step() < 4) this.step.update((s) => s + 1);
  }

  back(): void {
    if (this.step() > 1) this.step.update((s) => s - 1);
  }

  private guessName(idea: string): string {
    if (idea.includes('متجر') || idea.includes('بيع')) return 'متجري';
    if (idea.includes('حجز')) return 'نظام الحجوزات';
    return 'مشروعي';
  }

  private analyze(idea: string) {
    const screens = ['تسجيل الدخول', 'الرئيسية'];
    const entities = ['User'];
    let type = 'تطبيق عام';
    if (idea.includes('متجر') || idea.includes('منتج') || idea.includes('سلة')) {
      type = 'تجارة إلكترونية';
      screens.push('قائمة المنتجات', 'تفاصيل المنتج', 'السلة', 'إتمام الطلب', 'طلباتي', 'لوحة البائع');
      entities.push('Product', 'Order', 'OrderItem', 'CartItem');
    } else if (idea.includes('حجز') || idea.includes('موعد')) {
      type = 'حجوزات';
      screens.push('اختيار الموعد', 'تأكيد الحجز', 'مواعيدي');
      entities.push('Appointment', 'Service');
    } else {
      screens.push('قائمة العناصر', 'التفاصيل', 'الإعدادات');
      entities.push('Item');
    }
    const complexity =
      screens.length > 6 ? 'متوسط–مرتفع' : screens.length > 4 ? 'متوسط' : 'منخفض';
    return { type, screens, entities, complexity };
  }

  create(): void {
    const selected: Platform[] = [];
    if (this.platforms.android) selected.push('Android');
    if (this.platforms.ios) selected.push('iOS');
    if (this.platforms.web) selected.push('Web');
    if (this.platforms.desktop) selected.push('Desktop');

    this.projects.addProject({
      name: this.name || 'مشروع جديد',
      description: this.idea,
      platforms: selected.length ? selected : ['Web'],
    });
    this.projects.setIdeaText(this.idea);
    this.projects.runAnalysis();

    const preview = this.analysisPreview();
    this.knowledge.add({
      title: 'فكرة المشروع (من المعالج)',
      category: 'idea',
      body: this.idea + (this.audience ? `\n\nالجمهور: ${this.audience}` : ''),
      status: 'reviewed',
    });
    if (preview) {
      this.knowledge.add({
        title: 'تصور أولي للصفحات',
        category: 'ui',
        body: preview.screens.join('\n'),
        status: 'draft',
      });
      this.knowledge.add({
        title: 'كيانات مقترحة',
        category: 'data',
        body: preview.entities.join(', '),
        status: 'draft',
      });
    }

    this.audit.log('user', 'create_project', this.name, 'success', preview?.type);
    this.toast.success('تم إنشاء المشروع واعتماد التصور الأولي');
    this.router.navigateByUrl('/analysis');
  }
}
