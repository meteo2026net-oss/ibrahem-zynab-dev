import { Component, inject, computed, ChangeDetectionStrategy, OnInit } from '@angular/core';
import { NgClass } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { ProjectService } from '../../core/project.service';
import { Platform, ProjectStatus, Project } from '../../core/models';
import { CloudWorkspaceService } from '../../core/cloud-workspace.service';
import { ToastService } from '../../core/toast.service';

@Component({
  selector: 'app-projects',
  standalone: true,
  imports: [NgClass, FormsModule],
  templateUrl: './projects.component.html',
  changeDetection: ChangeDetectionStrategy.Eager,
  styleUrl: './projects.component.scss',
})
export class ProjectsComponent implements OnInit {
  private readonly store = inject(ProjectService);
  private readonly cloud = inject(CloudWorkspaceService);
  private readonly toast = inject(ToastService);
  saving = false;

  ngOnInit(): void {
    this.cloud.listProjects().then(({ projects }) => {
      const mapped: Project[] = projects.map((project) => ({
        id: project.id,
        name: project.name,
        description: project.description,
        status: project.status,
        phase: project.phase,
        progress: project.progress,
        platforms: project.platforms,
        updatedAt: project.updatedAt ? new Date(project.updatedAt).toLocaleDateString('ar') : '—',
      }));
      this.store.replaceProjectsFromCloud(mapped);
    }).catch(() => {
      this.toast.error('تعذر تحميل المشاريع السحابية؛ يتم عرض البيانات المحلية الحالية.');
    });
  }

  readonly projects = this.store.projects;
  readonly activeId = this.store.activeProjectId;
  showCreate = false;
  search = '';
  newName = 'مشروع جديد';
  newDesc = '';
  platforms = { android: true, ios: false, web: true, desktop: false };

  readonly filtered = computed(() => {
    const q = this.search.trim();
    const list = this.projects();
    if (!q) return list;
    return list.filter((p) => p.name.includes(q) || p.description.includes(q));
  });

  statusLabel(s: ProjectStatus): string {
    return ({ active: 'نشط', draft: 'مسودة', archived: 'مؤرشف' } as const)[s];
  }
  statusClass(s: ProjectStatus): string {
    return ({ active: 'badge-success', draft: 'badge-primary', archived: 'badge-muted' } as const)[s];
  }
  select(id: string): void { this.store.selectProject(id); }
  async create(): Promise<void> {
    if (this.saving) return;
    const selected: Platform[] = [];
    if (this.platforms.android) selected.push('Android');
    if (this.platforms.ios) selected.push('iOS');
    if (this.platforms.web) selected.push('Web');
    if (this.platforms.desktop) selected.push('Desktop');
    const input = {
      name: this.newName.trim() || 'مشروع جديد',
      description: this.newDesc.trim() || 'بدون وصف',
      platforms: selected.length ? selected : ['Web'] as Platform[],
    };
    this.saving = true;
    try {
      const { projectId } = await this.cloud.createProject(input);
      const project: Project = {
        id: projectId,
        ...input,
        status: 'draft',
        phase: 0,
        progress: 0,
        updatedAt: new Date().toLocaleDateString('ar'),
      };
      this.store.addCloudProject(project);
      this.showCreate = false;
      this.toast.success('تم إنشاء المشروع في Firebase.');
    } catch (error) {
      this.toast.error(error instanceof Error ? error.message : 'تعذر إنشاء المشروع السحابي.');
    } finally {
      this.saving = false;
    }
  }
}
