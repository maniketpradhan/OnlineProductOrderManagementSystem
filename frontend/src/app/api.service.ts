import { HttpClient, HttpParams } from "@angular/common/http";
import { Injectable } from "@angular/core";
import { Observable } from "rxjs";
import {
  CartItem,
  Category,
  CategoryRequest,
  LoginRequest,
  LoginResponse,
  Order,
  Product,
  ProductRequest,
  RegisterRequest,
} from "./models";

@Injectable({ providedIn: "root" })
export class ApiService {
  private readonly baseUrl = "http://localhost:8080";

  constructor(private readonly http: HttpClient) {}

  login(payload: LoginRequest): Observable<LoginResponse> {
    return this.http.post<LoginResponse>(`${this.baseUrl}/api/auth/login`, payload);
  }

  register(payload: RegisterRequest): Observable<string> {
    return this.http.post(`${this.baseUrl}/api/auth/register`, payload, { responseType: "text" });
  }

  getCategories(): Observable<Category[]> {
    return this.http.get<Category[]>(`${this.baseUrl}/api/categories`);
  }

  addCategory(payload: CategoryRequest): Observable<Category> {
    return this.http.post<Category>(`${this.baseUrl}/api/categories`, payload);
  }

  getProducts(): Observable<Product[]> {
    return this.http.get<Product[]>(`${this.baseUrl}/api/products`);
  }

  getProductsByCategory(categoryId: number): Observable<Product[]> {
    return this.http.get<Product[]>(`${this.baseUrl}/api/products/category/${categoryId}`);
  }

  searchProducts(keyword: string): Observable<Product[]> {
    return this.http.get<Product[]>(`${this.baseUrl}/api/products/search`, {
      params: new HttpParams().set("keyword", keyword),
    });
  }

  addProduct(payload: ProductRequest): Observable<Product> {
    return this.http.post<Product>(`${this.baseUrl}/api/products`, payload);
  }

  deleteProduct(productId: number): Observable<string> {
    return this.http.delete(`${this.baseUrl}/api/products/${productId}`, { responseType: "text" });
  }

  getCart(userId: number): Observable<CartItem[]> {
    return this.http.get<CartItem[]>(`${this.baseUrl}/api/cart/${userId}`);
  }

  addToCart(userId: number, productId: number, quantity = 1): Observable<CartItem> {
    return this.http.post<CartItem>(`${this.baseUrl}/api/cart`, { userId, productId, quantity });
  }

  updateCartQuantity(cartItemId: number, quantity: number): Observable<CartItem> {
    return this.http.put<CartItem>(`${this.baseUrl}/api/cart/${cartItemId}`, null, {
      params: new HttpParams().set("quantity", quantity),
    });
  }

  removeFromCart(cartItemId: number): Observable<string> {
    return this.http.delete(`${this.baseUrl}/api/cart/${cartItemId}`, { responseType: "text" });
  }

  placeOrder(userId: number): Observable<Order> {
    return this.http.post<Order>(`${this.baseUrl}/api/orders`, { userId });
  }

  getOrders(userId: number): Observable<Order[]> {
    return this.http.get<Order[]>(`${this.baseUrl}/api/orders/${userId}`);
  }
}
