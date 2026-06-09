import { Component } from '@angular/core';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html'
})
export class AppComponent {
  currentView = 'login';

  loginData = { email: '', password: '' };
  registerData = { name: '', email: '', password: '' };
  loginMessage = '';
  registerMessage = '';
  loginSuccess = false;
  registerSuccess = false;

  products: any[] = [];
  filteredProducts: any[] = [];
  searchQuery = '';
  selectedCategory = 'All';
  loadingProducts = false;
  selectedProduct: any = null;

  categories = ['All', 'Electronics', 'Clothing', 'Home & Kitchen', 'Books', 'Sports'];

  readonly GATEWAY = 'http://localhost:8087';

  showLogin() { this.currentView = 'login'; this.clearMessages(); }
  showRegister() { this.currentView = 'register'; this.clearMessages(); }
  clearMessages() { this.loginMessage = ''; this.registerMessage = ''; }

  async login() {
    try {
      const response = await fetch(`${this.GATEWAY}/auth/login`, {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify(this.loginData)
      });
      if (response.ok) {
        const data = await response.json();
        localStorage.setItem('token', data.token);
        this.loginSuccess = true;
        this.loginMessage = 'Login successful!';
        this.currentView = 'products';
        this.loadProducts();
      } else {
        this.loginMessage = 'Login failed. Please check your credentials.';
        this.loginSuccess = false;
      }
    } catch {
      this.loginMessage = 'Login failed. Server error.';
      this.loginSuccess = false;
    }
  }

  async register() {
    try {
      const response = await fetch(`${this.GATEWAY}/auth/register`, {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify(this.registerData)
      });
      if (response.ok) {
        this.registerMessage = 'Registered successfully! Please login.';
        this.registerSuccess = true;
        this.registerData = { name: '', email: '', password: '' };
      } else {
        this.registerMessage = 'Registration failed. Email may already exist.';
        this.registerSuccess = false;
      }
    } catch {
      this.registerMessage = 'Registration failed. Server error.';
      this.registerSuccess = false;
    }
  }

  async loadProducts(category?: string, search?: string) {
    this.loadingProducts = true;
    const token = localStorage.getItem('token');
    let url = `${this.GATEWAY}/products`;
    const params = new URLSearchParams();
    if (search) params.set('search', search);
    else if (category && category !== 'All') params.set('category', category);
    if (params.toString()) url += '?' + params.toString();

    try {
      const response = await fetch(url, {
        headers: { 'Authorization': `Bearer ${token}` }
      });
      if (response.ok) {
        this.products = await response.json();
        this.filteredProducts = this.products;
      }
    } catch {
      console.error('Failed to load products');
    }
    this.loadingProducts = false;
  }

  selectCategory(category: string) {
    this.selectedCategory = category;
    this.searchQuery = '';
    this.loadProducts(category);
  }

  onSearch() {
    this.selectedCategory = 'All';
    this.loadProducts(undefined, this.searchQuery);
  }

  openProduct(product: any) {
    this.selectedProduct = product;
  }

  closeProduct() {
    this.selectedProduct = null;
  }

  logout() {
    localStorage.removeItem('token');
    this.currentView = 'login';
    this.products = [];
    this.loginData = { email: '', password: '' };
  }

  getStars(rating: number): string {
    const full = Math.floor(rating);
    const half = rating % 1 >= 0.5 ? 1 : 0;
    return '★'.repeat(full) + (half ? '½' : '') + '☆'.repeat(5 - full - half);
  }
}
