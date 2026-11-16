import { Component, inject, ChangeDetectionStrategy, OnInit } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { ProjectService } from '../../core/project.service';
import { ToastService } from '../../core/toast.service';
import { AuditService } from '../../core/audit.service';
import { AgentStatus } from '../../core/models';
import { CloudWorkspaceService, CloudJob } from '../../core/cloud-workspace.service';

@Component({
  selector: 'app-agents',
  standalone: true,
  imports: [FormsModule],
  templateUrl: './agents.component.html',
  changeDetection: ChangeDetectionStrategy.Eager,
  styleUrl: './agents.component.scss',
})
export class AgentsComponent implements OnInit {
  readonly store = inject(ProjectService);
  private readonly toast = inject(ToastService);
  private readonly audit = inject(AuditService);
  private readonly cloud = inject(CloudWorkspaceService);
  jobs: CloudJob[] = [];
  loading = false;
  submitting = false;

  ngOnInit(): void { this.refreshJobs(); }

  async refreshJobs(): Promise<void> {
    const project = this.store.activeProject();
    if (!project?.id) { this.jobs = []; return; }
    this.loading = true;
    try {
      const result = await this.cloud.listJobs(project.id);
      this.jobs = result.jobs;
    } catch (error) {
      this.toast.error(error instanceof Error ? error.message : 'تعذر تحميل المهام السحابية.');
    } finally { this.loading = false; }
  }

  jobStatusLabel(status: string): string {
    return ({ QUEUED: 'بالانتظار', RUNNING: 'قيد التنفيذ', SUCCEEDED: 'مكتملة', FAILED: 'فشلت', CANCELLED: 'أُلغيت' } as Record<string, string>)[status] ?? status;
  }

  async cancel(job: CloudJob): Promise<void> {
    try { await this.cloud.cancelJob(job.id); this.toast.success('تم إرسال طلب الإلغاء.'); await this.refreshJobs(); }
    catch (error) { this.toast.error(error instanceof Error ? error.message : 'تعذر إلغاء المهمة.'); }
  }

  async retry(job: CloudJob): Promise<void> {
    try {
      const result = await this.cloud.retryJob(job.id);
      this.toast.success('تم إرسال إعادة المحاولة: ' + result.dispatchStatus);
      await this.refreshJobs();
    } catch (error) { this.toast.error(error instanceof Error ? error.message : 'تعذرت إعادة المحاولة.'); }
  }
  taskDesc = '';
  agent = 'Analysis';

  statusLabel(s: AgentStatus): string {
    return ({ done: 'مكتمل', review: 'مراجعة', running: 'يعمل', queued: 'بالانتظار' } as const)[s];
  }
  statusClass(s: AgentStatus): string {
    return ({ done: 'badge-success', review: 'badge-warning', running: 'badge-primary', queued: 'badge-muted' } as const)[s];
  }

  async enqueue(): Promise<void> {
    const description = this.taskDesc.trim();
    const project = this.store.activeProject();
    if (!description) { this.toast.error('اكتب وصف المهمة'); return; }
    if (!project?.id) { this.toast.error('أنشئ مشروعاً سحابياً أولاً.'); return; }
    if (this.submitting) return;
    this.submitting = true;
    try {
      const result = await this.cloud.createJob({
        projectId: project.id,
        projectName: project.name,
        jobType: this.agent.toUpperCase(),
        title: this.agent + ': ' + description.slice(0, 140),
        prompt: description,
        idempotencyKey: globalThis.crypto.randomUUID(),
      });
      this.audit.log('user', 'enqueue_cloud_job', this.agent, 'success', result.jobId);
      this.toast.success('تم حفظ المهمة السحابية: ' + result.dispatchStatus);
      this.taskDesc = '';
      await this.refreshJobs();
    } catch (error) {
      this.toast.error(error instanceof Error ? error.message : 'تعذر إنشاء المهمة السحابية.');
      this.audit.log('user', 'enqueue_cloud_job', this.agent, 'denied', 'Cloud callable failed');
    } finally { this.submitting = false; }
  }

  runQueued(): void {
    this.toast.error('تُشغّل المهام السحابية عبر Cloud Tasks بعد تهيئة عامل التنفيذ الخلفي.');
  }
}
