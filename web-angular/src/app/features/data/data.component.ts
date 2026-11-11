import { Component, inject } from '@angular/core';
import { ProjectService } from '../../core/project.service';

@Component({
  selector: 'app-data',
  standalone: true,
  imports: [],
  templateUrl: './data.component.html',
  styleUrl: './data.component.scss',
})
export class DataComponent {
  readonly store = inject(ProjectService);
}
