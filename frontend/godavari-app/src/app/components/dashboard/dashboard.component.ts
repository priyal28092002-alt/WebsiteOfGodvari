import { Component, OnInit } from '@angular/core';
import { OrderService } from '../../services/order.service';
import { InventoryService } from '../../services/inventory.service';
import { AuthService } from '../../services/auth.service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-dashboard',
  template: `
    <div class="page-container">

      <!-- Welcome Banner -->
      <div class="welcome-banner">
        <div class="wb-left">
          <div class="wb-badge">
            <mat-icon>admin_panel_settings</mat-icon> Admin Dashboard
          </div>
          <h1>Welcome back, <span class="wb-name">{{ auth.currentUser?.name?.split(' ')?.[0] }}</span> 👋</h1>
          <p>Here's what's happening with Godavari Building Materials today</p>
        </div>
        <div class="wb-right">
          <div class="wb-stat">
            <strong>{{ stats.totalOrders | number }}</strong>
            <span>Total Orders</span>
          </div>
          <div class="wb-stat">
            <strong>₹{{ stats.revenueThisMonth | number:'1.0-0' }}</strong>
            <span>This Month</span>
          </div>
        </div>
      </div>

      <!-- Stats Cards -->
      <div class="stats-grid">
        <div class="stat-card st-orange">
          <div class="st-icon"><mat-icon>receipt_long</mat-icon></div>
          <div class="st-body">
            <p>Orders This Month</p>
            <h3>{{ stats.totalOrdersThisMonth | number }}</h3>
            <span>Monthly total</span>
          </div>
        </div>
        <div class="stat-card st-green">
          <div class="st-icon"><mat-icon>account_balance_wallet</mat-icon></div>
          <div class="st-body">
            <p>Revenue This Month</p>
            <h3>₹{{ stats.revenueThisMonth | number:'1.0-0' }}</h3>
            <span>Excluding GST</span>
          </div>
        </div>
        <div class="stat-card st-amber">
          <div class="st-icon"><mat-icon>hourglass_top</mat-icon></div>
          <div class="st-body">
            <p>Pending Orders</p>
            <h3>{{ stats.pendingOrders }}</h3>
            <span>Awaiting confirmation</span>
          </div>
        </div>
        <div class="stat-card st-yellow">
          <div class="st-icon"><mat-icon>shopping_bag</mat-icon></div>
          <div class="st-body">
            <p>Total Orders</p>
            <h3>{{ stats.totalOrders | number }}</h3>
            <span>All time</span>
          </div>
        </div>
      </div>

      <!-- Low Stock Alerts -->
      <div class="section-card">
        <div class="section-header">
          <div class="section-title-wrap">
            <div class="section-icon warn"><mat-icon>warning_amber</mat-icon></div>
            <div>
              <h3>Low Stock Alerts</h3>
              <p>Products that need immediate restocking</p>
            </div>
          </div>
          <div class="section-actions">
            <span class="count-chip warn" *ngIf="lowStock.length > 0">
              {{ lowStock.length }} items
            </span>
            <a class="section-link" routerLink="/admin/inventory">
              View Inventory <mat-icon>arrow_forward</mat-icon>
            </a>
          </div>
        </div>

        <!-- All good -->
        <div *ngIf="lowStock.length === 0" class="all-good">
          <div class="all-good-icon"><mat-icon>check_circle</mat-icon></div>
          <div>
            <strong>All items are well stocked!</strong>
            <p>No low stock alerts at this time</p>
          </div>
        </div>

        <!-- Low stock table -->
        <div class="ls-table" *ngIf="lowStock.length > 0">
          <div class="ls-thead">
            <span>Product</span>
            <span>Available</span>
            <span>Threshold</span>
            <span>Location</span>
            <span>Status</span>
            <span>Action</span>
          </div>
          <div *ngFor="let s of lowStock; let i=index" class="ls-row"
            [class.ls-critical]="s.quantityAvailable === 0"
            [style.animation-delay.ms]="i*40">
            <div class="ls-product">
              <div class="ls-dot" [style.background]="s.quantityAvailable === 0 ? '#dc2626' : '#d97706'"></div>
              <div>
                <strong>{{ s.productName }}</strong>
                <span *ngIf="s.sku">SKU: {{ s.sku }}</span>
              </div>
            </div>
            <div class="ls-qty" [class.ls-zero]="s.quantityAvailable === 0">
              {{ s.quantityAvailable }} {{ s.unit || '' }}
            </div>
            <div class="ls-thresh">{{ s.lowStockThreshold }} {{ s.unit || '' }}</div>
            <div class="ls-loc">{{ s.warehouseLocation || 'Main Warehouse' }}</div>
            <div>
              <span class="ls-badge" [class.ls-out]="s.quantityAvailable === 0"
                [class.ls-low]="s.quantityAvailable > 0">
                {{ s.quantityAvailable === 0 ? '❌ Out of Stock' : '⚠️ Low Stock' }}
              </span>
            </div>
            <div>
              <button class="ls-restock-btn" routerLink="/admin/inventory">
                <mat-icon>add</mat-icon> Restock
              </button>
            </div>
          </div>
        </div>
      </div>

      <!-- Quick Actions -->
      <div class="section-card">
        <div class="section-header">
          <div class="section-title-wrap">
            <div class="section-icon orange"><mat-icon>bolt</mat-icon></div>
            <div>
              <h3>Quick Actions</h3>
              <p>Navigate to key sections</p>
            </div>
          </div>
        </div>
        <div class="actions-grid">
          <a *ngFor="let a of actions" class="action-card" [routerLink]="a.link">
            <div class="ac-icon" [style.background]="a.bg">{{ a.icon }}</div>
            <strong>{{ a.label }}</strong>
            <span>{{ a.desc }}</span>
          </a>
        </div>
      </div>

    </div>
  `,
  styles: [`
    /* Welcome Banner */
    .welcome-banner {
      background: linear-gradient(135deg, #7c2d12 0%, #c2410c 50%, #ea580c 100%);
      border-radius: 16px; padding: 28px 32px;
      margin-bottom: 24px;
      display: flex; justify-content: space-between; align-items: center;
      flex-wrap: wrap; gap: 20px;
      animation: fuUp .5s ease-out;
      box-shadow: 0 6px 24px rgba(194,65,12,.25);
    }
    .wb-badge {
      display: inline-flex; align-items: center; gap: 6px;
      background: rgba(255,255,255,.15); border: 1px solid rgba(255,255,255,.25);
      color: #fed7aa; padding: 5px 14px; border-radius: 20px;
      font-size: 11px; font-weight: 700; text-transform: uppercase;
      letter-spacing: 1px; margin-bottom: 10px;
    }
    .wb-badge mat-icon { font-size:14px; width:14px; height:14px; }
    .wb-left h1 { font-size: clamp(20px,3vw,26px); font-weight: 800; color: white; margin-bottom: 6px; }
    .wb-left p  { color: rgba(255,255,255,.7); font-size: 14px; }
    .wb-name    { color: #fed7aa; }
    .wb-right   { display: flex; gap: 24px; }
    .wb-stat    { text-align: center; }
    .wb-stat strong { display:block; font-size:24px; font-weight:800; color:white; }
    .wb-stat span   { font-size:12px; color:rgba(255,255,255,.6); }

    /* Stat Cards */
    .stats-grid {
      display: grid; grid-template-columns: repeat(auto-fill,minmax(240px,1fr));
      gap: 16px; margin-bottom: 24px;
    }
    .stat-card {
      background: white; border-radius: 14px; padding: 20px 22px;
      display: flex; align-items: center; gap: 16px;
      border: 1px solid #e8e0d8; box-shadow: 0 2px 8px rgba(100,60,10,.07);
      transition: all .2s; animation: fuUp .4s ease-out both;
      border-left: 5px solid;
    }
    .stat-card:hover { transform: translateY(-3px); box-shadow: 0 8px 24px rgba(100,60,10,.12); }
    .st-icon {
      width: 48px; height: 48px; border-radius: 12px;
      display: flex; align-items: center; justify-content: center;
      flex-shrink: 0;
    }
    .st-body p    { font-size:13px; color:#5c4a35; font-weight:600; margin-bottom:4px; }
    .st-body h3   { font-size:26px; font-weight:800; color:#2d1f0e; line-height:1; }
    .st-body span { font-size:11px; color:#9c8472; }
    .st-orange { border-left-color:#e8650a; }
    .st-orange .st-icon { background:#fff4ec; color:#e8650a; }
    .st-green  { border-left-color:#16a34a; }
    .st-green  .st-icon { background:#f0fdf4; color:#16a34a; }
    .st-amber  { border-left-color:#d97706; }
    .st-amber  .st-icon { background:#fffbeb; color:#d97706; }
    .st-yellow { border-left-color:#ca8a04; }
    .st-yellow .st-icon { background:#fefce8; color:#ca8a04; }

    /* Section Card */
    .section-card {
      background: white; border-radius: 14px; padding: 24px;
      margin-bottom: 20px; border: 1px solid #e8e0d8;
      box-shadow: 0 2px 8px rgba(100,60,10,.06);
      animation: fuUp .4s ease-out both;
    }
    .section-header {
      display: flex; justify-content: space-between; align-items: flex-start;
      margin-bottom: 20px; padding-bottom: 16px;
      border-bottom: 2px solid #f5f0ea; flex-wrap: wrap; gap: 12px;
    }
    .section-title-wrap { display:flex; align-items:center; gap:12px; }
    .section-icon {
      width: 42px; height: 42px; border-radius: 10px;
      display: flex; align-items: center; justify-content: center; flex-shrink: 0;
    }
    .section-icon.warn   { background:#fff4e6; color:#c2410c; }
    .section-icon.orange { background:#fff4ec; color:#e8650a; }
    .section-header h3   { font-size:16px; font-weight:700; color:#2d1f0e; margin-bottom:2px; }
    .section-header p    { font-size:13px; color:#9c8472; }
    .section-actions     { display:flex; align-items:center; gap:10px; flex-wrap:wrap; }
    .count-chip {
      padding: 4px 12px; border-radius: 20px;
      font-size: 12px; font-weight: 700;
    }
    .count-chip.warn { background:#fff4e6; color:#c2410c; border:1px solid #fbd5b5; }
    .section-link {
      display: flex; align-items: center; gap: 4px;
      padding: 8px 16px; border-radius: 8px;
      background: #fff4ec; color: #c2410c; border: 1px solid #fbd5b5;
      font-size: 13px; font-weight: 600; text-decoration: none;
      transition: all .2s;
    }
    .section-link:hover { background: #fbd5b5; }
    .section-link mat-icon { font-size:16px; width:16px; height:16px; }

    /* All Good */
    .all-good {
      display: flex; align-items: center; gap: 14px;
      background: #f0fdf4; border: 1px solid #bbf7d0;
      border-radius: 10px; padding: 16px 20px;
    }
    .all-good-icon { color:#16a34a; font-size:32px; display:flex; }
    .all-good-icon mat-icon { font-size:32px; width:32px; height:32px; color:#16a34a; }
    .all-good strong { display:block; font-size:14px; font-weight:700; color:#15803d; }
    .all-good p { font-size:12px; color:#4ade80; margin:2px 0 0; }

    /* Low Stock Table */
    .ls-table { overflow-x: auto; }
    .ls-thead {
      display: grid; grid-template-columns: 2fr 1fr 1fr 1.2fr 1.2fr 1fr;
      padding: 10px 14px; background: #fdf8f4;
      border-radius: 8px; margin-bottom: 4px;
      font-size: 11px; font-weight: 700; color: #9c8472;
      text-transform: uppercase; letter-spacing: .5px;
    }
    .ls-row {
      display: grid; grid-template-columns: 2fr 1fr 1fr 1.2fr 1.2fr 1fr;
      padding: 12px 14px; border-radius: 8px; margin-bottom: 4px;
      background: #fdf9f6; border: 1px solid #f0e8e0;
      align-items: center; transition: all .2s;
      animation: fuUp .3s ease-out both;
    }
    .ls-row:hover { background: #fff4ec; border-color: #fbd5b5; }
    .ls-critical { background: #fef2f2 !important; border-color: #fecaca !important; }
    .ls-critical:hover { background: #fee2e2 !important; }

    .ls-product { display:flex; align-items:center; gap:10px; }
    .ls-dot { width:10px; height:10px; border-radius:50%; flex-shrink:0; }
    .ls-product strong { display:block; font-size:13px; font-weight:700; color:#2d1f0e; }
    .ls-product span   { font-size:11px; color:#9c8472; }
    .ls-qty   { font-size:16px; font-weight:800; color:#d97706; }
    .ls-zero  { color:#dc2626 !important; }
    .ls-thresh { font-size:13px; color:#5c4a35; }
    .ls-loc   { font-size:12px; color:#9c8472; }

    .ls-badge {
      display: inline-flex; align-items: center; gap: 4px;
      padding: 4px 10px; border-radius: 20px;
      font-size: 11px; font-weight: 700; white-space: nowrap;
    }
    .ls-out { background:#fef2f2; color:#dc2626; border:1px solid #fecaca; }
    .ls-low { background:#fff4e6; color:#c2410c; border:1px solid #fbd5b5; }

    .ls-restock-btn {
      display: inline-flex; align-items: center; gap: 4px;
      padding: 6px 12px; border-radius: 7px;
      background: linear-gradient(135deg,#e8650a,#f97316); color: white;
      border: none; font-size: 12px; font-weight: 600; cursor: pointer;
      font-family: 'Inter',sans-serif; transition: all .2s;
      box-shadow: 0 2px 8px rgba(232,101,10,.25);
    }
    .ls-restock-btn:hover { transform:translateY(-1px); box-shadow:0 4px 12px rgba(232,101,10,.4); }
    .ls-restock-btn mat-icon { font-size:15px; width:15px; height:15px; }

    /* Quick Actions */
    .actions-grid {
      display: grid;
      grid-template-columns: repeat(auto-fill, minmax(160px, 1fr));
      gap: 12px;
    }
    .action-card {
      display: flex; flex-direction: column; align-items: center;
      gap: 10px; padding: 20px 14px; border-radius: 12px;
      background: #fdf8f4; border: 1px solid #f0e8e0;
      cursor: pointer; text-decoration: none;
      transition: all .2s; text-align: center;
    }
    .action-card:hover {
      background: #fff4ec; border-color: #fbd5b5;
      transform: translateY(-3px);
      box-shadow: 0 8px 20px rgba(194,65,12,.12);
    }
    .ac-icon {
      width: 48px; height: 48px; border-radius: 12px;
      display: flex; align-items: center; justify-content: center;
      font-size: 24px;
    }
    .action-card strong { font-size:13px; font-weight:700; color:#2d1f0e; }
    .action-card span   { font-size:11px; color:#9c8472; }

    @keyframes fuUp { from{opacity:0;transform:translateY(16px);} to{opacity:1;transform:translateY(0);} }

    @media(max-width:768px) {
      .welcome-banner { flex-direction:column; }
      .wb-right { width:100%; justify-content:flex-start; }
      .ls-thead,.ls-row { grid-template-columns:2fr 1fr 1fr; }
      .ls-thresh,.ls-loc,.ls-badge,.ls-restock-btn { display:none; }
    }
  `]
})
export class DashboardComponent implements OnInit {
  stats: any = { totalOrdersThisMonth:0, revenueThisMonth:0, pendingOrders:0, totalOrders:0 };
  lowStock: any[] = [];

  actions = [
    { icon:'🧾', label:'Orders',     desc:'Manage all orders',   link:'/orders',           bg:'#fff4ec' },
    { icon:'📋', label:'RFQ',        desc:'Approve quotations',  link:'/quotations',        bg:'#fefce8' },
    { icon:'📄', label:'Invoices',   desc:'View & mark paid',    link:'/invoices',          bg:'#fff4ec' },
    { icon:'📦', label:'Products',   desc:'Add & edit catalog',  link:'/admin/products',    bg:'#fdf8f4' },
    { icon:'🏭', label:'Inventory',  desc:'Stock management',    link:'/admin/inventory',   bg:'#f0fdf4' },
    { icon:'👥', label:'Users',      desc:'Manage staff',        link:'/admin/users',       bg:'#fffbeb' }
  ];

  constructor(
    private orderService: OrderService,
    private inventoryService: InventoryService,
    public auth: AuthService,
    private router: Router
  ) {
    if (!auth.isManager) router.navigate(['/products']);
  }

  ngOnInit(): void {
    this.orderService.getDashboardStats().subscribe(s => this.stats = s);
    this.inventoryService.getLowStock().subscribe(d => this.lowStock = d || []);
  }
}
