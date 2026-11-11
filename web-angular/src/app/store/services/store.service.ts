import { Injectable, signal, computed, effect } from '@angular/core';
import {
  StoreUser,
  Product,
  CartItem,
  Order,
  OrderItem,
  OrderStatus,
} from './store.models';

const KEY = 'matjari-real-store-v1';

interface StoreDb {
  users: StoreUser[];
  products: Product[];
  cart: CartItem[];
  orders: Order[];
  sessionUserId: string | null;
}

function uid(prefix: string): string {
  return prefix + '-' + Math.random().toString(36).slice(2, 9) + Date.now().toString(36).slice(-4);
}

function seed(): StoreDb {
  const sellerId = 'u-seller';
  const customerId = 'u-customer';
  return {
    users: [
      {
        id: sellerId,
        name: 'أحمد البائع',
        email: 'seller@matjari.demo',
        role: 'seller',
        password: '123456',
      },
      {
        id: customerId,
        name: 'سارة الزبون',
        email: 'customer@matjari.demo',
        role: 'customer',
        password: '123456',
      },
      {
        id: 'u-admin',
        name: 'مدير',
        email: 'admin@matjari.demo',
        role: 'admin',
        password: '123456',
      },
    ],
    products: [
      {
        id: 'pr1',
        name: 'سماعات لاسلكية',
        description: 'سماعات بلوتوث بجودة عالية وبطارية طويلة',
        price: 149,
        stock: 25,
        category: 'إلكترونيات',
        emoji: '🎧',
        sellerId,
        active: true,
      },
      {
        id: 'pr2',
        name: 'ساعة ذكية',
        description: 'تتبع اللياقة والإشعارات ومقاومة للماء',
        price: 299,
        stock: 12,
        category: 'إلكترونيات',
        emoji: '⌚',
        sellerId,
        active: true,
      },
      {
        id: 'pr3',
        name: 'غطاء حماية',
        description: 'غطاء شفاف مضاد للصدمات',
        price: 39,
        stock: 80,
        category: 'إكسسوارات',
        emoji: '📱',
        sellerId,
        active: true,
      },
      {
        id: 'pr4',
        name: 'شاحن سريع',
        description: 'شاحن 65 واط مع كابل Type-C',
        price: 79,
        stock: 40,
        category: 'إكسسوارات',
        emoji: '🔌',
        sellerId,
        active: true,
      },
      {
        id: 'pr5',
        name: 'كيبورد ميكانيكي',
        description: 'مفاتيح زرقاء وإضاءة RGB',
        price: 249,
        stock: 8,
        category: 'إلكترونيات',
        emoji: '⌨️',
        sellerId,
        active: true,
      },
      {
        id: 'pr6',
        name: 'حقيبة لابتوب',
        description: 'حقيبة 15 بوصة مقاومة للماء',
        price: 120,
        stock: 15,
        category: 'إكسسوارات',
        emoji: '💼',
        sellerId,
        active: true,
      },
    ],
    cart: [],
    orders: [],
    sessionUserId: null,
  };
}

function load(): StoreDb {
  try {
    const raw = localStorage.getItem(KEY);
    if (!raw) return seed();
    const parsed = JSON.parse(raw) as StoreDb;
    return { ...seed(), ...parsed, users: parsed.users?.length ? parsed.users : seed().users };
  } catch {
    return seed();
  }
}

@Injectable({ providedIn: 'root' })
export class StoreService {
  private readonly db = signal<StoreDb>(load());

  readonly products = computed(() => this.db().products.filter((p) => p.active));
  readonly allProducts = computed(() => this.db().products);
  readonly cart = computed(() => this.db().cart);
  readonly orders = computed(() => this.db().orders);
  readonly currentUser = computed(() => {
    const id = this.db().sessionUserId;
    return this.db().users.find((u) => u.id === id) ?? null;
  });
  readonly isLoggedIn = computed(() => !!this.currentUser());
  readonly isSeller = computed(() => {
    const r = this.currentUser()?.role;
    return r === 'seller' || r === 'admin';
  });

  readonly cartCount = computed(() =>
    this.db().cart.reduce((s, i) => s + i.quantity, 0)
  );

  readonly cartLines = computed(() => {
    const products = this.db().products;
    return this.db().cart
      .map((c) => {
        const p = products.find((x) => x.id === c.productId);
        if (!p) return null;
        return { product: p, quantity: c.quantity, lineTotal: p.price * c.quantity };
      })
      .filter(Boolean) as { product: Product; quantity: number; lineTotal: number }[];
  });

  readonly cartTotal = computed(() =>
    this.cartLines().reduce((s, l) => s + l.lineTotal, 0)
  );

  readonly myOrders = computed(() => {
    const u = this.currentUser();
    if (!u) return [];
    if (u.role === 'seller' || u.role === 'admin') {
      return this.db().orders;
    }
    return this.db().orders.filter((o) => o.userId === u.id);
  });

  constructor() {
    effect(() => {
      localStorage.setItem(KEY, JSON.stringify(this.db()));
    });
  }

  private patch(partial: Partial<StoreDb>): void {
    this.db.update((d) => ({ ...d, ...partial }));
  }

  login(email: string, password: string): { ok: boolean; message: string } {
    const user = this.db().users.find(
      (u) => u.email.toLowerCase() === email.trim().toLowerCase() && u.password === password
    );
    if (!user) return { ok: false, message: 'بيانات الدخول غير صحيحة' };
    this.patch({ sessionUserId: user.id });
    return { ok: true, message: 'تم تسجيل الدخول' };
  }

  logout(): void {
    this.patch({ sessionUserId: null, cart: [] });
  }

  register(name: string, email: string, password: string, role: 'customer' | 'seller' = 'customer'): { ok: boolean; message: string } {
    if (!name.trim() || !email.trim() || password.length < 4) {
      return { ok: false, message: 'أكمل البيانات (كلمة المرور 4 أحرف على الأقل)' };
    }
    if (this.db().users.some((u) => u.email.toLowerCase() === email.trim().toLowerCase())) {
      return { ok: false, message: 'البريد مستخدم مسبقاً' };
    }
    const user: StoreUser = {
      id: uid('u'),
      name: name.trim(),
      email: email.trim().toLowerCase(),
      role,
      password,
    };
    this.db.update((d) => ({
      ...d,
      users: [...d.users, user],
      sessionUserId: user.id,
    }));
    return { ok: true, message: 'تم إنشاء الحساب' };
  }

  getProduct(id: string): Product | undefined {
    return this.db().products.find((p) => p.id === id);
  }

  addToCart(productId: string, qty = 1): { ok: boolean; message: string } {
    const product = this.getProduct(productId);
    if (!product || !product.active) return { ok: false, message: 'المنتج غير متوفر' };
    if (product.stock < qty) return { ok: false, message: 'الكمية غير متوفرة في المخزون' };

    this.db.update((d) => {
      const cart = [...d.cart];
      const existing = cart.find((c) => c.productId === productId);
      const newQty = (existing?.quantity ?? 0) + qty;
      if (newQty > product.stock) {
        return d;
      }
      if (existing) existing.quantity = newQty;
      else cart.push({ productId, quantity: qty });
      return { ...d, cart };
    });

    const line = this.db().cart.find((c) => c.productId === productId);
    if (!line || (line.quantity === qty && !this.db().cart.some(() => true))) {
      // recheck stock failure
    }
    return { ok: true, message: 'تمت الإضافة إلى السلة' };
  }

  setCartQty(productId: string, quantity: number): void {
    this.db.update((d) => {
      let cart = d.cart.map((c) =>
        c.productId === productId ? { ...c, quantity } : c
      );
      cart = cart.filter((c) => c.quantity > 0);
      return { ...d, cart };
    });
  }

  removeFromCart(productId: string): void {
    this.db.update((d) => ({
      ...d,
      cart: d.cart.filter((c) => c.productId !== productId),
    }));
  }

  clearCart(): void {
    this.patch({ cart: [] });
  }

  placeOrder(address: string, phone: string): { ok: boolean; message: string; orderId?: string } {
    const user = this.currentUser();
    if (!user) return { ok: false, message: 'يجب تسجيل الدخول أولاً' };
    const lines = this.cartLines();
    if (!lines.length) return { ok: false, message: 'السلة فارغة' };
    if (!address.trim() || !phone.trim()) return { ok: false, message: 'أدخل العنوان والجوال' };

    for (const line of lines) {
      if (line.quantity > line.product.stock) {
        return { ok: false, message: `المخزون غير كافٍ: ${line.product.name}` };
      }
    }

    const items: OrderItem[] = lines.map((l) => ({
      productId: l.product.id,
      name: l.product.name,
      price: l.product.price,
      quantity: l.quantity,
    }));
    const total = items.reduce((s, i) => s + i.price * i.quantity, 0);
    const order: Order = {
      id: uid('ord'),
      userId: user.id,
      userName: user.name,
      items,
      total,
      status: 'new',
      address: address.trim(),
      phone: phone.trim(),
      createdAt: new Date().toISOString(),
    };

    this.db.update((d) => {
      const products = d.products.map((p) => {
        const line = lines.find((l) => l.product.id === p.id);
        if (!line) return p;
        return { ...p, stock: p.stock - line.quantity };
      });
      return {
        ...d,
        products,
        orders: [order, ...d.orders],
        cart: [],
      };
    });

    return { ok: true, message: 'تم إنشاء الطلب بنجاح', orderId: order.id };
  }

  updateOrderStatus(orderId: string, status: OrderStatus): { ok: boolean; message: string } {
    if (!this.isSeller()) return { ok: false, message: 'غير مصرح' };
    this.db.update((d) => ({
      ...d,
      orders: d.orders.map((o) => (o.id === orderId ? { ...o, status } : o)),
    }));
    return { ok: true, message: 'تم تحديث حالة الطلب' };
  }

  saveProduct(input: Omit<Product, 'id' | 'sellerId'> & { id?: string }): { ok: boolean; message: string } {
    const user = this.currentUser();
    if (!user || !this.isSeller()) return { ok: false, message: 'غير مصرح' };
    if (!input.name.trim() || input.price <= 0) return { ok: false, message: 'اسم وسعر صالحان مطلوبان' };

    if (input.id) {
      this.db.update((d) => ({
        ...d,
        products: d.products.map((p) =>
          p.id === input.id
            ? {
                ...p,
                name: input.name.trim(),
                description: input.description,
                price: input.price,
                stock: input.stock,
                category: input.category,
                emoji: input.emoji || '📦',
                active: input.active,
              }
            : p
        ),
      }));
      return { ok: true, message: 'تم تحديث المنتج' };
    }

    const product: Product = {
      id: uid('pr'),
      name: input.name.trim(),
      description: input.description,
      price: input.price,
      stock: input.stock,
      category: input.category,
      emoji: input.emoji || '📦',
      sellerId: user.id,
      active: input.active,
    };
    this.db.update((d) => ({ ...d, products: [product, ...d.products] }));
    return { ok: true, message: 'تمت إضافة المنتج' };
  }

  deleteProduct(id: string): { ok: boolean; message: string } {
    if (!this.isSeller()) return { ok: false, message: 'غير مصرح' };
    this.db.update((d) => ({
      ...d,
      products: d.products.map((p) => (p.id === id ? { ...p, active: false } : p)),
    }));
    return { ok: true, message: 'تم إخفاء المنتج' };
  }

  resetStore(): void {
    localStorage.removeItem(KEY);
    this.db.set(seed());
  }
}
