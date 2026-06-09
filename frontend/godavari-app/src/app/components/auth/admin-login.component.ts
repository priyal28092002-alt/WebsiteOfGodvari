import { Component } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { AuthService } from '../../services/auth.service';

@Component({
  selector: 'app-admin-login',
  template: `
    <div class="admin-login-page">
      <!-- Animated background -->
      <div class="bg-grid"></div>
      <div class="bg-glow g1"></div>
      <div class="bg-glow g2"></div>
      <div class="bg-glow g3"></div>

      <!-- Floating particles -->
      <div class="particles">
        <span *ngFor="let p of particles" class="particle"
          [style.left.%]="p.x" [style.top.%]="p.y"
          [style.width.px]="p.size" [style.height.px]="p.size"
          [style.animation-delay.s]="p.delay" [style.animation-duration.s]="p.dur">
        </span>
      </div>

      <div class="login-container">
        <!-- Left panel -->
        <div class="left-panel">
          <div class="brand">
            <div class="brand-icon">🏗️</div>
            <h1>Godavari</h1>
            <p>Building Materials</p>
          </div>
          <div class="features">
            <div class="feature" *ngFor="let f of features; let i = index" [style.animation-delay.ms]="i*150">
              <div class="feat-icon">{{ f.icon }}</div>
              <div>
                <strong>{{ f.title }}</strong>
                <p>{{ f.desc }}</p>
              </div>
            </div>
          </div>
          <div class="stats-row">
            <div class="stat" *ngFor="let s of stats">
              <strong>{{ s.val }}</strong>
              <span>{{ s.label }}</span>
            </div>
          </div>
        </div>

        <!-- Right panel - Login form -->
        <div class="right-panel">
          <div class="form-card">
            <div class="form-header">
              <div class="admin-badge">
                <mat-icon>admin_panel_settings</mat-icon>
                Admin Portal
              </div>
              <h2>Welcome Back</h2>
              <p>Sign in to manage your store</p>
            </div>

            <form [formGroup]="form" (ngSubmit)="onSubmit()">
              <div class="field-group">
                <label>Email Address</label>
                <div class="input-wrap" [class.focused]="emailFocused" [class.has-value]="form.value.email">
                  <mat-icon>alternate_email</mat-icon>
                  <input formControlName="email" type="email"
                    placeholder="admin@godavari.com"
                    (focus)="emailFocused=true" (blur)="emailFocused=false">
                </div>
              </div>

              <div class="field-group">
                <label>Password</label>
                <div class="input-wrap" [class.focused]="pwFocused" [class.has-value]="form.value.password">
                  <mat-icon>lock_outline</mat-icon>
                  <input formControlName="password"
                    [type]="showPw ? 'text' : 'password'"
                    placeholder="Enter your password"
                    (focus)="pwFocused=true" (blur)="pwFocused=false">
                  <button type="button" class="eye-btn" (click)="showPw=!showPw">
                    <mat-icon>{{ showPw ? 'visibility_off' : 'visibility' }}</mat-icon>
                  </button>
                </div>
              </div>

              <button type="submit" class="login-btn" [disabled]="loading">
                <span *ngIf="!loading">
                  <mat-icon>login</mat-icon>
                  Sign In to Admin
                </span>
                <span *ngIf="loading" class="loading-span">
                  <mat-spinner diameter="20"></mat-spinner>
                  Authenticating...
                </span>
              </button>

              <!-- Inline error -->
              <div *ngIf="errorMsg" class="err-box">
                <mat-icon>error_outline</mat-icon>
                {{ errorMsg }}
              </div>
            </form>

            <div class="divider"><span>or</span></div>

            <div class="customer-link">
              Not an admin? <a routerLink="/login">Customer Login →</a>
            </div>

            <div class="secure-note">
              <mat-icon>verified_user</mat-icon>
              Secured with JWT Authentication
            </div>
          </div>
        </div>
      </div>
    </div>
  `,
  styles: [`
    * { box-sizing: border-box; margin: 0; padding: 0; }

    .admin-login-page {
      min-height: 100vh;
      background: #050b1a;
      display: flex;
      align-items: center;
      justify-content: center;
      position: relative;
      overflow: hidden;
      font-family: 'Inter', sans-serif;
    }

    /* Background grid */
    .bg-grid {
      position: absolute; inset: 0;
      background-image:
        linear-gradient(rgba(99,179,255,0.03) 1px, transparent 1px),
        linear-gradient(90deg, rgba(99,179,255,0.03) 1px, transparent 1px);
      background-size: 50px 50px;
    }

    /* Glow blobs */
    .bg-glow {
      position: absolute;
      border-radius: 50%;
      filter: blur(80px);
      opacity: 0.15;
      animation: pulse 8s ease-in-out infinite;
    }
    .g1 { width: 500px; height: 500px; background: #3b82f6; top: -150px; left: -150px; }
    .g2 { width: 400px; height: 400px; background: #8b5cf6; bottom: -100px; right: -100px; animation-delay: 3s; }
    .g3 { width: 300px; height: 300px; background: #06b6d4; top: 50%; left: 50%; animation-delay: 5s; }

    /* Particles */
    .particles { position: absolute; inset: 0; pointer-events: none; }
    .particle {
      position: absolute;
      background: rgba(255,255,255,0.4);
      border-radius: 50%;
      animation: float linear infinite;
    }

    @keyframes pulse {
      0%,100% { transform: scale(1); opacity: 0.12; }
      50% { transform: scale(1.2); opacity: 0.2; }
    }
    @keyframes float {
      0% { transform: translateY(100vh) rotate(0deg); opacity: 0; }
      10% { opacity: 1; }
      90% { opacity: 1; }
      100% { transform: translateY(-100px) rotate(720deg); opacity: 0; }
    }

    /* Container */
    .login-container {
      position: relative; z-index: 10;
      display: flex;
      width: 100%;
      max-width: 1000px;
      min-height: 600px;
      border-radius: 24px;
      overflow: hidden;
      box-shadow: 0 25px 80px rgba(0,0,0,0.6);
      margin: 20px;
      animation: slideUp 0.7s cubic-bezier(0.22,1,0.36,1) both;
    }
    @keyframes slideUp {
      from { opacity: 0; transform: translateY(50px); }
      to { opacity: 1; transform: translateY(0); }
    }

    /* Left Panel */
    .left-panel {
      flex: 1;
      background: linear-gradient(145deg, #0f172a 0%, #1e3a5f 50%, #0f2d5e 100%);
      padding: 48px 40px;
      display: flex;
      flex-direction: column;
      gap: 32px;
      border-right: 1px solid rgba(255,255,255,0.07);
    }

    .brand { display: flex; flex-direction: column; gap: 6px; }
    .brand-icon {
      font-size: 48px;
      width: 72px; height: 72px;
      background: linear-gradient(135deg, #3b82f6, #8b5cf6);
      border-radius: 20px;
      display: flex; align-items: center; justify-content: center;
      box-shadow: 0 8px 32px rgba(59,130,246,0.4);
      margin-bottom: 8px;
    }
    .brand h1 { font-size: 32px; font-weight: 800; color: white; letter-spacing: -0.5px; }
    .brand p { color: rgba(255,255,255,0.5); font-size: 14px; }

    .features { display: flex; flex-direction: column; gap: 20px; }
    .feature {
      display: flex; align-items: flex-start; gap: 14px;
      animation: fadeInLeft 0.5s ease-out both;
    }
    @keyframes fadeInLeft {
      from { opacity: 0; transform: translateX(-20px); }
      to { opacity: 1; transform: translateX(0); }
    }
    .feat-icon {
      font-size: 22px;
      width: 44px; height: 44px;
      background: rgba(59,130,246,0.15);
      border: 1px solid rgba(59,130,246,0.3);
      border-radius: 12px;
      display: flex; align-items: center; justify-content: center;
      flex-shrink: 0;
    }
    .feature strong { color: white; font-size: 14px; font-weight: 600; display: block; }
    .feature p { color: rgba(255,255,255,0.45); font-size: 12px; margin-top: 2px; }

    .stats-row {
      display: flex; gap: 20px;
      padding-top: 24px;
      border-top: 1px solid rgba(255,255,255,0.08);
    }
    .stat { text-align: center; }
    .stat strong { display: block; font-size: 22px; font-weight: 800; color: #60a5fa; }
    .stat span { font-size: 11px; color: rgba(255,255,255,0.4); text-transform: uppercase; letter-spacing: 0.5px; }

    /* Right Panel */
    .right-panel {
      width: 420px;
      background: #0d1117;
      display: flex;
      align-items: center;
      justify-content: center;
      padding: 40px 36px;
    }

    .form-card { width: 100%; }

    .form-header { margin-bottom: 32px; }
    .admin-badge {
      display: inline-flex;
      align-items: center;
      gap: 6px;
      background: linear-gradient(135deg, rgba(59,130,246,0.15), rgba(139,92,246,0.15));
      border: 1px solid rgba(59,130,246,0.3);
      color: #60a5fa;
      padding: 5px 14px;
      border-radius: 20px;
      font-size: 12px;
      font-weight: 600;
      margin-bottom: 16px;
    }
    .admin-badge mat-icon { font-size: 16px; width: 16px; height: 16px; }
    .form-header h2 { font-size: 26px; font-weight: 800; color: white; margin-bottom: 6px; }
    .form-header p { color: rgba(255,255,255,0.4); font-size: 14px; }

    /* Fields */
    .field-group { margin-bottom: 20px; }
    .field-group label {
      display: block;
      font-size: 13px;
      font-weight: 600;
      color: rgba(255,255,255,0.6);
      margin-bottom: 8px;
      letter-spacing: 0.3px;
    }
    .input-wrap {
      display: flex;
      align-items: center;
      gap: 10px;
      background: rgba(255,255,255,0.04);
      border: 2px solid rgba(255,255,255,0.08);
      border-radius: 12px;
      padding: 0 16px;
      transition: all 0.2s ease;
    }
    .input-wrap mat-icon { color: rgba(255,255,255,0.3); font-size: 20px; flex-shrink: 0; }
    .input-wrap.focused {
      border-color: #3b82f6;
      background: rgba(59,130,246,0.06);
      box-shadow: 0 0 0 4px rgba(59,130,246,0.12);
    }
    .input-wrap input {
      flex: 1;
      background: none;
      border: none;
      outline: none;
      padding: 14px 0;
      font-size: 15px;
      color: white;
      font-family: 'Inter', sans-serif;
    }
    .input-wrap input::placeholder { color: rgba(255,255,255,0.2); }
    .eye-btn {
      background: none; border: none; cursor: pointer;
      color: rgba(255,255,255,0.3);
      display: flex; align-items: center; padding: 4px;
      transition: color 0.2s;
    }
    .eye-btn:hover { color: #60a5fa; }
    .eye-btn mat-icon { font-size: 20px; }

    /* Login button */
    .login-btn {
      width: 100%;
      height: 52px;
      background: linear-gradient(135deg, #3b82f6, #8b5cf6);
      color: white;
      border: none;
      border-radius: 12px;
      font-size: 15px;
      font-weight: 700;
      cursor: pointer;
      display: flex;
      align-items: center;
      justify-content: center;
      gap: 8px;
      margin-top: 8px;
      box-shadow: 0 6px 24px rgba(59,130,246,0.35);
      transition: all 0.2s ease;
      position: relative;
      overflow: hidden;
    }
    .login-btn::before {
      content: '';
      position: absolute;
      inset: 0;
      background: linear-gradient(135deg, rgba(255,255,255,0.1), transparent);
      opacity: 0;
      transition: opacity 0.2s;
    }
    .login-btn:hover:not(:disabled)::before { opacity: 1; }
    .login-btn:hover:not(:disabled) {
      transform: translateY(-2px);
      box-shadow: 0 10px 32px rgba(59,130,246,0.45);
    }
    .login-btn:disabled { opacity: 0.6; cursor: not-allowed; }
    .login-btn span { display: flex; align-items: center; gap: 8px; }
    .loading-span { display: flex; align-items: center; gap: 10px; }

    .divider {
      text-align: center;
      position: relative;
      margin: 24px 0 16px;
    }
    .divider::before {
      content: '';
      position: absolute;
      top: 50%; left: 0;
      width: 100%; height: 1px;
      background: rgba(255,255,255,0.08);
    }
    .divider span {
      position: relative;
      background: #0d1117;
      padding: 0 12px;
      font-size: 12px;
      color: rgba(255,255,255,0.25);
    }

    .customer-link {
      text-align: center;
      font-size: 13px;
      color: rgba(255,255,255,0.35);
    }
    .customer-link a { color: #60a5fa; text-decoration: none; font-weight: 600; }
    .customer-link a:hover { text-decoration: underline; }

    .secure-note {
      display: flex;
      align-items: center;
      justify-content: center;
      gap: 6px;
      margin-top: 20px;
      font-size: 12px;
      color: rgba(255,255,255,0.2);
    }
    .secure-note mat-icon { font-size: 14px; width: 14px; height: 14px; color: #4ade80; }

    /* Inline error box */
    .err-box {
      display: flex;
      align-items: center;
      gap: 8px;
      background: rgba(239,68,68,0.12);
      border: 1px solid rgba(239,68,68,0.35);
      color: #f87171;
      border-radius: 10px;
      padding: 11px 14px;
      font-size: 13px;
      font-weight: 500;
      margin-top: 12px;
      animation: shake 0.4s ease;
    }
    .err-box mat-icon { font-size: 17px; width: 17px; height: 17px; flex-shrink: 0; }
    @keyframes shake {
      0%,100% { transform: translateX(0); }
      20%,60% { transform: translateX(-5px); }
      40%,80% { transform: translateX(5px); }
    }

    @media (max-width: 768px) {
      .left-panel { display: none; }
      .right-panel { width: 100%; padding: 32px 24px; }
      .login-container { max-width: 420px; }
    }
  `]
})
export class AdminLoginComponent {
  form: FormGroup;
  loading = false;
  showPw = false;
  emailFocused = false;
  pwFocused = false;
  errorMsg = '';

  features = [
    { icon: '📦', title: 'Product Management', desc: 'Add, edit and manage your full catalog' },
    { icon: '📊', title: 'Inventory Control', desc: 'Real-time stock tracking & alerts' },
    { icon: '🧾', title: 'Order Management', desc: 'Process and track customer orders' },
    { icon: '📋', title: 'Quotation System', desc: 'Approve bulk order requests' }
  ];

  stats = [
    { val: '500+', label: 'Products' },
    { val: '1000+', label: 'Orders' },
    { val: '99.9%', label: 'Uptime' }
  ];

  particles = Array.from({ length: 20 }, () => ({
    x: Math.random() * 100,
    y: Math.random() * 100,
    size: Math.random() * 4 + 2,
    delay: Math.random() * 10,
    dur: Math.random() * 10 + 8
  }));

  constructor(private fb: FormBuilder, private auth: AuthService,
              private router: Router) {
    this.form = this.fb.group({
      email: ['', [Validators.required, Validators.email]],
      password: ['', Validators.required]
    });
    if (this.auth.isLoggedIn && this.auth.isManager) {
      this.router.navigate(['/admin/dashboard']);
    }
  }

  onSubmit(): void {
    this.errorMsg = '';
    if (this.form.invalid) {
      this.errorMsg = 'Please enter a valid email address and password.';
      return;
    }
    this.loading = true;
    const { email, password } = this.form.value;
    this.auth.login(email, password).subscribe({
      next: (user) => {
        if (['ADMIN', 'MANAGER'].includes(user.role)) {
          this.router.navigate(['/admin/dashboard']);
        } else {
          this.auth.logout();
          this.errorMsg = 'Access denied. Admin credentials required.';
          this.loading = false;
        }
      },
      error: err => {
        this.errorMsg = err.error?.error || err.error?.message || err.message || 'Invalid email or password';
        this.loading = false;
      }
    });
  }
}
