import { Component, inject } from '@angular/core';
import { Router, RouterLink, RouterLinkActive, RouterOutlet } from '@angular/router';
import { ProjectService } from '../core/project.service';
import { AuthService } from '../core/auth.service';
import { ToastService } from '../core/toast.service';

interface NavItem {
  path: string;
  label: string;
  icon: string;
  section?: string;
}

@Component({
  selector: 'app-shell',
  standalone: true,
  imports: [RouterOutlet, RouterLink, RouterLinkActive],
  templateUrl: './shell.component.html',
  styleUrl: './shell.component.scss',
})
export class ShellComponent {
  readonly store = inject(ProjectService);
  readonly auth = inject(AuthService);
  readonly toast = inject(ToastService);
  private readonly router = inject(Router);

  readonly centers: NavItem[] = [
    { path: '/home', label: 'الرئيسية', icon: '🏠', section: 'عام' },
    { path: '/shop', label: 'متجري (حقيقي)', icon: '🛒', section: 'عام' },
    { path: '/projects', label: 'المشاريع', icon: '📁', section: 'عام' },
    { path: '/analysis', label: 'التحليل', icon: '🔍', section: 'البناء' },
    { path: '/design', label: 'التصميم', icon: '🎨', section: 'البناء' },
    { path: '/functions', label: 'الوظائف', icon: '⚡', section: 'البناء' },
    { path: '/data', label: 'البيانات', icon: '🗄️', section: 'البناء' },
    { path: '/agents', label: 'الوكلاء', icon: '🤖', section: 'البناء' },
    { path: '/build', label: 'البناء', icon: '📦', section: 'البناء' },
    { path: '/test', label: 'الاختبار', icon: '🧪', section: 'الجودة' },
    { path: '/release', label: 'الإطلاق', icon: '🚀', section: 'الجودة' },
    { path: '/monitor', label: 'المراقبة', icon: '📊', section: 'الجودة' },
    { path: '/security', label: 'الأمان', icon: '🔒', section: 'الحوكمة' },
    { path: '/collab', label: 'التعاون', icon: '👥', section: 'الحوكمة' },
    { path: '/reports', label: 'التقارير', icon: '📈', section: 'الحوكمة' },
    { path: '/settings', label: 'الإعدادات', icon: '⚙️', section: 'الحوكمة' },
  ];

  sectionOf(index: number): string | null {
    const cur = this.centers[index]?.section;
    const prev = index > 0 ? this.centers[index - 1]?.section : null;
    return cur !== prev ? cur ?? null : null;
  }

  logout(): void {
    this.auth.logout();
    this.router.navigateByUrl('/login');
  }
}
