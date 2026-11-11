export interface CatalogProduct {
  id: string;
  name: string;
  price: number;
  emoji: string;
  category: string;
}

export interface AppScreenDef {
  id: string;
  name: string;
  role: 'customer' | 'seller' | 'auth';
  description: string;
}

export const DEMO_PRODUCTS: CatalogProduct[] = [
  { id: '1', name: 'سماعات لاسلكية', price: 149, emoji: '🎧', category: 'إلكترونيات' },
  { id: '2', name: 'ساعة ذكية', price: 299, emoji: '⌚', category: 'إلكترونيات' },
  { id: '3', name: 'غطاء حماية', price: 39, emoji: '📱', category: 'إكسسوارات' },
  { id: '4', name: 'شاحن سريع', price: 79, emoji: '🔌', category: 'إكسسوارات' },
  { id: '5', name: 'حقيبة لابتوب', price: 120, emoji: '💼', category: 'إكسسوارات' },
  { id: '6', name: 'كيبورد ميكانيكي', price: 249, emoji: '⌨️', category: 'إلكترونيات' },
];

export const APP_SCREENS: AppScreenDef[] = [
  { id: 'login', name: 'تسجيل الدخول', role: 'auth', description: 'دخول الزبون/البائع' },
  { id: 'register', name: 'إنشاء حساب', role: 'auth', description: 'تسجيل مستخدم جديد' },
  { id: 'products', name: 'قائمة المنتجات', role: 'customer', description: 'تصفح وبحث وفلترة' },
  { id: 'product-detail', name: 'تفاصيل المنتج', role: 'customer', description: 'صورة، سعر، إضافة للسلة' },
  { id: 'cart', name: 'السلة', role: 'customer', description: 'عرض العناصر والمجموع' },
  { id: 'checkout', name: 'إتمام الطلب', role: 'customer', description: 'عنوان وتأكيد' },
  { id: 'orders', name: 'طلباتي', role: 'customer', description: 'قائمة الطلبات وحالاتها' },
  { id: 'seller-products', name: 'منتجات البائع', role: 'seller', description: 'إدارة المنتجات' },
  { id: 'seller-orders', name: 'طلبات البائع', role: 'seller', description: 'تغيير حالة الطلب' },
  { id: 'seller-dashboard', name: 'لوحة البائع', role: 'seller', description: 'ملخص المبيعات' },
];
