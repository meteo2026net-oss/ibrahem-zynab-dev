import { Component, inject, computed } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { ProjectService } from '../../core/project.service';
import { ToastService } from '../../core/toast.service';
import { DEMO_PRODUCTS, APP_SCREENS } from '../../core/catalog';

@Component({
  selector: 'app-design',
  standalone: true,
  imports: [FormsModule],
  templateUrl: './design.component.html',
  styleUrl: './design.component.scss',
})
export class DesignComponent {
  readonly store = inject(ProjectService);
  readonly toast = inject(ToastService);
  readonly products = DEMO_PRODUCTS;
  readonly screenDefs = APP_SCREENS;

  newPageName = '';
  platform = 'Android';
  selectedProduct = DEMO_PRODUCTS[0];
  cartQty = 2;
  previewMode: 'products' | 'detail' | 'cart' | 'checkout' | 'orders' | 'seller' = 'products';

  readonly pageHint = computed(() => {
    const name = this.store.currentDesignPage()?.name || '';
    if (name.includes('تفاصيل')) return 'detail';
    if (name.includes('سلة')) return 'cart';
    if (name.includes('إتمام') || name.includes('طلب')) return 'checkout';
    if (name.includes('بائع')) return 'seller';
    if (name.includes('طلباتي')) return 'orders';
    return 'products';
  });

  addPage(): void {
    if (!this.newPageName.trim()) return;
    this.store.addDesignPage(this.newPageName.trim());
    this.toast.success('تمت إضافة الصفحة');
    this.newPageName = '';
  }

  selectScreen(name: string): void {
    const existing = this.store.designPages().find((p) => p.name === name);
    if (existing) {
      this.store.selectDesignPage(existing.id);
    } else {
      this.store.addDesignPage(name);
    }
    this.toast.info('معاينة: ' + name);
  }

  cartTotal(): number {
    return this.products.slice(0, this.cartQty).reduce((s, p) => s + p.price, 0);
  }

  aiGenerate(): void {
    for (const s of APP_SCREENS.slice(0, 6)) {
      if (!this.store.designPages().some((p) => p.name === s.name)) {
        this.store.addDesignPage(s.name);
      }
    }
    this.toast.success('تم توليد صفحات أساسية بالذكاء الاصطناعي (محاكاة)');
  }
}
