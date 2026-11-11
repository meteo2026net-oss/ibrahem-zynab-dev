import { Component, inject } from '@angular/core';
import { ProjectService } from '../../core/project.service';
import { ToastService } from '../../core/toast.service';

@Component({
  selector: 'app-build',
  standalone: true,
  imports: [],
  templateUrl: './build.component.html',
  styleUrl: './build.component.scss',
})
export class BuildComponent {
  readonly store = inject(ProjectService);
  readonly toast = inject(ToastService);
  platform = 'Android';

  build(): void {
    this.store.simulateBuild();
    this.toast.info('بدأ البناء...');
    setTimeout(() => this.toast.success('اكتمل البناء'), 2800);
  }
}
