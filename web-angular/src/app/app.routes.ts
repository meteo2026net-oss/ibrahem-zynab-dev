import { Routes } from '@angular/router';
import { ShellComponent } from './layout/shell.component';
import { authGuard } from './core/auth.guard';

export const routes: Routes = [
  {
    path: 'login',
    loadComponent: () => import('./features/login/login.component').then((m) => m.LoginComponent),
  },
  {
    path: 'shop',
    loadComponent: () => import('./store/layout/store-shell.component').then((m) => m.StoreShellComponent),
    children: [
      { path: '', loadComponent: () => import('./store/pages/shop/shop.component').then((m) => m.ShopComponent) },
      { path: 'product/:id', loadComponent: () => import('./store/pages/product/product.component').then((m) => m.ProductComponent) },
      { path: 'cart', loadComponent: () => import('./store/pages/cart/cart.component').then((m) => m.CartComponent) },
      { path: 'checkout', loadComponent: () => import('./store/pages/checkout/checkout.component').then((m) => m.CheckoutComponent) },
      { path: 'orders', loadComponent: () => import('./store/pages/orders/orders.component').then((m) => m.OrdersComponent) },
      { path: 'seller', loadComponent: () => import('./store/pages/seller/seller.component').then((m) => m.SellerComponent) },
      { path: 'login', loadComponent: () => import('./store/pages/shop-login/shop-login.component').then((m) => m.ShopLoginComponent) },
    ],
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
      { path: 'code', loadComponent: () => import('./features/code/code.component').then(m => m.CodeComponent) },
      { path: 'test', loadComponent: () => import('./features/test/test.component').then(m => m.TestComponent) },
      { path: 'release', loadComponent: () => import('./features/release/release.component').then(m => m.ReleaseComponent) },
      { path: 'monitor', loadComponent: () => import('./features/monitor/monitor.component').then(m => m.MonitorComponent) },
      { path: 'security', loadComponent: () => import('./features/security/security.component').then(m => m.SecurityComponent) },
      { path: 'settings', loadComponent: () => import('./features/settings/settings.component').then(m => m.SettingsComponent) },
      { path: 'collab', loadComponent: () => import('./features/collab/collab.component').then(m => m.CollabComponent) },
      { path: 'reports', loadComponent: () => import('./features/reports/reports.component').then(m => m.ReportsComponent) },
      { path: 'knowledge', loadComponent: () => import('./features/knowledge/knowledge.component').then(m => m.KnowledgeComponent) },
      { path: 'integrations', loadComponent: () => import('./features/integrations/integrations.component').then(m => m.IntegrationsComponent) },
      { path: 'wizard', loadComponent: () => import('./features/wizard/wizard.component').then(m => m.WizardComponent) },
      { path: 'pages', loadComponent: () => import('./features/pages-manager/pages-manager.component').then(m => m.PagesManagerComponent) },
      { path: 'media', loadComponent: () => import('./features/media/media.component').then(m => m.MediaComponent) },

    ],
  },
  { path: '**', redirectTo: 'home' },
];
