import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { HttpClient } from '@angular/common/http';
import { MatSnackBar } from '@angular/material/snack-bar';
import { AuthService } from '../../services/auth.service';
import { environment } from '../../../environments/environment';

@Component({
  selector: 'app-user-management',
  template: `
    <div class="page-container">

      <!-- Header -->
      <div class="um-header">
        <div class="um-header-left">
          <div class="um-icon">👥</div>
          <div>
            <h2>User Management</h2>
            <p>Manage staff accounts and register new users</p>
          </div>
        </div>
        <button class="reg-btn" (click)="showForm = true" *ngIf="!showForm">
          <mat-icon>person_add</mat-icon> Add New User
        </button>
      </div>

      <!-- Register New User Form -->
      <div class="form-card" *ngIf="showForm">
        <div class="form-card-header">
          <h3><mat-icon>person_add</mat-icon> Register New User</h3>
          <button class="close-btn" (click)="cancelForm()">
            <mat-icon>close</mat-icon>
          </button>
        </div>

        <!-- Inline error -->
        <div *ngIf="formError" class="err-box">
          <mat-icon>error_outline</mat-icon> {{ formError }}
        </div>

        <!-- Success -->
        <div *ngIf="formSuccess" class="success-box">
          <mat-icon>check_circle</mat-icon> {{ formSuccess }}
        </div>

        <form [formGroup]="userForm" (ngSubmit)="registerUser()">
          <div class="fg">

            <!-- Role selector - prominent at top -->
            <div class="full-col">
              <label class="field-label">Account Role</label>
              <div class="role-grid">
                <div *ngFor="let r of roles"
                  class="role-opt"
                  [class.selected]="userForm.value.role === r.value"
                  (click)="userForm.patchValue({role: r.value})">
                  <span class="role-ico">{{ r.icon }}</span>
                  <div>
                    <strong>{{ r.label }}</strong>
                    <p>{{ r.desc }}</p>
                  </div>
                  <div class="role-check" *ngIf="userForm.value.role === r.value">✓</div>
                </div>
              </div>
            </div>

            <!-- Name & Phone -->
            <div class="field-wrap">
              <label class="field-label">Full Name *</label>
              <div class="finput" [class.err]="isInvalid('name')">
                <mat-icon>person</mat-icon>
                <input formControlName="name" placeholder="e.g. Rajesh Patil">
              </div>
              <span class="err-text" *ngIf="isInvalid('name')">Name is required</span>
            </div>

            <div class="field-wrap">
              <label class="field-label">Phone Number</label>
              <div class="finput">
                <mat-icon>phone</mat-icon>
                <input formControlName="phone" placeholder="9876543210">
              </div>
            </div>

            <!-- Email -->
            <div class="field-wrap full-col">
              <label class="field-label">Email Address *</label>
              <div class="finput" [class.err]="isInvalid('email')">
                <mat-icon>alternate_email</mat-icon>
                <input formControlName="email" type="email" placeholder="user@godavari.com">
              </div>
              <span class="err-text" *ngIf="isInvalid('email')">Valid email is required</span>
            </div>

            <!-- Password -->
            <div class="field-wrap">
              <label class="field-label">Password *</label>
              <div class="finput" [class.err]="isInvalid('password')">
                <mat-icon>lock_outline</mat-icon>
                <input formControlName="password"
                  [type]="showPw ? 'text' : 'password'"
                  placeholder="Min 6 characters">
                <button type="button" class="eye-btn" (click)="showPw = !showPw">
                  <mat-icon>{{ showPw ? 'visibility_off' : 'visibility' }}</mat-icon>
                </button>
              </div>
              <span class="err-text" *ngIf="isInvalid('password')">Min 6 characters required</span>
            </div>

            <!-- Company -->
            <div class="field-wrap">
              <label class="field-label">Company Name</label>
              <div class="finput">
                <mat-icon>business</mat-icon>
                <input formControlName="companyName" placeholder="Company / Store name">
              </div>
            </div>

            <!-- City & State -->
            <div class="field-wrap">
              <label class="field-label">City</label>
              <div class="finput">
                <mat-icon>location_city</mat-icon>
                <input formControlName="city" placeholder="Nashik">
              </div>
            </div>

            <div class="field-wrap">
              <label class="field-label">State</label>
              <div class="finput">
                <mat-icon>map</mat-icon>
                <input formControlName="state" placeholder="Maharashtra">
              </div>
            </div>

          </div>

          <div class="form-actions">
            <button type="button" class="cancel-btn" (click)="cancelForm()">Cancel</button>
            <button type="submit" class="submit-btn" [disabled]="saving">
              <mat-spinner *ngIf="saving" diameter="18"></mat-spinner>
              <mat-icon *ngIf="!saving">person_add</mat-icon>
              {{ saving ? 'Creating...' : 'Create User' }}
            </button>
          </div>
        </form>
      </div>

      <!-- Users Table -->
      <div class="users-card">
        <div class="users-card-header">
          <h3><mat-icon>people</mat-icon> All Users ({{ users.length }})</h3>
          <button class="refresh-btn" (click)="loadUsers()">
            <mat-icon>refresh</mat-icon>
          </button>
        </div>

        <!-- Filter chips -->
        <div class="filter-chips">
          <button *ngFor="let f of filters"
            class="fchip" [class.active]="activeFilter === f.value"
            (click)="activeFilter = f.value">
            {{ f.icon }} {{ f.label }}
          </button>
        </div>

        <div *ngIf="loading" style="text-align:center;padding:48px;">
          <mat-spinner diameter="36" style="margin:0 auto;"></mat-spinner>
        </div>

        <div *ngIf="!loading" class="users-table-wrap">
          <table class="users-table">
            <thead>
              <tr>
                <th>User</th>
                <th>Role</th>
                <th>Phone</th>
                <th>City</th>
                <th>Status</th>
                <th>Joined</th>
              </tr>
            </thead>
            <tbody>
              <tr *ngFor="let u of filteredUsers; let i=index" [style.animation-delay.ms]="i*40">
                <td>
                  <div class="user-cell">
                    <div class="uavatar" [style.background]="getAvatarColor(u.role)">
                      {{ u.name?.charAt(0)?.toUpperCase() }}
                    </div>
                    <div>
                      <strong>{{ u.name }}</strong>
                      <span>{{ u.email }}</span>
                    </div>
                  </div>
                </td>
                <td>
                  <span class="rbadge" [ngClass]="roleClass(u.role)">
                    {{ roleIcon(u.role) }} {{ u.role }}
                  </span>
                </td>
                <td>{{ u.phone || '—' }}</td>
                <td>{{ u.city || '—' }}</td>
                <td>
                  <span class="sbadge" [class.active]="u.active" [class.inactive]="!u.active">
                    {{ u.active ? '● Active' : '● Inactive' }}
                  </span>
                </td>
                <td>{{ u.createdAt | date:'dd MMM yyyy' }}</td>
              </tr>
              <tr *ngIf="filteredUsers.length === 0">
                <td colspan="6" style="text-align:center;padding:32px;color:#64748b;">
                  No users found for this filter
                </td>
              </tr>
            </tbody>
          </table>
        </div>
      </div>

    </div>
  `,
  styles: [`
    /* Header */
    .um-header {
      display: flex; justify-content: space-between; align-items: center;
      margin-bottom: 24px; flex-wrap: wrap; gap: 16px;
      animation: fuUp .4s ease-out;
    }
    .um-header-left { display: flex; align-items: center; gap: 14px; }
    .um-icon {
      font-size: 28px; width: 54px; height: 54px;
      background: linear-gradient(135deg,#1a237e,#3949ab);
      border-radius: 14px; display: flex; align-items: center; justify-content: center;
      box-shadow: 0 4px 14px rgba(26,35,126,.3);
    }
    .um-header h2 { font-size: 24px; font-weight: 800; color: #1a237e; }
    .um-header p  { font-size: 14px; color: #64748b; margin-top: 2px; }
    .reg-btn {
      display: flex; align-items: center; gap: 8px;
      padding: 11px 24px; border-radius: 10px;
      background: linear-gradient(135deg,#1a237e,#3949ab); color: #fff;
      border: none; font-size: 14px; font-weight: 700; cursor: pointer;
      font-family: 'Inter',sans-serif;
      box-shadow: 0 4px 14px rgba(26,35,126,.3); transition: all .2s;
    }
    .reg-btn:hover { transform: translateY(-2px); box-shadow: 0 8px 20px rgba(26,35,126,.4); }

    /* Form card */
    .form-card {
      background: #fff; border-radius: 16px; padding: 28px 32px;
      margin-bottom: 24px; border: 1px solid #e2e8f0;
      box-shadow: 0 4px 16px rgba(0,0,0,.05);
      animation: fuUp .4s ease-out;
    }
    .form-card-header {
      display: flex; justify-content: space-between; align-items: center;
      margin-bottom: 24px; padding-bottom: 16px; border-bottom: 2px solid #f1f5f9;
    }
    .form-card-header h3 {
      display: flex; align-items: center; gap: 8px;
      font-size: 18px; font-weight: 700; color: #1a237e;
    }
    .close-btn {
      width: 36px; height: 36px; border-radius: 8px;
      background: #f1f5f9; border: none; cursor: pointer;
      display: flex; align-items: center; justify-content: center;
      color: #64748b; transition: all .2s;
    }
    .close-btn:hover { background: #e2e8f0; color: #1a237e; }

    /* Error / Success */
    .err-box {
      display: flex; align-items: center; gap: 8px;
      background: #fef2f2; border: 1px solid #fecaca; color: #dc2626;
      border-radius: 10px; padding: 12px 16px;
      font-size: 14px; font-weight: 500; margin-bottom: 20px;
      animation: shake .4s ease;
    }
    .success-box {
      display: flex; align-items: center; gap: 8px;
      background: #f0fdf4; border: 1px solid #bbf7d0; color: #16a34a;
      border-radius: 10px; padding: 12px 16px;
      font-size: 14px; font-weight: 500; margin-bottom: 20px;
      animation: fuUp .4s ease;
    }
    .err-box mat-icon, .success-box mat-icon { font-size: 18px; width: 18px; height: 18px; flex-shrink: 0; }

    /* Form grid */
    .fg {
      display: grid; grid-template-columns: 1fr 1fr; gap: 16px; margin-bottom: 24px;
    }
    .full-col { grid-column: span 2; }
    .field-wrap { display: flex; flex-direction: column; gap: 5px; }
    .field-label { font-size: 12px; font-weight: 700; color: #475569; text-transform: uppercase; letter-spacing: .4px; }
    .finput {
      display: flex; align-items: center; gap: 10px;
      background: #f8fafc; border: 2px solid #e2e8f0; border-radius: 10px;
      padding: 0 14px; transition: all .2s;
    }
    .finput:focus-within { border-color: #1a237e; background: #fff; box-shadow: 0 0 0 4px rgba(26,35,126,.07); }
    .finput.err { border-color: #ef4444; }
    .finput mat-icon { color: #94a3b8; font-size: 20px; flex-shrink: 0; }
    .finput input {
      flex: 1; border: none; background: transparent; padding: 13px 0;
      font-size: 14px; font-family: 'Inter',sans-serif; color: #1a1a2e; outline: none;
    }
    .finput input::placeholder { color: #94a3b8; }
    .err-text { font-size: 12px; color: #ef4444; margin-top: 2px; }
    .eye-btn {
      background: none; border: none; cursor: pointer; color: #94a3b8;
      display: flex; align-items: center; transition: color .2s;
    }
    .eye-btn:hover { color: #1a237e; }

    /* Role selector */
    .role-grid {
      display: grid; grid-template-columns: repeat(auto-fill, minmax(200px, 1fr)); gap: 10px;
    }
    .role-opt {
      display: flex; align-items: center; gap: 12px;
      background: #f8fafc; border: 2px solid #e2e8f0; border-radius: 12px;
      padding: 12px 14px; cursor: pointer; transition: all .2s; position: relative;
    }
    .role-opt:hover { border-color: #1a237e; background: #f0f2ff; }
    .role-opt.selected { border-color: #1a237e; background: linear-gradient(135deg,#f0f2ff,#e8eaf6); }
    .role-ico { font-size: 24px; flex-shrink: 0; }
    .role-opt strong { display: block; font-size: 13px; font-weight: 700; color: #1a1a2e; }
    .role-opt p { font-size: 11px; color: #64748b; margin-top: 1px; }
    .role-check {
      position: absolute; top: 8px; right: 10px;
      width: 20px; height: 20px; border-radius: 50%;
      background: #1a237e; color: #fff;
      font-size: 12px; display: flex; align-items: center; justify-content: center;
      font-weight: 700;
    }

    /* Form actions */
    .form-actions { display: flex; justify-content: flex-end; gap: 12px; padding-top: 8px; border-top: 1px solid #f1f5f9; }
    .cancel-btn {
      padding: 11px 24px; border-radius: 10px; background: #f1f5f9;
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

    /* Users table card */
    .users-card {
      background: #fff; border-radius: 16px; overflow: hidden;
      border: 1px solid #e2e8f0; box-shadow: 0 2px 10px rgba(0,0,0,.04);
      animation: fuUp .5s ease-out;
    }
    .users-card-header {
      display: flex; justify-content: space-between; align-items: center;
      padding: 20px 24px; border-bottom: 1px solid #f1f5f9;
    }
    .users-card-header h3 {
      display: flex; align-items: center; gap: 8px;
      font-size: 17px; font-weight: 700; color: #1a237e;
    }
    .refresh-btn {
      width: 36px; height: 36px; border-radius: 8px;
      background: #f1f5f9; border: none; cursor: pointer;
      display: flex; align-items: center; justify-content: center;
      color: #64748b; transition: all .2s;
    }
    .refresh-btn:hover { background: #e2e8f0; transform: rotate(90deg); }

    /* Filter chips */
    .filter-chips {
      display: flex; gap: 8px; padding: 14px 24px;
      border-bottom: 1px solid #f1f5f9; flex-wrap: wrap;
    }
    .fchip {
      padding: 6px 16px; border-radius: 20px; border: 2px solid #e2e8f0;
      background: #fff; color: #475569; font-size: 13px; font-weight: 600;
      cursor: pointer; transition: all .2s; font-family: 'Inter',sans-serif;
    }
    .fchip:hover { border-color: #1a237e; color: #1a237e; }
    .fchip.active { background: #1a237e; color: #fff; border-color: #1a237e; }

    /* Table */
    .users-table-wrap { overflow-x: auto; }
    .users-table {
      width: 100%; border-collapse: collapse; font-size: 14px;
    }
    .users-table thead tr { background: linear-gradient(135deg,#f8fafc,#f1f5f9); }
    .users-table th {
      padding: 14px 20px; text-align: left; font-size: 12px; font-weight: 700;
      color: #334155; text-transform: uppercase; letter-spacing: .5px;
      border-bottom: 2px solid #e2e8f0;
    }
    .users-table td {
      padding: 14px 20px; border-bottom: 1px solid #f8fafc; color: #475569;
      animation: fuUp .3s ease-out both;
    }
    .users-table tr:hover td { background: #f8fafc; }
    .users-table tr:last-child td { border-bottom: none; }

    .user-cell { display: flex; align-items: center; gap: 12px; }
    .uavatar {
      width: 38px; height: 38px; border-radius: 10px;
      color: #fff; font-size: 16px; font-weight: 700;
      display: flex; align-items: center; justify-content: center; flex-shrink: 0;
    }
    .user-cell strong { display: block; font-size: 14px; font-weight: 600; color: #1a1a2e; }
    .user-cell span  { font-size: 12px; color: #64748b; }

    .rbadge {
      display: inline-flex; align-items: center; gap: 4px;
      padding: 4px 12px; border-radius: 20px;
      font-size: 12px; font-weight: 700; text-transform: uppercase; letter-spacing: .3px;
    }
    .r-admin    { background: #ede9fe; color: #6d28d9; }
    .r-manager  { background: #dbeafe; color: #1d4ed8; }
    .r-sales    { background: #fef3c7; color: #b45309; }
    .r-customer { background: #e8eaf6; color: #1a237e; }
    .r-contractor { background: #dcfce7; color: #15803d; }

    .sbadge { font-size: 13px; font-weight: 600; }
    .sbadge.active   { color: #16a34a; }
    .sbadge.inactive { color: #dc2626; }

    @keyframes fuUp { from{opacity:0;transform:translateY(16px);} to{opacity:1;transform:translateY(0);} }
    @keyframes shake { 0%,100%{transform:translateX(0);} 20%,60%{transform:translateX(-5px);} 40%,80%{transform:translateX(5px);} }

    @media (max-width: 768px) {
      .fg { grid-template-columns: 1fr; }
      .full-col { grid-column: span 1; }
      .role-grid { grid-template-columns: 1fr 1fr; }
    }
    @media (max-width: 480px) {
      .role-grid { grid-template-columns: 1fr; }
      .form-card { padding: 20px 16px; }
    }
  `]
})
export class UserManagementComponent implements OnInit {
  users: any[] = [];
  loading = false;
  saving = false;
  showForm = false;
  showPw = false;
  formError = '';
  formSuccess = '';
  activeFilter = 'ALL';

  userForm!: FormGroup;

  roles = [
    { value: 'ADMIN',           icon: '🛡️', label: 'Admin',           desc: 'Full system access' },
    { value: 'MANAGER',         icon: '👔', label: 'Manager',         desc: 'Store management' },
    { value: 'SALES_EXECUTIVE', icon: '💼', label: 'Sales Executive',  desc: 'Sales & orders' },
    { value: 'CUSTOMER',        icon: '🛒', label: 'Customer',         desc: 'Regular buyer' },
    { value: 'CONTRACTOR',      icon: '👷', label: 'Contractor',       desc: 'Bulk orders & RFQ' }
  ];

  filters = [
    { value: 'ALL',           icon: '👥', label: 'All' },
    { value: 'ADMIN',         icon: '🛡️', label: 'Admins' },
    { value: 'MANAGER',       icon: '👔', label: 'Managers' },
    { value: 'SALES_EXECUTIVE', icon: '💼', label: 'Sales' },
    { value: 'CUSTOMER',      icon: '🛒', label: 'Customers' },
    { value: 'CONTRACTOR',    icon: '👷', label: 'Contractors' }
  ];

  constructor(
    private fb: FormBuilder,
    private auth: AuthService,
    private http: HttpClient,
    private snack: MatSnackBar
  ) {}

  ngOnInit(): void {
    this.initForm();
    this.loadUsers();
  }

  initForm(): void {
    this.userForm = this.fb.group({
      name:        ['', Validators.required],
      email:       ['', [Validators.required, Validators.email]],
      password:    ['', [Validators.required, Validators.minLength(6)]],
      phone:       [''],
      role:        ['ADMIN'],
      companyName: ['Godavari Building Materials'],
      city:        ['Nashik'],
      state:       ['Maharashtra']
    });
  }

  loadUsers(): void {
    this.loading = true;
    this.http.get<any[]>(`${environment.apiUrl}/users`).subscribe({
      next: data => { this.users = data; this.loading = false; },
      error: () => {
        this.snack.open('Failed to load users', 'Close', { duration: 3000 });
        this.loading = false;
      }
    });
  }

  get filteredUsers(): any[] {
    if (this.activeFilter === 'ALL') return this.users;
    return this.users.filter(u => u.role === this.activeFilter);
  }

  isInvalid(field: string): boolean {
    const c = this.userForm.get(field);
    return !!(c && c.invalid && c.touched);
  }

  registerUser(): void {
    this.userForm.markAllAsTouched();
    this.formError = '';
    this.formSuccess = '';

    if (this.userForm.invalid) {
      this.formError = 'Please fill all required fields correctly.';
      return;
    }

    this.saving = true;
    this.auth.registerStaff(this.userForm.value).subscribe({
      next: (res: any) => {
        this.formSuccess = `✅ User "${res.name}" (${res.role}) created successfully!`;
        this.saving = false;
        this.initForm();
        this.loadUsers();
        // Auto-hide success after 5s
        setTimeout(() => { this.formSuccess = ''; this.showForm = false; }, 4000);
      },
      error: err => {
        this.formError = err.error?.error || err.error?.message || 'Failed to create user. Email may already exist.';
        this.saving = false;
      }
    });
  }

  cancelForm(): void {
    this.showForm = false;
    this.formError = '';
    this.formSuccess = '';
    this.initForm();
  }

  roleClass(role: string): string {
    const m: Record<string,string> = {
      ADMIN:'r-admin', MANAGER:'r-manager',
      SALES_EXECUTIVE:'r-sales', CUSTOMER:'r-customer', CONTRACTOR:'r-contractor'
    };
    return m[role] || 'r-customer';
  }

  roleIcon(role: string): string {
    const m: Record<string,string> = {
      ADMIN:'🛡️', MANAGER:'👔', SALES_EXECUTIVE:'💼', CUSTOMER:'🛒', CONTRACTOR:'👷'
    };
    return m[role] || '👤';
  }

  getAvatarColor(role: string): string {
    const m: Record<string,string> = {
      ADMIN:'linear-gradient(135deg,#6d28d9,#8b5cf6)',
      MANAGER:'linear-gradient(135deg,#1d4ed8,#3b82f6)',
      SALES_EXECUTIVE:'linear-gradient(135deg,#b45309,#f59e0b)',
      CUSTOMER:'linear-gradient(135deg,#1a237e,#3949ab)',
      CONTRACTOR:'linear-gradient(135deg,#15803d,#22c55e)'
    };
    return m[role] || 'linear-gradient(135deg,#1a237e,#3949ab)';
  }
}
