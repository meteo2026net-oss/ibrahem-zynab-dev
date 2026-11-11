import { Component, inject } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { ProjectService } from '../../core/project.service';

@Component({
  selector: 'app-settings',
  standalone: true,
  imports: [FormsModule],
  templateUrl: './settings.component.html',
  styleUrl: './settings.component.scss',
})
export class SettingsComponent {
  readonly store = inject(ProjectService);
  language = this.store.settings().language;
  timezone = this.store.settings().timezone;
  notifyBuild = this.store.settings().notifyBuild;
  notifyRelease = this.store.settings().notifyRelease;
  notifyComments = this.store.settings().notifyComments;
  saved = false;

  save(): void {
    this.store.updateSettings({
      language: this.language as 'ar' | 'en',
      timezone: this.timezone,
      notifyBuild: this.notifyBuild,
      notifyRelease: this.notifyRelease,
      notifyComments: this.notifyComments,
    });
    this.saved = true;
    setTimeout(() => (this.saved = false), 2000);
  }

  reset(): void {
    if (confirm('إعادة ضبط بيانات العرض التجريبية؟')) {
      this.store.resetDemoData();
      location.reload();
    }
  }
}
