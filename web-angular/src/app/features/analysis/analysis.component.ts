import { Component } from '@angular/core';
import { } from '@angular/common';
import { FormsModule } from '@angular/forms';

@Component({
  selector: 'app-analysis',
  standalone: true,
  imports: [FormsModule],
  templateUrl: './analysis.component.html',
  styleUrl: './analysis.component.scss',
})
export class AnalysisComponent {
  title = 'مركز التحليل';
}
