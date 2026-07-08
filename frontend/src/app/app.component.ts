import { CommonModule, CurrencyPipe, DatePipe } from "@angular/common";
import { Component, OnInit } from "@angular/core";
import { FormsModule } from "@angular/forms";
import { forkJoin } from "rxjs";
import { ApiService } from "./api.service";
import { CartItem, Category, LoginResponse, Order, Product } from "./models";

@Component({
  selector: "app-root",
  standalone: true,
  imports: [CommonModule, FormsModule, CurrencyPipe, DatePipe],
  templateUrl: "./app.component.html",
})
export class AppComponent implements OnInit {
  readonly fallbackImage =
    "https://images.unsplash.com/photo-1556742049-0cfed4f6a45d?auto=format&fit=crop&w=900&q=80";

  auth: LoginResponse | null = this.readAuth();
  authMode: "login" | "register" = "login";
  notice = "";
  loading = false;
  keyword = "";
  categoryFilter: number | "all" = "all";

  loginForm = { email: "", password: "" };
  registerForm = { fullName: "", email: "", password: "", phone: "" };
  categoryForm = { categoryName: "", description: "" };
  productForm = {
    productName: "",
    description: "",
    price: 0,
    stockQuantity: 0,
    imageUrl: "",
    categoryId: 0,
  };

  products: Product[] = [];
  categories: Category[] = [];
  cart: CartItem[] = [];
  orders: Order[] = [];

  constructor(private readonly api: ApiService) {}

  ngOnInit(): void {
    if (this.auth?.token) {
      this.refreshStore();
    }
  }

  get isAdmin(): boolean {
    return this.auth?.role === "ADMIN";
  }

  get cartTotal(): number {
    return this.cart.reduce((sum, item) => sum + Number(item.product?.price ?? 0) * item.quantity, 0);
  }

  login(): void {
    this.api.login(this.loginForm).subscribe({
      next: (response) => {
        if (!response.token) {
          this.showNotice(response.message || "Login failed");
          return;
        }
        this.auth = response;
        localStorage.setItem("oms-auth", JSON.stringify(response));
        this.showNotice(response.message || "Login successful");
        this.refreshStore();
      },
      error: (error) => this.showNotice(this.errorMessage(error)),
    });
  }

  register(): void {
    this.api.register(this.registerForm).subscribe({
      next: (message) => {
        this.showNotice(message);
        this.authMode = "login";
      },
      error: (error) => this.showNotice(this.errorMessage(error)),
    });
  }

  logout(): void {
    this.auth = null;
    this.products = [];
    this.categories = [];
    this.cart = [];
    this.orders = [];
    localStorage.removeItem("oms-auth");
    this.showNotice("Signed out");
  }

  refreshStore(): void {
    this.loading = true;
    forkJoin({
      categories: this.api.getCategories(),
      products: this.api.getProducts(),
    }).subscribe({
      next: ({ categories, products }) => {
        this.categories = categories;
        this.products = products;
        this.loadCart();
        this.loadOrders();
        this.loading = false;
      },
      error: (error) => {
        this.loading = false;
        this.showNotice(this.errorMessage(error));
      },
    });
  }

  searchProducts(): void {
    const keyword = this.keyword.trim();
    const source = keyword ? this.api.searchProducts(keyword) : this.api.getProducts();
    source.subscribe({
      next: (products) => (this.products = products),
      error: (error) => this.showNotice(this.errorMessage(error)),
    });
  }

  filterByCategory(categoryId: number | "all"): void {
    this.categoryFilter = categoryId;
    const source = categoryId === "all" ? this.api.getProducts() : this.api.getProductsByCategory(categoryId);
    source.subscribe({
      next: (products) => (this.products = products),
      error: (error) => this.showNotice(this.errorMessage(error)),
    });
  }

  addToCart(productId: number): void {
    const userId = this.auth?.userId;
    if (!userId) return;

    this.api.addToCart(userId, productId).subscribe({
      next: () => {
        this.showNotice("Added to cart");
        this.loadCart();
      },
      error: (error) => this.showNotice(this.errorMessage(error)),
    });
  }

  updateCartQuantity(item: CartItem, quantity: number): void {
    if (quantity < 1) return;

    this.api.updateCartQuantity(item.cartItemId, quantity).subscribe({
      next: () => this.loadCart(),
      error: (error) => this.showNotice(this.errorMessage(error)),
    });
  }

  removeFromCart(cartItemId: number): void {
    this.api.removeFromCart(cartItemId).subscribe({
      next: () => {
        this.showNotice("Removed from cart");
        this.loadCart();
      },
      error: (error) => this.showNotice(this.errorMessage(error)),
    });
  }

  placeOrder(): void {
    const userId = this.auth?.userId;
    if (!userId || !this.cart.length) {
      this.showNotice("Your cart is empty");
      return;
    }

    this.api.placeOrder(userId).subscribe({
      next: () => {
        this.showNotice("Order placed successfully");
        this.loadCart();
        this.loadOrders();
      },
      error: (error) => this.showNotice(this.errorMessage(error)),
    });
  }

  addCategory(): void {
    this.api.addCategory(this.categoryForm).subscribe({
      next: () => {
        this.categoryForm = { categoryName: "", description: "" };
        this.showNotice("Category added");
        this.api.getCategories().subscribe((categories) => (this.categories = categories));
      },
      error: (error) => this.showNotice(this.errorMessage(error)),
    });
  }

  addProduct(): void {
    this.api.addProduct(this.productForm).subscribe({
      next: () => {
        this.productForm = {
          productName: "",
          description: "",
          price: 0,
          stockQuantity: 0,
          imageUrl: "",
          categoryId: 0,
        };
        this.showNotice("Product added");
        this.refreshStore();
      },
      error: (error) => this.showNotice(this.errorMessage(error)),
    });
  }

  deleteProduct(productId: number): void {
    this.api.deleteProduct(productId).subscribe({
      next: () => {
        this.showNotice("Product deleted");
        this.refreshStore();
      },
      error: (error) => this.showNotice(this.errorMessage(error)),
    });
  }

  imageFailed(event: Event): void {
    const image = event.target as HTMLImageElement;
    image.src = this.fallbackImage;
  }

  private loadCart(): void {
    const userId = this.auth?.userId;
    if (!userId) return;

    this.api.getCart(userId).subscribe({
      next: (cart) => (this.cart = cart),
      error: (error) => this.showNotice(this.errorMessage(error)),
    });
  }

  private loadOrders(): void {
    const userId = this.auth?.userId;
    if (!userId) return;

    this.api.getOrders(userId).subscribe({
      next: (orders) => (this.orders = orders),
      error: (error) => this.showNotice(this.errorMessage(error)),
    });
  }

  private showNotice(message: string): void {
    this.notice = message;
    window.clearTimeout(this.noticeTimer);
    this.noticeTimer = window.setTimeout(() => (this.notice = ""), 3200);
  }

  private noticeTimer = 0;

  private readAuth(): LoginResponse | null {
    const saved = localStorage.getItem("oms-auth");
    return saved ? JSON.parse(saved) : null;
  }

  private errorMessage(error: { error?: unknown; message?: string }): string {
    if (typeof error.error === "string") return error.error;
    if (error.error && typeof error.error === "object" && "message" in error.error) {
      return String(error.error.message);
    }
    return error.message || "Request failed";
  }
}
