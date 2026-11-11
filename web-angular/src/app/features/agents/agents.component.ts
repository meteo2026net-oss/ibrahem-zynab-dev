import { Component, inject, computed } from '@angular/core';
import { ProjectService } from '../../core/project.service';

@Component({
  selector: 'app-agents',
  standalone: true,
  imports: [],
  templateUrl: './agents.component.html',
  styleUrl: './agents.component.scss',
})
export class AgentsComponent {
  readonly store = inject(ProjectService);
  readonly counts = computed(() => {
    const t = this.store.agentTasks();
    return {
      review: t.filter((x) => x.status === 'review').length,
      running: t.filter((x) => x.status === 'running').length,
      done: t.filter((x) => x.status === 'done').length,
    };
  });
  label(s: string): string {
    return ({ done: 'مكتمل', review: 'مراجعة', running: 'يعمل', queued: 'مجدول' } as Record<string, string>)[s] || s;
  }
  cls(s: string): string {
    return ({ done: 'badge-success', review: 'badge-warning', running: 'badge-primary', queued: 'badge-muted' } as Record<string, string>)[s] || 'badge-muted';
  }
}
