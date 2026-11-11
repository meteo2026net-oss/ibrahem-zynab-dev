import { Component, inject } from '@angular/core';
import { ProjectService } from '../../core/project.service';

@Component({
  selector: 'app-security',
  standalone: true,
  imports: [],
  templateUrl: './security.component.html',
  styleUrl: './security.component.scss',
})
export class SecurityComponent {
  readonly store = inject(ProjectService);
}
