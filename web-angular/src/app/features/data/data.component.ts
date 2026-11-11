import { Component, inject, signal } from '@angular/core';
import { ProjectService } from '../../core/project.service';
import { ToastService } from '../../core/toast.service';

@Component({
  selector: 'app-data',
  standalone: true,
  imports: [],
  templateUrl: './data.component.html',
  styleUrl: './data.component.scss',
})
export class DataComponent {
  readonly store = inject(ProjectService);
  readonly toast = inject(ToastService);
  readonly selected = signal(0);

  select(i: number): void {
    this.selected.set(i);
  }

  save(): void {
    this.toast.success('تم حفظ نموذج البيانات');
  }
}
