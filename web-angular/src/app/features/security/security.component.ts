import { Component, inject } from '@angular/core';
import { DatePipe } from '@angular/common';
import { ProjectService } from '../../core/project.service';
import { AuditService } from '../../core/audit.service';

@Component({
  selector: 'app-security',
  standalone: true,
  imports: [DatePipe],
  templateUrl: './security.component.html',
  styleUrl: './security.component.scss',
})
export class SecurityComponent {
  readonly store = inject(ProjectService);
  readonly audit = inject(AuditService);
}
