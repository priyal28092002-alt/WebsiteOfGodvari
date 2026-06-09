import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, FormArray, Validators } from '@angular/forms';
import { MatSnackBar } from '@angular/material/snack-bar';
import { QuotationService } from '../../services/quotation.service';
import { ProductService } from '../../services/product.service';
import { AuthService } from '../../services/auth.service';

@Component({
  selector: 'app-quotation',
  template: `
    <div class="page-container">

      <!-- Header -->
      <div class="qhdr">
        <div class="qhdr-left">
          <div class="qhdr-icon"><mat-icon>request_quote</mat-icon></div>
          <div>
            <h2>Quotations & RFQ</h2>
            <p>Request bulk pricing and manage quotations</p>
          </div>
        </div>
      </div>

      <mat-tab-group animationDuration="250ms">

        <!-- ── TAB 1: NEW RFQ ── -->
        <mat-tab>
          <ng-template mat-tab-label>
            <mat-icon>add_circle_outline</mat-icon>&nbsp;New RFQ
          </ng-template>

          <div class="tab-body">
            <div class="rfq-wrap">
              <div class="rfq-top">
                <div class="rfq-top-icon">
                  <mat-icon>request_quote</mat-icon>
                </div>
                <div>
                  <h3>Submit Bulk Order Request</h3>
                  <p>Get competitive pricing for large quantity orders</p>
                </div>
              </div>

              <form [formGroup]="rfqForm" (ngSubmit)="submitRFQ()">
                <div class="fgrid2">
                  <div class="ffield">
                    <label>Company Name</label>
                    <div class="finput">
                      <mat-icon>business</mat-icon>
                      <input formControlName="companyName" placeholder="Your company name">
                    </div>
                  </div>
                  <div class="ffield">
                    <label>WhatsApp / Mobile No <span class="wa-hint">📲 Updates sent here</span></label>
                    <div class="finput" [class.wa-filled]="rfqForm.get('userPhone')?.value">
                      <mat-icon style="color:#25d366">whatsapp</mat-icon>
                      <input formControlName="userPhone" placeholder="e.g. 9876543210" maxlength="13">
                    </div>
                  </div>
                  <div class="ffield">
                    <label>Notes / Requirements</label>
                    <div class="finput">
                      <mat-icon>note_alt</mat-icon>
                      <input formControlName="notes" placeholder="Any special requirements">
                    </div>
                  </div>
                </div>

                <h4 class="items-label"><mat-icon>list_alt</mat-icon> Items Required</h4>

                <div formArrayName="items">
                  <div *ngFor="let ctrl of itemsArr.controls; let i=index"
                    [formGroupName]="i" class="item-row">
                    <div class="ffield item-prod">
                      <label>Product</label>
                      <div class="finput sel">
                        <mat-icon>storefront</mat-icon>
                        <select formControlName="productId" (change)="onProdChange(i, $event)">
                          <option value="">Select product...</option>
                          <option *ngFor="let p of products" [value]="p.id">{{ p.name }}</option>
                        </select>
                      </div>
                    </div>
                    <div class="ffield item-qty">
                      <label>Quantity</label>
                      <div class="finput">
                        <mat-icon>numbers</mat-icon>
                        <input type="number" formControlName="quantity" min="1" placeholder="1">
                      </div>
                    </div>
                    <div class="ffield item-unit">
                      <label>Unit</label>
                      <div class="finput">
                        <input formControlName="unit" readonly placeholder="—">
                      </div>
                    </div>
                    <button type="button" class="rm-btn" (click)="removeItem(i)"
                      *ngIf="itemsArr.length > 1" title="Remove">
                      <mat-icon>close</mat-icon>
                    </button>
                  </div>
                </div>

                <button type="button" class="add-item-btn" (click)="addItem()">
                  <mat-icon>add</mat-icon> Add Another Item
                </button>

                <div class="form-foot">
                  <button type="submit" class="submit-btn" [disabled]="submitting">
                    <mat-spinner *ngIf="submitting" diameter="18"></mat-spinner>
                    <mat-icon *ngIf="!submitting">send</mat-icon>
                    {{ submitting ? 'Submitting...' : 'Submit RFQ' }}
                  </button>
                </div>
              </form>
            </div>
          </div>
        </mat-tab>

        <!-- ── TAB 2: MY QUOTATIONS ── -->
        <mat-tab>
          <ng-template mat-tab-label>
            <mat-icon>history</mat-icon>&nbsp;My Quotations
          </ng-template>

          <div class="tab-body">
            <div *ngIf="loadingMy" class="tab-loading">
              <mat-spinner diameter="36"></mat-spinner>
            </div>
            <div *ngIf="!loadingMy && myQuotes.length === 0" class="tab-empty">
              <mat-icon>inbox</mat-icon>
              <p>No quotations submitted yet</p>
              <span>Submit an RFQ from the first tab</span>
            </div>
            <div *ngFor="let q of myQuotes; let i=index"
              class="qcard" [style.animation-delay.ms]="i*70">
              <div class="qcard-top">
                <div class="qcard-left">
                  <div class="qnum">{{ q.quotationNumber }}</div>
                  <div class="qmeta">
                    <mat-icon>calendar_today</mat-icon>
                    {{ q.createdAt | date:'dd MMM yyyy' }}
                    <span *ngIf="q.companyName"> &bull; {{ q.companyName }}</span>
                    <span *ngIf="q.userPhone"> &bull; 📲 {{ q.userPhone }}</span>
                  </div>
                  <p *ngIf="q.notes" class="qnotes">📝 {{ q.notes }}</p>
                </div>
                <div class="qcard-right">
                  <span class="qbadge" [ngClass]="statusClass(q.status)">
                    {{ statusEmoji(q.status) }} {{ q.status }}
                  </span>
                  <div *ngIf="q.totalAmount" class="qamt">₹{{ q.totalAmount | number:'1.2-2' }}</div>
                  <div *ngIf="q.adminRemarks" class="qremarks">💬 {{ q.adminRemarks }}</div>
                </div>
              </div>
              <div class="qitems" *ngIf="q.items?.length">
                <span *ngFor="let it of q.items" class="qchip">
                  {{ it.productName }} × {{ it.quantity }} {{ it.unit }}
                </span>
              </div>
            </div>
          </div>
        </mat-tab>

        <!-- ── TAB 3: ADMIN - ALL RFQs ── -->
        <mat-tab *ngIf="auth.isManager">
          <ng-template mat-tab-label>
            <mat-icon>admin_panel_settings</mat-icon>&nbsp;All RFQs
          </ng-template>

          <div class="tab-body">
            <div *ngIf="loadingAll" class="tab-loading">
              <mat-spinner diameter="36"></mat-spinner>
            </div>
            <div *ngIf="!loadingAll && allQuotes.length === 0" class="tab-empty">
              <mat-icon>inbox</mat-icon>
              <p>No quotations found</p>
            </div>
            <div *ngFor="let q of allQuotes; let i=index"
              class="qcard" [style.animation-delay.ms]="i*70">
              <div class="qcard-top">
                <div class="qcard-left">
                  <div class="qnum">{{ q.quotationNumber }}</div>
                  <div class="qmeta">
                    <mat-icon>person</mat-icon> {{ q.userName }}
                    &bull; {{ q.createdAt | date:'dd MMM yyyy' }}
                    <span *ngIf="q.companyName"> &bull; {{ q.companyName }}</span>
                    <span *ngIf="q.userPhone"> &bull; 📲 {{ q.userPhone }}</span>
                  </div>
                </div>
                <span class="qbadge" [ngClass]="statusClass(q.status)">
                  {{ statusEmoji(q.status) }} {{ q.status }}
                </span>
              </div>

              <div class="qitems" *ngIf="q.items?.length">
                <span *ngFor="let it of q.items" class="qchip">
                  {{ it.productName }} × {{ it.quantity }} {{ it.unit }}
                </span>
              </div>

              <!-- Admin remark input -->
              <div class="admin-row">
                <div class="finput remark-input">
                  <mat-icon>comment</mat-icon>
                  <input [(ngModel)]="remarks[q.id]" placeholder="Add admin remarks (optional)">
                </div>
                <div class="admin-btns">
                  <button class="abtn approve" (click)="changeStatus(q.id,'APPROVED')">✅ Approve</button>
                  <button class="abtn reject"  (click)="changeStatus(q.id,'REJECTED')">❌ Reject</button>
                  <button class="abtn review"  (click)="changeStatus(q.id,'REVIEWED')">🔍 Reviewed</button>
                </div>
              </div>
            </div>
          </div>
        </mat-tab>

      </mat-tab-group>
    </div>
  `,
  styles: [`
    .qhdr {
      display: flex; align-items: center; gap: 16px;
      margin-bottom: 24px;
      animation: fadeUp .4s ease-out;
    }
    .qhdr-left { display: flex; align-items: center; gap: 14px; }
    .qhdr-icon {
      font-size: 32px; width: 54px; height: 54px;
      background: linear-gradient(135deg,#1a237e,#3949ab);
      border-radius: 14px; display: flex; align-items: center; justify-content: center;
      box-shadow: 0 4px 14px rgba(26,35,126,.3);
    }
    .qhdr h2 { font-size: 24px; font-weight: 800; color: #1a237e; }
    .qhdr p  { font-size: 14px; color: #64748b; margin-top: 2px; }

    .tab-body { padding: 28px 0; animation: fadeUp .4s ease-out; }

    /* RFQ form */
    .rfq-wrap {
      background: #fff; border-radius: 16px; padding: 28px 32px;
      max-width: 860px; border: 1px solid #e2e8f0;
      box-shadow: 0 2px 12px rgba(0,0,0,.04);
    }
    .rfq-top {
      display: flex; align-items: center; gap: 14px;
      padding-bottom: 20px; margin-bottom: 24px;
      border-bottom: 2px solid #f1f5f9;
    }
    .rfq-top-icon {
      width: 48px; height: 48px; border-radius: 12px;
      background: linear-gradient(135deg,#1a237e,#3949ab);
      display: flex; align-items: center; justify-content: center; color: #fff;
    }
    .rfq-top h3 { font-size: 17px; font-weight: 700; color: #1a237e; }
    .rfq-top p  { font-size: 13px; color: #64748b; margin-top: 2px; }

    .fgrid2 {
      display: grid; grid-template-columns: 1fr 1fr 1fr; gap: 16px; margin-bottom: 24px;
    }
    .ffield { display: flex; flex-direction: column; gap: 6px; }
    .ffield label { font-size: 12px; font-weight: 700; color: #475569; text-transform: uppercase; letter-spacing: .4px; }
    .finput {
      display: flex; align-items: center; gap: 10px;
      background: #f8fafc; border: 2px solid #e2e8f0; border-radius: 10px;
      padding: 0 14px; transition: all .2s;
    }
    .finput:focus-within { border-color: #1a237e; background: #fff; box-shadow: 0 0 0 4px rgba(26,35,126,.07); }
    .finput mat-icon { color: #94a3b8; font-size: 20px; flex-shrink: 0; }
    .finput input, .finput select {
      flex: 1; border: none; background: transparent; padding: 13px 0;
      font-size: 14px; font-family: 'Inter',sans-serif; color: #1a1a2e; outline: none;
    }
    .finput.sel select { cursor: pointer; }
    .wa-hint { color:#25d366; font-size:10px; font-weight:600; margin-left:6px; }
    .wa-filled { border-color:#25d366 !important; }

    .items-label {
      display: flex; align-items: center; gap: 8px;
      font-size: 14px; font-weight: 700; color: #334155;
      margin-bottom: 14px;
    }
    .items-label mat-icon { color: #1a237e; }

    .item-row {
      display: grid; grid-template-columns: 2.5fr 1fr 1fr auto;
      gap: 12px; align-items: end; margin-bottom: 10px;
      animation: fadeUp .3s ease-out both;
    }
    .rm-btn {
      width: 44px; height: 44px; border-radius: 10px;
      background: #fef2f2; border: 1px solid #fecaca; color: #ef4444;
      cursor: pointer; display: flex; align-items: center; justify-content: center;
      transition: all .2s; margin-bottom: 0;
    }
    .rm-btn:hover { background: #fee2e2; }

    .add-item-btn {
      display: inline-flex; align-items: center; gap: 6px;
      margin: 12px 0 24px; padding: 10px 20px;
      border: 2px dashed #cbd5e1; border-radius: 10px;
      background: none; color: #64748b; font-size: 14px;
      font-weight: 500; cursor: pointer; transition: all .2s;
      font-family: 'Inter',sans-serif;
    }
    .add-item-btn:hover { border-color: #1a237e; color: #1a237e; }

    .form-foot { display: flex; justify-content: flex-end; padding-top: 8px; }
    .submit-btn {
      display: flex; align-items: center; gap: 8px;
      padding: 13px 32px; border-radius: 10px;
      background: linear-gradient(135deg,#1a237e,#3949ab); color: #fff;
      border: none; font-size: 15px; font-weight: 700; cursor: pointer;
      font-family: 'Inter',sans-serif; transition: all .2s;
      box-shadow: 0 6px 16px rgba(26,35,126,.3);
    }
    .submit-btn:hover:not(:disabled) { transform: translateY(-2px); box-shadow: 0 10px 24px rgba(26,35,126,.4); }
    .submit-btn:disabled { opacity: .65; cursor: not-allowed; }

    /* Loading / empty */
    .tab-loading { text-align: center; padding: 60px; }
    .tab-empty { text-align: center; padding: 60px 20px; color: #64748b; }
    .tab-empty mat-icon { font-size: 56px; width: 56px; height: 56px; color: #e2e8f0; margin-bottom: 12px; }
    .tab-empty p { font-size: 16px; font-weight: 600; margin-bottom: 4px; }
    .tab-empty span { font-size: 13px; color: #94a3b8; }

    /* Quotation cards */
    .qcard {
      background: #fff; border-radius: 14px; padding: 20px 24px;
      margin-bottom: 14px; border: 1px solid #e2e8f0;
      box-shadow: 0 2px 6px rgba(0,0,0,.03);
      animation: fadeUp .4s ease-out both; transition: all .2s;
    }
    .qcard:hover { box-shadow: 0 6px 18px rgba(0,0,0,.07); }
    .qcard-top { display: flex; justify-content: space-between; align-items: flex-start; flex-wrap: wrap; gap: 12px; }
    .qnum { font-size: 16px; font-weight: 700; color: #1a237e; margin-bottom: 5px; }
    .qmeta {
      display: flex; align-items: center; gap: 5px;
      font-size: 13px; color: #64748b;
    }
    .qmeta mat-icon { font-size: 14px; width: 14px; height: 14px; }
    .qnotes { font-size: 12px; color: #94a3b8; margin-top: 4px; }
    .qcard-right { text-align: right; }
    .qamt { font-size: 20px; font-weight: 800; color: #1a237e; margin-top: 6px; }
    .qremarks { font-size: 12px; color: #64748b; margin-top: 4px; }

    .qbadge {
      display: inline-flex; align-items: center; gap: 4px;
      padding: 5px 14px; border-radius: 20px;
      font-size: 12px; font-weight: 700; text-transform: uppercase; letter-spacing: .4px;
    }
    .s-draft    { background: #f1f5f9; color: #475569; }
    .s-submitted{ background: #e0f2fe; color: #0369a1; }
    .s-reviewed { background: #fef9c3; color: #92400e; }
    .s-approved { background: #dcfce7; color: #166534; }
    .s-rejected { background: #fee2e2; color: #991b1b; }
    .s-expired  { background: #f1f5f9; color: #64748b; }

    .qitems {
      display: flex; gap: 8px; flex-wrap: wrap;
      margin-top: 14px; padding-top: 14px; border-top: 1px solid #f1f5f9;
    }
    .qchip {
      background: #f8fafc; border: 1px solid #e2e8f0; border-radius: 8px;
      padding: 5px 12px; font-size: 12px; font-weight: 500; color: #475569;
    }

    .admin-row {
      display: flex; align-items: center; gap: 12px; flex-wrap: wrap;
      margin-top: 14px; padding-top: 14px; border-top: 1px solid #f1f5f9;
    }
    .remark-input { flex: 1; min-width: 200px; }
    .admin-btns { display: flex; gap: 8px; flex-wrap: wrap; }
    .abtn {
      padding: 8px 16px; border-radius: 8px; border: none;
      font-size: 13px; font-weight: 600; cursor: pointer;
      font-family: 'Inter',sans-serif; transition: all .2s;
    }
    .abtn:hover { transform: translateY(-1px); }
    .approve { background: #dcfce7; color: #166534; border: 1px solid #bbf7d0; }
    .reject  { background: #fee2e2; color: #991b1b; border: 1px solid #fecaca; }
    .review  { background: #fef9c3; color: #92400e; border: 1px solid #fde68a; }

    @keyframes fadeUp {
      from { opacity: 0; transform: translateY(16px); }
      to   { opacity: 1; transform: translateY(0); }
    }

    @media (max-width: 768px) {
      .fgrid2 { grid-template-columns: 1fr; }
      .item-row { grid-template-columns: 1fr 1fr; }
      .item-prod { grid-column: span 2; }
      .rfq-wrap { padding: 20px 16px; }
    }
  `]
})
export class QuotationComponent implements OnInit {
  rfqForm!: FormGroup;
  products: any[] = [];
  myQuotes: any[] = [];
  allQuotes: any[] = [];
  loadingMy = false;
  loadingAll = false;
  submitting = false;
  remarks: Record<number, string> = {};

  constructor(
    private fb: FormBuilder,
    private quotationService: QuotationService,
    private productService: ProductService,
    public auth: AuthService,
    private snack: MatSnackBar
  ) {}

  ngOnInit(): void {
    this.rfqForm = this.fb.group({
      companyName: [this.auth.currentUser?.name || ''],
      userPhone:   [this.auth.currentUser?.phone || ''],
      notes: [''],
      items: this.fb.array([this.newItem()])
    });
    this.productService.getAll(0, 200).subscribe({
      next: (d: any) => this.products = d.content || [],
      error: () => {}
    });
    this.loadMy();
    if (this.auth.isManager) this.loadAll();
  }

  get itemsArr(): FormArray { return this.rfqForm.get('items') as FormArray; }

  newItem(): FormGroup {
    return this.fb.group({
      productId: ['', Validators.required],
      productName: [''],
      quantity: [1, [Validators.required, Validators.min(1)]],
      unit: ['']
    });
  }

  addItem(): void { this.itemsArr.push(this.newItem()); }
  removeItem(i: number): void { if (this.itemsArr.length > 1) this.itemsArr.removeAt(i); }

  onProdChange(i: number, event: Event): void {
    const id = Number((event.target as HTMLSelectElement).value);
    const p = this.products.find(x => x.id === id);
    if (p) this.itemsArr.at(i).patchValue({ productName: p.name, unit: p.unit });
  }

  submitRFQ(): void {
    if (this.rfqForm.invalid) {
      this.snack.open('Please fill all required fields', 'Close', { duration: 2500 });
      return;
    }
    this.submitting = true;
    const user = this.auth.currentUser!;
    const payload = {
      userId:      user.userId,
      userName:    user.name,
      userEmail:   user.email,
      userPhone:   this.rfqForm.value.userPhone || user.phone || '',
      companyName: this.rfqForm.value.companyName,
      notes:       this.rfqForm.value.notes,
      status:      'SUBMITTED',
      items:       this.rfqForm.value.items.filter((it: any) => it.productId)
    };
    this.quotationService.create(payload).subscribe({
      next: () => {
        this.snack.open('RFQ submitted successfully!', 'Close', { duration: 3000, panelClass: 'snack-success' });
        this.submitting = false;
        this.rfqForm.patchValue({ companyName: '', userPhone: '', notes: '' });
        this.itemsArr.clear();
        this.itemsArr.push(this.newItem());
        this.loadMy();
      },
      error: (err) => {
        this.snack.open(err.error?.message || 'Failed to submit RFQ', 'Close', { duration: 3000, panelClass: 'snack-error' });
        this.submitting = false;
      }
    });
  }

  loadMy(): void {
    this.loadingMy = true;
    this.quotationService.getByUser(this.auth.currentUser!.userId).subscribe({
      next: (d: any) => { this.myQuotes = d.content || []; this.loadingMy = false; },
      error: () => this.loadingMy = false
    });
  }

  loadAll(): void {
    this.loadingAll = true;
    this.quotationService.getAll().subscribe({
      next: (d: any) => { this.allQuotes = d.content || []; this.loadingAll = false; },
      error: () => this.loadingAll = false
    });
  }

  changeStatus(id: number, status: string): void {
    const remark = this.remarks[id] || '';
    this.quotationService.updateStatus(id, status, remark || undefined).subscribe({
      next: () => {
        this.snack.open(`Status updated to ${status}`, 'Close', { duration: 2000, panelClass: 'snack-success' });
        this.remarks[id] = '';
        this.loadAll();
      },
      error: () => this.snack.open('Update failed', 'Close', { duration: 2000, panelClass: 'snack-error' })
    });
  }

  statusClass(s: string): string {
    const m: Record<string, string> = {
      DRAFT:'s-draft', SUBMITTED:'s-submitted', REVIEWED:'s-reviewed',
      APPROVED:'s-approved', REJECTED:'s-rejected', EXPIRED:'s-expired'
    };
    return m[s] || 's-draft';
  }

  statusEmoji(s: string): string {
    const m: Record<string, string> = {
      DRAFT:'📝', SUBMITTED:'📨', REVIEWED:'🔍',
      APPROVED:'✅', REJECTED:'❌', EXPIRED:'⏰'
    };
    return m[s] || '📋';
  }
}
