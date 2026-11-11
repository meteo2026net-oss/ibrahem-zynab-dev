import { Component, inject } from '@angular/core';
import { Router, RouterLink } from '@angular/router';
import { FormsModule } from '@angular/forms';
import { StoreService } from '../../services/store.service';
import { ToastService } from '../../../core/toast.service';

@Component({
  selector: 'app-checkout',
  standalone: true,
  imports: [FormsModule, RouterLink],
  templateUrl: './checkout.component.html',
  styleUrl: './checkout.component.scss',
})
export class CheckoutComponent {
  readonly store = inject(StoreService);
  private readonly toast = inject(ToastService);
  private readonly router = inject(Router);
  address = 'الرياض، حي النرجس';
  phone = '05';

  submit(): void {
    if (!this.store.isLoggedIn()) {
      this.toast.error('يجب تسجيل الدخول');
      this.router.navigateByUrl('/shop/login');
      return;
    }
    const r = this.store.placeOrder(this.address, this.phone);
    if (r.ok) {
      this.toast.success(r.message + (r.orderId ? ' — ' + r.orderId : ''));
      this.router.navigateByUrl('/shop/orders');
    } else {
      this.toast.error(r.message);
    }
  }
}
