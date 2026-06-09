import { Component } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { MatSnackBar } from '@angular/material/snack-bar';
import { AuthService } from '../../services/auth.service';

@Component({
  selector: 'app-register',
  template: `
    <div class="reg-page">

      <!-- ── BACKGROUND IMAGE ── -->
      <div class="bg-wrap">
        <img class="bg-img"
          src="https://images.unsplash.com/photo-1503387762-592deb58ef4e?w=1600&q=85"
          alt="Construction">
        <div class="bg-overlay"></div>

        <!-- Animated grid lines -->
        <div class="grid-lines">
          <div class="gl" *ngFor="let g of [1,2,3,4,5,6]" [style.animation-delay.s]="g*0.5"></div>
        </div>
      </div>

      <!-- ── SCROLL CONTAINER ── -->
      <div class="scroll-wrap">

        <!-- Header bar -->
        <div class="reg-topbar">
          <div class="reg-logo" (click)="router.navigate(['/'])">
            <span>🏗️</span>
            <strong>Godavari</strong>
            <small>Building Materials</small>
          </div>
          <div class="reg-topbar-right">
            Already have an account?
            <a routerLink="/login">Sign In →</a>
          </div>
        </div>

        <!-- Card -->
        <div class="reg-card">

          <!-- Card header -->
          <div class="reg-card-head">
            <div class="head-icon">
              <span>🏗️</span>
            </div>
            <div>
              <h2>Create Your Account</h2>
              <p>Join thousands of builders & contractors in Nashik</p>
            </div>
          </div>

          <!-- Error -->
          <div *ngIf="errorMsg" class="err-box">
            <mat-icon>error_outline</mat-icon> {{errorMsg}}
          </div>

          <form [formGroup]="form" (ngSubmit)="onSubmit()">

            <!-- Role selection -->
            <div class="section">
              <div class="sec-label">Account Type</div>
              <div class="role-grid">
                <div *ngFor="let r of roles"
                  class="role-card" [class.sel]="form.value.role===r.val"
                  (click)="form.patchValue({role:r.val})">
                  <div class="role-ico" [style.background]="r.bg">{{r.icon}}</div>
                  <div>
                    <strong>{{r.name}}</strong>
                    <p>{{r.desc}}</p>
                  </div>
                  <div class="role-tick" *ngIf="form.value.role===r.val">
                    <mat-icon>check</mat-icon>
                  </div>
                </div>
              </div>
            </div>

            <!-- Personal info -->
            <div class="section">
              <div class="sec-label">Personal Information</div>
              <div class="fields-grid">

                <div class="fw">
                  <label>Full Name <span class="req">*</span></label>
                  <div class="fi" [class.invalid]="t('name')">
                    <mat-icon>person</mat-icon>
                    <input formControlName="name" placeholder="Rajesh Patil">
                  </div>
                  <span *ngIf="t('name')">Name is required</span>
                </div>

                <div class="fw">
                  <label>Phone Number</label>
                  <div class="fi">
                    <mat-icon>phone</mat-icon>
                    <input formControlName="phone" placeholder="9876543210">
                  </div>
                </div>

                <div class="fw full">
                  <label>Email Address <span class="req">*</span></label>
                  <div class="fi" [class.invalid]="t('email')">
                    <mat-icon>alternate_email</mat-icon>
                    <input formControlName="email" type="email" placeholder="you@example.com">
                  </div>
                  <span *ngIf="t('email')">Valid email is required</span>
                </div>

                <div class="fw">
                  <label>Password <span class="req">*</span></label>
                  <div class="fi" [class.invalid]="t('password')">
                    <mat-icon>lock_outline</mat-icon>
                    <input formControlName="password"
                      [type]="showPw?'text':'password'"
                      placeholder="Min 6 characters">
                    <button type="button" class="eye" (click)="showPw=!showPw">
                      <mat-icon>{{showPw?'visibility_off':'visibility'}}</mat-icon>
                    </button>
                  </div>
                  <span *ngIf="t('password')">Minimum 6 characters required</span>
                </div>

                <div class="fw">
                  <label>Confirm Password <span class="req">*</span></label>
                  <div class="fi" [class.invalid]="pwMismatch">
                    <mat-icon>lock</mat-icon>
                    <input [(ngModel)]="confirmPw" [ngModelOptions]="{standalone:true}"
                      [type]="showPw?'text':'password'"
                      placeholder="Re-enter password">
                  </div>
                  <span *ngIf="pwMismatch">Passwords do not match</span>
                </div>

              </div>
            </div>

            <!-- Business info -->
            <div class="section">
              <div class="sec-label">Business Details <small>(Optional)</small></div>
              <div class="fields-grid">
                <div class="fw">
                  <label>GST Number</label>
                  <div class="fi">
                    <mat-icon>receipt_long</mat-icon>
                    <input formControlName="gstNumber" placeholder="27XXXXX">
                  </div>
                </div>
                <div class="fw">
                  <label>Company Name</label>
                  <div class="fi">
                    <mat-icon>business</mat-icon>
                    <input formControlName="companyName" placeholder="ABC Constructions">
                  </div>
                </div>
              </div>
            </div>

            <!-- Address -->
            <div class="section">
              <div class="sec-label">Address <small>(Optional)</small></div>
              <div class="fields-grid">
                <div class="fw full">
                  <label>Street Address</label>
                  <div class="fi">
                    <mat-icon>home</mat-icon>
                    <input formControlName="address" placeholder="Plot No, Street, Area">
                  </div>
                </div>
                <div class="fw">
                  <label>City</label>
                  <div class="fi">
                    <mat-icon>location_city</mat-icon>
                    <input formControlName="city" placeholder="Nashik">
                  </div>
                </div>
                <div class="fw">
                  <label>Pincode</label>
                  <div class="fi">
                    <mat-icon>pin_drop</mat-icon>
                    <input formControlName="pincode" placeholder="422001">
                  </div>
                </div>
              </div>
            </div>

            <button type="submit" class="submit-btn" [disabled]="loading">
              <span *ngIf="!loading">
                <mat-icon>how_to_reg</mat-icon> Create Account
              </span>
              <span *ngIf="loading" class="ldg">
                <mat-spinner diameter="20"></mat-spinner> Creating...
              </span>
            </button>

          </form>

          <div class="bottom-links">
            <span>Already have an account? <a routerLink="/login">Sign In →</a></span>
          </div>
        </div>

        <!-- Footer note -->
        <div class="reg-footer">
          © 2025 Godavari Building Materials Nashik · All rights reserved
        </div>
      </div>
    </div>
  `,
  styles: [`
    *{box-sizing:border-box;margin:0;padding:0;}

    .reg-page {
      min-height: 100vh;
      position: relative;
      font-family: 'Inter', sans-serif;
    }

    /* Background */
    .bg-wrap {
      position: fixed; inset: 0; z-index: 0; overflow: hidden;
    }
    .bg-img {
      width: 100%; height: 100%; object-fit: cover;
      animation: bgZoom 30s ease-in-out infinite alternate;
    }
    @keyframes bgZoom { from{transform:scale(1);} to{transform:scale(1.07);} }
    .bg-overlay {
      position: absolute; inset: 0;
      background: linear-gradient(
        135deg,
        rgba(5,10,30,0.82) 0%,
        rgba(26,35,126,0.72) 40%,
        rgba(255,109,0,0.45) 80%,
        rgba(5,10,30,0.75) 100%
      );
    }

    /* Animated grid lines */
    .grid-lines { position:absolute; inset:0; overflow:hidden; }
    .gl {
      position: absolute; left: 0; right: 0;
      height: 1px; background: rgba(255,255,255,.04);
      animation: scanLine 8s linear infinite;
    }
    .gl:nth-child(1){top:15%;}  .gl:nth-child(2){top:30%;}
    .gl:nth-child(3){top:45%;}  .gl:nth-child(4){top:60%;}
    .gl:nth-child(5){top:75%;}  .gl:nth-child(6){top:90%;}
    @keyframes scanLine {
      0%   { transform: translateX(-100%); opacity: 0; }
      10%  { opacity: 1; }
      90%  { opacity: 0.3; }
      100% { transform: translateX(100%); opacity: 0; }
    }

    /* Scroll container */
    .scroll-wrap {
      position: relative; z-index: 10;
      min-height: 100vh; padding: 0 20px 40px;
      display: flex; flex-direction: column; align-items: center;
    }

    /* Top bar */
    .reg-topbar {
      width: 100%; max-width: 780px;
      display: flex; justify-content: space-between; align-items: center;
      padding: 20px 0; margin-bottom: 16px;
      animation: fadeDown .6s ease-out;
    }
    @keyframes fadeDown { from{opacity:0;transform:translateY(-16px);} to{opacity:1;transform:translateY(0);} }
    .reg-logo {
      display: flex; align-items: center; gap: 10px; cursor: pointer;
    }
    .reg-logo span { font-size:28px; }
    .reg-logo strong { font-size:18px; font-weight:800; color:#fff; }
    .reg-logo small  { font-size:11px; color:rgba(255,255,255,.5); margin-left:2px; }
    .reg-topbar-right { font-size:13px; color:rgba(255,255,255,.6); }
    .reg-topbar-right a { color:#ff9100; font-weight:700; text-decoration:none; margin-left:4px; }
    .reg-topbar-right a:hover { text-decoration:underline; }

    /* Main card */
    .reg-card {
      width: 100%; max-width: 780px;
      background: rgba(255,255,255,.97);
      backdrop-filter: blur(24px);
      border-radius: 24px;
      padding: 36px 40px;
      box-shadow: 0 32px 80px rgba(0,0,0,.4), 0 0 0 1px rgba(255,255,255,.1);
      animation: cardUp .8s cubic-bezier(.22,1,.36,1) .1s both;
    }
    @keyframes cardUp { from{opacity:0;transform:translateY(40px) scale(.97);} to{opacity:1;transform:translateY(0) scale(1);} }

    /* Card header */
    .reg-card-head {
      display: flex; align-items: center; gap: 16px;
      margin-bottom: 28px; padding-bottom: 20px;
      border-bottom: 2px solid #f1f5f9;
    }
    .head-icon {
      width: 56px; height: 56px; font-size: 28px; flex-shrink: 0;
      background: linear-gradient(135deg,#1a237e,#3949ab);
      border-radius: 16px; display: flex; align-items: center; justify-content: center;
      box-shadow: 0 6px 20px rgba(26,35,126,.3);
      animation: popIn .8s cubic-bezier(.34,1.56,.64,1) .3s both;
    }
    @keyframes popIn { from{opacity:0;transform:scale(0) rotate(-20deg);} to{opacity:1;transform:scale(1);} }
    .reg-card-head h2 { font-size:22px; font-weight:800; color:#1a1a2e; }
    .reg-card-head p  { font-size:13px; color:#64748b; margin-top:3px; }

    /* Error */
    .err-box {
      display:flex; align-items:center; gap:8px;
      background:#fef2f2; border:1px solid #fecaca; color:#dc2626;
      border-radius:10px; padding:12px 14px; font-size:13px; font-weight:500;
      margin-bottom:20px; animation:shake .4s ease;
    }
    @keyframes shake { 0%,100%{transform:translateX(0);} 20%,60%{transform:translateX(-5px);} 40%,80%{transform:translateX(5px);} }

    /* Sections */
    .section { margin-bottom: 24px; }
    .sec-label {
      font-size:11px; font-weight:700; text-transform:uppercase; letter-spacing:1px;
      color:#1a237e; margin-bottom:12px;
      display:flex; align-items:center; gap:8px;
    }
    .sec-label::after { content:''; flex:1; height:1px; background:#e8eaf6; }
    .sec-label small { font-size:11px; color:#94a3b8; text-transform:none; letter-spacing:0; font-weight:400; }

    /* Role cards */
    .role-grid {
      display: grid; grid-template-columns: 1fr 1fr; gap: 10px;
    }
    .role-card {
      display: flex; align-items: center; gap: 12px;
      background: #f8fafc; border: 2px solid #e2e8f0; border-radius: 12px;
      padding: 14px 16px; cursor: pointer; transition: all .2s; position: relative;
    }
    .role-card:hover { border-color: #1a237e; background: #f0f2ff; }
    .role-card.sel   { border-color: #1a237e; background: linear-gradient(135deg,#f0f2ff,#e8eaf6); box-shadow: 0 0 0 4px rgba(26,35,126,.08); }
    .role-ico { width:40px; height:40px; border-radius:10px; font-size:20px; display:flex; align-items:center; justify-content:center; flex-shrink:0; }
    .role-card strong { display:block; font-size:13px; font-weight:700; color:#1a1a2e; }
    .role-card p      { font-size:11px; color:#64748b; margin-top:1px; }
    .role-tick {
      position:absolute; top:8px; right:10px;
      width:22px; height:22px; border-radius:50%;
      background:linear-gradient(135deg,#1a237e,#3949ab); color:#fff;
      display:flex; align-items:center; justify-content:center;
    }
    .role-tick mat-icon { font-size:14px; width:14px; height:14px; }

    /* Fields grid */
    .fields-grid { display:grid; grid-template-columns:1fr 1fr; gap:14px; }
    .fw { display:flex; flex-direction:column; gap:5px; }
    .fw.full { grid-column:span 2; }
    .fw > label { font-size:12px; font-weight:700; color:#475569; text-transform:uppercase; letter-spacing:.4px; }
    .req { color:#ef4444; }
    .fw > span  { font-size:11px; color:#ef4444; }
    .fi {
      display:flex; align-items:center; gap:8px;
      background:#f8fafc; border:2px solid #e2e8f0; border-radius:10px;
      padding:0 13px; transition:all .2s;
    }
    .fi:focus-within { border-color:#1a237e; background:#fff; box-shadow:0 0 0 4px rgba(26,35,126,.07); }
    .fi.invalid { border-color:#ef4444; }
    .fi mat-icon { color:#94a3b8; font-size:19px; flex-shrink:0; }
    .fi input {
      flex:1; border:none; background:transparent; padding:13px 0;
      font-size:14px; color:#1a1a2e; font-family:'Inter',sans-serif; outline:none;
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
      margin-top:8px; font-family:'Inter',sans-serif;
      box-shadow:0 6px 20px rgba(26,35,126,.35); transition:all .2s;
      position:relative; overflow:hidden;
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
    .ldg { display:flex; align-items:center; gap:10px; }

    .bottom-links {
      text-align:center; font-size:14px; color:#64748b; margin-top:20px;
    }
    .bottom-links a { color:#1a237e; font-weight:700; text-decoration:none; }
    .bottom-links a:hover { text-decoration:underline; }

    /* Footer */
    .reg-footer {
      margin-top:20px; font-size:12px; color:rgba(255,255,255,.3);
      text-align:center; padding-bottom:10px;
    }

    @media(max-width:640px) {
      .reg-card { padding:24px 16px; }
      .fields-grid { grid-template-columns:1fr; }
      .fw.full { grid-column:span 1; }
      .role-grid { grid-template-columns:1fr; }
      .reg-topbar-right { display:none; }
    }
  `]
})
export class RegisterComponent {
  form: FormGroup;
  loading = false;
  showPw = false;
  confirmPw = '';
  errorMsg = '';

  roles = [
    { val:'CUSTOMER',   icon:'🛒', name:'Customer',   desc:'Buy for personal use',   bg:'linear-gradient(135deg,#e8eaf6,#c5cae9)' },
    { val:'CONTRACTOR', icon:'👷', name:'Contractor',  desc:'Bulk orders & RFQ',      bg:'linear-gradient(135deg,#e8f5e9,#a5d6a7)' }
  ];

  constructor(private fb: FormBuilder, private auth: AuthService,
              public router: Router, private snack: MatSnackBar) {
    this.form = this.fb.group({
      name:        ['', Validators.required],
      email:       ['', [Validators.required, Validators.email]],
      password:    ['', [Validators.required, Validators.minLength(6)]],
      phone:       [''],
      role:        ['CUSTOMER'],
      gstNumber:   [''],
      companyName: [''],
      address:     [''],
      city:        ['Nashik'],
      state:       ['Maharashtra'],
      pincode:     ['']
    });
  }

  t(f: string): boolean {
    const c = this.form.get(f);
    return !!(c && c.invalid && c.touched);
  }

  get pwMismatch(): boolean {
    return !!(this.confirmPw && this.form.value.password && this.confirmPw !== this.form.value.password);
  }

  onSubmit(): void {
    this.form.markAllAsTouched();
    this.errorMsg = '';
    if (this.form.invalid) { this.errorMsg = 'Please fill all required fields.'; return; }
    if (this.pwMismatch)   { this.errorMsg = 'Passwords do not match.'; return; }
    this.loading = true;
    this.auth.register(this.form.value).subscribe({
      next: () => {
        this.snack.open('🎉 Account created! Welcome to Godavari!', 'Close',
          { duration: 3500, panelClass: 'snack-success' });
        this.router.navigate(['/products']);
      },
      error: err => {
        this.errorMsg = err.error?.error || err.error?.message || 'Registration failed. Email may already exist.';
        this.loading = false;
      }
    });
  }
}
