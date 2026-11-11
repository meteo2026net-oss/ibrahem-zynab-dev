import { Component, inject } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { RouterLink } from '@angular/router';
import { ProjectService } from '../../core/project.service';
import { ToastService } from '../../core/toast.service';
import { APP_SCREENS } from '../../core/catalog';

@Component({
  selector: 'app-analysis',
  standalone: true,
  imports: [FormsModule, RouterLink],
  templateUrl: './analysis.component.html',
  styleUrl: './analysis.component.scss',
})
export class AnalysisComponent {
  readonly store = inject(ProjectService);
  readonly toast = inject(ToastService);
  readonly screens = APP_SCREENS;

  idea = this.store.ideaText();
  analyzing = false;

  run(): void {
    this.analyzing = true;
    this.store.setIdeaText(this.idea);
    setTimeout(() => {
      this.store.runAnalysis();
      this.analyzing = false;
      this.toast.success('اكتمل التحليل — راجع المتطلبات');
    }, 700);
  }

  priorityLabel(p: string): string {
    return p === 'high' ? 'عالي' : p === 'medium' ? 'متوسط' : 'منخفض';
  }

  priorityClass(p: string): string {
    return p === 'high' ? 'badge-danger' : p === 'medium' ? 'badge-warning' : 'badge-muted';
  }
}
