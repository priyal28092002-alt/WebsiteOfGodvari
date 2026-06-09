import { Component } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { MatSnackBar } from '@angular/material/snack-bar';
import { AuthService } from '../../services/auth.service';

@Component({
  selector: 'app-login',
  template: `
    <div class="login-page">

      <!-- ── LEFT PANEL: Vivid background image ── -->
      <div class="left-panel">
        <img class="bg-img" src="https://images.unsplash.com/photo-1504307651254-35680f356dfd?w=1200&q=90" alt="Construction site">
        <div class="left-overlay"></div>

        <!-- Floating particles -->
        <div class="particles">
          <span *ngFor="let p of particles" class="particle"
            [style.left.%]="p.x" [style.top.%]="p.y"
            [style.width.px]="p.s" [style.height.px]="p.s"
            [style.animation-delay.s]="p.d" [style.animation-duration.s]="p.dur">
          </span>
        </div>

        <!-- Left content -->
        <div class="left-content">
          <div class="brand-mark" (click)="router.navigate(['/'])">
            <div class="brand-icon">🏗️</div>
            <div>
              <h1>Godavari</h1>
              <p>Building Materials</p>
            </div>
          </div>

          <div class="left-headline">
            <h2>Build Your<br><span>Dream Project</span></h2>
            <p>Premium quality materials at wholesale prices. Serving Nashik since 2009.</p>
          </div>

          <div class="stat-cards">
            <div class="sc" *ngFor="let s of stats; let i=index" [style.animation-delay.ms]="i*120">
              <strong>{{s.val}}</strong>
              <span>{{s.label}}</span>
            </div>
          </div>

          <div class="product-pills">
            <span *ngFor="let p of pills; let i=index" [style.animation-delay.ms]="600+i*80">{{p}}</span>
          </div>

          <div class="location-tag">
            <span class="loc-dot"></span>
            Nashik, Maharashtra · Est. 2009
          </div>
        </div>
      </div>

      <!-- ── RIGHT PANEL: Login form ── -->
      <div class="right-panel">
        <div class="form-wrap">

          <div class="form-top">
            <div class="form-logo" (click)="router.navigate(['/'])">🏗️</div>
            <div>
              <h3>Welcome Back</h3>
              <p>Sign in to your account</p>
            </div>
          </div>

          <!-- Error -->
          <div *ngIf="errorMsg" class="err-box">
            <mat-icon>error_outline</mat-icon>
            {{errorMsg}}
          </div>

          <form [formGroup]="form" (ngSubmit)="onSubmit()">

            <div class="field">
              <label>Email Address</label>
              <div class="fi" [class.focused]="emailF" [class.err]="touched('email')">
                <mat-icon>alternate_email</mat-icon>
                <input formControlName="email" type="email"
                  placeholder="you@example.com"
                  (focus)="emailF=true" (blur)="emailF=false">
              </div>
              <span *ngIf="touched('email')">Please enter a valid email</span>
            </div>

            <div class="field">
              <label>Password</label>
              <div class="fi" [class.focused]="pwF" [class.err]="touched('password')">
                <mat-icon>lock_outline</mat-icon>
                <input formControlName="password"
                  [type]="showPw ? 'text':'password'"
                  placeholder="Enter your password"
                  (focus)="pwF=true" (blur)="pwF=false">
                <button type="button" class="eye" (click)="showPw=!showPw">
                  <mat-icon>{{showPw?'visibility_off':'visibility'}}</mat-icon>
                </button>
              </div>
              <span *ngIf="touched('password')">Password is required</span>
            </div>

            <button type="submit" class="submit-btn" [disabled]="loading">
              <span *ngIf="!loading"><mat-icon>login</mat-icon> Sign In</span>
              <span *ngIf="loading" class="loading-s">
                <mat-spinner diameter="18"></mat-spinner> Signing in...
              </span>
            </button>

          </form>

          <div class="divider"><span>or</span></div>

          <div class="links">
            <span>New to Godavari? <a routerLink="/register">Create Account →</a></span>
          </div>
          <div class="links" style="margin-top:8px;">
            <span>Admin? <a routerLink="/admin/login">Admin Portal →</a></span>
          </div>

          <div class="trust-row">
            <div class="trust-item"><span>🔒</span><p>Secure</p></div>
            <div class="trust-item"><span>⚡</span><p>Fast</p></div>
            <div class="trust-item"><span>🛡️</span><p>JWT Auth</p></div>
            <div class="trust-item"><span>📱</span><p>Mobile</p></div>
          </div>
        </div>
      </div>

    </div>
  `,
  styles: [`
    *{box-sizing:border-box;margin:0;padding:0;}

    .login-page {
      min-height: 100vh;
      display: flex;
      font-family: 'Inter', sans-serif;
    }

    /* ── LEFT PANEL ── */
    .left-panel {
      flex: 1.1;
      position: relative;
      overflow: hidden;
      display: flex;
      align-items: flex-end;
    }
    .bg-img {
      position: absolute; inset: 0;
      width: 100%; height: 100%;
      object-fit: cover;
      animation: bgZoom 25s ease-in-out infinite alternate;
    }
    @keyframes bgZoom { from{transform:scale(1);} to{transform:scale(1.08);} }
    .left-overlay {
      position: absolute; inset: 0;
      background: linear-gradient(
        135deg,
        rgba(10,20,60,0.75) 0%,
        rgba(26,35,126,0.65) 40%,
        rgba(255,109,0,0.35) 100%
      );
    }

    /* Particles */
    .particles { position:absolute; inset:0; pointer-events:none; overflow:hidden; }
    .particle {
      position: absolute;
      background: rgba(255,255,255,0.5);
      border-radius: 50%;
      animation: rise linear infinite;
    }
    @keyframes rise {
      0%   { transform: translateY(110vh) scale(0); opacity: 0; }
      10%  { opacity: 1; }
      90%  { opacity: 0.8; }
      100% { transform: translateY(-20px) scale(1); opacity: 0; }
    }

    .left-content {
      position: relative; z-index: 2;
      padding: 48px;
      display: flex; flex-direction: column; gap: 28px;
      width: 100%;
    }

    .brand-mark {
      display: flex; align-items: center; gap: 12px;
      cursor: pointer;
    }
    .brand-icon {
      width: 52px; height: 52px; font-size: 26px;
      background: linear-gradient(135deg,#ff6d00,#ff9100);
      border-radius: 14px;
      display: flex; align-items: center; justify-content: center;
      box-shadow: 0 6px 20px rgba(255,109,0,.5);
      animation: popIn .8s cubic-bezier(.34,1.56,.64,1) .2s both;
    }
    @keyframes popIn { from{opacity:0;transform:scale(0) rotate(-20deg);} to{opacity:1;transform:scale(1) rotate(0);} }
    .brand-mark h1 { font-size:22px; font-weight:800; color:#fff; line-height:1.2; }
    .brand-mark p  { font-size:12px; color:rgba(255,255,255,.55); }

    .left-headline { animation: fadeUp .7s ease-out .3s both; }
    @keyframes fadeUp { from{opacity:0;transform:translateY(24px);} to{opacity:1;transform:translateY(0);} }
    .left-headline h2 {
      font-size: clamp(2rem,3.5vw,3rem);
      font-weight: 900; color: #fff; line-height: 1.15;
      letter-spacing: -1px;
    }
    .left-headline h2 span {
      background: linear-gradient(90deg,#ff9100,#ffcc00);
      -webkit-background-clip: text; -webkit-text-fill-color: transparent;
      background-clip: text;
    }
    .left-headline p { color:rgba(255,255,255,.7); font-size:15px; margin-top:12px; line-height:1.6; }

    .stat-cards {
      display: flex; gap: 14px; flex-wrap: wrap;
      animation: fadeUp .7s ease-out .5s both;
    }
    .sc {
      background: rgba(255,255,255,.1);
      backdrop-filter: blur(12px);
      border: 1px solid rgba(255,255,255,.18);
      border-radius: 14px; padding: 14px 18px;
      animation: fadeUp .5s ease-out both;
    }
    .sc strong { display:block; font-size:22px; font-weight:800; color:#fff; }
    .sc span   { font-size:11px; color:rgba(255,255,255,.6); text-transform:uppercase; letter-spacing:.5px; }

    .product-pills {
      display: flex; gap: 8px; flex-wrap: wrap;
      animation: fadeUp .7s ease-out .7s both;
    }
    .product-pills span {
      background: rgba(255,255,255,.12);
      backdrop-filter: blur(8px);
      border: 1px solid rgba(255,255,255,.2);
      color: rgba(255,255,255,.9);
      padding: 6px 14px; border-radius: 20px;
      font-size: 12px; font-weight: 600;
      animation: fadeUp .4s ease-out both;
    }

    .location-tag {
      display: flex; align-items: center; gap: 8px;
      font-size: 12px; color: rgba(255,255,255,.45);
      animation: fadeUp .7s ease-out .9s both;
    }
    .loc-dot {
      width: 8px; height: 8px; border-radius: 50%;
      background: #4ade80; box-shadow: 0 0 8px #4ade80;
      animation: blink 2s infinite;
    }
    @keyframes blink { 0%,100%{opacity:1;} 50%{opacity:.2;} }

    /* ── RIGHT PANEL ── */
    .right-panel {
      width: 480px; flex-shrink: 0;
      background: #fff;
      display: flex; align-items: center; justify-content: center;
      padding: 40px 32px;
      animation: slideIn .7s cubic-bezier(.22,1,.36,1) both;
    }
    @keyframes slideIn { from{opacity:0;transform:translateX(40px);} to{opacity:1;transform:translateX(0);} }
    .form-wrap { width: 100%; max-width: 380px; }

    .form-top {
      display: flex; align-items: center; gap: 12px;
      margin-bottom: 32px;
    }
    .form-logo {
      width: 44px; height: 44px; font-size: 22px;
      background: linear-gradient(135deg,#1a237e,#3949ab);
      border-radius: 12px;
      display: flex; align-items: center; justify-content: center;
      box-shadow: 0 4px 14px rgba(26,35,126,.3); cursor: pointer;
    }
    .form-top h3 { font-size:22px; font-weight:800; color:#1a1a2e; }
    .form-top p  { font-size:13px; color:#64748b; margin-top:2px; }

    /* Error */
    .err-box {
      display: flex; align-items: center; gap: 8px;
      background: #fef2f2; border: 1px solid #fecaca; color: #dc2626;
      border-radius: 10px; padding: 12px 14px;
      font-size: 13px; font-weight: 500; margin-bottom: 20px;
      animation: shake .4s ease;
    }
    .err-box mat-icon { font-size:17px; width:17px; height:17px; flex-shrink:0; }
    @keyframes shake { 0%,100%{transform:translateX(0);} 20%,60%{transform:translateX(-5px);} 40%,80%{transform:translateX(5px);} }

    /* Fields */
    .field { margin-bottom: 18px; }
    .field > label {
      display: block; font-size:12px; font-weight:700;
      color:#475569; text-transform:uppercase; letter-spacing:.5px; margin-bottom:7px;
    }
    .field > span { font-size:11px; color:#ef4444; margin-top:4px; display:block; }
    .fi {
      display: flex; align-items: center; gap: 10px;
      background: #f8fafc; border: 2px solid #e2e8f0; border-radius: 12px;
      padding: 0 14px; transition: all .2s;
    }
    .fi.focused { border-color:#1a237e; background:#fff; box-shadow:0 0 0 4px rgba(26,35,126,.07); }
    .fi.err { border-color:#ef4444; }
    .fi mat-icon { color:#94a3b8; font-size:20px; flex-shrink:0; }
    .fi input {
      flex:1; border:none; background:transparent; padding:14px 0;
      font-size:15px; color:#1a1a2e; font-family:'Inter',sans-serif; outline:none;
    }
    .fi input::placeholder { color:#94a3b8; }
    .eye { background:none; border:none; cursor:pointer; color:#94a3b8; display:flex; align-items:center; transition:color .2s; }
    .eye:hover { color:#1a237e; }

    /* Submit */
    .submit-btn {
      width:100%; height:52px; border:none; border-radius:12px;
      background:linear-gradient(135deg,#1a237e,#3949ab);
      color:#fff; font-size:15px; font-weight:700; cursor:pointer;
      display:flex; align-items:center; justify-content:center; gap:8px;
      margin-top:4px;
      box-shadow: 0 6px 20px rgba(26,35,126,.35);
      transition: all .2s; position:relative; overflow:hidden;
      font-family:'Inter',sans-serif;
    }
    .submit-btn::before {
      content:''; position:absolute; top:0; left:-100%;
      width:60%; height:100%;
      background:linear-gradient(90deg,transparent,rgba(255,255,255,.15),transparent);
      transform:skewX(-15deg); transition:left .6s;
    }
    .submit-btn:hover:not(:disabled)::before { left:150%; }
    .submit-btn:hover:not(:disabled) { transform:translateY(-2px); box-shadow:0 10px 28px rgba(26,35,126,.45); }
    .submit-btn:disabled { opacity:.65; cursor:not-allowed; }
    .submit-btn span { display:flex; align-items:center; gap:8px; }
    .loading-s { display:flex; align-items:center; gap:10px; }

    /* Divider */
    .divider {
      text-align:center; position:relative; margin:24px 0 16px;
    }
    .divider::before {
      content:''; position:absolute; top:50%; left:0;
      width:100%; height:1px; background:#f1f5f9;
    }
    .divider span {
      position:relative; background:#fff; padding:0 12px;
      font-size:12px; color:#94a3b8;
    }

    .links { text-align:center; font-size:14px; color:#64748b; }
    .links a { color:#1a237e; font-weight:700; text-decoration:none; }
    .links a:hover { text-decoration:underline; }

    .trust-row {
      display:flex; justify-content:center; gap:20px;
      margin-top:24px; padding-top:20px; border-top:1px solid #f1f5f9;
    }
    .trust-item { text-align:center; }
    .trust-item span { font-size:20px; display:block; }
    .trust-item p { font-size:10px; color:#94a3b8; font-weight:600; text-transform:uppercase; letter-spacing:.5px; margin-top:3px; }

    @media(max-width:900px) {
      .left-panel { display:none; }
      .right-panel { width:100%; padding:32px 24px; }
      .login-page { background: linear-gradient(135deg,#1a237e,#3949ab); }
      .right-panel { background:rgba(255,255,255,.96); border-radius:20px; margin:20px; min-height:auto; }
    }
  `]
})
export class LoginComponent {
  form: FormGroup;
  loading = false;
  showPw = false;
  emailF = false;
  pwF = false;
  errorMsg = '';

  stats = [
    {val:'500+', label:'Products'},
    {val:'5K+',  label:'Customers'},
    {val:'15+',  label:'Years'}
  ];
  pills = ['🧱 Cement','🔩 Steel','🎨 Paints','🪟 Tiles','🔧 Plumbing','⚡ Electrical'];
  particles = Array.from({length:18},()=>({
    x: Math.random()*100, y: Math.random()*100,
    s: Math.random()*3+1.5, d: Math.random()*8, dur: Math.random()*8+6
  }));

  constructor(private fb: FormBuilder, private auth: AuthService,
              public router: Router, private snack: MatSnackBar) {
    this.form = this.fb.group({
      email:    ['', [Validators.required, Validators.email]],
      password: ['', Validators.required]
    });
    if (this.auth.isLoggedIn) this.router.navigate(['/products']);
  }

  touched(f: string): boolean {
    const c = this.form.get(f);
    return !!(c && c.invalid && c.touched);
  }

  onSubmit(): void {
    this.form.markAllAsTouched();
    this.errorMsg = '';
    if (this.form.invalid) { this.errorMsg = 'Please enter a valid email and password.'; return; }
    this.loading = true;
    const {email, password} = this.form.value;
    this.auth.login(email, password).subscribe({
      next: (user) => {
        ['ADMIN','MANAGER'].includes(user.role)
          ? this.router.navigate(['/admin/dashboard'])
          : this.router.navigate(['/products']);
      },
      error: err => {
        this.errorMsg = err.error?.error || err.error?.message || 'Invalid email or password';
        this.loading = false;
      }
    });
  }
}
