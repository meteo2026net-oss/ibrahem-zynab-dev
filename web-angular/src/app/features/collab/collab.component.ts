import { Component } from '@angular/core';
import { FormsModule } from '@angular/forms';
@Component({
  selector: 'app-collab',
  standalone: true,
  imports: [FormsModule],
  templateUrl: './collab.component.html',
  styleUrl: './collab.component.scss',
})
export class CollabComponent { title = 'مركز التعاون'; }
