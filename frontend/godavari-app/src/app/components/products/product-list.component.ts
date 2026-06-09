import { Component, OnInit, ChangeDetectionStrategy, ChangeDetectorRef } from '@angular/core';
import { MatSnackBar } from '@angular/material/snack-bar';
import { ProductService } from '../../services/product.service';
import { CartService } from '../../services/cart.service';

@Component({
  selector: 'app-product-list',
  changeDetection: ChangeDetectionStrategy.OnPush,
  template: `
    <div class="page-container">

      <!-- Hero Search Section -->
      <div class="search-hero">
        <h2 class="search-title">
          <mat-icon>storefront</mat-icon>
          Explore Our Products
        </h2>
        <p class="search-subtitle">Quality building materials for every project</p>

        <div class="search-bar-wrap">
          <mat-form-field appearance="outline" class="search-field">
            <mat-label>Search products...</mat-label>
            <input matInput [(ngModel)]="searchQuery" (keyup.enter)="onSearch()" placeholder="e.g. cement, TMT steel, pipes">
            <button matSuffix mat-icon-button (click)="onSearch()">
              <mat-icon>search</mat-icon>
            </button>
          </mat-form-field>

          <mat-form-field appearance="outline" class="sort-field">
            <mat-label>Sort By</mat-label>
            <mat-select [(ngModel)]="sortBy" (selectionChange)="loadProducts()">
              <mat-option value="createdAt">Latest</mat-option>
              <mat-option value="price">Price</mat-option>
              <mat-option value="name">Name</mat-option>
            </mat-select>
          </mat-form-field>

          <button mat-stroked-button class="clear-btn" (click)="clearFilters()" *ngIf="searchQuery || selectedCategory">
            <mat-icon>clear</mat-icon> Clear
          </button>
        </div>
      </div>

      <!-- Category Chips -->
      <div class="category-chips">
        <button class="chip" [class.active]="!selectedCategory" (click)="selectCategory(null)">
          All Products
        </button>
        <button *ngFor="let cat of categories" class="chip"
          [class.active]="selectedCategory?.id === cat.id"
          (click)="selectCategory(cat)">
          {{ cat.name }}
        </button>
      </div>

      <!-- Loading -->
      <div *ngIf="loading" style="text-align:center; padding:80px 20px;">
        <mat-spinner diameter="48" style="margin:0 auto;"></mat-spinner>
        <p style="margin-top:20px; color:#64748b; font-size:15px;">Loading products...</p>
      </div>

      <!-- Result count -->
      <p *ngIf="!loading" class="result-count">
        Showing <strong>{{ totalElements }}</strong> products
        <span *ngIf="selectedCategory"> in <strong>{{ selectedCategory.name }}</strong></span>
        <span *ngIf="searchQuery"> for "<strong>{{ searchQuery }}</strong>"</span>
      </p>

      <!-- Product Grid -->
      <div class="product-grid" *ngIf="!loading">
        <div class="product-card" *ngFor="let p of products; trackBy: trackProduct" (click)="openDetail(p)">
          <div class="product-img-wrap">
            <img [src]="getImage(p.imageUrl)" [alt]="p.name">
            <span class="product-badge">{{ p.category?.name }}</span>
            <span *ngIf="p.bulkDiscountPercent > 0" class="discount-badge">
              -{{ p.bulkDiscountPercent }}%
            </span>
          </div>
          <div class="product-info">
            <div class="product-name">{{ p.name }}</div>
            <div class="stars">{{ getStars(p.rating) }}
              <span class="rating-count">({{ p.reviewCount | number }})</span>
            </div>
            <div class="price-row">
              <span class="product-price">₹{{ p.price | number:'1.2-2' }}</span>
              <span class="product-unit">/ {{ p.unit }}</span>
            </div>
            <div class="product-gst">+{{ p.gstRate }}% GST</div>
            <div *ngIf="p.bulkDiscountPercent > 0" class="bulk-offer">
              🏷️ {{ p.bulkDiscountPercent }}% off on {{ p.bulkMinQty }}+ qty
            </div>
            <button class="add-cart-btn" (click)="addToCart($event, p)">
              <mat-icon>add_shopping_cart</mat-icon> Add to Cart
            </button>
          </div>
        </div>
      </div>

      <!-- Empty -->
      <div *ngIf="!loading && products.length === 0" class="empty-state">
        <mat-icon>inventory_2</mat-icon>
        <p>No products found</p>
        <button mat-stroked-button (click)="clearFilters()">Clear Filters</button>
      </div>

      <!-- Pagination -->
      <mat-paginator *ngIf="!loading && totalElements > pageSize"
        [length]="totalElements" [pageSize]="pageSize"
        [pageSizeOptions]="[12,24,48]"
        (page)="onPageChange($event)"
        style="margin-top:24px; background:white; border-radius:12px; box-shadow:0 2px 8px rgba(0,0,0,0.04);">
      </mat-paginator>

      <!-- Product Detail Modal -->
      <div *ngIf="selectedProduct" class="modal-overlay" (click)="selectedProduct=null">
        <div class="modal-content" (click)="$event.stopPropagation()">
          <button class="modal-close" (click)="selectedProduct=null">
            <mat-icon>close</mat-icon>
          </button>
          <div class="modal-body">
            <img [src]="getImage(selectedProduct.imageUrl)" [alt]="selectedProduct.name" class="modal-img">
            <div class="modal-details">
              <span class="badge badge-warn">{{ selectedProduct.category?.name }}</span>
              <h2 class="modal-title">{{ selectedProduct.name }}</h2>
              <p class="modal-meta">
                Brand: <strong>{{ selectedProduct.brand }}</strong> &nbsp;|&nbsp;
                SKU: <strong>{{ selectedProduct.sku }}</strong>
              </p>
              <div class="stars" style="font-size:16px;">{{ getStars(selectedProduct.rating) }}
                <span style="color:#64748b; font-size:13px;"> {{ selectedProduct.reviewCount | number }} ratings</span>
              </div>
              <mat-divider style="margin:16px 0;"></mat-divider>
              <div class="modal-price">
                ₹{{ selectedProduct.price | number:'1.2-2' }}
                <span class="modal-unit">/ {{ selectedProduct.unit }}</span>
              </div>
              <div *ngIf="selectedProduct.mrp" style="font-size:14px; color:#94a3b8; text-decoration:line-through;">
                MRP ₹{{ selectedProduct.mrp | number:'1.2-2' }}
              </div>
              <p style="font-size:13px; color:#64748b; margin:6px 0;">
                GST: {{ selectedProduct.gstRate }}% | HSN: {{ selectedProduct.hsnCode }}
              </p>
              <p *ngIf="selectedProduct.bulkDiscountPercent > 0" class="bulk-offer" style="margin:8px 0;">
                🏷️ {{ selectedProduct.bulkDiscountPercent }}% discount on orders of {{ selectedProduct.bulkMinQty }}+
              </p>
              <mat-divider style="margin:16px 0;"></mat-divider>
              <p style="font-size:14px; color:#475569; line-height:1.7;">{{ selectedProduct.description }}</p>
              <div class="modal-actions">
                <mat-form-field appearance="outline" style="width:100px;">
                  <mat-label>Qty</mat-label>
                  <input matInput type="number" [(ngModel)]="detailQty" [min]="selectedProduct.minOrderQty">
                </mat-form-field>
                <button class="add-cart-btn modal-cart-btn" (click)="addToCartDetail()">
                  <mat-icon>add_shopping_cart</mat-icon> Add to Cart
                </button>
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>
  `,
  styles: [`
    .search-hero {
      background: linear-gradient(135deg, #7c2d12, #c2410c, #ea580c);
      border-radius: 16px;
      padding: 36px 32px 28px;
      margin-bottom: 24px;
      animation: fadeInUp 0.5s ease-out;
    }
    .search-title {
      display: flex;
      align-items: center;
      gap: 10px;
      font-size: 24px;
      font-weight: 800;
      color: white;
      margin-bottom: 4px;
    }
    .search-subtitle { color: rgba(255,255,255,0.75); font-size: 14px; margin-bottom: 20px; }
    .search-bar-wrap {
      display: flex;
      gap: 12px;
      flex-wrap: wrap;
      align-items: flex-start;
    }
    .search-field {
      flex: 1;
      min-width: 280px;
    }
    .sort-field {
      min-width: 160px;
    }
    .search-field .mat-form-field-wrapper,
    .sort-field .mat-form-field-wrapper {
      margin: 0 !important;
      padding: 0 !important;
    }
    :host ::ng-deep .search-hero .mat-form-field-appearance-outline .mat-form-field-outline {
      color: rgba(255,255,255,0.4) !important;
    }
    :host ::ng-deep .search-hero .mat-form-field-label { color: rgba(255,255,255,0.7) !important; }
    :host ::ng-deep .search-hero input { color: white !important; }
    .clear-btn {
      height: 56px;
      border-radius: 10px !important;
      color: white !important;
      border-color: rgba(255,255,255,0.4) !important;
    }

    .category-chips {
      display: flex;
      gap: 10px;
      flex-wrap: wrap;
      margin-bottom: 24px;
      animation: fadeInUp 0.5s ease-out 0.1s both;
    }
    .chip {
      padding: 10px 20px;
      border-radius: 24px;
      border: 2px solid #e2e8f0;
      background: white;
      color: #475569;
      font-size: 14px;
      font-weight: 500;
      cursor: pointer;
      transition: all 0.2s ease;
    }
    .chip:hover { border-color: #e8650a; color: #e8650a; }
    .chip.active {
      background: linear-gradient(135deg, #e8650a, #f97316);
      color: white;
      border-color: transparent;
      box-shadow: 0 4px 12px rgba(232,101,10,0.3);
    }

    .result-count {
      color: #64748b;
      font-size: 14px;
      margin-bottom: 16px;
      animation: fadeIn 0.4s ease;
    }

    .product-card {
      /* No per-card animation - prevents blinking on cart add or any re-render */
      transition: transform 0.3s ease, box-shadow 0.3s ease, border-color 0.3s ease;
    }
    .product-img-wrap {
      position: relative;
      overflow: hidden;
    }
    .product-badge {
      position: absolute;
      top: 12px; left: 12px;
      background: rgba(26,35,126,0.9);
      color: white;
      padding: 4px 12px;
      border-radius: 20px;
      font-size: 11px;
      font-weight: 600;
      text-transform: uppercase;
      letter-spacing: 0.5px;
    }
    .discount-badge {
      position: absolute;
      top: 12px; right: 12px;
      background: #d50000;
      color: white;
      padding: 4px 10px;
      border-radius: 6px;
      font-size: 12px;
      font-weight: 700;
    }
    .rating-count { color: #94a3b8; font-size: 12px; }
    .price-row {
      display: flex;
      align-items: baseline;
      gap: 6px;
      margin-top: 8px;
    }
    .bulk-offer {
      color: #00c853;
      font-size: 12px;
      font-weight: 500;
      margin-top: 6px;
    }
    .add-cart-btn {
      width: 100%;
      margin-top: 14px;
      padding: 12px;
      background: linear-gradient(135deg, #e8650a, #f97316);
      color: white;
      border: none;
      border-radius: 10px;
      font-size: 14px;
      font-weight: 600;
      cursor: pointer;
      display: flex;
      align-items: center;
      justify-content: center;
      gap: 8px;
      transition: all 0.2s ease;
    }
    .add-cart-btn:hover {
      transform: translateY(-2px);
      box-shadow: 0 6px 16px rgba(26,35,126,0.35);
    }
    .add-cart-btn mat-icon { font-size: 20px; width: 20px; height: 20px; }

    .empty-state {
      text-align: center;
      padding: 80px 20px;
      animation: fadeIn 0.5s ease;
    }
    .empty-state mat-icon { font-size: 72px; width: 72px; height: 72px; color: #e2e8f0; }
    .empty-state p { color: #64748b; margin: 16px 0; font-size: 16px; }

    /* Modal */
    .modal-overlay {
      position: fixed; inset: 0;
      background: rgba(15,23,42,0.7);
      backdrop-filter: blur(4px);
      z-index: 200;
      display: flex;
      align-items: center;
      justify-content: center;
      padding: 20px;
      animation: fadeIn 0.3s ease;
    }
    .modal-content {
      background: white;
      border-radius: 16px;
      max-width: 780px;
      width: 100%;
      max-height: 90vh;
      overflow-y: auto;
      position: relative;
      animation: scaleIn 0.3s ease;
    }
    .modal-close {
      position: absolute;
      top: 16px; right: 16px;
      background: #f1f5f9;
      border: none;
      border-radius: 50%;
      width: 36px; height: 36px;
      cursor: pointer;
      display: flex;
      align-items: center;
      justify-content: center;
      z-index: 10;
      transition: background 0.2s;
    }
    .modal-close:hover { background: #e2e8f0; }
    .modal-body {
      display: flex;
      gap: 28px;
      padding: 28px;
      flex-wrap: wrap;
    }
    .modal-img {
      width: 300px;
      height: 300px;
      object-fit: cover;
      border-radius: 12px;
      flex-shrink: 0;
    }
    .modal-details { flex: 1; min-width: 240px; }
    .modal-title { font-size: 20px; font-weight: 700; margin: 12px 0 8px; color: #1a1a2e; }
    .modal-meta { color: #64748b; font-size: 14px; margin-bottom: 8px; }
    .modal-price {
      font-size: 30px;
      font-weight: 800;
      color: #e8650a;
    }
    .modal-unit { font-size: 16px; color: #64748b; font-weight: 400; }
    .modal-actions {
      display: flex;
      align-items: center;
      gap: 16px;
      margin-top: 20px;
    }
    .modal-cart-btn { flex: 1; }

    @keyframes fadeInUp {
      from { opacity: 0; transform: translateY(20px); }
      to { opacity: 1; transform: translateY(0); }
    }
    @keyframes fadeIn {
      from { opacity: 0; }
      to { opacity: 1; }
    }
    @keyframes scaleIn {
      from { opacity: 0; transform: scale(0.9); }
      to { opacity: 1; transform: scale(1); }
    }

    @media (max-width: 768px) {
      .search-hero { padding: 24px 16px 20px; }
      .search-title { font-size: 20px; }
      .modal-body { flex-direction: column; }
      .modal-img { width: 100%; height: 220px; }
    }
  `]
})
export class ProductListComponent implements OnInit {
  products: any[] = [];
  categories: any[] = [];
  selectedCategory: any = null;
  selectedProduct: any = null;
  searchQuery = '';
  sortBy = 'createdAt';
  loading = false;
  totalElements = 0;
  pageSize = 12;
  currentPage = 0;
  detailQty = 1;

  constructor(private productService: ProductService,
              private cart: CartService,
              private snack: MatSnackBar,
              private cdr: ChangeDetectorRef) {}

  ngOnInit(): void {
    this.loadCategories();
    this.loadProducts();
  }

  loadCategories(): void {
    this.productService.getCategories().subscribe(cats => {
      this.categories = cats;
      this.cdr.markForCheck();
    });
  }

  loadProducts(): void {
    this.loading = true;
    this.cdr.markForCheck();
    let obs;
    if (this.searchQuery.trim())
      obs = this.productService.search(this.searchQuery, this.currentPage, this.pageSize);
    else if (this.selectedCategory)
      obs = this.productService.getByCategory(this.selectedCategory.id, this.currentPage, this.pageSize);
    else
      obs = this.productService.getAll(this.currentPage, this.pageSize, this.sortBy);

    obs.subscribe({
      next: (data: any) => {
        this.products = data.content;
        this.totalElements = data.totalElements;
        this.loading = false;
        this.cdr.markForCheck();
      },
      error: () => { this.loading = false; this.cdr.markForCheck(); }
    });
  }

  onSearch(): void { this.currentPage = 0; this.selectedCategory = null; this.loadProducts(); }

  selectCategory(cat: any): void {
    this.selectedCategory = cat;
    this.searchQuery = '';
    this.currentPage = 0;
    this.loadProducts();
  }

  clearFilters(): void { this.searchQuery = ''; this.selectedCategory = null; this.currentPage = 0; this.loadProducts(); }

  onPageChange(event: any): void { this.currentPage = event.pageIndex; this.pageSize = event.pageSize; this.loadProducts(); }

  openDetail(product: any): void { this.selectedProduct = product; this.detailQty = product.minOrderQty || 1; }

  addToCart(event: Event, product: any): void {
    event.stopPropagation();
    this.cart.addToCart(product, 1);
    this.snack.open(`${product.name} added to cart!`, 'View Cart', { duration: 2500, panelClass: 'snack-success' });
  }

  addToCartDetail(): void {
    this.cart.addToCart(this.selectedProduct, this.detailQty);
    this.snack.open(`${this.selectedProduct.name} added to cart!`, 'Close', { duration: 2500, panelClass: 'snack-success' });
    this.selectedProduct = null;
  }

  getStars(rating: number): string {
    const full = Math.floor(rating || 0);
    return '★'.repeat(full) + '☆'.repeat(5 - full);
  }

  readonly NO_IMAGE = `data:image/svg+xml;utf8,<svg xmlns='http://www.w3.org/2000/svg' width='400' height='220' viewBox='0 0 400 220'><rect width='400' height='220' fill='%23f5f0ea'/><rect x='160' y='70' width='80' height='60' rx='8' fill='%23e8d5c0'/><circle cx='200' cy='95' r='15' fill='%23d4b896'/><polygon points='160,130 200,90 240,130' fill='%23c9a87c'/><text x='200' y='165' text-anchor='middle' font-family='Inter,sans-serif' font-size='13' fill='%239c8472'>No Image</text></svg>`;

  getImage(url: string | null | undefined): string {
    return url && url.trim() ? url : this.NO_IMAGE;
  }

  trackProduct(index: number, product: any): number {
    return product.id;
  }
}
