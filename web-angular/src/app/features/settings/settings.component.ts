import { Component, inject } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { ProjectService } from '../../core/project.service';
import { ToastService } from '../../core/toast.service';

@Component({
  selector: 'app-settings',
  standalone: true,
  imports: [FormsModule],
  templateUrl: './settings.component.html',
  styleUrl: './settings.component.scss',
})
export class SettingsComponent {
  readonly store = inject(ProjectService);
  private readonly toast = inject(ToastService);

  language = this.store.settings().language;
  timezone = this.store.settings().timezone;
  notifyBuild = this.store.settings().notifyBuild;
  notifyRelease = this.store.settings().notifyRelease;
  notifyComments = this.store.settings().notifyComments;
  uiMode: 'simple' | 'pro' = (localStorage.getItem('iad-ui-mode') as 'simple' | 'pro') || 'pro';
  saved = false;

  save(): void {
    this.store.updateSettings({
      language: this.language as 'ar' | 'en',
      timezone: this.timezone,
      notifyBuild: this.notifyBuild,
      notifyRelease: this.notifyRelease,
      notifyComments: this.notifyComments,
    });
    localStorage.setItem('iad-ui-mode', this.uiMode);
    this.saved = true;
    this.toast.success('تم حفظ الإعدادات');
    setTimeout(() => (this.saved = false), 2000);
  }

  exportProject(): void {
    const data = {
      project: this.store.activeProject(),
      idea: this.store.ideaText(),
      requirements: this.store.requirements(),
      entities: this.store.entities(),
      pages: this.store.designPages(),
      tests: this.store.tests(),
      exportedAt: new Date().toISOString(),
    };
    const blob = new Blob([JSON.stringify(data, null, 2)], { type: 'application/json' });
    const url = URL.createObjectURL(blob);
    const a = document.createElement('a');
    a.href = url;
    a.download = `${data.project?.name || 'project'}-export.json`;
    a.click();
    URL.revokeObjectURL(url);
    this.toast.success('تم تصدير حزمة المشروع');
  }

  reset(): void {
    if (confirm('إعادة ضبط بيانات العرض التجريبية؟')) {
      this.store.resetDemoData();
      location.reload();
    }
  }
}
