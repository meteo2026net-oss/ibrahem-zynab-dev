import { Component, inject, computed } from '@angular/core';
import { ActivatedRoute, Router, RouterLink } from '@angular/router';
import { FormsModule } from '@angular/forms';
import { StoreService } from '../../services/store.service';
import { ToastService } from '../../../core/toast.service';

@Component({
  selector: 'app-product',
  standalone: true,
  imports: [RouterLink, FormsModule],
  templateUrl: './product.component.html',
  styleUrl: './product.component.scss',
})
export class ProductComponent {
  private readonly route = inject(ActivatedRoute);
  private readonly router = inject(Router);
  readonly store = inject(StoreService);
  private readonly toast = inject(ToastService);
  qty = 1;

  readonly product = computed(() => {
    const id = this.route.snapshot.paramMap.get('id') || '';
    return this.store.getProduct(id);
  });

  add(): void {
    const p = this.product();
    if (!p) return;
    if (!this.store.isLoggedIn()) {
      this.toast.error('سجّل الدخول أولاً');
      this.router.navigateByUrl('/shop/login');
      return;
    }
    const r = this.store.addToCart(p.id, this.qty);
    if (r.ok) this.toast.success(r.message);
    else this.toast.error(r.message);
  }
}
