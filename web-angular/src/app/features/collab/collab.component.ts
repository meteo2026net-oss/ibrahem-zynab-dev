import { Component, inject, ChangeDetectionStrategy } from '@angular/core';
import { ProjectService } from '../../core/project.service';

@Component({
  selector: 'app-collab',
  standalone: true,
  imports: [],
  templateUrl: './collab.component.html',
  changeDetection: ChangeDetectionStrategy.Eager,
  styleUrl: './collab.component.scss',
})
export class CollabComponent {
  readonly store = inject(ProjectService);
}
