import { Routes } from '@angular/router';
import { ShellComponent } from './layout/shell.component';
import { authGuard } from './core/auth.guard';

export const routes: Routes = [
  {
    path: 'login',
    loadComponent: () => import('./features/login/login.component').then((m) => m.LoginComponent),
  },
  {
    path: '',
    component: ShellComponent,
    canActivate: [authGuard],
    children: [
      { path: '', pathMatch: 'full', redirectTo: 'home' },
      { path: 'home', loadComponent: () => import('./features/home/home.component').then(m => m.HomeComponent) },
      { path: 'projects', loadComponent: () => import('./features/projects/projects.component').then(m => m.ProjectsComponent) },
      { path: 'analysis', loadComponent: () => import('./features/analysis/analysis.component').then(m => m.AnalysisComponent) },
      { path: 'design', loadComponent: () => import('./features/design/design.component').then(m => m.DesignComponent) },
      { path: 'functions', loadComponent: () => import('./features/functions/functions.component').then(m => m.FunctionsComponent) },
      { path: 'data', loadComponent: () => import('./features/data/data.component').then(m => m.DataComponent) },
      { path: 'agents', loadComponent: () => import('./features/agents/agents.component').then(m => m.AgentsComponent) },
      { path: 'build', loadComponent: () => import('./features/build/build.component').then(m => m.BuildComponent) },
      { path: 'test', loadComponent: () => import('./features/test/test.component').then(m => m.TestComponent) },
      { path: 'release', loadComponent: () => import('./features/release/release.component').then(m => m.ReleaseComponent) },
      { path: 'monitor', loadComponent: () => import('./features/monitor/monitor.component').then(m => m.MonitorComponent) },
      { path: 'security', loadComponent: () => import('./features/security/security.component').then(m => m.SecurityComponent) },
      { path: 'settings', loadComponent: () => import('./features/settings/settings.component').then(m => m.SettingsComponent) },
      { path: 'collab', loadComponent: () => import('./features/collab/collab.component').then(m => m.CollabComponent) },
      { path: 'reports', loadComponent: () => import('./features/reports/reports.component').then(m => m.ReportsComponent) },
    ],
  },
  { path: '**', redirectTo: 'home' },
];
