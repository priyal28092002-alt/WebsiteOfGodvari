import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { AuthService } from '../../services/auth.service';
import { CartService } from '../../services/cart.service';

@Component({
  selector: 'app-navbar',
  template: `
    <!-- ADMIN NAVBAR -->
    <ng-container *ngIf="auth.isManager">
      <header class="admin-nav" [class.scrolled]="scrolled">
        <div class="nav-inner">
          <div class="logo" (click)="router.navigate(['/admin/dashboard'])">
            <div class="logo-box admin">🏗️</div>
            <div>
              <span class="logo-brand">Godavari</span>
              <span class="logo-sub admin-sub">ADMIN PANEL</span>
            </div>
          </div>
          <nav class="links admin-links">
            <a routerLink="/admin/dashboard"  routerLinkActive="act"><mat-icon>dashboard</mat-icon> Dashboard</a>
            <a routerLink="/admin/products"   routerLinkActive="act"><mat-icon>add_box</mat-icon> Products</a>
            <a routerLink="/admin/inventory"  routerLinkActive="act"><mat-icon>inventory_2</mat-icon> Inventory</a>
            <a routerLink="/orders"           routerLinkActive="act"><mat-icon>receipt_long</mat-icon> Orders</a>
            <a routerLink="/quotations"       routerLinkActive="act"><mat-icon>request_quote</mat-icon> RFQ</a>
            <a routerLink="/invoices"         routerLinkActive="act"><mat-icon>description</mat-icon> Invoices</a>
            <a routerLink="/admin/users"      routerLinkActive="act"><mat-icon>people</mat-icon> Users</a>
            <a *ngIf="auth.isAdmin" routerLink="/admin/register" routerLinkActive="act"><mat-icon>person_add</mat-icon> Register</a>
            <a routerLink="/admin/logs"       routerLinkActive="act"><mat-icon>terminal</mat-icon> Logs</a>
          </nav>
          <div class="nav-right">
            <div class="role-chip">{{ auth.role }}</div>
            <button class="user-pill" [matMenuTriggerFor]="aMenu">
              <div class="avatar admin-av">{{ auth.currentUser?.name?.charAt(0) }}</div>
              <span>{{ auth.currentUser?.name?.split(' ')?.[0] }}</span>
              <mat-icon style="font-size:16px;">expand_more</mat-icon>
            </button>
            <mat-menu #aMenu="matMenu">
              <div class="menu-head">
                <div class="menu-av admin-av">{{ auth.currentUser?.name?.charAt(0) }}</div>
                <div>
                  <strong>{{ auth.currentUser?.name }}</strong>
                  <p>{{ auth.currentUser?.role }}</p>
                </div>
              </div>
              <mat-divider></mat-divider>
              <button mat-menu-item routerLink="/products"><mat-icon>storefront</mat-icon> View Store</button>
              <button mat-menu-item (click)="auth.logout()"><mat-icon style="color:#f87171">logout</mat-icon> Sign Out</button>
            </mat-menu>
            <button class="ham" (click)="mob=!mob"><mat-icon>{{ mob?'close':'menu' }}</mat-icon></button>
          </div>
        </div>
      </header>
      <div class="mob-bg" *ngIf="mob" (click)="mob=false"></div>
      <nav class="mob-nav admin-mob" [class.open]="mob">
        <div class="mob-head"><span>🏗️ Admin</span><span class="role-chip">{{ auth.role }}</span></div>
        <a routerLink="/admin/dashboard"  (click)="mob=false"><mat-icon>dashboard</mat-icon> Dashboard</a>
        <a routerLink="/admin/products"   (click)="mob=false"><mat-icon>add_box</mat-icon> Products</a>
        <a routerLink="/admin/inventory"  (click)="mob=false"><mat-icon>inventory_2</mat-icon> Inventory</a>
        <a routerLink="/orders"           (click)="mob=false"><mat-icon>receipt_long</mat-icon> Orders</a>
        <a routerLink="/quotations"       (click)="mob=false"><mat-icon>request_quote</mat-icon> RFQ</a>
        <a routerLink="/invoices"         (click)="mob=false"><mat-icon>description</mat-icon> Invoices</a>
        <a routerLink="/admin/users"      (click)="mob=false"><mat-icon>people</mat-icon> Users</a>
        <a routerLink="/admin/logs"       (click)="mob=false"><mat-icon>terminal</mat-icon> Logs</a>
        <div class="mob-foot">
          <button (click)="auth.logout();mob=false"><mat-icon>logout</mat-icon> Sign Out</button>
        </div>
      </nav>
    </ng-container>

    <!-- CUSTOMER NAVBAR -->
    <ng-container *ngIf="!auth.isManager">
      <div class="top-strip">
        <span>📍 Nashik, Maharashtra</span>
        <span>📞 +91-XXXXX-XXXXX</span>
        <span>✉️ info&#64;godavari.com</span>
      </div>
      <header class="cust-nav" [class.scrolled]="scrolled">
        <div class="nav-inner">
          <div class="logo" (click)="router.navigate(['/products'])">
            <div class="logo-box cust">🏗️</div>
            <div>
              <span class="logo-brand">Godavari</span>
              <span class="logo-sub">Building Materials</span>
            </div>
          </div>
          <nav class="links cust-links">
            <a routerLink="/products"   routerLinkActive="act"><mat-icon>storefront</mat-icon> Products</a>
            <a routerLink="/orders"     routerLinkActive="act"><mat-icon>receipt_long</mat-icon> Orders</a>
            <a routerLink="/quotations" routerLinkActive="act"><mat-icon>request_quote</mat-icon> Quotations</a>
            <a routerLink="/invoices"   routerLinkActive="act"><mat-icon>description</mat-icon> Invoices</a>
          </nav>
          <div class="nav-right">
            <button class="cart-btn" routerLink="/cart">
              <mat-icon>shopping_cart</mat-icon>
              <span *ngIf="cartCount>0" class="cart-dot">{{ cartCount }}</span>
            </button>
            <button class="user-pill" [matMenuTriggerFor]="uMenu">
              <div class="avatar cust-av">{{ auth.currentUser?.name?.charAt(0) }}</div>
              <span>{{ auth.currentUser?.name?.split(' ')?.[0] }}</span>
              <mat-icon style="font-size:16px;">expand_more</mat-icon>
            </button>
            <mat-menu #uMenu="matMenu">
              <div class="menu-head">
                <div class="menu-av cust-av">{{ auth.currentUser?.name?.charAt(0) }}</div>
                <div>
                  <strong>{{ auth.currentUser?.name }}</strong>
                  <p>{{ auth.currentUser?.role }}</p>
                </div>
              </div>
              <mat-divider></mat-divider>
              <button mat-menu-item (click)="auth.logout()"><mat-icon style="color:#f87171">logout</mat-icon> Sign Out</button>
            </mat-menu>
            <button class="ham" (click)="mob=!mob"><mat-icon>{{ mob?'close':'menu' }}</mat-icon></button>
          </div>
        </div>
      </header>
      <div class="mob-bg" *ngIf="mob" (click)="mob=false"></div>
      <nav class="mob-nav cust-mob" [class.open]="mob">
        <div class="mob-head"><span>🏗️ Godavari</span></div>
        <a routerLink="/products"   (click)="mob=false"><mat-icon>storefront</mat-icon> Products</a>
        <a routerLink="/orders"     (click)="mob=false"><mat-icon>receipt_long</mat-icon> Orders</a>
        <a routerLink="/quotations" (click)="mob=false"><mat-icon>request_quote</mat-icon> Quotations</a>
        <a routerLink="/invoices"   (click)="mob=false"><mat-icon>description</mat-icon> Invoices</a>
        <div class="mob-foot">
          <button (click)="auth.logout();mob=false"><mat-icon>logout</mat-icon> Sign Out</button>
        </div>
      </nav>
    </ng-container>
  `,
  styles: [`
    /* ── SHARED ── */
    .nav-inner {
      max-width:1600px; margin:0 auto;
      display:flex; align-items:center;
      padding:10px 24px; gap:20px;
    }
    .logo { display:flex; align-items:center; gap:10px; cursor:pointer; flex-shrink:0; }
    .logo-box {
      width:38px; height:38px; border-radius:9px;
      display:flex; align-items:center; justify-content:center; font-size:20px;
    }
    .logo-box.admin { background:linear-gradient(135deg,#e8650a,#f97316); box-shadow:0 3px 12px rgba(232,101,10,0.35); }
    .logo-box.cust  { background:linear-gradient(135deg,#7c3a10,#a84e1a); border:none; }
    .logo-brand { display:block; font-size:17px; font-weight:800; color:white; line-height:1.2; }
    .logo-sub   { display:block; font-size:10px; color:rgba(255,255,255,0.4); line-height:1.2; }
    .admin-sub  { color:#fcd34d; font-weight:700; letter-spacing:1px; text-transform:uppercase; font-size:9px; }

    .links { display:flex; align-items:center; gap:2px; flex:1; flex-wrap:wrap; }
    .links a {
      display:flex; align-items:center; gap:5px;
      padding:7px 12px; border-radius:8px;
      text-decoration:none; font-size:13px; font-weight:500;
      color:rgba(255,255,255,0.5); cursor:pointer; transition:all .2s;
      white-space:nowrap;
    }
    .links a mat-icon { font-size:17px; width:17px; height:17px; }
    .links a:hover { color:rgba(255,255,255,0.9); background:rgba(255,255,255,0.07); }
    .links a.act  { color:white; background:rgba(232,101,10,0.18); border:1px solid rgba(232,101,10,0.35); }
    .admin-links a.act { background:rgba(139,92,246,0.18); border-color:rgba(139,92,246,0.35); }

    .nav-right { display:flex; align-items:center; gap:10px; flex-shrink:0; }

    .role-chip {
      background:rgba(232,101,10,0.12); border:1px solid rgba(232,101,10,0.3);
      color:#e8650a; padding:3px 10px; border-radius:12px;
      font-size:10px; font-weight:700; text-transform:uppercase; letter-spacing:0.5px;
    }

    .cart-btn {
      position:relative; background:rgba(255,255,255,0.06);
      border:1px solid rgba(255,255,255,0.1); border-radius:9px;
      width:38px; height:38px; display:flex; align-items:center; justify-content:center;
      cursor:pointer; transition:all .2s; color:rgba(255,255,255,0.7);
    }
    .cart-btn:hover { background:rgba(255,255,255,0.12); border-color:rgba(255,255,255,0.2); }
    .cart-dot {
      position:absolute; top:-4px; right:-4px;
      background:#ef4444; color:white;
      width:18px; height:18px; border-radius:50%;
      font-size:10px; font-weight:700;
      display:flex; align-items:center; justify-content:center;
      animation:scaleIn .3s ease;
    }

    .user-pill {
      display:flex; align-items:center; gap:7px;
      background:rgba(255,255,255,0.06); border:1px solid rgba(255,255,255,0.1);
      border-radius:20px; padding:5px 12px 5px 5px;
      cursor:pointer; transition:all .2s; color:rgba(255,255,255,0.8);
    }
    .user-pill:hover { background:rgba(255,255,255,0.1); border-color:rgba(255,255,255,0.2); }
    .user-pill span { font-size:13px; font-weight:500; }
    .avatar {
      width:28px; height:28px; border-radius:50%;
      display:flex; align-items:center; justify-content:center;
      font-weight:700; font-size:13px; color:white; flex-shrink:0;
    }
    .admin-av { background:linear-gradient(135deg,#e8650a,#f97316); }
    .cust-av  { background:linear-gradient(135deg,#10b981,#059669); }

    .menu-head {
      display:flex; align-items:center; gap:10px;
      padding:14px 16px; border-bottom:1px solid rgba(255,255,255,0.08);
    }
    .menu-head strong { display:block; font-size:13px; color:white; }
    .menu-head p { font-size:11px; color:rgba(255,255,255,0.4); margin-top:2px; }
    .menu-av { width:36px; height:36px; border-radius:50%; font-size:15px; flex-shrink:0; }

    .ham {
      display:none; background:rgba(255,255,255,0.06); border:1px solid rgba(255,255,255,0.1);
      border-radius:8px; width:36px; height:36px;
      cursor:pointer; display:none; align-items:center; justify-content:center;
      color:rgba(255,255,255,0.7); transition:all .2s;
    }
    .ham:hover { background:rgba(255,255,255,0.12); }

    /* ADMIN HEADER */
    .admin-nav {
      background:#080d1a;
      border-bottom:1px solid rgba(255,255,255,0.07);
      position:sticky; top:0; z-index:1000; transition:all .3s;
    }
    .admin-nav.scrolled { box-shadow:0 4px 24px rgba(0,0,0,0.2); border-color:rgba(232,101,10,0.2); }

    /* CUSTOMER TOP STRIP */
    .top-strip {
      background:#1e293b;
      border-bottom:1px solid #334155;
      display:flex; justify-content:flex-end; align-items:center;
      gap:20px; padding:5px 24px;
      font-size:11px; color:rgba(255,255,255,.55);
    }

    /* CUSTOMER HEADER */
    .cust-nav {
      background:white;
      border-bottom:1px solid #e2e8f0;
      position:sticky; top:0; z-index:1000; transition:all .3s;
      box-shadow:0 1px 4px rgba(0,0,0,.06);
    }
    .cust-nav.scrolled { box-shadow:0 4px 16px rgba(0,0,0,.1); }
    /* Override link colors for light customer nav */
    .cust-nav .links a { color:#475569; }
    .cust-nav .links a:hover { color:#c4520a; background:#fff4ec; }
    .cust-nav .links a.act { color:#c4520a; background:#fff4ec; border:1px solid #fbd5b5; }
    .cust-nav .logo-brand { color:#1e293b; }
    .cust-nav .logo-sub { color:#94a3b8; }
    .cust-nav .logo-box.cust { background:linear-gradient(135deg,#e8650a,#f97316); border:none; }
    .cust-nav .user-pill { background:#f1f5f9; border-color:#e2e8f0; color:#334155; }
    .cust-nav .user-pill span { color:#334155; }
    .cust-nav .user-pill:hover { background:#e2e8f0; }
    .cust-nav .cart-btn { background:#f1f5f9; border-color:#e2e8f0; color:#475569; }
    .cust-nav .cart-btn:hover { background:#e2e8f0; border-color:#94a3b8; }
    .cust-nav .ham { background:#f1f5f9; border-color:#e2e8f0; color:#475569; }

    /* MOBILE (light for customer) */
    .cust-mob {
      background:white;
      border-right:1px solid #e2e8f0;
      box-shadow:4px 0 24px rgba(0,0,0,.1);
    }
    .cust-mob .mob-head { color:#1e293b; border-bottom-color:#e2e8f0; }
    .cust-mob a { color:#475569; }
    .cust-mob a:hover { background:#fff4ec; color:#c4520a; }
    .cust-mob .mob-foot { border-top-color:#e2e8f0; }
    .cust-mob .mob-foot button { background:#fef2f2; border-color:#fecaca; color:#dc2626; }

    /* MOBILE */
    .mob-bg {
      position:fixed; inset:0;
      background:rgba(0,0,0,0.7); backdrop-filter:blur(4px);
      z-index:1100;
    }
    .mob-nav {
      position:fixed; top:0; left:-300px; width:280px; height:100vh;
      background:#0d1428; border-right:1px solid rgba(255,255,255,0.08);
      z-index:1200; padding:20px 14px;
      transition:left .3s cubic-bezier(.4,0,.2,1);
      display:flex; flex-direction:column; overflow-y:auto;
      box-shadow:4px 0 32px rgba(0,0,0,0.6);
    }
    .mob-nav.open { left:0; }
    .mob-head {
      display:flex; justify-content:space-between; align-items:center;
      font-size:16px; font-weight:800; color:white;
      margin-bottom:20px; padding-bottom:14px;
      border-bottom:1px solid rgba(255,255,255,0.08);
    }
    .mob-nav a {
      display:flex; align-items:center; gap:10px;
      padding:12px 12px; border-radius:9px;
      text-decoration:none; color:rgba(255,255,255,0.6);
      font-size:14px; font-weight:500; transition:all .2s; cursor:pointer;
      margin-bottom:3px;
    }
    .mob-nav a:hover { background:rgba(255,255,255,0.07); color:white; }
    .mob-foot {
      margin-top:auto; padding-top:14px;
      border-top:1px solid rgba(255,255,255,0.08);
    }
    .mob-foot button {
      display:flex; align-items:center; gap:10px;
      width:100%; padding:12px; border-radius:9px;
      background:rgba(239,68,68,0.1); border:1px solid rgba(239,68,68,0.2);
      color:#f87171; font-size:14px; font-weight:500; cursor:pointer;
      font-family:'Inter',sans-serif; transition:all .2s;
    }
    .mob-foot button:hover { background:rgba(239,68,68,0.18); }

    @keyframes scaleIn { from{transform:scale(0);} to{transform:scale(1);} }

    @media(max-width:1100px) {
      .links { display:none; }
      .ham   { display:flex !important; }
      .role-chip { display:none; }
      .user-pill span { display:none; }
      .top-strip { display:none; }
    }
    @media(max-width:600px) {
      .nav-inner { padding:8px 12px; }
      .logo-brand { font-size:15px; }
    }
  `]
})
export class NavbarComponent {
  cartCount = 0;
  mob = false;
  scrolled = false;

  constructor(public auth: AuthService, public router: Router, private cart: CartService) {
    this.cart.cart$.subscribe(items => this.cartCount = items.reduce((s, i) => s + i.quantity, 0));
    if (typeof window !== 'undefined') {
      window.addEventListener('scroll', () => this.scrolled = window.scrollY > 20);
    }
  }
}
