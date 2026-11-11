import { Component, inject } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { ProjectService } from '../../core/project.service';

@Component({
  selector: 'app-release',
  standalone: true,
  imports: [FormsModule],
  templateUrl: './release.component.html',
  styleUrl: './release.component.scss',
})
export class ReleaseComponent {
  readonly store = inject(ProjectService);
  version = this.store.release().version;
  notes = this.store.release().notes;
  message = '';

  save(): void {
    this.store.setReleaseVersion(this.version, this.notes);
    this.message = 'تم حفظ بيانات الإصدار';
  }

  publish(): void {
    this.store.setReleaseVersion(this.version, this.notes);
    const ok = this.store.publishRelease();
    this.message = ok
      ? 'تم النشر بنجاح (محاكاة)'
      : 'تعذّر النشر: أكمل قائمة التحقق أولاً';
  }
}
