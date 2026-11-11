import { Component, inject, computed } from '@angular/core';
import { ProjectService } from '../../core/project.service';

@Component({
  selector: 'app-test',
  standalone: true,
  imports: [],
  templateUrl: './test.component.html',
  styleUrl: './test.component.scss',
})
export class TestComponent {
  readonly store = inject(ProjectService);
  readonly summary = computed(() => {
    const t = this.store.tests();
    return {
      pass: t.filter((x) => x.result === 'pass').length,
      fail: t.filter((x) => x.result === 'fail').length,
      pending: t.filter((x) => x.result === 'pending').length,
    };
  });

  label(r: string): string {
    return r === 'pass' ? 'نجح' : r === 'fail' ? 'فشل' : 'لم يُنفَّذ';
  }
  cls(r: string): string {
    return r === 'pass' ? 'badge-success' : r === 'fail' ? 'badge-danger' : 'badge-muted';
  }
}
