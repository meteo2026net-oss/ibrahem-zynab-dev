import { Component, inject } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { RouterLink } from '@angular/router';
import { ProjectService } from '../../core/project.service';
import { APP_SCREENS } from '../../core/catalog';
import { ToastService } from '../../core/toast.service';

@Component({
  selector: 'app-pages-manager',
  standalone: true,
  imports: [FormsModule, RouterLink],
  templateUrl: './pages-manager.component.html',
  styleUrl: './pages-manager.component.scss',
})
export class PagesManagerComponent {
  readonly store = inject(ProjectService);
  private readonly toast = inject(ToastService);
  readonly catalog = APP_SCREENS;
  newName = '';

  add(): void {
    if (!this.newName.trim()) return;
    this.store.addDesignPage(this.newName.trim());
    this.toast.success('أُضيفت الصفحة');
    this.newName = '';
  }

  importCatalog(): void {
    for (const s of APP_SCREENS) {
      if (!this.store.designPages().some((p) => p.name === s.name)) {
        this.store.addDesignPage(s.name);
      }
    }
    this.toast.success('استيراد كتالوج شاشات متجري');
  }
}
