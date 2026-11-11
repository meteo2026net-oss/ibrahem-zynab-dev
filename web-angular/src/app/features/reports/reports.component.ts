import { Component, inject, computed } from '@angular/core';
import { ProjectService } from '../../core/project.service';

@Component({
  selector: 'app-reports',
  standalone: true,
  imports: [],
  templateUrl: './reports.component.html',
  styleUrl: './reports.component.scss',
})
export class ReportsComponent {
  readonly store = inject(ProjectService);
  readonly phases = computed(() => {
    const phase = this.store.activeProject()?.phase ?? 0;
    const rows = [
      { name: '0 التأسيس', target: 0 },
      { name: '1 التحليل', target: 1 },
      { name: '2 المعمارية / التصميم', target: 2 },
      { name: '5–7 البناء', target: 7 },
      { name: '8 الاختبار', target: 8 },
      { name: '9 الإطلاق', target: 9 },
    ];
    return rows.map((r) => ({
      ...r,
      status: phase > r.target ? 'done' : phase === r.target ? 'current' : 'todo',
      pct: phase > r.target ? 100 : phase === r.target ? Math.max(this.store.activeProject()?.progress ?? 0, 10) : 0,
    }));
  });
}
