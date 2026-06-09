import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { AuthService } from '../../services/auth.service';

@Component({
  selector: 'app-admin-register',
  template: `
    <div class="page-container">

      <!-- Access Denied (extra safety, guard handles routing but this shows in-component) -->
      <div *ngIf="!auth.isAdmin" class="denied-wrap">
        <div class="denied-card">
          <div class="denied-icon">🔒</div>
          <h2>Access Denied</h2>
          <p>Only <strong>ADMIN</strong> role can register new users.</p>
          <p class="denied-sub">Your current role is <span class="role-pill">{{ auth.role }}</span></p>
          <button class="back-btn" (click)="router.navigate(['/admin/dashboard'])">
            <mat-icon>arrow_back</mat-icon> Back to Dashboard
          </button>
        </div>
      </div>

      <!-- Main Form (ADMIN only) -->
      <ng-container *ngIf="auth.isAdmin">

        <!-- Header -->
        <div class="reg-header">
          <div class="reg-header-left">
            <div class="reg-icon">
              <mat-icon>person_add</mat-icon>
            </div>
            <div>
              <h2>Register New User</h2>
              <p>Create staff or customer accounts for Godavari</p>
            </div>
          </div>
          <button class="back-link" (click)="router.navigate(['/admin/users'])">
            <mat-icon>people</mat-icon> View All Users
          </button>
        </div>

        <!-- Who can create what - info strip -->
        <div class="access-strip">
          <mat-icon>info_outline</mat-icon>
          You are logged in as <strong>{{ auth.currentUser?.name }}</strong>
          (<span class="strip-role">{{ auth.role }}</span>).
          You can create accounts with any role.
        </div>

        <div class="reg-grid">

          <!-- Left: Role Selection -->
          <div class="role-panel">
            <h3>Select Role</h3>
            <p>Choose what access level this new user will have</p>

            <div class="role-list">
              <div *ngFor="let r of roles"
                class="role-card" [class.selected]="form.value.role === r.value"
                (click)="form.patchValue({role: r.value})">
                <div class="role-card-ico" [style.background]="r.bg">{{ r.icon }}</div>
                <div class="role-card-info">
                  <strong>{{ r.label }}</strong>
                  <p>{{ r.desc }}</p>
                </div>
                <div class="role-card-check" *ngIf="form.value.role === r.value">
                  <mat-icon>check_circle</mat-icon>
                </div>
              </div>
            </div>

            <!-- Permission preview -->
            <div class="perm-preview">
              <h4>Permissions for <span>{{ selectedRole?.label }}</span></h4>
              <div class="perm-list">
                <div *ngFor="let p of selectedRole?.perms" class="perm-item">
                  <mat-icon class="perm-yes">check</mat-icon>
                  <span>{{ p }}</span>
                </div>
              </div>
            </div>
          </div>

          <!-- Right: Form -->
          <div class="form-panel">
            <h3>Account Details</h3>

            <!-- Inline error -->
            <div *ngIf="errorMsg" class="err-box">
              <mat-icon>error_outline</mat-icon> {{ errorMsg }}
            </div>

            <!-- Success -->
            <div *ngIf="successMsg" class="success-box">
              <mat-icon>check_circle</mat-icon> {{ successMsg }}
            </div>

            <form [formGroup]="form" (ngSubmit)="onSubmit()">
              <div class="fields-grid">

                <div class="fw">
                  <label>Full Name *</label>
                  <div class="fi" [class.invalid]="touched('name')">
                    <mat-icon>person</mat-icon>
                    <input formControlName="name" placeholder="e.g. Rajesh Patil">
                  </div>
                  <span *ngIf="touched('name')">Name is required</span>
                </div>

                <div class="fw">
                  <label>Phone Number</label>
                  <div class="fi">
                    <mat-icon>phone</mat-icon>
                    <input formControlName="phone" placeholder="9876543210">
                  </div>
                </div>

                <div class="fw full">
                  <label>Email Address *</label>
                  <div class="fi" [class.invalid]="touched('email')">
                    <mat-icon>alternate_email</mat-icon>
                    <input formControlName="email" type="email" placeholder="user@godavari.com">
                  </div>
                  <span *ngIf="touched('email')">Valid email is required</span>
                </div>

                <div class="fw">
                  <label>Password *</label>
                  <div class="fi" [class.invalid]="touched('password')">
                    <mat-icon>lock_outline</mat-icon>
                    <input formControlName="password"
                      [type]="showPw ? 'text' : 'password'"
                      placeholder="Min 6 characters">
                    <button type="button" class="eye-btn" (click)="showPw=!showPw">
                      <mat-icon>{{ showPw ? 'visibility_off' : 'visibility' }}</mat-icon>
                    </button>
                  </div>
                  <span *ngIf="touched('password')">Minimum 6 characters required</span>
                </div>

                <div class="fw">
                  <label>Company Name</label>
                  <div class="fi">
                    <mat-icon>business</mat-icon>
                    <input formControlName="companyName" placeholder="Company / Organization">
                  </div>
                </div>

                <div class="fw">
                  <label>GST Number</label>
                  <div class="fi">
                    <mat-icon>receipt</mat-icon>
                    <input formControlName="gstNumber" placeholder="27XXXXX (optional)">
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
                  <label>State</label>
                  <div class="fi">
                    <mat-icon>map</mat-icon>
                    <input formControlName="state" placeholder="Maharashtra">
                  </div>
                </div>

              </div>

              <div class="form-actions">
                <button type="button" class="cancel-btn"
                  (click)="resetForm()">
                  <mat-icon>refresh</mat-icon> Reset
                </button>
                <button type="submit" class="submit-btn" [disabled]="saving">
                  <mat-spinner *ngIf="saving" diameter="18"></mat-spinner>
                  <mat-icon *ngIf="!saving">how_to_reg</mat-icon>
                  {{ saving ? 'Creating Account...' : 'Create Account' }}
                </button>
              </div>
            </form>
          </div>

        </div>
      </ng-container>
    </div>
  `,
  styles: [`
    /* Access Denied */
    .denied-wrap {
      display: flex; align-items: center; justify-content: center;
      min-height: 60vh;
    }
    .denied-card {
      background: #fff; border-radius: 20px; padding: 48px 40px;
      text-align: center; max-width: 420px; width: 100%;
      border: 1px solid #fecaca;
      box-shadow: 0 8px 32px rgba(220,38,38,.08);
      animation: fuUp .5s ease-out;
    }
    .denied-icon { font-size: 56px; margin-bottom: 16px; }
    .denied-card h2 { font-size: 24px; font-weight: 800; color: #dc2626; margin-bottom: 10px; }
    .denied-card p { font-size: 15px; color: #475569; margin-bottom: 6px; }
    .denied-sub { font-size: 14px; color: #64748b; }
    .role-pill {
      background: #fef2f2; color: #dc2626; border: 1px solid #fecaca;
      padding: 2px 10px; border-radius: 12px; font-size: 13px; font-weight: 700;
    }
    .back-btn {
      display: inline-flex; align-items: center; gap: 8px;
      margin-top: 24px; padding: 12px 28px; border-radius: 10px;
      background: linear-gradient(135deg,#1a237e,#3949ab); color: #fff;
      border: none; font-size: 14px; font-weight: 700; cursor: pointer;
      font-family: 'Inter',sans-serif;
    }

    /* Header */
    .reg-header {
      display: flex; justify-content: space-between; align-items: center;
      margin-bottom: 18px; flex-wrap: wrap; gap: 14px;
      animation: fuUp .4s ease-out;
    }
    .reg-header-left { display: flex; align-items: center; gap: 14px; }
    .reg-icon {
      width: 52px; height: 52px;
      background: linear-gradient(135deg,#1a237e,#3949ab);
      border-radius: 14px; display: flex; align-items: center; justify-content: center;
      color: #fff; box-shadow: 0 4px 14px rgba(26,35,126,.3);
    }
    .reg-header h2 { font-size: 24px; font-weight: 800; color: #1a237e; }
    .reg-header p  { font-size: 14px; color: #64748b; margin-top: 2px; }
    .back-link {
      display: flex; align-items: center; gap: 6px;
      padding: 9px 18px; border-radius: 8px;
      background: #f1f5f9; border: none; color: #475569;
      font-size: 14px; font-weight: 600; cursor: pointer;
      font-family: 'Inter',sans-serif; transition: all .2s;
    }
    .back-link:hover { background: #e2e8f0; color: #1a237e; }

    /* Access strip */
    .access-strip {
      display: flex; align-items: center; gap: 8px;
      background: #eff6ff; border: 1px solid #bfdbfe;
      border-radius: 10px; padding: 12px 18px;
      font-size: 13px; color: #1e40af; margin-bottom: 24px;
      animation: fuUp .4s ease-out .1s both;
    }
    .access-strip mat-icon { font-size: 18px; width: 18px; height: 18px; flex-shrink: 0; }
    .strip-role {
      background: #1a237e; color: #fff;
      padding: 2px 10px; border-radius: 10px;
      font-size: 12px; font-weight: 700;
    }

    /* Main grid */
    .reg-grid {
      display: grid; grid-template-columns: 340px 1fr; gap: 24px;
      align-items: start;
      animation: fuUp .5s ease-out;
    }

    /* Role panel */
    .role-panel {
      background: #fff; border-radius: 16px; padding: 24px;
      border: 1px solid #e2e8f0; box-shadow: 0 2px 8px rgba(0,0,0,.04);
      position: sticky; top: 80px;
    }
    .role-panel > h3 { font-size: 16px; font-weight: 700; color: #1a237e; margin-bottom: 4px; }
    .role-panel > p  { font-size: 13px; color: #64748b; margin-bottom: 16px; }

    .role-list { display: flex; flex-direction: column; gap: 8px; margin-bottom: 20px; }
    .role-card {
      display: flex; align-items: center; gap: 12px;
      background: #f8fafc; border: 2px solid #e2e8f0; border-radius: 12px;
      padding: 12px 14px; cursor: pointer; transition: all .2s; position: relative;
    }
    .role-card:hover { border-color: #1a237e; background: #f0f2ff; }
    .role-card.selected { border-color: #1a237e; background: linear-gradient(135deg,#f0f2ff,#e8eaf6); }
    .role-card-ico {
      width: 38px; height: 38px; border-radius: 10px;
      display: flex; align-items: center; justify-content: center;
      font-size: 20px; flex-shrink: 0;
    }
    .role-card-info { flex: 1; }
    .role-card-info strong { display: block; font-size: 13px; font-weight: 700; color: #1a1a2e; }
    .role-card-info p { font-size: 11px; color: #64748b; margin-top: 1px; }
    .role-card-check mat-icon { color: #1a237e; font-size: 20px; }

    .perm-preview {
      background: #f8fafc; border-radius: 10px; padding: 14px;
      border: 1px solid #e2e8f0;
    }
    .perm-preview h4 { font-size: 12px; font-weight: 700; color: #334155; margin-bottom: 10px; text-transform: uppercase; letter-spacing: .4px; }
    .perm-preview h4 span { color: #1a237e; }
    .perm-list { display: flex; flex-direction: column; gap: 6px; }
    .perm-item { display: flex; align-items: center; gap: 6px; font-size: 12px; color: #475569; }
    .perm-yes { font-size: 16px !important; width: 16px !important; height: 16px !important; color: #16a34a !important; flex-shrink: 0; }

    /* Form panel */
    .form-panel {
      background: #fff; border-radius: 16px; padding: 28px 32px;
      border: 1px solid #e2e8f0; box-shadow: 0 2px 8px rgba(0,0,0,.04);
    }
    .form-panel > h3 { font-size: 16px; font-weight: 700; color: #1a237e; margin-bottom: 20px; }

    /* Error / Success */
    .err-box {
      display: flex; align-items: center; gap: 8px;
      background: #fef2f2; border: 1px solid #fecaca; color: #dc2626;
      border-radius: 10px; padding: 12px 16px;
      font-size: 14px; font-weight: 500; margin-bottom: 18px;
      animation: shake .4s ease;
    }
    .success-box {
      display: flex; align-items: center; gap: 8px;
      background: #f0fdf4; border: 1px solid #bbf7d0; color: #16a34a;
      border-radius: 10px; padding: 12px 16px;
      font-size: 14px; font-weight: 500; margin-bottom: 18px;
      animation: fuUp .4s ease;
    }
    .err-box mat-icon, .success-box mat-icon { font-size: 18px; width: 18px; height: 18px; flex-shrink: 0; }

    /* Fields */
    .fields-grid { display: grid; grid-template-columns: 1fr 1fr; gap: 14px; margin-bottom: 24px; }
    .fw { display: flex; flex-direction: column; gap: 5px; }
    .fw.full { grid-column: span 2; }
    .fw > label { font-size: 12px; font-weight: 700; color: #475569; text-transform: uppercase; letter-spacing: .4px; }
    .fw > span  { font-size: 11px; color: #ef4444; }
    .fi {
      display: flex; align-items: center; gap: 8px;
      background: #f8fafc; border: 2px solid #e2e8f0; border-radius: 10px;
      padding: 0 13px; transition: all .2s;
    }
    .fi:focus-within { border-color: #1a237e; background: #fff; box-shadow: 0 0 0 4px rgba(26,35,126,.07); }
    .fi.invalid { border-color: #ef4444; }
    .fi mat-icon { color: #94a3b8; font-size: 20px; flex-shrink: 0; }
    .fi input {
      flex: 1; border: none; background: transparent; padding: 13px 0;
      font-size: 14px; color: #1a1a2e; font-family: 'Inter',sans-serif; outline: none;
    }
    .fi input::placeholder { color: #94a3b8; }
    .eye-btn {
      background: none; border: none; cursor: pointer;
      color: #94a3b8; display: flex; align-items: center; transition: color .2s;
    }
    .eye-btn:hover { color: #1a237e; }

    /* Actions */
    .form-actions {
      display: flex; justify-content: flex-end; gap: 12px;
      padding-top: 20px; border-top: 1px solid #f1f5f9;
    }
    .cancel-btn {
      display: flex; align-items: center; gap: 6px;
      padding: 11px 22px; border-radius: 10px; background: #f1f5f9;
      border: none; color: #475569; font-size: 14px; font-weight: 600;
      cursor: pointer; font-family: 'Inter',sans-serif; transition: all .2s;
    }
    .cancel-btn:hover { background: #e2e8f0; }
    .submit-btn {
      display: flex; align-items: center; gap: 8px;
      padding: 11px 28px; border-radius: 10px;
      background: linear-gradient(135deg,#1a237e,#3949ab); color: #fff;
      border: none; font-size: 14px; font-weight: 700; cursor: pointer;
      font-family: 'Inter',sans-serif; transition: all .2s;
      box-shadow: 0 4px 14px rgba(26,35,126,.3);
    }
    .submit-btn:hover:not(:disabled) { transform: translateY(-2px); box-shadow: 0 8px 20px rgba(26,35,126,.4); }
    .submit-btn:disabled { opacity: .65; cursor: not-allowed; }

    @keyframes fuUp  { from{opacity:0;transform:translateY(16px);} to{opacity:1;transform:translateY(0);} }
    @keyframes shake { 0%,100%{transform:translateX(0);} 20%,60%{transform:translateX(-5px);} 40%,80%{transform:translateX(5px);} }

    @media (max-width: 1024px) {
      .reg-grid { grid-template-columns: 1fr; }
      .role-panel { position: static; }
    }
    @media (max-width: 600px) {
      .fields-grid { grid-template-columns: 1fr; }
      .fw.full { grid-column: span 1; }
      .form-panel { padding: 20px 16px; }
    }
  `]
})
export class AdminRegisterComponent implements OnInit {
  form!: FormGroup;
  saving = false;
  showPw = false;
  errorMsg = '';
  successMsg = '';

  roles = [
    { value: 'ADMIN',           icon: '🛡️', label: 'Admin',           desc: 'Full system access', bg: 'linear-gradient(135deg,#ede9fe,#c4b5fd)',
      perms: ['Manage all products', 'Manage inventory', 'View all orders', 'Manage all users', 'Access dashboard'] },
    { value: 'MANAGER',         icon: '👔', label: 'Manager',          desc: 'Store management',    bg: 'linear-gradient(135deg,#dbeafe,#93c5fd)',
      perms: ['Manage products', 'Manage inventory', 'View all orders', 'Approve quotations', 'Access dashboard'] },
    { value: 'SALES_EXECUTIVE', icon: '💼', label: 'Sales Executive',  desc: 'Sales & orders',      bg: 'linear-gradient(135deg,#fef3c7,#fde68a)',
      perms: ['View products', 'Manage orders', 'View quotations', 'Access invoices'] },
    { value: 'CUSTOMER',        icon: '🛒', label: 'Customer',         desc: 'Regular buyer',       bg: 'linear-gradient(135deg,#e8eaf6,#c5cae9)',
      perms: ['Browse products', 'Place orders', 'Submit quotations', 'View own invoices'] },
    { value: 'CONTRACTOR',      icon: '👷', label: 'Contractor',       desc: 'Bulk orders & RFQ',   bg: 'linear-gradient(135deg,#dcfce7,#a7f3d0)',
      perms: ['Browse products', 'Place bulk orders', 'Submit & track RFQs', 'View own invoices'] }
  ];

  constructor(
    private fb: FormBuilder,
    public auth: AuthService,
    public router: Router
  ) {}

  ngOnInit(): void { this.initForm(); }

  initForm(): void {
    this.form = this.fb.group({
      name:        ['', Validators.required],
      email:       ['', [Validators.required, Validators.email]],
      password:    ['', [Validators.required, Validators.minLength(6)]],
      phone:       [''],
      role:        ['ADMIN'],
      companyName: ['Godavari Building Materials'],
      gstNumber:   [''],
      city:        ['Nashik'],
      state:       ['Maharashtra']
    });
  }

  get selectedRole() {
    return this.roles.find(r => r.value === this.form.value.role);
  }

  touched(field: string): boolean {
    const c = this.form.get(field);
    return !!(c && c.invalid && c.touched);
  }

  resetForm(): void {
    this.initForm();
    this.errorMsg = '';
    this.successMsg = '';
  }

  onSubmit(): void {
    this.form.markAllAsTouched();
    this.errorMsg = '';
    this.successMsg = '';

    if (this.form.invalid) {
      this.errorMsg = 'Please fill all required fields correctly.';
      return;
    }

    this.saving = true;
    this.auth.registerStaff(this.form.value).subscribe({
      next: (res: any) => {
        this.saving = false;
        this.successMsg = `✅ Account created! "${res.name}" registered as ${res.role}.`;
        this.initForm();
      },
      error: err => {
        this.saving = false;
        this.errorMsg = err.error?.error || err.error?.message || 'Failed to create account. Email may already be in use.';
      }
    });
  }
}
