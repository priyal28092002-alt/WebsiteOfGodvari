import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { MatSnackBar } from '@angular/material/snack-bar';
import { CartService } from '../../services/cart.service';
import { OrderService } from '../../services/order.service';
import { AuthService } from '../../services/auth.service';

@Component({
  selector: 'app-cart',
  template: `
    <div class="page-container">
      <!-- Header -->
      <div class="cart-header">
        <div class="cart-header-left">
          <div class="cart-icon-wrap">
            <mat-icon>shopping_cart</mat-icon>
          </div>
          <div>
            <h2>Shopping Cart</h2>
            <p>{{ cart.totalItems }} {{ cart.totalItems === 1 ? 'item' : 'items' }} in your cart</p>
          </div>
        </div>
        <button mat-stroked-button routerLink="/products" class="continue-btn">
          <mat-icon>arrow_back</mat-icon> Continue Shopping
        </button>
      </div>

      <!-- Empty Cart -->
      <div *ngIf="cart.items.length === 0" class="empty-cart">
        <div class="empty-cart-icon"><mat-icon>shopping_cart</mat-icon></div>
        <h3>Your cart is empty</h3>
        <p>Add some building materials to get started</p>
        <button mat-raised-button color="warn" routerLink="/products">
          <mat-icon>storefront</mat-icon> Browse Products
        </button>
      </div>

      <div *ngIf="cart.items.length > 0" class="cart-grid">
        <!-- Cart Items -->
        <div class="cart-items">
          <div *ngFor="let item of cart.items; let i = index" class="cart-item" [style.animation-delay.ms]="i*80">
            <img [src]="item.imageUrl || noImage" [alt]="item.productName" class="item-img">
            <div class="item-details">
              <h4>{{ item.productName }}</h4>
              <p class="item-meta">SKU: {{ item.sku }} &bull; Unit: {{ item.unit }}</p>
              <div class="item-price">₹{{ item.unitPrice | number:'1.2-2' }}
                <span class="item-gst">+{{ item.gstRate }}% GST</span>
              </div>
            </div>
            <div class="item-qty">
              <button mat-icon-button class="qty-btn" (click)="cart.updateQuantity(item.productId, item.quantity - 1)">
                <mat-icon>remove</mat-icon>
              </button>
              <span class="qty-value">{{ item.quantity }}</span>
              <button mat-icon-button class="qty-btn" (click)="cart.updateQuantity(item.productId, item.quantity + 1)">
                <mat-icon>add</mat-icon>
              </button>
            </div>
            <div class="item-total">
              <span class="total-price">₹{{ (item.unitPrice * item.quantity) | number:'1.2-2' }}</span>
              <button mat-icon-button class="delete-btn" (click)="cart.remove(item.productId)" matTooltip="Remove">
                <mat-icon>delete_outline</mat-icon>
              </button>
            </div>
          </div>
        </div>

        <!-- Order Summary -->
        <div class="order-summary">
          <div class="summary-header">
            <div class="summary-icon"><mat-icon>receipt_long</mat-icon></div>
            <h3>Order Summary</h3>
          </div>

          <div class="summary-row">
            <span>Subtotal</span>
            <span>₹{{ cart.subtotal | number:'1.2-2' }}</span>
          </div>
          <div class="summary-row">
            <span>GST</span>
            <span>₹{{ cart.totalGst | number:'1.2-2' }}</span>
          </div>
          <div class="summary-row">
            <span>Delivery</span>
            <span [class.free]="cart.deliveryCharge === 0">
              {{ cart.deliveryCharge === 0 ? '🎉 FREE' : ('₹' + cart.deliveryCharge) }}
            </span>
          </div>
          <p *ngIf="cart.deliveryCharge > 0" class="free-hint">
            Add ₹{{ 5000 - cart.subtotal | number:'1.0-0' }} more for free delivery
          </p>

          <div class="summary-total">
            <span>Grand Total</span>
            <span>₹{{ cart.grandTotal | number:'1.2-2' }}</span>
          </div>

          <div class="delivery-section">
            <h4>
              <mat-icon>local_shipping</mat-icon>
              Delivery Details
            </h4>

            <div class="dfield">
              <label>WhatsApp / Phone Number <span class="wa-hint">📲 Order confirmation will be sent here</span></label>
              <div class="dinput" [class.dinput-filled]="phone">
                <mat-icon>whatsapp</mat-icon>
                <input [(ngModel)]="phone" placeholder="e.g. 9876543210" maxlength="13">
              </div>
            </div>

            <div class="dfield">
              <label>Delivery Address</label>
              <div class="dinput">
                <mat-icon>location_on</mat-icon>
                <textarea [(ngModel)]="address" rows="2" placeholder="Enter full street address"></textarea>
              </div>
            </div>

            <div class="dfield">
              <label>City</label>
              <div class="dinput">
                <mat-icon>location_city</mat-icon>
                <input [(ngModel)]="city" placeholder="Nashik">
              </div>
            </div>

            <div class="dfield">
              <label>Pincode</label>
              <div class="dinput">
                <mat-icon>pin_drop</mat-icon>
                <input [(ngModel)]="pincode" placeholder="422001">
              </div>
            </div>

            <div class="dfield">
              <label>Payment Method</label>
              <div class="dinput sel">
                <mat-icon>payment</mat-icon>
                <select [(ngModel)]="paymentMethod">
                  <option value="CASH">💵 Cash on Delivery</option>
                  <option value="UPI">📱 UPI Payment</option>
                  <option value="BANK_TRANSFER">🏦 Bank Transfer</option>
                  <option value="CHEQUE">📝 Cheque</option>
                </select>
              </div>
            </div>
          </div>

          <button class="place-order-btn" (click)="placeOrder()" [disabled]="placing">
            <mat-spinner *ngIf="placing" diameter="20"></mat-spinner>
            <mat-icon *ngIf="!placing">lock</mat-icon>
            {{ placing ? 'Processing...' : 'Place Order Securely' }}
          </button>

          <div class="trust-badges">
            <span>🔒 Secure</span>
            <span>🚚 Fast Delivery</span>
            <span>✅ GST Invoice</span>
          </div>
        </div>
      </div>
    </div>
  `,
  styles: [`
    .cart-header {
      display: flex;
      justify-content: space-between;
      align-items: center;
      margin-bottom: 28px;
      flex-wrap: wrap;
      gap: 16px;
      animation: fadeInUp 0.4s ease-out;
    }
    .cart-header-left {
      display: flex;
      align-items: center;
      gap: 16px;
    }
    .cart-icon-wrap {
      width: 52px; height: 52px;
      background: linear-gradient(135deg, #1a237e, #3949ab);
      border-radius: 14px;
      display: flex;
      align-items: center;
      justify-content: center;
      color: white;
      box-shadow: 0 4px 12px rgba(26,35,126,0.3);
    }
    .cart-header h2 { font-size: 24px; font-weight: 800; color: #1a237e; }
    .cart-header p { font-size: 14px; color: #64748b; margin-top: 2px; }
    .continue-btn { border-radius: 10px !important; }

    .empty-cart {
      text-align: center; padding: 80px 20px; animation: fadeInUp 0.5s ease-out;
    }
    .empty-cart-icon {
      width: 100px; height: 100px; border-radius: 24px;
      background: linear-gradient(135deg,#e8eaf6,#c5cae9);
      display: flex; align-items: center; justify-content: center;
      margin: 0 auto 24px;
      animation: float 3s ease-in-out infinite;
    }
    .empty-cart-icon mat-icon { font-size: 52px; width: 52px; height: 52px; color: #1a237e; }
    .empty-cart h3 { font-size: 22px; font-weight: 700; color: #1a237e; margin-bottom: 8px; }
    .empty-cart p { color: #64748b; margin-bottom: 24px; }

    .cart-grid {
      display: grid;
      grid-template-columns: 1fr 380px;
      gap: 24px;
      align-items: start;
      animation: fadeInUp 0.5s ease-out;
    }

    .cart-item {
      display: flex;
      gap: 16px;
      align-items: center;
      background: white;
      border-radius: 14px;
      padding: 18px;
      margin-bottom: 14px;
      border: 1px solid #e2e8f0;
      box-shadow: 0 2px 6px rgba(0,0,0,0.04);
      transition: all 0.2s ease;
      animation: fadeInUp 0.4s ease-out both;
    }
    .cart-item:hover { box-shadow: 0 6px 16px rgba(0,0,0,0.08); }
    .item-img {
      width: 80px; height: 80px;
      object-fit: cover;
      border-radius: 10px;
      flex-shrink: 0;
    }
    .item-details { flex: 1; min-width: 160px; }
    .item-details h4 { font-size: 15px; font-weight: 600; color: #1a1a2e; margin-bottom: 4px; }
    .item-meta { font-size: 12px; color: #94a3b8; }
    .item-price { font-size: 16px; font-weight: 700; color: #1a237e; margin-top: 6px; }
    .item-gst { font-size: 11px; font-weight: 400; color: #94a3b8; margin-left: 4px; }

    .item-qty {
      display: flex;
      align-items: center;
      gap: 4px;
      background: #f8fafc;
      border-radius: 10px;
      padding: 4px;
    }
    .qty-btn { width: 32px !important; height: 32px !important; }
    .qty-value {
      font-size: 16px;
      font-weight: 700;
      min-width: 36px;
      text-align: center;
      color: #1a237e;
    }

    .item-total { text-align: right; min-width: 100px; }
    .total-price { display: block; font-size: 17px; font-weight: 700; color: #1a237e; }
    .delete-btn { color: #ef4444 !important; }

    .order-summary {
      background: white;
      border-radius: 16px;
      padding: 28px;
      border: 1px solid #e2e8f0;
      box-shadow: 0 4px 12px rgba(0,0,0,0.06);
      position: sticky;
      top: 100px;
    }
    .summary-header {
      display: flex; align-items: center; gap: 12px; margin-bottom: 20px;
    }
    .summary-icon {
      width: 40px; height: 40px; border-radius: 10px;
      background: linear-gradient(135deg,#1a237e,#3949ab);
      display: flex; align-items: center; justify-content: center; color: white;
    }
    .summary-icon mat-icon { font-size: 22px; width: 22px; height: 22px; }
    .summary-header h3 { font-size: 18px; font-weight: 700; color: #1a237e; }

    .summary-row {
      display: flex;
      justify-content: space-between;
      font-size: 14px;
      margin-bottom: 12px;
      color: #475569;
    }
    .summary-row .free { color: #00c853; font-weight: 600; }
    .free-hint { font-size: 12px; color: #ff6d00; margin: -4px 0 12px; }

    .summary-total {
      display: flex;
      justify-content: space-between;
      font-size: 20px;
      font-weight: 800;
      color: #1a237e;
      padding: 16px 0;
      margin: 12px 0;
      border-top: 2px solid #f1f5f9;
      border-bottom: 2px solid #f1f5f9;
    }

    .delivery-section { margin-top: 20px; }
    .delivery-section h4 {
      display: flex;
      align-items: center;
      gap: 8px;
      font-size: 15px;
      font-weight: 700;
      color: #334155;
      margin-bottom: 16px;
    }
    .dfield { display:flex; flex-direction:column; gap:5px; margin-bottom:12px; }
    .dfield label { font-size:12px; font-weight:700; color:#475569; text-transform:uppercase; letter-spacing:.4px; }
    .dinput {
      display:flex; align-items:center; gap:8px;
      background:#f8fafc; border:2px solid #e2e8f0; border-radius:10px;
      padding:0 13px; transition:all .2s;
    }
    .dinput:focus-within { border-color:#1a237e; background:#fff; box-shadow:0 0 0 4px rgba(26,35,126,.06); }
    .dinput mat-icon { color:#94a3b8; font-size:19px; flex-shrink:0; }
    .dinput input, .dinput textarea, .dinput select {
      flex:1; border:none; background:transparent; padding:12px 0;
      font-size:14px; font-family:'Inter',sans-serif; color:#1a1a2e; outline:none; resize:none;
    }
    .wa-hint { color:#25d366; font-size:10px; font-weight:600; margin-left:6px; }
    .dinput-filled { border-color:#25d366 !important; }
    .dinput-filled mat-icon { color:#25d366 !important; }
    .dinput input::placeholder, .dinput textarea::placeholder { color:#94a3b8; }

    .place-order-btn {
      width: 100%;
      height: 52px;
      background: linear-gradient(135deg, #1a237e, #3949ab);
      color: white;
      border: none;
      border-radius: 12px;
      font-size: 16px;
      font-weight: 700;
      cursor: pointer;
      display: flex;
      align-items: center;
      justify-content: center;
      gap: 10px;
      margin-top: 20px;
      box-shadow: 0 6px 20px rgba(26,35,126,0.35);
      transition: all 0.2s ease;
    }
    .place-order-btn:hover:not(:disabled) {
      transform: translateY(-2px);
      box-shadow: 0 10px 28px rgba(26,35,126,0.45);
    }
    .place-order-btn:disabled { opacity: 0.7; cursor: not-allowed; }

    .trust-badges {
      display: flex;
      justify-content: center;
      gap: 16px;
      margin-top: 16px;
      font-size: 12px;
      color: #64748b;
      font-weight: 500;
    }

    @keyframes fadeInUp {
      from { opacity: 0; transform: translateY(20px); }
      to { opacity: 1; transform: translateY(0); }
    }
    @keyframes float {
      0%,100% { transform: translateY(0); }
      50% { transform: translateY(-10px); }
    }

    @media (max-width: 1024px) {
      .cart-grid { grid-template-columns: 1fr; }
      .order-summary { position: static; }
    }
    @media (max-width: 600px) {
      .cart-item { flex-wrap: wrap; }
      .item-total { width: 100%; text-align: left; margin-top: 8px; }
    }
  `]
})
export class CartComponent {
  readonly noImage = `data:image/svg+xml;utf8,<svg xmlns='http://www.w3.org/2000/svg' width='80' height='80' viewBox='0 0 80 80'><rect width='80' height='80' rx='10' fill='%23f5f0ea'/><rect x='25' y='22' width='30' height='22' rx='4' fill='%23e8d5c0'/><circle cx='40' cy='30' r='6' fill='%23d4b896'/><polygon points='25,44 40,28 55,44' fill='%23c9a87c'/></svg>`;

  address = '';
  city = 'Nashik';
  pincode = '';
  paymentMethod = 'CASH';
  phone = '';
  placing = false;

  constructor(public cart: CartService, private orderService: OrderService,
              private auth: AuthService, private router: Router, private snack: MatSnackBar) {
    this.phone = auth.currentUser?.phone || '';
  }

  placeOrder(): void {
    if (!this.address) { this.snack.open('Please enter delivery address', 'Close', { duration: 2500 }); return; }
    this.placing = true;
    const user = this.auth.currentUser!;
    const orderReq = {
      userId: user.userId,
      userName: user.name,
      userEmail: user.email,
      deliveryAddress: this.address,
      deliveryCity: this.city,
      deliveryPincode: this.pincode,
      paymentMethod: this.paymentMethod,
      customerPhone: this.phone || user.phone || '',
      items: this.cart.items.map(i => ({
        productId: i.productId, productName: i.productName,
        sku: i.sku, unit: i.unit, quantity: i.quantity,
        unitPrice: i.unitPrice, gstRate: i.gstRate
      }))
    };
    this.orderService.create(orderReq).subscribe({
      next: (order) => {
        this.cart.clear();
        this.snack.open(`Order ${order.orderNumber} placed successfully!`, 'View Orders', { duration: 4000, panelClass: 'snack-success' });
        this.router.navigate(['/orders']);
      },
      error: () => {
        this.snack.open('Failed to place order. Try again.', 'Close', { duration: 3000, panelClass: 'snack-error' });
        this.placing = false;
      }
    });
  }
}
