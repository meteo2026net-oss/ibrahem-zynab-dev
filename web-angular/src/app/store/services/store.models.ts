export type OrderStatus = 'new' | 'preparing' | 'shipped' | 'completed' | 'cancelled';

export interface StoreUser {
  id: string;
  name: string;
  email: string;
  role: 'customer' | 'seller' | 'admin';
  password: string;
}

export interface Product {
  id: string;
  name: string;
  description: string;
  price: number;
  stock: number;
  category: string;
  emoji: string;
  sellerId: string;
  active: boolean;
}

export interface CartItem {
  productId: string;
  quantity: number;
}

export interface OrderItem {
  productId: string;
  name: string;
  price: number;
  quantity: number;
}

export interface Order {
  id: string;
  userId: string;
  userName: string;
  items: OrderItem[];
  total: number;
  status: OrderStatus;
  address: string;
  phone: string;
  createdAt: string;
}
