import { Component, inject } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { RouterLink } from '@angular/router';
import { StoreService } from '../../services/store.service';
import { ToastService } from '../../../core/toast.service';
import { OrderStatus } from '../../services/store.models';

@Component({
  selector: 'app-seller',
  standalone: true,
  imports: [FormsModule, RouterLink],
  templateUrl: './seller.component.html',
  styleUrl: './seller.component.scss',
})
export class SellerComponent {
  readonly store = inject(StoreService);
  private readonly toast = inject(ToastService);

  form = {
    id: '' as string | undefined,
    name: '',
    description: '',
    price: 50,
    stock: 10,
    category: 'إلكترونيات',
    emoji: '📦',
    active: true,
  };

  save(): void {
    const r = this.store.saveProduct({
      id: this.form.id || undefined,
      name: this.form.name,
      description: this.form.description,
      price: Number(this.form.price),
      stock: Number(this.form.stock),
      category: this.form.category,
      emoji: this.form.emoji,
      active: this.form.active,
    });
    if (r.ok) {
      this.toast.success(r.message);
      this.form = { id: undefined, name: '', description: '', price: 50, stock: 10, category: 'إلكترونيات', emoji: '📦', active: true };
    } else this.toast.error(r.message);
  }

  edit(id: string): void {
    const p = this.store.getProduct(id);
    if (!p) return;
    this.form = {
      id: p.id,
      name: p.name,
      description: p.description,
      price: p.price,
      stock: p.stock,
      category: p.category,
      emoji: p.emoji,
      active: p.active,
    };
  }

  remove(id: string): void {
    const r = this.store.deleteProduct(id);
    if (r.ok) this.toast.success(r.message);
    else this.toast.error(r.message);
  }

  setStatus(orderId: string, status: OrderStatus): void {
    const r = this.store.updateOrderStatus(orderId, status);
    if (r.ok) this.toast.success(r.message);
    else this.toast.error(r.message);
  }
}
