import { Component } from '@angular/core';
import { NgClass } from '@angular/common';
import { FormsModule } from '@angular/forms';

interface Project {
  id: string;
  name: string;
  description: string;
  status: 'active' | 'draft' | 'archived';
  phase: number;
  progress: number;
  platforms: string[];
  updatedAt: string;
}

@Component({
  selector: 'app-projects',
  standalone: true,
  imports: [NgClass, FormsModule],
  templateUrl: './projects.component.html',
  styleUrl: './projects.component.scss',
})
export class ProjectsComponent {
  showCreate = false;
  search = '';
  newProject = {
    name: 'متجري',
    description: 'تطبيق لبيع المنتجات مع سلة وطلبات ولوحة بائع',
    platforms: { android: true, ios: false, web: true, desktop: false },
  };

  projects: Project[] = [
    {
      id: '1',
      name: 'متجري',
      description: 'تطبيق تجارة إلكترونية — منتجات، سلة، طلبات، لوحة بائع',
      status: 'active',
      phase: 1,
      progress: 18,
      platforms: ['Android', 'Web'],
      updatedAt: 'اليوم',
    },
    {
      id: '2',
      name: 'نظام الحجوزات',
      description: 'حجز مواعيد عيادات مع تذكيرات',
      status: 'draft',
      phase: 0,
      progress: 5,
      platforms: ['Web'],
      updatedAt: 'أمس',
    },
    {
      id: '3',
      name: 'تطبيق التوصيل',
      description: 'تتبع طلبات التوصيل للسائقين',
      status: 'archived',
      phase: 4,
      progress: 42,
      platforms: ['Android'],
      updatedAt: 'قبل أسبوع',
    },
  ];

  statusLabel(s: Project['status']): string {
    return ({ active: 'نشط', draft: 'مسودة', archived: 'مؤرشف' } as const)[s];
  }

  statusClass(s: Project['status']): string {
    return ({ active: 'badge-success', draft: 'badge-primary', archived: 'badge-muted' } as const)[s];
  }

  create(): void {
    this.projects.unshift({
      id: String(Date.now()),
      name: this.newProject.name || 'مشروع جديد',
      description: this.newProject.description,
      status: 'draft',
      phase: 0,
      progress: 0,
      platforms: Object.entries(this.newProject.platforms)
        .filter(([, v]) => v)
        .map(([k]) => ({ android: 'Android', ios: 'iOS', web: 'Web', desktop: 'Desktop' }[k]!)),
      updatedAt: 'الآن',
    });
    this.showCreate = false;
  }
}
