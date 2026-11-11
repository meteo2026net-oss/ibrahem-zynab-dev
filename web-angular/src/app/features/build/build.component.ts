import { Component, inject } from '@angular/core';
import { ProjectService } from '../../core/project.service';

@Component({
  selector: 'app-build',
  standalone: true,
  imports: [],
  templateUrl: './build.component.html',
  styleUrl: './build.component.scss',
})
export class BuildComponent {
  readonly store = inject(ProjectService);
  platform = 'Android';

  build(): void {
    this.store.simulateBuild();
  }
}
