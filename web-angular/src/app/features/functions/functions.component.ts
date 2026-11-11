import { Component, inject } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { ProjectService } from '../../core/project.service';

@Component({
  selector: 'app-functions',
  standalone: true,
  imports: [FormsModule],
  templateUrl: './functions.component.html',
  styleUrl: './functions.component.scss',
})
export class FunctionsComponent {
  readonly store = inject(ProjectService);
  natural = this.store.functionNatural();
  tested = false;
  testMsg = '';

  convert(): void {
    this.store.setFunctionNatural(this.natural);
    this.store.convertNaturalToSteps();
  }

  test(): void {
    this.tested = true;
    this.testMsg = 'تم اختبار التسلسل بنجاح (محاكاة): تسجيل الدخول → addToCart → رسالة نجاح';
  }

  kindClass(k: string): string {
    return k === 'success' ? 'step-success' : k === 'error' ? 'step-error' : 'step-normal';
  }
}
