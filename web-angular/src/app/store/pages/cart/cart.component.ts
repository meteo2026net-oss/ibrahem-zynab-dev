import { Component, inject } from '@angular/core';
import { RouterLink } from '@angular/router';
import { StoreService } from '../../services/store.service';
import { ToastService } from '../../../core/toast.service';

@Component({
  selector: 'app-cart',
  standalone: true,
  imports: [RouterLink],
  templateUrl: './cart.component.html',
  styleUrl: './cart.component.scss',
})
export class CartComponent {
  readonly store = inject(StoreService);
  private readonly toast = inject(ToastService);

  inc(id: string, q: number, stock: number): void {
    if (q + 1 > stock) { this.toast.error('تجاوز المخزون'); return; }
    this.store.setCartQty(id, q + 1);
  }
  dec(id: string, q: number): void {
    this.store.setCartQty(id, q - 1);
  }
  remove(id: string): void {
    this.store.removeFromCart(id);
    this.toast.info('تم الحذف من السلة');
  }
}
