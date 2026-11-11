import { Component, inject } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { ProjectService } from '../../core/project.service';

@Component({
  selector: 'app-design',
  standalone: true,
  imports: [FormsModule],
  templateUrl: './design.component.html',
  styleUrl: './design.component.scss',
})
export class DesignComponent {
  readonly store = inject(ProjectService);
  newPageName = '';
  platform = 'Android';

  addPage(): void {
    if (!this.newPageName.trim()) return;
    this.store.addDesignPage(this.newPageName.trim());
    this.newPageName = '';
  }
}
