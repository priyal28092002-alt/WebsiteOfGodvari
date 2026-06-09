import { Component, OnInit } from '@angular/core';
import { MatSnackBar } from '@angular/material/snack-bar';
import { OrderService } from '../../services/order.service';
import { AuthService } from '../../services/auth.service';
import { ActivatedRoute } from '@angular/router';

@Component({
  selector: 'app-order-list',
  template: `
    <div class="page-container">
      <!-- Header -->
      <div class="order-header">
        <div class="order-header-left">
          <div class="header-icon"><mat-icon>receipt_long</mat-icon></div>
          <div>
            <h2>{{ auth.isManager ? 'All Orders' : 'My Orders' }}</h2>
            <p>Track and manage your building material orders</p>
          </div>
        </div>
        <div *ngIf="auth.isManager" class="status-filter">
          <mat-icon>filter_list</mat-icon>
          <select [(ngModel)]="statusFilter" (change)="load()">
            <option value="">📋 All Orders</option>
            <option *ngFor="let s of statuses" [value]="s">{{ getStatusEmoji(s) }} {{ s }}</option>
          </select>
        </div>
        <button mat-icon-button (click)="load()" matTooltip="Refresh" class="refresh-btn">
          <mat-icon>refresh</mat-icon>
        </button>
      </div>

      <!-- Loading -->
      <div *ngIf="loading" style="text-align:center; padding:80px;">
        <mat-spinner diameter="48" style="margin:0 auto;"></mat-spinner>
        <p style="margin-top:16px; color:#64748b;">Loading orders...</p>
      </div>

      <!-- Empty -->
      <div *ngIf="!loading && orders.length === 0" class="empty-state">
        <div class="empty-icon"><mat-icon>receipt_long</mat-icon></div>
        <h3>No orders found</h3>
        <p>Start shopping to create your first order</p>
        <button mat-raised-button color="warn" routerLink="/products">
          <mat-icon>storefront</mat-icon> Shop Now
        </button>
      </div>

      <!-- Order Cards -->
      <div *ngFor="let order of orders; let i = index" class="order-card" [style.animation-delay.ms]="i*80">
        <div class="order-top">
          <div class="order-info">
            <div class="order-number">{{ order.orderNumber }}</div>
            <div class="order-date">
              <mat-icon>calendar_today</mat-icon>
              {{ order.createdAt | date:'dd MMM yyyy, hh:mm a' }}
            </div>
            <div class="order-customer">
              <mat-icon>person</mat-icon>
              {{ order.userName }} &bull; {{ order.userEmail }}
            </div>
            <div class="order-address">
              <mat-icon>location_on</mat-icon>
              {{ order.deliveryAddress }}, {{ order.deliveryCity }} - {{ order.deliveryPincode }}
            </div>
          </div>
          <div class="order-summary">
            <div class="order-badges">
              <span class="badge" [ngClass]="getStatusClass(order.status)">
                {{ getStatusEmoji(order.status) }} {{ order.status }}
              </span>
              <span class="pay-badge"
                [class.pay-paid]="order.paymentStatus === 'PAID'"
                [class.pay-pending]="order.paymentStatus !== 'PAID'">
                {{ order.paymentStatus === 'PAID' ? '💰 PAID' : '⏳ PAYMENT PENDING' }}
              </span>
            </div>
            <div class="order-amount">₹{{ order.totalAmount | number:'1.2-2' }}</div>
            <div class="order-delivery">
              <mat-icon>local_shipping</mat-icon>
              Expected: {{ order.expectedDeliveryDate | date:'dd MMM yyyy' }}
            </div>
          </div>
        </div>

        <mat-divider></mat-divider>

        <!-- Items -->
        <div class="order-items">
          <div *ngFor="let item of order.items" class="order-item-chip">
            <span class="item-name">{{ item.productName }}</span>
            <span class="item-qty">×{{ item.quantity }} {{ item.unit }}</span>
            <span class="item-price">₹{{ item.totalPrice | number:'1.2-2' }}</span>
          </div>
        </div>

        <!-- Admin Actions -->
        <div *ngIf="auth.isManager && nextStatuses(order.status).length" class="order-actions">
          <span class="action-label">Update Status:</span>
          <button *ngFor="let s of nextStatuses(order.status)"
            mat-stroked-button class="status-btn" (click)="updateStatus(order.id, s)">
            {{ getStatusEmoji(s) }} {{ s }}
          </button>
        </div>
      </div>

      <mat-paginator *ngIf="totalElements > pageSize"
        [length]="totalElements" [pageSize]="pageSize"
        (page)="onPage($event)"
        style="background:white; border-radius:12px; margin-top:20px; box-shadow:0 2px 6px rgba(0,0,0,0.04);">
      </mat-paginator>
    </div>
  `,
  styles: [`
    .order-header {
      display: flex;
      justify-content: space-between;
      align-items: center;
      margin-bottom: 28px;
      flex-wrap: wrap;
      gap: 16px;
      animation: fadeInUp 0.4s ease-out;
    }
    .order-header-left { display: flex; align-items: center; gap: 16px; }
    .header-icon {
      width: 52px; height: 52px;
      background: linear-gradient(135deg,#1a237e,#3949ab);
      border-radius: 14px; display: flex; align-items: center; justify-content: center;
      color: white; box-shadow: 0 4px 14px rgba(26,35,126,.3);
    }
    .header-icon mat-icon { font-size: 28px; width: 28px; height: 28px; }
    .order-header h2 { font-size: 24px; font-weight: 800; color: #1a237e; }
    .order-header p { font-size: 14px; color: #64748b; margin-top: 2px; }
    .status-filter {
      display: flex; align-items: center; gap: 10px;
      background: white; border: 2px solid #e2e8f0; border-radius: 10px;
      padding: 0 14px; min-width: 220px; transition: all .2s;
    }
    .status-filter:focus-within { border-color: #1a237e; box-shadow: 0 0 0 4px rgba(26,35,126,.06); }
    .status-filter mat-icon { color: #94a3b8; font-size: 20px; flex-shrink: 0; }
    .status-filter select {
      flex: 1; border: none; background: transparent; padding: 12px 0;
      font-size: 14px; font-family: 'Inter',sans-serif; color: #1a1a2e;
      outline: none; cursor: pointer;
    }
    .refresh-btn { color: #e8650a !important; }

    .empty-state {
      text-align: center; padding: 80px 20px; animation: fadeInUp 0.5s ease-out;
    }
    .empty-icon {
      width: 80px; height: 80px; border-radius: 20px;
      background: #f1f5f9; display: flex; align-items: center; justify-content: center;
      margin: 0 auto 20px;
    }
    .empty-icon mat-icon { font-size: 42px; width: 42px; height: 42px; color: #94a3b8; }
    .empty-state h3 { font-size: 20px; font-weight: 700; color: #1a237e; margin-bottom: 8px; }
    .empty-state p { color: #64748b; margin-bottom: 20px; }

    .order-card {
      background: white;
      border-radius: 16px;
      padding: 24px;
      margin-bottom: 16px;
      border: 1px solid #e2e8f0;
      box-shadow: 0 2px 8px rgba(0,0,0,0.04);
      transition: all 0.2s ease;
      animation: fadeInUp 0.5s ease-out both;
    }
    .order-card:hover { box-shadow: 0 6px 20px rgba(0,0,0,0.08); }

    .order-top {
      display: flex;
      justify-content: space-between;
      gap: 16px;
      flex-wrap: wrap;
      margin-bottom: 16px;
    }
    .order-number { font-size: 17px; font-weight: 700; color: #1a237e; margin-bottom: 8px; }
    .order-date, .order-customer, .order-address {
      display: flex; align-items: center; gap: 6px;
      font-size: 13px; color: #64748b; margin-bottom: 4px;
    }
    .order-date mat-icon, .order-customer mat-icon, .order-address mat-icon {
      font-size: 16px; width: 16px; height: 16px; color: #94a3b8;
    }

    .order-summary { text-align: right; }
    .order-badges { display:flex; flex-direction:column; align-items:flex-end; gap:6px; }
    .pay-badge { padding: 4px 12px; border-radius: 20px; font-size: 11px; font-weight: 700; }
    .pay-paid    { background:#f0fdf4; color:#16a34a; border:1px solid #bbf7d0; }
    .pay-pending { background:#fffbeb; color:#92400e; border:1px solid #fde68a; }
    .order-amount { font-size: 24px; font-weight: 800; color: #1a237e; margin: 8px 0; }
    .order-delivery {
      display: flex; align-items: center; gap: 4px;
      font-size: 12px; color: #64748b; justify-content: flex-end;
    }
    .order-delivery mat-icon { font-size: 14px; width: 14px; height: 14px; }

    .order-items { display: flex; gap: 10px; flex-wrap: wrap; padding: 16px 0; }
    .order-item-chip {
      display: flex; align-items: center; gap: 8px;
      background: #f8fafc; border: 1px solid #e2e8f0;
      border-radius: 10px; padding: 10px 14px; transition: background 0.2s;
    }
    .order-item-chip:hover { background: #f1f5f9; }
    .item-name { font-size: 13px; font-weight: 600; color: #334155; }
    .item-qty { font-size: 12px; color: #64748b; }
    .item-price { font-size: 13px; font-weight: 700; color: #1a237e; }

    .order-actions {
      display: flex; align-items: center; gap: 10px;
      padding-top: 14px; border-top: 1px solid #f1f5f9; flex-wrap: wrap;
    }
    .action-label { font-size: 13px; font-weight: 600; color: #64748b; }
    .status-btn { font-size: 12px !important; border-radius: 8px !important; }

    @keyframes fadeInUp {
      from { opacity: 0; transform: translateY(20px); }
      to { opacity: 1; transform: translateY(0); }
    }

    @media (max-width: 768px) {
      .order-top { flex-direction: column; }
      .order-summary { text-align: left; }
    }
  `]
})
export class OrderListComponent implements OnInit {
  orders: any[] = [];
  loading = false;
  statusFilter = '';
  totalElements = 0;
  pageSize = 10;
  currentPage = 0;
  statuses = ['PENDING','CONFIRMED','PROCESSING','SHIPPED','DELIVERED','CANCELLED'];

  constructor(public auth: AuthService, private orderService: OrderService,
              private snack: MatSnackBar, private route: ActivatedRoute) {}

  ngOnInit(): void {
    this.route.queryParams.subscribe(() => this.load());
    this.load();
  }

  load(): void {
    this.loading = true;
    const obs = this.auth.isManager
      ? this.orderService.getAll(this.currentPage, this.pageSize)
      : this.orderService.getByUser(this.auth.currentUser!.userId, this.currentPage, this.pageSize);
    obs.subscribe({
      next: d => { this.orders = [...d.content]; this.totalElements = d.totalElements; this.loading = false; },
      error: () => this.loading = false
    });
  }

  onPage(e: any): void { this.currentPage = e.pageIndex; this.pageSize = e.pageSize; this.load(); }

  updateStatus(id: number, status: string): void {
    this.orderService.updateStatus(id, status).subscribe({
      next: () => { this.snack.open('Status updated', 'Close', { duration: 2000, panelClass: 'snack-success' }); this.load(); }
    });
  }

  nextStatuses(current: string): string[] {
    const flow: Record<string, string[]> = {
      PENDING: ['CONFIRMED','CANCELLED'], CONFIRMED: ['PROCESSING','CANCELLED'],
      PROCESSING: ['SHIPPED'], SHIPPED: ['DELIVERED']
    };
    return flow[current] || [];
  }

  getStatusClass(status: string): string {
    const map: Record<string, string> = {
      PENDING:'badge-warn', CONFIRMED:'badge-info', PROCESSING:'badge-info',
      SHIPPED:'badge-warn', DELIVERED:'badge-success', CANCELLED:'badge-danger'
    };
    return map[status] || 'badge-default';
  }

  getStatusEmoji(status: string): string {
    const map: Record<string, string> = {
      PENDING:'⏳', CONFIRMED:'✅', PROCESSING:'⚙️',
      SHIPPED:'🚚', DELIVERED:'📦', CANCELLED:'❌'
    };
    return map[status] || '📋';
  }
}
