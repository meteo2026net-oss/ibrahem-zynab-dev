import { Component } from '@angular/core';
import { } from '@angular/common';
import { FormsModule } from '@angular/forms';

@Component({
  selector: 'app-design',
  standalone: true,
  imports: [FormsModule],
  templateUrl: './design.component.html',
  styleUrl: './design.component.scss',
})
export class DesignComponent {
  title = 'مركز التصميم';
}
