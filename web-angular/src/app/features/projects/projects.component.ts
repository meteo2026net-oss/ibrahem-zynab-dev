import { Component, inject, computed, signal } from '@angular/core';
import { NgClass } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { ProjectService } from '../../core/project.service';
import { Platform, ProjectStatus } from '../../core/models';

@Component({
  selector: 'app-projects',
  standalone: true,
  imports: [NgClass, FormsModule],
  templateUrl: './projects.component.html',
  styleUrl: './projects.component.scss',
})
export class ProjectsComponent {
  private readonly store = inject(ProjectService);

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
    return list.filter(
      (p) => p.name.includes(q) || p.description.includes(q)
    );
  });

  statusLabel(s: ProjectStatus): string {
    return ({ active: 'نشط', draft: 'مسودة', archived: 'مؤرشف' } as const)[s];
  }

  statusClass(s: ProjectStatus): string {
    return ({ active: 'badge-success', draft: 'badge-primary', archived: 'badge-muted' } as const)[s];
  }

  select(id: string): void {
    this.store.selectProject(id);
  }

  create(): void {
    const selected: Platform[] = [];
    if (this.platforms.android) selected.push('Android');
    if (this.platforms.ios) selected.push('iOS');
    if (this.platforms.web) selected.push('Web');
    if (this.platforms.desktop) selected.push('Desktop');
    this.store.addProject({
      name: this.newName || 'مشروع جديد',
      description: this.newDesc || 'بدون وصف',
      platforms: selected.length ? selected : ['Web'],
    });
    this.showCreate = false;
    this.newName = 'مشروع جديد';
    this.newDesc = '';
  }
}
