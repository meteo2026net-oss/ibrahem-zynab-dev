import { Component, inject } from '@angular/core';
import { RouterLink, RouterLinkActive, RouterOutlet } from '@angular/router';
import { ProjectService } from '../core/project.service';

interface NavItem {
  path: string;
  label: string;
  icon: string;
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

  readonly centers: NavItem[] = [
    { path: '/home', label: 'الرئيسية', icon: '🏠' },
    { path: '/projects', label: '01 المشاريع', icon: '📁' },
    { path: '/analysis', label: '02 التحليل', icon: '🔍' },
    { path: '/design', label: '03 التصميم', icon: '🎨' },
    { path: '/functions', label: '04 الوظائف', icon: '⚡' },
    { path: '/data', label: '05 البيانات', icon: '🗄️' },
    { path: '/agents', label: '06 الوكلاء', icon: '🤖' },
    { path: '/build', label: '07 البناء', icon: '📦' },
    { path: '/test', label: '08 الاختبار', icon: '🧪' },
    { path: '/release', label: '09 الإطلاق', icon: '🚀' },
    { path: '/monitor', label: '10 المراقبة', icon: '📊' },
    { path: '/security', label: '11 الأمان', icon: '🔒' },
    { path: '/settings', label: '12 الإعدادات', icon: '⚙️' },
    { path: '/collab', label: '13 التعاون', icon: '👥' },
    { path: '/reports', label: '14 التقارير', icon: '📈' },
  ];
}
