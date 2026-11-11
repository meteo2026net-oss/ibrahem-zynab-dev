import { Component, inject } from '@angular/core';
import { RouterLink, RouterLinkActive, RouterOutlet, Router } from '@angular/router';
import { StoreService } from '../services/store.service';

@Component({
  selector: 'app-store-shell',
  standalone: true,
  imports: [RouterOutlet, RouterLink, RouterLinkActive],
  templateUrl: './store-shell.component.html',
  styleUrl: './store-shell.component.scss',
})
export class StoreShellComponent {
  readonly store = inject(StoreService);
  private readonly router = inject(Router);

  logout(): void {
    this.store.logout();
    this.router.navigateByUrl('/shop/login');
  }
}
