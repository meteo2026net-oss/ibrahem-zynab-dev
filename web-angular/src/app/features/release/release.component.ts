import { Component, inject } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { ProjectService } from '../../core/project.service';
import { ToastService } from '../../core/toast.service';

@Component({
  selector: 'app-release',
  standalone: true,
  imports: [FormsModule],
  templateUrl: './release.component.html',
  styleUrl: './release.component.scss',
})
export class ReleaseComponent {
  readonly store = inject(ProjectService);
  readonly toast = inject(ToastService);
  version = this.store.release().version;
  notes = this.store.release().notes;
  message = '';

  save(): void {
    this.store.setReleaseVersion(this.version, this.notes);
    this.message = 'تم حفظ بيانات الإصدار';
    this.toast.success(this.message);
  }

  publish(): void {
    this.store.setReleaseVersion(this.version, this.notes);
    const ok = this.store.publishRelease();
    if (ok) {
      this.message = 'تم النشر بنجاح (محاكاة)';
      this.toast.success(this.message);
    } else {
      this.message = 'تعذّر النشر: أكمل قائمة التحقق أولاً';
      this.toast.error(this.message);
    }
  }
}
