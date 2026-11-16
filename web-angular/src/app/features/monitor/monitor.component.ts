import { Component, inject, computed, ChangeDetectionStrategy } from '@angular/core';
import { ProjectService } from '../../core/project.service';

@Component({
  selector: 'app-monitor',
  standalone: true,
  imports: [],
  templateUrl: './monitor.component.html',
  changeDetection: ChangeDetectionStrategy.Eager,
  styleUrl: './monitor.component.scss',
})
export class MonitorComponent {
  readonly store = inject(ProjectService);
  readonly errorTotal = computed(() =>
    this.store.monitorErrors().reduce((a, e) => a + e.count, 0)
  );
}
