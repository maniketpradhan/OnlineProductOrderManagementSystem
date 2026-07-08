export interface LoginRequest {
  email: string;
  password: string;
}

export interface RegisterRequest {
  fullName: string;
  email: string;
  password: string;
  phone: string;
}

export interface LoginResponse {
  userId: number | null;
  fullName: string | null;
  email: string | null;
  role: string | null;
  token: string | null;
  message: string;
}

export interface Category {
  categoryId: number;
  categoryName: string;
  description: string;
}

export interface CategoryRequest {
  categoryName: string;
  description: string;
}

export interface Product {
  productId: number;
  productName: string;
  description: string;
  price: number;
  stockQuantity: number;
  imageUrl: string;
  category: Category | null;
}

export interface ProductRequest {
  productName: string;
  description: string;
  price: number;
  stockQuantity: number;
  imageUrl: string;
  categoryId: number;
}

export interface CartItem {
  cartItemId: number;
  product: Product | null;
  quantity: number;
}

export interface Order {
  orderId: number;
  orderDate: string;
  totalAmount: number;
  status: string;
  orderItems: unknown[];
}
