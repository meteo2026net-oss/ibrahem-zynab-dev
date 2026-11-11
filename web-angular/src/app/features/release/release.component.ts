import { Component } from '@angular/core';
import { FormsModule } from '@angular/forms';
@Component({
  selector: 'app-release',
  standalone: true,
  imports: [FormsModule],
  templateUrl: './release.component.html',
  styleUrl: './release.component.scss',
})
export class ReleaseComponent { title = 'مركز الإطلاق'; }
