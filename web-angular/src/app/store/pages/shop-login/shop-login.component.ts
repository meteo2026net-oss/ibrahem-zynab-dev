import { Component, inject } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { Router, RouterLink } from '@angular/router';
import { StoreService } from '../../services/store.service';
import { ToastService } from '../../../core/toast.service';

@Component({
  selector: 'app-shop-login',
  standalone: true,
  imports: [FormsModule, RouterLink],
  templateUrl: './shop-login.component.html',
  styleUrl: './shop-login.component.scss',
})
export class ShopLoginComponent {
  private readonly store = inject(StoreService);
  private readonly toast = inject(ToastService);
  private readonly router = inject(Router);

  mode: 'login' | 'register' = 'login';
  email = 'customer@matjari.demo';
  password = '123456';
  name = '';
  role: 'customer' | 'seller' = 'customer';

  submit(): void {
    if (this.mode === 'login') {
      const r = this.store.login(this.email, this.password);
      if (r.ok) {
        this.toast.success(r.message);
        this.router.navigateByUrl('/shop');
      } else this.toast.error(r.message);
    } else {
      const r = this.store.register(this.name, this.email, this.password, this.role);
      if (r.ok) {
        this.toast.success(r.message);
        this.router.navigateByUrl('/shop');
      } else this.toast.error(r.message);
    }
  }
}
