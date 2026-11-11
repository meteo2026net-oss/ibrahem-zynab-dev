import { Component } from '@angular/core';
import { FormsModule } from '@angular/forms';
@Component({
  selector: 'app-data',
  standalone: true,
  imports: [FormsModule],
  templateUrl: './data.component.html',
  styleUrl: './data.component.scss',
})
export class DataComponent { title = 'مركز البيانات'; }
