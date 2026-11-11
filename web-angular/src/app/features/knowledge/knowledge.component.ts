import { Component, inject } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { DatePipe, KeyValuePipe } from '@angular/common';
import { KnowledgeService, KnowledgeDoc } from '../../core/knowledge.service';
import { ProjectService } from '../../core/project.service';
import { ToastService } from '../../core/toast.service';

@Component({
  selector: 'app-knowledge',
  standalone: true,
  imports: [FormsModule, DatePipe],
  templateUrl: './knowledge.component.html',
  styleUrl: './knowledge.component.scss',
})
export class KnowledgeComponent {
  readonly knowledge = inject(KnowledgeService);
  readonly projects = inject(ProjectService);
  private readonly toast = inject(ToastService);

  editing: KnowledgeDoc | null = null;
  form = { title: '', category: 'idea' as KnowledgeDoc['category'], body: '' };

  categories = Object.entries(this.knowledge.categoryLabels) as [KnowledgeDoc['category'], string][];

  startNew(): void {
    this.editing = null;
    this.form = { title: '', category: 'idea', body: '' };
  }

  edit(doc: KnowledgeDoc): void {
    this.editing = doc;
    this.form = { title: doc.title, category: doc.category, body: doc.body };
  }

  save(): void {
    if (!this.form.title.trim() || !this.form.body.trim()) {
      this.toast.error('العنوان والمحتوى مطلوبان');
      return;
    }
    if (this.editing) {
      this.knowledge.update(this.editing.id, {
        title: this.form.title,
        category: this.form.category,
        body: this.form.body,
        status: 'reviewed',
      });
      this.toast.success('تم تحديث الوثيقة');
    } else {
      this.knowledge.add({
        title: this.form.title,
        category: this.form.category,
        body: this.form.body,
        status: 'reviewed',
      });
      this.toast.success('تمت إضافة الوثيقة');
    }
    this.startNew();
  }

  exportMd(): void {
    const md = this.knowledge.exportMarkdown();
    const blob = new Blob([md], { type: 'text/markdown;charset=utf-8' });
    const url = URL.createObjectURL(blob);
    const a = document.createElement('a');
    a.href = url;
    a.download = `knowledge-${this.projects.activeProject()?.name || 'project'}.md`;
    a.click();
    URL.revokeObjectURL(url);
    this.toast.success('تم تصدير Markdown');
  }

  statusLabel(s: string): string {
    return s === 'reviewed' ? 'مراجع' : s === 'stale' ? 'قديم' : 'مسودة';
  }
  statusClass(s: string): string {
    return s === 'reviewed' ? 'badge-success' : s === 'stale' ? 'badge-warning' : 'badge-muted';
  }
}
