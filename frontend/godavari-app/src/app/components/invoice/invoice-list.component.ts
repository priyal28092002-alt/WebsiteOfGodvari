import { Component, OnInit } from '@angular/core';
import { MatSnackBar } from '@angular/material/snack-bar';
import { BillingService } from '../../services/billing.service';
import { AuthService } from '../../services/auth.service';

@Component({
  selector: 'app-invoice-list',
  template: `
    <div class="page-container">
      <!-- Header -->
      <div class="page-header">
        <div class="page-header-left">
          <div class="ph-icon"><mat-icon>description</mat-icon></div>
          <div>
            <h2>{{ auth.isManager ? 'All Invoices' : 'My Invoices' }}</h2>
            <p>View and manage GST tax invoices</p>
          </div>
        </div>
      </div>

      <!-- Loading -->
      <div *ngIf="loading" style="text-align:center; padding:80px;">
        <mat-spinner diameter="48" style="margin:0 auto;"></mat-spinner>
        <p style="margin-top:16px; color:#64748b;">Loading invoices...</p>
      </div>

      <!-- Empty -->
      <div *ngIf="!loading && invoices.length === 0" class="empty-state">
        <div class="empty-icon"><mat-icon>description</mat-icon></div>
        <h3>No invoices found</h3>
        <p>Invoices will appear here once orders are confirmed</p>
      </div>

      <!-- Invoice Cards -->
      <div *ngFor="let inv of invoices; let i = index" class="invoice-card" [style.animation-delay.ms]="i*80">
        <div class="inv-header">
          <div class="inv-info">
            <div class="inv-number">
              <mat-icon>receipt</mat-icon>
              {{ inv.invoiceNumber }}
            </div>
            <div class="inv-meta">{{ inv.invoiceDate | date:'dd MMM yyyy' }} &bull; Order: {{ inv.orderNumber }}</div>
            <div class="inv-customer">{{ inv.userName }} &bull; {{ inv.userEmail }}</div>
            <div *ngIf="inv.userPhone" class="inv-phone">📱 {{ inv.userPhone }}</div>
            <div *ngIf="inv.gstNumber" class="inv-gst">GST: {{ inv.gstNumber }}</div>
          </div>
          <div class="inv-summary">
            <span class="badge" [ngClass]="inv.paid ? 'badge-success' : 'badge-warn'">
              {{ inv.paid ? '✅ PAID' : '⏳ PENDING' }}
            </span>
            <div class="inv-breakdown">
              <span>Subtotal: ₹{{ inv.subtotal | number:'1.2-2' }}</span>
              <span>GST: ₹{{ inv.totalGst | number:'1.2-2' }}</span>
            </div>
            <div class="inv-total">₹{{ inv.totalAmount | number:'1.2-2' }}</div>
          </div>
        </div>

        <mat-divider></mat-divider>

        <!-- GST Breakdown + Actions -->
        <div class="inv-footer">
          <div class="gst-breakdown">
            CGST: ₹{{ inv.cgst | number:'1.2-2' }} &bull;
            SGST: ₹{{ inv.sgst | number:'1.2-2' }}
            <span *ngIf="inv.igst > 0"> &bull; IGST: ₹{{ inv.igst | number:'1.2-2' }}</span>
          </div>
          <div class="inv-actions">
            <button mat-stroked-button (click)="printInvoice(inv)">
              <mat-icon>print</mat-icon> Print
            </button>
            <button *ngIf="auth.isManager && !inv.paid" mat-raised-button color="primary"
              (click)="markPaid(inv)">
              <mat-icon>check_circle</mat-icon> Mark Paid
              <span *ngIf="inv.userPhone" class="wa-hint">📲 {{ inv.userPhone }}</span>
            </button>
          </div>
        </div>
      </div>
    </div>
  `,
  styles: [`
    .page-header {
      display: flex; justify-content: space-between; align-items: center;
      margin-bottom: 28px; animation: fadeInUp 0.4s ease-out;
    }
    .page-header-left { display: flex; align-items: center; gap: 16px; }
    .ph-icon {
      width: 52px; height: 52px;
      background: linear-gradient(135deg,#1a237e,#3949ab);
      border-radius: 14px; display: flex; align-items: center; justify-content: center;
      color: white; box-shadow: 0 4px 14px rgba(26,35,126,.3);
    }
    .ph-icon mat-icon { font-size: 28px; width: 28px; height: 28px; }
    .page-header h2 { font-size: 24px; font-weight: 800; color: #1a237e; }
    .page-header p { font-size: 14px; color: #64748b; margin-top: 2px; }

    .empty-state { text-align: center; padding: 80px 20px; animation: fadeInUp 0.5s ease-out; }
    .empty-icon {
      width: 80px; height: 80px; border-radius: 20px;
      background: #f1f5f9; display: flex; align-items: center; justify-content: center;
      margin: 0 auto 20px;
    }
    .empty-icon mat-icon { font-size: 40px; width: 40px; height: 40px; color: #94a3b8; }
    .empty-state h3 { font-size: 20px; font-weight: 700; color: #1a237e; margin-bottom: 8px; }
    .empty-state p { color: #64748b; }

    .invoice-card {
      background: white; border-radius: 16px; padding: 24px; margin-bottom: 18px;
      border: 1px solid #e2e8f0; box-shadow: 0 2px 8px rgba(0,0,0,0.04);
      animation: fadeInUp 0.5s ease-out both; transition: all 0.2s ease;
    }
    .invoice-card:hover { box-shadow: 0 6px 20px rgba(0,0,0,0.08); }

    .inv-header {
      display: flex; justify-content: space-between;
      flex-wrap: wrap; gap: 16px; margin-bottom: 18px;
    }
    .inv-number {
      display: flex; align-items: center; gap: 8px;
      font-size: 17px; font-weight: 700; color: #1a237e; margin-bottom: 6px;
    }
    .inv-number mat-icon { font-size: 20px; width: 20px; height: 20px; }
    .inv-meta { font-size: 13px; color: #64748b; margin-bottom: 3px; }
    .inv-customer { font-size: 13px; color: #475569; }
    .inv-phone { font-size: 12px; color: #25d366; margin-top: 2px; }
    .inv-gst { font-size: 12px; color: #94a3b8; margin-top: 2px; }

    .inv-summary { text-align: right; }
    .inv-breakdown { font-size: 12px; color: #64748b; margin: 8px 0 4px; }
    .inv-breakdown span { margin-left: 12px; }
    .inv-total { font-size: 24px; font-weight: 800; color: #1a237e; }

    .inv-footer {
      display: flex; justify-content: space-between; align-items: center;
      flex-wrap: wrap; gap: 12px; margin-top: 16px;
      padding-top: 16px; border-top: 1px solid #f1f5f9;
    }
    .gst-breakdown { font-size: 12px; color: #64748b; }
    .inv-actions { display: flex; gap: 10px; }
    .wa-hint { font-size: 11px; font-weight: 600; color: #25d366; margin-left: 4px; }

    @keyframes fadeInUp {
      from { opacity: 0; transform: translateY(20px); }
      to { opacity: 1; transform: translateY(0); }
    }
  `]
})
export class InvoiceListComponent implements OnInit {
  invoices: any[] = [];
  loading = false;

  constructor(private billingService: BillingService,
              public auth: AuthService,
              private snack: MatSnackBar) {}

  ngOnInit(): void { this.load(); }

  load(): void {
    this.loading = true;
    const obs = this.auth.isManager
      ? this.billingService.getAll()
      : this.billingService.getByUser(this.auth.currentUser!.userId);
    obs.subscribe({
      next: data => { this.invoices = data; this.loading = false; },
      error: () => this.loading = false
    });
  }

  markPaid(inv: any): void {
    const phone = inv.userPhone || 'no phone';
    console.log(`[Invoice] Marking paid: ${inv.invoiceNumber} | WhatsApp will be sent to: ${phone}`);
    this.billingService.markPaid(inv.id).subscribe({
      next: () => {
        const msg = inv.userPhone
          ? `✅ Invoice marked as PAID. WhatsApp sent to ${inv.userPhone}`
          : `✅ Invoice marked as PAID. (No phone — WhatsApp skipped)`;
        this.snack.open(msg, 'Close', { duration: 4000, panelClass: 'snack-success' });
        this.load();
      },
      error: () => this.snack.open('Failed to mark paid', 'Close', { duration: 3000, panelClass: 'snack-error' })
    });
  }

  printInvoice(inv: any): void {
    const win = window.open('', '_blank', 'width=800,height=900');
    if (!win) return;
    win.document.write(`<!DOCTYPE html><html><head>
      <title>Invoice - ${inv.invoiceNumber}</title>
      <style>
        body { font-family: Arial, sans-serif; padding: 40px; color: #333; }
        .header { display:flex; justify-content:space-between; border-bottom:3px solid #1a237e; padding-bottom:16px; margin-bottom:20px; }
        .company { font-size:22px; font-weight:bold; color:#1a237e; }
        .totals { margin-top:24px; text-align:right; }
        .totals p { margin:4px 0; font-size:14px; }
        .grand-total { font-size:20px; font-weight:bold; color:#1a237e; }
        @media print { button { display:none; } }
      </style></head><body>
      <div class="header">
        <div>
          <div class="company">🏗️ Godavari Building Materials</div>
          <div style="font-size:13px;color:#666;margin-top:4px">Nashik, Maharashtra</div>
        </div>
        <div style="text-align:right">
          <div style="font-size:20px;font-weight:bold;color:#1a237e">TAX INVOICE</div>
          <div><strong>${inv.invoiceNumber}</strong></div>
          <div style="font-size:12px;color:#666">Date: ${inv.invoiceDate}</div>
        </div>
      </div>
      <div style="margin-bottom:16px">
        <strong>Bill To:</strong><br>
        ${inv.userName || ''}<br>
        ${inv.userEmail || ''}<br>
        ${inv.userPhone || ''}<br>
        Order: ${inv.orderNumber} | Payment: ${inv.paymentMethod || '-'}
      </div>
      <div class="totals">
        <p>Subtotal: ₹${Number(inv.subtotal || 0).toFixed(2)}</p>
        <p>CGST: ₹${Number(inv.cgst || 0).toFixed(2)} | SGST: ₹${Number(inv.sgst || 0).toFixed(2)}</p>
        <p class="grand-total">Grand Total: ₹${Number(inv.totalAmount || 0).toFixed(2)}</p>
      </div>
      <br><button onclick="window.print()" style="padding:10px 24px;background:#1a237e;color:white;border:none;border-radius:8px;font-size:14px;cursor:pointer;">🖨️ Print</button>
    </body></html>`);
    win.document.close();
  }
}
