import { Component, inject, signal } from '@angular/core';
import { ToastService } from '../../core/toast.service';

interface Asset {
  id: string;
  name: string;
  kind: string;
  note: string;
}

@Component({
  selector: 'app-media',
  standalone: true,
  imports: [],
  templateUrl: './media.component.html',
  styleUrl: './media.component.scss',
})
export class MediaComponent {
  private readonly toast = inject(ToastService);
  readonly assets = signal<Asset[]>([
    { id: '1', name: 'logo-primary.svg', kind: 'شعار', note: 'هوية متجري' },
    { id: '2', name: 'product-placeholder.png', kind: 'صورة', note: 'Placeholder المنتجات' },
    { id: '3', name: 'icon-cart.svg', kind: 'أيقونة', note: 'أيقونة السلة' },
  ]);

  onFile(ev: Event): void {
    const input = ev.target as HTMLInputElement;
    const file = input.files?.[0];
    if (!file) return;
    // فحص بسيط للنوع والحجم وفق مواصفات الأمان
    const okTypes = ['image/png', 'image/jpeg', 'image/svg+xml', 'image/webp'];
    if (!okTypes.includes(file.type)) {
      this.toast.error('نوع الملف غير مسموح');
      return;
    }
    if (file.size > 5 * 1024 * 1024) {
      this.toast.error('الحد الأقصى 5MB');
      return;
    }
    this.assets.update((list) => [
      {
        id: String(Date.now()),
        name: file.name,
        kind: 'مرفوع',
        note: `${Math.round(file.size / 1024)} KB — تم الفحص الأساسي`,
      },
      ...list,
    ]);
    this.toast.success('أُضيف الملف بعد الفحص');
    input.value = '';
  }
}
