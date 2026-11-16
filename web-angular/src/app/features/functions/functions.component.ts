import { Component, inject, ChangeDetectionStrategy } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { ProjectService } from '../../core/project.service';
import { ToastService } from '../../core/toast.service';

@Component({
  selector: 'app-functions',
  standalone: true,
  imports: [FormsModule],
  templateUrl: './functions.component.html',
  changeDetection: ChangeDetectionStrategy.Eager,
  styleUrl: './functions.component.scss',
})
export class FunctionsComponent {
  readonly store = inject(ProjectService);
  readonly toast = inject(ToastService);
  natural = this.store.functionNatural();
  tested = false;
  testMsg = '';

  convert(): void {
    this.store.setFunctionNatural(this.natural);
    this.store.convertNaturalToSteps();
    if (this.natural.trim()) this.toast.info('أُنشئت مسودة خطوات محلية؛ راجعها قبل اعتمادها.');
    else this.toast.error('اكتب وصف الوظيفة أولاً.');
  }

  test(): void {
    this.tested = true;
    this.testMsg = 'لم يُنفّذ اختبار فعلي: يلزم ربط مشغل تنفيذ واختبار آمن قبل اعتماد الوظيفة.';
    this.toast.error('مشغل اختبار الوظائف غير موصول.');
  }

  kindClass(k: string): string {
    return k === 'success' ? 'step-success' : k === 'error' ? 'step-error' : 'step-normal';
  }
}
