import { Component, inject, computed } from '@angular/core';
import { ProjectService } from '../../core/project.service';
import { ToastService } from '../../core/toast.service';
import { AuditService } from '../../core/audit.service';

@Component({
  selector: 'app-code',
  standalone: true,
  imports: [],
  templateUrl: './code.component.html',
  styleUrl: './code.component.scss',
})
export class CodeComponent {
  readonly store = inject(ProjectService);
  private readonly toast = inject(ToastService);
  private readonly audit = inject(AuditService);

  selectedPath = '';
  selectedContent = '';

  readonly tree = computed(() => {
    const p = this.store.activeProject();
    const name = (p?.name || 'app').replace(/\s+/g, '-').toLowerCase();
    const pages = this.store.designPages();
    const entities = this.store.entities();
    const files: { path: string; content: string }[] = [
      {
        path: `${name}/README.md`,
        content: `# ${p?.name}\n\n${p?.description}\n\nالمنصات: ${p?.platforms?.join(', ')}\n`,
      },
      {
        path: `${name}/src/app/app.routes.ts`,
        content:
          pages.map((pg) => `// route: /${pg.name.replace(/\s+/g, '-')}`).join('\n') ||
          '// no pages yet',
      },
    ];
    for (const pg of pages) {
      const slug = pg.name.replace(/\s+/g, '-');
      files.push({
        path: `${name}/src/app/pages/${slug}/${slug}.page.ts`,
        content: `// صفحة: ${pg.name}\n// منصة: ${pg.platform}\nexport class ${slug.replace(/-/g, '')}Page {}\n`,
      });
    }
    for (const e of entities) {
      files.push({
        path: `${name}/src/app/models/${e.name}.ts`,
        content:
          `export interface ${e.name} {\n` +
          e.fields.map((f) => `  ${f.name}: ${f.type}; // ${f.notes}`).join('\n') +
          `\n}\n`,
      });
    }
    files.push({
      path: `${name}/src/app/services/api.service.ts`,
      content: `// خدمات مشتقة من المتطلبات المعتمدة\n// ${this.store.requirements().filter((r) => r.approved).map((r) => r.text).join('\n// ')}\n`,
    });
    return files;
  });

  open(path: string, content: string): void {
    this.selectedPath = path;
    this.selectedContent = content;
  }

  downloadAll(): void {
    const bundle = this.tree()
      .map((f) => `===== ${f.path} =====\n${f.content}`)
      .join('\n\n');
    const blob = new Blob([bundle], { type: 'text/plain;charset=utf-8' });
    const url = URL.createObjectURL(blob);
    const a = document.createElement('a');
    a.href = url;
    a.download = `${this.store.activeProject()?.name || 'project'}-code-skeleton.txt`;
    a.click();
    URL.revokeObjectURL(url);
    this.audit.log('user', 'export_code_skeleton', this.store.activeProject()?.name || '', 'success');
    this.toast.success('تم تنزيل هيكل الكود');
  }
}
