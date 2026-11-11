import { Component, inject } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { ProjectService } from '../../core/project.service';
import { ToastService } from '../../core/toast.service';
import { AuditService } from '../../core/audit.service';
import { AgentStatus } from '../../core/models';

@Component({
  selector: 'app-agents',
  standalone: true,
  imports: [FormsModule],
  templateUrl: './agents.component.html',
  styleUrl: './agents.component.scss',
})
export class AgentsComponent {
  readonly store = inject(ProjectService);
  private readonly toast = inject(ToastService);
  private readonly audit = inject(AuditService);
  taskDesc = '';
  agent = 'Analysis';

  statusLabel(s: AgentStatus): string {
    return ({ done: 'مكتمل', review: 'مراجعة', running: 'يعمل', queued: 'بالانتظار' } as const)[s];
  }
  statusClass(s: AgentStatus): string {
    return ({ done: 'badge-success', review: 'badge-warning', running: 'badge-primary', queued: 'badge-muted' } as const)[s];
  }

  enqueue(): void {
    if (!this.taskDesc.trim()) {
      this.toast.error('اكتب وصف المهمة');
      return;
    }
    this.store.enqueueAgent(this.agent, this.taskDesc.trim());
    this.audit.log('user', 'enqueue_agent', this.agent, 'success', this.taskDesc.trim());
    this.toast.success('أُضيفت المهمة للطابور');
    this.taskDesc = '';
  }

  runQueued(): void {
    this.store.runQueuedAgents();
    this.toast.info('تشغيل المهام المعلّقة...');
    this.audit.log('user', 'run_agents', 'queue', 'success');
  }
}
