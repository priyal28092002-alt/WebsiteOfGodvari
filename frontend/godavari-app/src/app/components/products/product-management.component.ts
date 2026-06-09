import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { MatSnackBar } from '@angular/material/snack-bar';
import { ProductService } from '../../services/product.service';
import { InventoryService } from '../../services/inventory.service';

@Component({
  selector: 'app-product-management',
  template: `
    <div class="pm-root">

      <!-- ═══════════════════════════
           PRODUCT LIST VIEW
           ═══════════════════════════ -->
      <div *ngIf="!showForm" class="list-view">

        <!-- Header -->
        <div class="list-header">
          <div class="list-header-left">
            <div class="hdr-icon"><mat-icon>storefront</mat-icon></div>
            <div>
              <h2>Product Catalog</h2>
              <p>{{ totalElements }} products in your store</p>
            </div>
          </div>
          <button class="add-product-btn" (click)="openAddForm()">
            <mat-icon>add</mat-icon> Add New Product
          </button>
        </div>

        <!-- Search & Filter Bar -->
        <div class="filter-bar">
          <div class="search-wrap">
            <mat-icon>search</mat-icon>
            <input [(ngModel)]="searchQuery" placeholder="Search by name, SKU, brand..."
              (keyup.enter)="loadProducts()" (input)="onSearchInput()">
            <button *ngIf="searchQuery" (click)="searchQuery=''; loadProducts()">
              <mat-icon>close</mat-icon>
            </button>
          </div>
          <div class="filter-select-wrap">
            <mat-icon>category</mat-icon>
            <select [(ngModel)]="categoryFilter" (change)="loadProducts()">
              <option value="">All Categories</option>
              <option *ngFor="let c of categories" [value]="c.id">{{ c.name }}</option>
            </select>
          </div>
          <button class="refresh-btn" (click)="loadProducts()">
            <mat-icon>refresh</mat-icon>
          </button>
        </div>

        <!-- Loading -->
        <div *ngIf="loading" class="loading-state">
          <div class="loading-spinner"></div>
          <p>Loading products...</p>
        </div>

        <!-- Empty State -->
        <div *ngIf="!loading && products.length === 0" class="empty-state">
          <div class="empty-icon">📦</div>
          <h3>No products yet</h3>
          <p>Start by adding your first product to the catalog</p>
          <button class="add-product-btn" (click)="openAddForm()">
            <mat-icon>add</mat-icon> Add First Product
          </button>
        </div>

        <!-- Products Grid Cards -->
        <div class="products-grid" *ngIf="!loading && products.length > 0">
          <div *ngFor="let p of products; let i=index"
            class="product-tile" [style.animation-delay.ms]="i*50">

            <div class="tile-img-wrap">
              <img [src]="p.imageUrl || 'https://images.unsplash.com/photo-1589939705384-5185137a7f0f?w=300&q=70'"
                [alt]="p.name"
                onerror="this.src='https://images.unsplash.com/photo-1589939705384-5185137a7f0f?w=300&q=70'">
              <div class="tile-status" [class.active]="p.active" [class.inactive]="!p.active">
                {{ p.active ? '● Active' : '● Inactive' }}
              </div>
              <div class="tile-discount" *ngIf="p.bulkDiscountPercent > 0">
                -{{ p.bulkDiscountPercent }}%
              </div>
            </div>

            <div class="tile-body">
              <div class="tile-cat">{{ p.category?.name }}</div>
              <h4 class="tile-name">{{ p.name }}</h4>
              <p class="tile-sku">SKU: {{ p.sku }} · {{ p.brand }}</p>

              <div class="tile-price-row">
                <span class="tile-price">₹{{ p.price | number:'1.0-0' }}</span>
                <span class="tile-unit">/ {{ p.unit }}</span>
                <span class="tile-gst">+{{ p.gstRate }}% GST</span>
              </div>

              <div class="tile-stock">
                <span class="stock-badge"
                  [class.in]="getStock(p) > 0"
                  [class.low]="getStock(p) > 0 && getStock(p) <= 20"
                  [class.out]="getStock(p) === 0"
                  [class.na]="getStock(p) === -1">
                  <mat-icon>{{ getStock(p) > 0 ? 'check_circle' : getStock(p) === -1 ? 'help_outline' : 'cancel' }}</mat-icon>
                  <span *ngIf="getStock(p) === -1">No stock record</span>
                  <span *ngIf="getStock(p) === 0">Out of Stock</span>
                  <span *ngIf="getStock(p) > 0">{{ getStock(p) }} {{ p.unit }} in stock</span>
                </span>
              </div>

              <div class="tile-actions">
                <button class="tile-edit-btn" (click)="openEditForm(p)">
                  <mat-icon>edit</mat-icon> Edit
                </button>
                <button class="tile-del-btn" (click)="confirmDelete(p)">
                  <mat-icon>delete_outline</mat-icon>
                </button>
              </div>
            </div>
          </div>
        </div>

        <!-- Pagination -->
        <mat-paginator *ngIf="!loading && totalElements > pageSize"
          [length]="totalElements" [pageSize]="pageSize"
          [pageSizeOptions]="[12,24,48]"
          (page)="onPage($event)"
          class="paginator">
        </mat-paginator>
      </div>

      <!-- ═══════════════════════════
           ADD / EDIT FORM VIEW
           ═══════════════════════════ -->
      <div *ngIf="showForm" class="form-view">

        <!-- Form Header -->
        <div class="form-header">
          <button class="back-btn" (click)="cancelForm()">
            <mat-icon>arrow_back</mat-icon> Back to Products
          </button>
          <div class="form-header-title">
            <div class="form-hdr-icon">
              <mat-icon>{{ editingProduct ? 'edit' : 'add_circle' }}</mat-icon>
            </div>
            <div>
              <h2>{{ editingProduct ? 'Edit Product' : 'Add New Product' }}</h2>
              <p>{{ editingProduct ? 'Update product details below' : 'Fill in the details to add a new product' }}</p>
            </div>
          </div>
        </div>

        <form [formGroup]="productForm" (ngSubmit)="saveProduct()" class="product-form">

          <!-- Error Banner -->
          <div *ngIf="saveError" class="save-error-banner">
            <mat-icon>error_outline</mat-icon>
            <span>{{ saveError }}</span>
            <button type="button" (click)="saveError=''">
              <mat-icon>close</mat-icon>
            </button>
          </div>
          <div class="form-body">

            <!-- LEFT COLUMN -->
            <div class="form-left">

              <!-- Image Preview Card -->
              <div class="img-preview-card">
                <div class="img-preview-wrap">
                  <img *ngIf="imagePreview" [src]="imagePreview" alt="Preview"
                    onerror="this.style.display='none'">
                  <div *ngIf="!imagePreview" class="img-placeholder">
                    <mat-icon>add_photo_alternate</mat-icon>
                    <p>Image Preview</p>
                    <span>Enter URL below to preview</span>
                  </div>
                </div>
                <div class="img-url-field">
                  <mat-icon>link</mat-icon>
                  <input formControlName="imageUrl" placeholder="https://... image URL"
                    (input)="onImageUrlChange($event)">
                </div>
                <p class="img-hint">💡 Use Unsplash or any image URL</p>
                <div class="quick-images">
                  <p>Quick picks:</p>
                  <div class="quick-imgs-row">
                    <img *ngFor="let qi of quickImages" [src]="qi.url" [title]="qi.label"
                      (click)="setQuickImage(qi.url)">
                  </div>
                </div>
              </div>

              <!-- Description -->
              <div class="form-section">
                <div class="section-hdr">
                  <mat-icon>description</mat-icon>
                  <h4>Description</h4>
                </div>
                <div class="text-field-wrap">
                  <textarea formControlName="description" rows="5"
                    placeholder="Enter detailed product description, features, specifications..."></textarea>
                </div>
              </div>

            </div>

            <!-- RIGHT COLUMN -->
            <div class="form-right">

              <!-- Basic Info Section -->
              <div class="form-section">
                <div class="section-hdr">
                  <mat-icon>info</mat-icon>
                  <h4>Basic Information</h4>
                </div>
                <div class="fields-grid">
                  <div class="fw full">
                    <label>Product Name <span class="req">*</span></label>
                    <div class="fi" [class.err]="inv('name')">
                      <mat-icon>label</mat-icon>
                      <input formControlName="name" placeholder="e.g. UltraTech OPC 53 Grade Cement">
                    </div>
                    <span *ngIf="inv('name')" class="err-msg">Product name is required</span>
                  </div>

                  <div class="fw">
                    <label>SKU Code <span class="req">*</span></label>
                    <div class="fi" [class.err]="inv('sku')">
                      <mat-icon>qr_code</mat-icon>
                      <input formControlName="sku" placeholder="e.g. CEM-OPC53-50KG">
                    </div>
                    <span *ngIf="inv('sku')" class="err-msg">SKU is required</span>
                  </div>

                  <div class="fw">
                    <label>Brand</label>
                    <div class="fi">
                      <mat-icon>verified</mat-icon>
                      <input formControlName="brand" placeholder="e.g. UltraTech">
                    </div>
                  </div>

                  <div class="fw">
                    <label>Category <span class="req">*</span></label>
                    <div class="fsel" [class.err]="inv('categoryId')">
                      <mat-icon>category</mat-icon>
                      <select formControlName="categoryId">
                        <option value="">-- Select category --</option>
                        <option *ngFor="let c of categories" [value]="c.id">{{ c.name }}</option>
                      </select>
                    </div>
                    <span *ngIf="inv('categoryId')" class="err-msg">Category is required</span>
                    <div class="cat-add-row" *ngIf="!showAddCat">
                      <button type="button" class="add-cat-link" (click)="showAddCat=true">
                        <mat-icon>add</mat-icon> Add new category
                      </button>
                    </div>
                    <div class="cat-add-row" *ngIf="showAddCat">
                      <input [(ngModel)]="newCatName" [ngModelOptions]="{standalone:true}"
                        placeholder="Category name" class="cat-input">
                      <button type="button" class="cat-save-btn" (click)="addCategory()">
                        Save
                      </button>
                      <button type="button" class="cat-cancel-btn" (click)="showAddCat=false; newCatName=''">
                        Cancel
                      </button>
                    </div>
                  </div>

                  <div class="fw">
                    <label>Unit <span class="req">*</span></label>
                    <div class="fsel">
                      <mat-icon>straighten</mat-icon>
                      <select formControlName="unit">
                        <option *ngFor="let u of units" [value]="u">{{ u }}</option>
                      </select>
                    </div>
                  </div>

                  <div class="fw">
                    <label>HSN Code</label>
                    <div class="fi">
                      <mat-icon>tag</mat-icon>
                      <input formControlName="hsnCode" placeholder="e.g. 2523">
                    </div>
                  </div>
                </div>
              </div>

              <!-- Pricing Section -->
              <div class="form-section">
                <div class="section-hdr">
                  <mat-icon>currency_rupee</mat-icon>
                  <h4>Pricing & Tax</h4>
                </div>
                <div class="fields-grid">
                  <div class="fw">
                    <label>Selling Price (₹) <span class="req">*</span></label>
                    <div class="fi" [class.err]="inv('price')">
                      <span class="fi-prefix">₹</span>
                      <input formControlName="price" type="number" min="0" placeholder="0.00">
                    </div>
                    <span *ngIf="inv('price')" class="err-msg">Price is required</span>
                  </div>

                  <div class="fw">
                    <label>MRP (₹)</label>
                    <div class="fi">
                      <span class="fi-prefix">₹</span>
                      <input formControlName="mrp" type="number" min="0" placeholder="0.00">
                    </div>
                  </div>

                  <div class="fw">
                    <label>GST Rate <span class="req">*</span></label>
                    <div class="fsel">
                      <mat-icon>percent</mat-icon>
                      <select formControlName="gstRate">
                        <option [value]="0">0% — Exempt</option>
                        <option [value]="5">5% — Essential</option>
                        <option [value]="12">12% — Standard</option>
                        <option [value]="18">18% — Standard</option>
                        <option [value]="28">28% — Luxury</option>
                      </select>
                    </div>
                  </div>

                  <!-- Price preview -->
                  <div class="fw price-preview" *ngIf="productForm.value.price > 0">
                    <div class="price-preview-card">
                      <div class="pp-row">
                        <span>Base Price</span>
                        <strong>₹{{ productForm.value.price | number:'1.2-2' }}</strong>
                      </div>
                      <div class="pp-row">
                        <span>GST ({{ productForm.value.gstRate }}%)</span>
                        <strong>₹{{ (productForm.value.price * productForm.value.gstRate / 100) | number:'1.2-2' }}</strong>
                      </div>
                      <div class="pp-row total">
                        <span>Total Price</span>
                        <strong>₹{{ (productForm.value.price * (1 + productForm.value.gstRate / 100)) | number:'1.2-2' }}</strong>
                      </div>
                    </div>
                  </div>
                </div>
              </div>

              <!-- Stock & Inventory Section -->
              <div class="form-section" *ngIf="!editingProduct">
                <div class="section-hdr">
                  <mat-icon>warehouse</mat-icon>
                  <h4>Initial Stock</h4>
                  <span class="section-hint">Set opening inventory for this product</span>
                </div>
                <div class="fields-grid">
                  <div class="fw">
                    <label>Opening Stock Quantity <span class="req">*</span></label>
                    <div class="fi" [class.err]="inv('initialStock')">
                      <mat-icon>inventory_2</mat-icon>
                      <input formControlName="initialStock" type="number" min="0" placeholder="e.g. 100">
                      <span class="fi-unit">{{ productForm.value.unit || 'units' }}</span>
                    </div>
                    <span *ngIf="inv('initialStock')" class="err-msg">Stock quantity is required</span>
                    <span class="field-hint">How many {{ productForm.value.unit || 'units' }} do you have in stock right now?</span>
                  </div>
                  <div class="fw">
                    <label>Low Stock Alert Threshold</label>
                    <div class="fi">
                      <mat-icon>warning_amber</mat-icon>
                      <input formControlName="lowStockThreshold" type="number" min="0" placeholder="e.g. 20">
                      <span class="fi-unit">{{ productForm.value.unit || 'units' }}</span>
                    </div>
                    <span class="field-hint">Alert when stock falls below this level</span>
                  </div>
                  <div class="fw">
                    <label>Warehouse Location</label>
                    <div class="fi">
                      <mat-icon>location_on</mat-icon>
                      <input formControlName="warehouseLocation" placeholder="e.g. Rack A-1, Main Warehouse">
                    </div>
                  </div>

                  <!-- Stock preview -->
                  <div class="fw stock-preview" *ngIf="productForm.value.initialStock > 0">
                    <div class="stock-preview-card"
                      [class.low]="productForm.value.initialStock <= productForm.value.lowStockThreshold">
                      <mat-icon>{{ productForm.value.initialStock <= productForm.value.lowStockThreshold ? 'warning' : 'check_circle' }}</mat-icon>
                      <div>
                        <strong>{{ productForm.value.initialStock }} {{ productForm.value.unit || 'units' }}</strong>
                        <span>{{ productForm.value.initialStock <= productForm.value.lowStockThreshold ? '⚠️ Below threshold' : '✅ Healthy stock level' }}</span>
                      </div>
                    </div>
                  </div>
                </div>
              </div>

              <!-- Edit stock info (when editing existing product) -->
              <div class="form-section" *ngIf="editingProduct">
                <div class="section-hdr">
                  <mat-icon>warehouse</mat-icon>
                  <h4>Stock Management</h4>
                </div>
                <div class="stock-info-box">
                  <mat-icon>info_outline</mat-icon>
                  <div>
                    <strong>To update stock quantity</strong>
                    <p>Go to <strong>Inventory Management</strong> page and use the Stock Update form to add or deduct stock for this product.</p>
                  </div>
                  <a routerLink="/admin/inventory" class="inv-link">Go to Inventory →</a>
                </div>
              </div>

              <!-- Quantity & Bulk -->
              <div class="form-section">
                <div class="section-hdr">
                  <mat-icon>inventory</mat-icon>
                  <h4>Order Quantities & Bulk Discount</h4>
                </div>
                <div class="fields-grid">
                  <div class="fw">
                    <label>Min Order Qty</label>
                    <div class="fi">
                      <mat-icon>shopping_basket</mat-icon>
                      <input formControlName="minOrderQty" type="number" min="1" placeholder="1">
                    </div>
                  </div>

                  <div class="fw">
                    <label>Bulk Min Qty</label>
                    <div class="fi">
                      <mat-icon>local_shipping</mat-icon>
                      <input formControlName="bulkMinQty" type="number" min="0" placeholder="0">
                    </div>
                  </div>

                  <div class="fw">
                    <label>Bulk Discount (%)</label>
                    <div class="fi">
                      <mat-icon>discount</mat-icon>
                      <input formControlName="bulkDiscountPercent" type="number" min="0" max="100" placeholder="0">
                    </div>
                  </div>

                  <!-- Bulk discount preview -->
                  <div class="fw bulk-preview"
                    *ngIf="productForm.value.bulkMinQty > 0 && productForm.value.bulkDiscountPercent > 0">
                    <div class="bulk-preview-card">
                      🏷️ Buy {{ productForm.value.bulkMinQty }}+ units and get
                      <strong>{{ productForm.value.bulkDiscountPercent }}% off</strong>
                      → ₹{{ (productForm.value.price * (1 - productForm.value.bulkDiscountPercent/100)) | number:'1.2-2' }} per {{ productForm.value.unit || 'unit' }}
                    </div>
                  </div>
                </div>
              </div>

            </div>
          </div>

          <!-- Form Footer Actions -->
          <div class="form-footer">
            <button type="button" class="clear-btn" (click)="clearForm()">
              <mat-icon>restart_alt</mat-icon> Clear Form
            </button>
            <div class="footer-right">
              <button type="button" class="cancel-btn" (click)="cancelForm()">
                Cancel
              </button>
              <button type="submit" class="save-btn" [disabled]="saving">
                <mat-spinner *ngIf="saving" diameter="18"></mat-spinner>
                <mat-icon *ngIf="!saving">{{ editingProduct ? 'save' : 'add_circle' }}</mat-icon>
                {{ saving ? 'Saving...' : (editingProduct ? 'Update Product' : 'Add Product') }}
              </button>
            </div>
          </div>
        </form>
      </div>

      <!-- Delete Confirm Overlay -->
      <div *ngIf="deleteTarget" class="delete-overlay" (click)="deleteTarget=null">
        <div class="delete-card" (click)="$event.stopPropagation()">
          <div class="delete-icon">🗑️</div>
          <h3>Delete Product?</h3>
          <p>You are about to permanently delete:</p>
          <div class="delete-product-name">{{ deleteTarget.name }}</div>
          <p class="delete-warn">This action cannot be undone.</p>
          <div class="delete-actions">
            <button class="del-cancel" (click)="deleteTarget=null">Cancel</button>
            <button class="del-confirm" (click)="deleteProduct()">
              <mat-icon>delete</mat-icon> Yes, Delete
            </button>
          </div>
        </div>
      </div>

    </div>
  `,
  styles: [`
    .pm-root { min-height: 100vh; background: #f0f2f5; }

    /* ───────── LIST VIEW ───────── */
    .list-view { max-width: 1440px; margin: 0 auto; padding: 28px 24px; animation: fuUp .4s ease-out; }

    .list-header {
      display: flex; justify-content: space-between; align-items: center;
      margin-bottom: 24px; flex-wrap: wrap; gap: 16px;
    }
    .list-header-left { display: flex; align-items: center; gap: 14px; }
    .hdr-icon {
      width: 52px; height: 52px;
      background: linear-gradient(135deg,#1a237e,#3949ab);
      border-radius: 14px; display: flex; align-items: center; justify-content: center;
      color: white; box-shadow: 0 4px 14px rgba(26,35,126,.3);
    }
    .list-header h2 { font-size: 24px; font-weight: 800; color: #1a237e; }
    .list-header p  { font-size: 14px; color: #64748b; margin-top: 2px; }

    .add-product-btn {
      display: flex; align-items: center; gap: 8px;
      padding: 12px 24px; border-radius: 12px;
      background: linear-gradient(135deg,#1a237e,#3949ab); color: white;
      border: none; font-size: 14px; font-weight: 700; cursor: pointer;
      font-family: 'Inter',sans-serif;
      box-shadow: 0 4px 16px rgba(26,35,126,.3); transition: all .2s;
    }
    .add-product-btn:hover { transform: translateY(-2px); box-shadow: 0 8px 24px rgba(26,35,126,.4); }

    /* Filter bar */
    .filter-bar {
      display: flex; align-items: center; gap: 12px;
      background: white; border-radius: 14px; padding: 14px 20px;
      margin-bottom: 24px; border: 1px solid #e2e8f0;
      box-shadow: 0 2px 8px rgba(0,0,0,.04); flex-wrap: wrap;
    }
    .search-wrap {
      flex: 1; min-width: 240px; display: flex; align-items: center; gap: 10px;
      background: #f8fafc; border: 2px solid #e2e8f0; border-radius: 10px;
      padding: 0 14px; transition: all .2s;
    }
    .search-wrap:focus-within { border-color: #1a237e; background: white; box-shadow: 0 0 0 4px rgba(26,35,126,.06); }
    .search-wrap mat-icon { color: #94a3b8; font-size: 20px; flex-shrink: 0; }
    .search-wrap input { flex:1; border:none; background:transparent; padding:11px 0; font-size:14px; font-family:'Inter',sans-serif; outline:none; color:#1a1a2e; }
    .search-wrap input::placeholder { color: #94a3b8; }
    .search-wrap button { background:none; border:none; cursor:pointer; color:#94a3b8; display:flex; align-items:center; }

    .filter-select-wrap {
      display: flex; align-items: center; gap: 10px;
      background: #f8fafc; border: 2px solid #e2e8f0; border-radius: 10px;
      padding: 0 14px; min-width: 200px; transition: all .2s;
    }
    .filter-select-wrap:focus-within { border-color: #1a237e; background: white; }
    .filter-select-wrap mat-icon { color: #94a3b8; font-size: 20px; flex-shrink: 0; }
    .filter-select-wrap select { border:none; background:transparent; padding:11px 0; font-size:14px; font-family:'Inter',sans-serif; outline:none; color:#1a1a2e; cursor:pointer; width:100%; }

    .refresh-btn { width:42px; height:42px; border-radius:10px; background:#f1f5f9; border:none; cursor:pointer; display:flex; align-items:center; justify-content:center; color:#64748b; transition:all .2s; }
    .refresh-btn:hover { background:#e2e8f0; transform:rotate(90deg); }

    /* States */
    .loading-state { text-align:center; padding:80px; }
    .loading-spinner { width:48px; height:48px; border-radius:50%; border:4px solid #e2e8f0; border-top-color:#1a237e; margin:0 auto 20px; animation:spin .8s linear infinite; }
    @keyframes spin { to { transform: rotate(360deg); } }
    .loading-state p { color:#64748b; font-size:15px; }

    .empty-state { text-align:center; padding:80px 20px; animation:fuUp .5s ease-out; }
    .empty-icon { font-size:64px; margin-bottom:16px; }
    .empty-state h3 { font-size:22px; font-weight:700; color:#1a237e; margin-bottom:8px; }
    .empty-state p { color:#64748b; margin-bottom:24px; }

    /* Products Grid */
    .products-grid {
      display: grid;
      grid-template-columns: repeat(auto-fill, minmax(260px, 1fr));
      gap: 20px; margin-bottom: 24px;
    }
    .product-tile {
      background: white; border-radius: 16px; overflow: hidden;
      border: 1px solid #e2e8f0;
      box-shadow: 0 2px 8px rgba(0,0,0,.04);
      transition: all .3s ease;
      animation: fuUp .4s ease-out both;
    }
    .product-tile:hover { transform: translateY(-6px); box-shadow: 0 16px 40px rgba(0,0,0,.1); border-color: transparent; }

    .tile-img-wrap { position:relative; height:185px; overflow:hidden; }
    .tile-img-wrap img { width:100%; height:100%; object-fit:cover; transition:transform .4s; }
    .product-tile:hover .tile-img-wrap img { transform:scale(1.06); }
    .tile-status {
      position:absolute; top:10px; left:10px;
      padding:3px 10px; border-radius:20px; font-size:11px; font-weight:700;
    }
    .tile-status.active { background:rgba(0,200,83,.15); color:#00c853; border:1px solid rgba(0,200,83,.3); }
    .tile-status.inactive { background:rgba(213,0,0,.12); color:#d50000; border:1px solid rgba(213,0,0,.2); }
    .tile-discount { position:absolute; top:10px; right:10px; background:#ef4444; color:white; padding:3px 8px; border-radius:6px; font-size:11px; font-weight:700; }

    .tile-body { padding:16px; }
    .tile-cat { font-size:11px; font-weight:700; text-transform:uppercase; color:#1a237e; background:#e8eaf6; padding:3px 10px; border-radius:10px; display:inline-block; margin-bottom:8px; }
    .tile-name { font-size:14px; font-weight:700; color:#1a1a2e; margin-bottom:3px; display:-webkit-box; -webkit-line-clamp:2; -webkit-box-orient:vertical; overflow:hidden; }
    .tile-sku { font-size:11px; color:#94a3b8; margin-bottom:10px; }
    .tile-price-row { display:flex; align-items:baseline; gap:6px; margin-bottom:8px; }
    .tile-price { font-size:20px; font-weight:800; color:#1a237e; }
    .tile-unit { font-size:12px; color:#64748b; }
    .tile-gst { font-size:11px; color:#94a3b8; margin-left:auto; }
    .stock-badge { display:inline-flex; align-items:center; gap:4px; font-size:12px; font-weight:600; padding:4px 10px; border-radius:8px; }
    .stock-badge mat-icon { font-size:14px; width:14px; height:14px; }
    .stock-badge.in  { background:#f0fdf4; color:#16a34a; }
    .stock-badge.low { background:#fffbeb; color:#92400e; }
    .stock-badge.out { background:#fef2f2; color:#dc2626; }
    .stock-badge.na  { background:#f8fafc; color:#94a3b8; }

    .tile-actions { display:flex; gap:8px; margin-top:12px; padding-top:12px; border-top:1px solid #f1f5f9; }
    .tile-edit-btn {
      flex:1; display:flex; align-items:center; justify-content:center; gap:5px;
      padding:8px; border-radius:8px; background:#f0f2ff; border:none;
      color:#1a237e; font-size:13px; font-weight:600; cursor:pointer;
      font-family:'Inter',sans-serif; transition:all .2s;
    }
    .tile-edit-btn:hover { background:#e8eaf6; }
    .tile-del-btn {
      width:36px; height:36px; border-radius:8px; background:#fef2f2; border:none;
      color:#dc2626; cursor:pointer; display:flex; align-items:center; justify-content:center;
      transition:all .2s;
    }
    .tile-del-btn:hover { background:#fee2e2; }

    .paginator { background:white; border-radius:12px; box-shadow:0 2px 8px rgba(0,0,0,.04); }

    /* ───────── FORM VIEW ───────── */
    .form-view { animation: fuUp .4s ease-out; }

    .form-header {
      background: #0f172a; padding: 16px 32px;
      display: flex; align-items: center; gap: 24px; flex-wrap: wrap;
    }
    .back-btn {
      display:flex; align-items:center; gap:6px;
      background:rgba(255,255,255,.08); border:1px solid rgba(255,255,255,.12);
      color:rgba(255,255,255,.8); padding:8px 16px; border-radius:8px;
      font-size:13px; font-weight:500; cursor:pointer; font-family:'Inter',sans-serif;
      transition:all .2s; flex-shrink:0;
    }
    .back-btn:hover { background:rgba(255,255,255,.15); color:white; }
    .form-header-title { display:flex; align-items:center; gap:14px; }
    .form-hdr-icon {
      width:44px; height:44px; border-radius:10px;
      background:linear-gradient(135deg,#3b82f6,#8b5cf6);
      display:flex; align-items:center; justify-content:center; color:white;
    }
    .form-header h2 { font-size:20px; font-weight:800; color:white; }
    .form-header p  { font-size:13px; color:rgba(255,255,255,.5); margin-top:2px; }

    .product-form { display:flex; flex-direction:column; }

    .form-body {
      display: grid; grid-template-columns: 340px 1fr;
      gap: 24px; padding: 24px 32px; max-width: 1400px; margin: 0 auto; width: 100%;
    }

    /* Sections */
    .form-section {
      background: white; border-radius: 14px; padding: 22px 24px;
      margin-bottom: 20px; border: 1px solid #e2e8f0;
      box-shadow: 0 2px 6px rgba(0,0,0,.04); animation: fuUp .4s ease-out both;
    }
    .section-hdr {
      display:flex; align-items:center; gap:10px;
      margin-bottom:18px; padding-bottom:14px; border-bottom:2px solid #f1f5f9;
    }
    .section-hdr mat-icon { color:#1a237e; }
    .section-hdr h4 { font-size:15px; font-weight:700; color:#1a237e; }

    /* Image Preview */
    .img-preview-card { background:white; border-radius:14px; padding:20px; border:1px solid #e2e8f0; margin-bottom:20px; box-shadow:0 2px 6px rgba(0,0,0,.04); }
    .img-preview-wrap {
      width:100%; height:220px; border-radius:10px; overflow:hidden;
      background:#f8fafc; border:2px dashed #e2e8f0; margin-bottom:14px;
      display:flex; align-items:center; justify-content:center; transition:all .3s;
    }
    .img-preview-wrap img { width:100%; height:100%; object-fit:cover; animation:fuUp .4s ease; }
    .img-placeholder { text-align:center; color:#94a3b8; }
    .img-placeholder mat-icon { font-size:48px; width:48px; height:48px; color:#e2e8f0; }
    .img-placeholder p { font-size:14px; font-weight:600; margin-top:8px; }
    .img-placeholder span { font-size:12px; }
    .img-url-field {
      display:flex; align-items:center; gap:8px; background:#f8fafc;
      border:2px solid #e2e8f0; border-radius:10px; padding:0 12px; transition:all .2s;
    }
    .img-url-field:focus-within { border-color:#1a237e; background:white; }
    .img-url-field mat-icon { color:#94a3b8; font-size:18px; flex-shrink:0; }
    .img-url-field input { flex:1; border:none; background:transparent; padding:11px 0; font-size:13px; font-family:'Inter',sans-serif; outline:none; color:#1a1a2e; }
    .img-url-field input::placeholder { color:#94a3b8; }
    .img-hint { font-size:11px; color:#94a3b8; margin-top:6px; }

    .quick-images { margin-top:14px; padding-top:14px; border-top:1px solid #f1f5f9; }
    .quick-images p { font-size:11px; font-weight:700; color:#64748b; text-transform:uppercase; margin-bottom:8px; }
    .quick-imgs-row { display:flex; gap:8px; flex-wrap:wrap; }
    .quick-imgs-row img { width:52px; height:52px; object-fit:cover; border-radius:8px; cursor:pointer; border:2px solid #e2e8f0; transition:all .2s; }
    .quick-imgs-row img:hover { border-color:#1a237e; transform:scale(1.08); }

    /* Description textarea */
    .text-field-wrap textarea {
      width:100%; border:2px solid #e2e8f0; border-radius:10px;
      padding:12px 14px; font-size:14px; font-family:'Inter',sans-serif;
      outline:none; resize:vertical; transition:all .2s; background:#f8fafc; color:#1a1a2e;
    }
    .text-field-wrap textarea:focus { border-color:#1a237e; background:white; box-shadow:0 0 0 4px rgba(26,35,126,.06); }
    .text-field-wrap textarea::placeholder { color:#94a3b8; }

    /* Fields grid */
    .fields-grid { display:grid; grid-template-columns:1fr 1fr; gap:14px; }
    .fw { display:flex; flex-direction:column; gap:5px; }
    .fw.full { grid-column:span 2; }
    .fw > label { font-size:12px; font-weight:700; color:#475569; text-transform:uppercase; letter-spacing:.4px; }
    .req { color:#ef4444; }
    .err-msg { font-size:11px; color:#ef4444; }

    .fi {
      display:flex; align-items:center; gap:8px;
      background:#f8fafc; border:2px solid #e2e8f0; border-radius:10px;
      padding:0 13px; transition:all .2s;
    }
    .fi:focus-within { border-color:#1a237e; background:white; box-shadow:0 0 0 4px rgba(26,35,126,.06); }
    .fi.err { border-color:#ef4444; }
    .fi input {
      flex:1; border:none; background:transparent; padding:12px 0;
      font-size:14px; font-family:'Inter',sans-serif; outline:none; color:#1a1a2e;
    }
    .fi input::placeholder { color:#94a3b8; }
    .fi mat-icon { color:#94a3b8; font-size:19px; flex-shrink:0; }
    .fi .fi-prefix { color:#94a3b8; font-size:15px; font-weight:600; }
    /* Dropdown select styling */
    .fsel {
      display:flex; align-items:center; gap:8px;
      background:#f8fafc; border:2px solid #e2e8f0; border-radius:10px;
      padding:0 4px 0 13px; transition:all .2s;
    }
    .fsel:focus-within { border-color:#1a237e; background:white; box-shadow:0 0 0 4px rgba(26,35,126,.06); }
    .fsel.err { border-color:#ef4444; }
    .fsel mat-icon { color:#94a3b8; font-size:19px; flex-shrink:0; }
    .fsel select {
      flex:1; border:none; background:transparent; padding:12px 4px;
      font-size:14px; font-family:'Inter',sans-serif; outline:none; color:#1a1a2e;
      cursor:pointer; appearance:auto; -webkit-appearance:auto;
    }
    .fsel select option { background:white; color:#1a1a2e; padding:8px; font-size:14px; }

    /* Price preview */
    .price-preview, .bulk-preview { grid-column:span 2; }
    .price-preview-card {
      background:linear-gradient(135deg,#f0f2ff,#e8eaf6); border-radius:10px;
      padding:14px 16px; border:1px solid #c5cae9;
    }
    .pp-row { display:flex; justify-content:space-between; font-size:13px; color:#475569; padding:3px 0; }
    .pp-row.total { border-top:1px solid #c5cae9; margin-top:6px; padding-top:8px; }
    .pp-row.total span { font-weight:700; color:#1a237e; }
    .pp-row.total strong { font-size:16px; color:#1a237e; }
    .bulk-preview-card {
      background:linear-gradient(135deg,#f0fdf4,#dcfce7); border-radius:10px;
      padding:12px 16px; font-size:13px; color:#166534;
      border:1px solid #bbf7d0;
    }

    /* Form Footer */
    .form-footer {
      display:flex; justify-content:space-between; align-items:center;
      padding:20px 32px; background:white;
      border-top:2px solid #f1f5f9; position:sticky; bottom:0; z-index:10;
      box-shadow:0 -4px 20px rgba(0,0,0,.06);
    }
    .footer-right { display:flex; gap:12px; }
    .clear-btn {
      display:flex; align-items:center; gap:6px; padding:10px 20px;
      border-radius:10px; background:#fef9c3; border:1px solid #fde68a;
      color:#92400e; font-size:14px; font-weight:600; cursor:pointer;
      font-family:'Inter',sans-serif; transition:all .2s;
    }
    .clear-btn:hover { background:#fef08a; }
    .cancel-btn {
      padding:11px 24px; border-radius:10px; background:#f1f5f9; border:none;
      color:#475569; font-size:14px; font-weight:600; cursor:pointer;
      font-family:'Inter',sans-serif; transition:all .2s;
    }
    .cancel-btn:hover { background:#e2e8f0; }
    .save-btn {
      display:flex; align-items:center; gap:8px; padding:11px 32px;
      border-radius:10px; background:linear-gradient(135deg,#1a237e,#3949ab);
      color:white; border:none; font-size:15px; font-weight:700; cursor:pointer;
      font-family:'Inter',sans-serif; transition:all .2s;
      box-shadow:0 4px 16px rgba(26,35,126,.3);
    }
    .save-btn:hover:not(:disabled) { transform:translateY(-2px); box-shadow:0 8px 24px rgba(26,35,126,.4); }
    .save-btn:disabled { opacity:.65; cursor:not-allowed; }

    /* Delete Overlay */
    .delete-overlay {
      position:fixed; inset:0; background:rgba(15,23,42,.7);
      backdrop-filter:blur(6px); z-index:500;
      display:flex; align-items:center; justify-content:center; padding:20px;
      animation:fadeIn .3s ease;
    }
    .delete-card {
      background:white; border-radius:20px; padding:40px 36px;
      max-width:400px; width:100%; text-align:center;
      box-shadow:0 24px 60px rgba(0,0,0,.3); animation:scaleIn .3s ease;
    }
    .delete-icon { font-size:52px; margin-bottom:16px; }
    .delete-card h3 { font-size:22px; font-weight:800; color:#dc2626; margin-bottom:10px; }
    .delete-card p { font-size:14px; color:#64748b; }
    .delete-product-name {
      background:#fef2f2; border:1px solid #fecaca; border-radius:8px;
      padding:10px 16px; margin:12px 0; font-size:14px; font-weight:600; color:#dc2626;
    }
    .delete-warn { font-size:12px; color:#94a3b8; margin-bottom:24px; }
    .delete-actions { display:flex; gap:12px; justify-content:center; }
    .del-cancel {
      padding:11px 24px; border-radius:10px; background:#f1f5f9; border:none;
      color:#475569; font-size:14px; font-weight:600; cursor:pointer; transition:all .2s;
    }
    .del-confirm {
      display:flex; align-items:center; gap:6px; padding:11px 24px;
      border-radius:10px; background:#dc2626; color:white; border:none;
      font-size:14px; font-weight:700; cursor:pointer; transition:all .2s;
      box-shadow:0 4px 12px rgba(220,38,38,.3);
    }
    .del-confirm:hover { transform:translateY(-1px); box-shadow:0 6px 18px rgba(220,38,38,.4); }

    .cat-add-row { display:flex; align-items:center; gap:8px; margin-top:6px; }
    .add-cat-link {
      display:flex; align-items:center; gap:4px;
      background:none; border:none; color:#1a237e; font-size:12px;
      font-weight:600; cursor:pointer; padding:0; font-family:'Inter',sans-serif;
    }
    .add-cat-link mat-icon { font-size:16px; width:16px; height:16px; }
    .add-cat-link:hover { text-decoration:underline; }
    .cat-input {
      flex:1; border:2px solid #e2e8f0; border-radius:8px;
      padding:7px 10px; font-size:13px; outline:none;
      font-family:'Inter',sans-serif; color:#1a1a2e;
    }
    .cat-input:focus { border-color:#1a237e; }
    .cat-save-btn {
      padding:7px 14px; border-radius:8px;
      background:#1a237e; color:white; border:none;
      font-size:13px; font-weight:600; cursor:pointer;
      font-family:'Inter',sans-serif;
    }
    .cat-cancel-btn {
      padding:7px 14px; border-radius:8px;
      background:#f1f5f9; color:#475569; border:none;
      font-size:13px; font-weight:600; cursor:pointer;
      font-family:'Inter',sans-serif;
    }
    .section-hint { font-size:11px; color:#94a3b8; font-weight:400; margin-left:auto; }
    .field-hint { font-size:11px; color:#94a3b8; margin-top:3px; }
    .fi-unit { font-size:12px; color:#94a3b8; padding-right:4px; white-space:nowrap; }
    .stock-preview { grid-column:span 2; }
    .stock-preview-card {
      display:flex; align-items:center; gap:12px;
      background:#f0fdf4; border:1px solid #bbf7d0; border-radius:10px;
      padding:12px 16px; color:#166534;
    }
    .stock-preview-card.low { background:#fef9c3; border-color:#fde68a; color:#92400e; }
    .stock-preview-card mat-icon { font-size:22px; width:22px; height:22px; flex-shrink:0; }
    .stock-preview-card strong { display:block; font-size:15px; font-weight:700; }
    .stock-preview-card span { font-size:12px; opacity:.8; }
    .stock-info-box {
      display:flex; align-items:flex-start; gap:12px;
      background:#eff6ff; border:1px solid #bfdbfe; border-radius:10px;
      padding:16px;
    }
    .stock-info-box mat-icon { color:#3b82f6; flex-shrink:0; margin-top:2px; }
    .stock-info-box strong { display:block; font-size:14px; font-weight:700; color:#1e40af; margin-bottom:4px; }
    .stock-info-box p { font-size:13px; color:#3730a3; margin:0; }
    .inv-link {
      margin-left:auto; flex-shrink:0; padding:8px 16px; border-radius:8px;
      background:#3b82f6; color:white; text-decoration:none;
      font-size:13px; font-weight:600; white-space:nowrap;
      transition:all .2s;
    }
    .inv-link:hover { background:#2563eb; }
    @keyframes scaleIn { from{opacity:0;transform:scale(.85);} to{opacity:1;transform:scale(1);} }
    @keyframes fuUp { from{opacity:0;transform:translateY(20px);} to{opacity:1;transform:translateY(0);} }
    @keyframes shake { 0%,100%{transform:translateX(0);} 20%,60%{transform:translateX(-6px);} 40%,80%{transform:translateX(6px);} }
    .save-error-banner {
      display: flex; align-items: center; gap: 12px;
      background: #fef2f2; border: 2px solid #fecaca; border-left: 5px solid #dc2626;
      border-radius: 10px; padding: 14px 18px;
      margin: 16px 32px 0; animation: shake .4s ease;
      color: #dc2626; font-size: 14px; font-weight: 500;
    }
    .save-error-banner mat-icon:first-child { font-size: 22px; width: 22px; height: 22px; flex-shrink: 0; }
    .save-error-banner span { flex: 1; }
    .save-error-banner button { background: none; border: none; cursor: pointer; color: #dc2626; display: flex; align-items: center; padding: 2px; border-radius: 4px; }
    .save-error-banner button:hover { background: rgba(220,38,38,.1); }

    @media (max-width:1100px) {
      .form-body { grid-template-columns:1fr; }
    }
    @media (max-width:768px) {
      .list-view, .form-header { padding:16px; }
      .form-body { padding:16px; }
      .form-footer { padding:14px 16px; }
      .fields-grid { grid-template-columns:1fr; }
      .fw.full { grid-column:span 1; }
    }
  `]
})
export class ProductManagementComponent implements OnInit {
  products: any[] = [];
  categories: any[] = [];
  inventoryMap: Map<number, any> = new Map(); // productId -> inventory
  units = ['bag','kg','ton','piece','box','bundle','litre','sq ft','sq m','rft','set','meter','pair'];
  loading = false;
  saving = false;
  showForm = false;
  editingProduct: any = null;
  deleteTarget: any = null;
  searchQuery = '';
  categoryFilter = '';
  totalElements = 0;
  pageSize = 12;
  currentPage = 0;
  imagePreview = '';
  productForm!: FormGroup;

  showAddCat = false;
  newCatName = '';
  saveError = '';

  quickImages = [
    { url:'https://images.unsplash.com/photo-1578662996442-48f60103fc96?w=300&q=70', label:'Cement' },
    { url:'https://images.unsplash.com/photo-1621905252507-b35492cc74b4?w=300&q=70', label:'Steel' },
    { url:'https://images.unsplash.com/photo-1615873968403-89e068629265?w=300&q=70', label:'Tiles' },
    { url:'https://images.unsplash.com/photo-1589939705384-5185137a7f0f?w=300&q=70', label:'Paint' },
    { url:'https://images.unsplash.com/photo-1504917595217-d4dc5ebe6122?w=300&q=70', label:'Pipes' },
    { url:'https://images.unsplash.com/photo-1558618666-fcd25c85cd64?w=300&q=70', label:'Electrical' }
  ];

  constructor(private productService: ProductService, private fb: FormBuilder,
              private snack: MatSnackBar, private inventoryService: InventoryService) {}

  ngOnInit(): void {
    this.initForm();
    this.productService.getCategories().subscribe({
      next: c => {
        this.categories = c;
        if (c.length === 0) {
          this.snack.open('No categories found. Add a category first.', 'Close', { duration: 4000 });
        }
      },
      error: () => this.snack.open('Failed to load categories', 'Close', { duration: 3000 })
    });
    this.loadProducts();
  }

  initForm(p?: any): void {
    this.productForm = this.fb.group({
      name:               [p?.name || '', Validators.required],
      sku:                [p?.sku || '', Validators.required],
      categoryId:         [p?.category?.id || '', Validators.required],
      brand:              [p?.brand || ''],
      unit:               [p?.unit || 'bag', Validators.required],
      price:              [p?.price || null, [Validators.required, Validators.min(0)]],
      mrp:                [p?.mrp || null],
      gstRate:            [p?.gstRate ?? 18, Validators.required],
      hsnCode:            [p?.hsnCode || ''],
      minOrderQty:        [p?.minOrderQty || 1],
      bulkMinQty:         [p?.bulkMinQty || 0],
      bulkDiscountPercent:[p?.bulkDiscountPercent || 0],
      imageUrl:           [p?.imageUrl || ''],
      description:        [p?.description || ''],
      // Stock fields (only used when adding new product)
      initialStock:       [0, [Validators.required, Validators.min(0)]],
      lowStockThreshold:  [20],
      warehouseLocation:  ['Main Warehouse']
    });
    this.imagePreview = p?.imageUrl || '';
  }

  loadProducts(): void {
    this.loading = true;
    const obs = this.searchQuery.trim()
      ? this.productService.search(this.searchQuery, this.currentPage, this.pageSize)
      : this.productService.getAll(this.currentPage, this.pageSize);
    obs.subscribe({
      next: (d: any) => {
        this.products = d.content;
        this.totalElements = d.totalElements;
        this.loading = false;
        // Load inventory data for all products
        this.loadInventoryMap();
      },
      error: () => this.loading = false
    });
  }

  loadInventoryMap(): void {
    this.inventoryService.getAll().subscribe({
      next: (inv: any[]) => {
        this.inventoryMap = new Map();
        (inv || []).forEach(i => this.inventoryMap.set(i.productId, i));
      },
      error: () => {} // inventory not critical for product list display
    });
  }

  getStock(product: any): number {
    return this.inventoryMap.get(product.id)?.quantityAvailable ?? -1;
  }

  onSearchInput(): void {
    if (!this.searchQuery) this.loadProducts();
  }

  onPage(e: any): void { this.currentPage = e.pageIndex; this.pageSize = e.pageSize; this.loadProducts(); }

  openAddForm(): void  { this.editingProduct = null; this.initForm(); this.showForm = true; window.scrollTo(0,0); }
  openEditForm(p: any): void { this.editingProduct = p; this.initForm(p); this.showForm = true; window.scrollTo(0,0); }

  cancelForm(): void { this.showForm = false; this.editingProduct = null; this.saveError = ''; }

  clearForm(): void { this.initForm(this.editingProduct); this.saveError = ''; }

  onImageUrlChange(e: any): void {
    this.imagePreview = e.target.value;
  }

  setQuickImage(url: string): void {
    this.productForm.patchValue({ imageUrl: url });
    this.imagePreview = url;
  }

  inv(field: string): boolean {
    const c = this.productForm.get(field);
    return !!(c && c.invalid && c.touched);
  }

  saveProduct(): void {
    this.productForm.markAllAsTouched();
    this.saveError = '';
    if (this.productForm.invalid) {
      this.saveError = 'Please fill all required fields (Name, SKU, Category, Price).';
      return;
    }
    this.saving = true;
    const v = this.productForm.value;
    const obs = this.editingProduct
      ? this.productService.update(this.editingProduct.id, v)
      : this.productService.create(v);

    obs.subscribe({
      next: (savedProduct: any) => {
        // If adding new product, create inventory record automatically
        if (!this.editingProduct && savedProduct?.id) {
          const stockReq = {
            productId:         savedProduct.id,
            productName:       savedProduct.name,
            sku:               savedProduct.sku,
            unit:              v.unit,
            transactionType:   'IN',
            quantity:          v.initialStock || 0,
            lowStockThreshold: v.lowStockThreshold || 20,
            warehouseLocation: v.warehouseLocation || 'Main Warehouse',
            notes:             'Initial stock on product creation',
            createdBy:         'Admin'
          };
          this.inventoryService.updateStock(stockReq).subscribe({
            next: () => {
              this.snack.open(
                `✅ Product added! Stock set to ${v.initialStock} ${v.unit}.`,
                'Close', { duration: 4000, panelClass: 'snack-success' }
              );
            },
            error: () => {
              this.snack.open(
                `✅ Product saved! ⚠️ Stock creation failed — set stock in Inventory page.`,
                'Close', { duration: 5000 }
              );
            }
          });
        } else {
          this.snack.open('✅ Product updated!', 'Close', { duration: 3000, panelClass: 'snack-success' });
        }
        this.saving = false;
        this.showForm = false;
        this.editingProduct = null;
        this.saveError = '';
        this.loadProducts();
      },
      error: err => {
        const msg = err.error?.error || err.error?.message || err.message || 'Save failed. Please try again.';
        this.saveError = msg;
        this.saving = false;
        window.scrollTo(0, 0);
      }
    });
  }

  addCategory(): void {
    const name = this.newCatName.trim();
    if (!name) return;
    this.productService.createCategory({ name }).subscribe({
      next: (cat: any) => {
        this.categories.push(cat);
        this.productForm.patchValue({ categoryId: cat.id });
        this.snack.open(`Category "${cat.name}" added!`, 'Close', { duration: 2500, panelClass: 'snack-success' });
        this.showAddCat = false;
        this.newCatName = '';
      },
      error: () => this.snack.open('Failed to add category', 'Close', { duration: 2500, panelClass: 'snack-error' })
    });
  }

  confirmDelete(p: any): void { this.deleteTarget = p; }

  deleteProduct(): void {
    if (!this.deleteTarget) return;
    this.productService.delete(this.deleteTarget.id).subscribe({
      next: () => {
        this.snack.open('Product deleted', 'Close', { duration: 2000 });
        this.deleteTarget = null;
        this.loadProducts();
      }
    });
  }
}
