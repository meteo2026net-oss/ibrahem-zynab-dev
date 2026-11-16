import { Component, inject, ChangeDetectionStrategy } from '@angular/core';
import { ProjectService } from '../../core/project.service';
import { ToastService } from '../../core/toast.service';

@Component({
  selector: 'app-build',
  standalone: true,
  imports: [],
  templateUrl: './build.component.html',
  changeDetection: ChangeDetectionStrategy.Eager,
  styleUrl: './build.component.scss',
})
export class BuildComponent {
  readonly store = inject(ProjectService);
  readonly toast = inject(ToastService);
  platform = 'Android';

  build(): void {
    this.store.simulateBuild();
    this.toast.error('لم يبدأ البناء: محرك التوليد والبناء غير موصول، ولم يتم إنشاء ملف APK.');
  }
}
