import { Component, inject, ChangeDetectionStrategy } from '@angular/core';
import { ProjectService } from '../../core/project.service';

@Component({
  selector: 'app-test',
  standalone: true,
  imports: [],
  templateUrl: './test.component.html',
  changeDetection: ChangeDetectionStrategy.Eager,
  styleUrl: './test.component.scss',
})
export class TestComponent {
  readonly store = inject(ProjectService);
  label(r: string): string {
    return r === 'pass' ? 'نجح' : r === 'fail' ? 'فشل' : 'لم يُنفَّذ';
  }
  cls(r: string): string {
    return r === 'pass' ? 'badge-success' : r === 'fail' ? 'badge-danger' : 'badge-muted';
  }
}
