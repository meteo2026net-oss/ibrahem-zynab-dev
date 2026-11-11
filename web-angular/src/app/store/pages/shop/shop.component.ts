import { Component, inject, signal, computed } from '@angular/core';
import { RouterLink } from '@angular/router';
import { FormsModule } from '@angular/forms';
import { StoreService } from '../../services/store.service';
import { ToastService } from '../../../core/toast.service';

@Component({
  selector: 'app-shop',
  standalone: true,
  imports: [RouterLink, FormsModule],
  templateUrl: './shop.component.html',
  styleUrl: './shop.component.scss',
})
export class ShopComponent {
  readonly store = inject(StoreService);
  private readonly toast = inject(ToastService);
  search = '';
  category = 'الكل';

  readonly categories = computed(() => {
    const set = new Set(this.store.products().map((p) => p.category));
    return ['الكل', ...Array.from(set)];
  });

  readonly filtered = computed(() => {
    let list = this.store.products();
    if (this.category !== 'الكل') list = list.filter((p) => p.category === this.category);
    const q = this.search.trim();
    if (q) list = list.filter((p) => p.name.includes(q) || p.description.includes(q));
    return list;
  });

  // triggers recomputation when search/category change via template ngModel - need signals
  searchSig = signal('');
  catSig = signal('الكل');

  onSearch(v: string): void { this.search = v; this.searchSig.set(v); }
  onCat(v: string): void { this.category = v; this.catSig.set(v); }

  readonly filtered2 = computed(() => {
    this.searchSig(); this.catSig();
    let list = this.store.products();
    if (this.category !== 'الكل') list = list.filter((p) => p.category === this.category);
    const q = this.search.trim();
    if (q) list = list.filter((p) => p.name.includes(q) || p.description.includes(q));
    return list;
  });

  add(id: string): void {
    if (!this.store.isLoggedIn()) {
      this.toast.error('سجّل الدخول أولاً لإضافة للسلة');
      return;
    }
    const r = this.store.addToCart(id, 1);
    if (r.ok) this.toast.success(r.message);
    else this.toast.error(r.message);
  }
}
