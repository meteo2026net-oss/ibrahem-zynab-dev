import { Component } from '@angular/core';
import { FormsModule } from '@angular/forms';
@Component({
  selector: 'app-build',
  standalone: true,
  imports: [FormsModule],
  templateUrl: './build.component.html',
  styleUrl: './build.component.scss',
})
export class BuildComponent { title = 'مركز البناء'; }
