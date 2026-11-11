import { Component, inject } from '@angular/core';
import { RouterLink } from '@angular/router';
import { DatePipe } from '@angular/common';
import { StoreService } from '../../services/store.service';

@Component({
  selector: 'app-orders',
  standalone: true,
  imports: [RouterLink, DatePipe],
  templateUrl: './orders.component.html',
  styleUrl: './orders.component.scss',
})
export class OrdersComponent {
  readonly store = inject(StoreService);

  statusLabel(s: string): string {
    return (
      {
        new: 'جديد',
        preparing: 'قيد التجهيز',
        shipped: 'تم الشحن',
        completed: 'مكتمل',
        cancelled: 'ملغى',
      } as Record<string, string>
    )[s] || s;
  }
  statusClass(s: string): string {
    return (
      {
        new: 'badge-primary',
        preparing: 'badge-warning',
        shipped: 'badge-primary',
        completed: 'badge-success',
        cancelled: 'badge-danger',
      } as Record<string, string>
    )[s] || 'badge-muted';
  }
}
