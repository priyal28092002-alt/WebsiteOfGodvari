import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { MatSnackBar } from '@angular/material/snack-bar';
import { InventoryService } from '../../services/inventory.service';

@Component({
  selector: 'app-inventory-management',
  template: `
    <div class="page-container">

      <!-- Header -->
      <div class="page-hdr">
        <div class="page-hdr-left">
          <div class="hdr-ico"><mat-icon>warehouse</mat-icon></div>
          <div>
            <h2>Inventory Management</h2>
            <p>{{ inventory.length }} products tracked · {{ lowStock.length }} low stock alerts</p>
          </div>
        </div>
        <div class="hdr-actions">
          <span class="alert-badge" *ngIf="lowStock.length > 0">
            <mat-icon>warning</mat-icon> {{ lowStock.length }} Low Stock
          </span>
          <button class="refresh-btn" (click)="loadAll()">
            <mat-icon>refresh</mat-icon> Refresh
          </button>
        </div>
      </div>

      <!-- Stats row -->
      <div class="inv-stats">
        <div class="ist" style="border-color:#3b82f6">
          <mat-icon style="color:#3b82f6">inventory_2</mat-icon>
          <div>
            <strong>{{ inventory.length }}</strong>
            <span>Total Products</span>
          </div>
        </div>
        <div class="ist" style="border-color:#10b981">
          <mat-icon style="color:#10b981">check_circle</mat-icon>
          <div>
            <strong>{{ inStockCount }}</strong>
            <span>In Stock</span>
          </div>
        </div>
        <div class="ist" style="border-color:#f59e0b">
          <mat-icon style="color:#f59e0b">warning_amber</mat-icon>
          <div>
            <strong>{{ lowStock.length }}</strong>
            <span>Low Stock</span>
          </div>
        </div>
        <div class="ist" style="border-color:#ef4444">
          <mat-icon style="color:#ef4444">cancel</mat-icon>
          <div>
            <strong>{{ outOfStockCount }}</strong>
            <span>Out of Stock</span>
          </div>
        </div>
      </div>

      <!-- Tabs -->
      <div class="tab-bar">
        <button class="tab-btn" [class.act]="activeTab===0" (click)="activeTab=0">
          <mat-icon>list_alt</mat-icon> All Stock ({{ inventory.length }})
        </button>
        <button class="tab-btn warn" [class.act]="activeTab===1" (click)="activeTab=1">
          <mat-icon>warning_amber</mat-icon> Low Stock ({{ lowStock.length }})
        </button>
      </div>

      <!-- ALL STOCK TAB -->
      <div *ngIf="activeTab===0">
        <div *ngIf="loading" class="loading-box">
          <div class="spin"></div>
          <p>Loading inventory...</p>
        </div>

        <div *ngIf="!loading && inventory.length===0" class="empty-box">
          <mat-icon>inbox</mat-icon>
          <h3>No inventory records yet</h3>
          <p>Add products with initial stock to see them here</p>
        </div>

        <div class="inv-table-card" *ngIf="!loading && inventory.length>0">
          <table class="inv-table">
            <thead>
              <tr>
                <th>Product</th>
                <th>Available</th>
                <th>Reserved</th>
                <th>Threshold</th>
                <th>Location</th>
                <th>Status</th>
                <th style="text-align:right">Actions</th>
              </tr>
            </thead>
            <tbody>
              <tr *ngFor="let row of inventory" [class.low-row]="row.quantityAvailable <= row.lowStockThreshold"
                [class.out-row]="row.quantityAvailable === 0">
                <td>
                  <div class="prod-cell">
                    <div class="prod-dot" [style.background]="getStockColor(row)"></div>
                    <div>
                      <strong>{{ row.productName }}</strong>
                      <span>{{ row.sku || 'No SKU' }}</span>
                    </div>
                  </div>
                </td>
                <td>
                  <span class="qty-val" [style.color]="getStockColor(row)">
                    {{ row.quantityAvailable }}
                  </span>
                  <span class="qty-unit">{{ row.unit || '' }}</span>
                </td>
                <td>{{ row.quantityReserved || 0 }} {{ row.unit || '' }}</td>
                <td>{{ row.lowStockThreshold }} {{ row.unit || '' }}</td>
                <td>{{ row.warehouseLocation || '—' }}</td>
                <td>
                  <span class="status-chip"
                    [class.s-ok]="row.quantityAvailable > row.lowStockThreshold"
                    [class.s-low]="row.quantityAvailable > 0 && row.quantityAvailable <= row.lowStockThreshold"
                    [class.s-out]="row.quantityAvailable === 0">
                    {{ row.quantityAvailable === 0 ? 'Out of Stock' :
                       row.quantityAvailable <= row.lowStockThreshold ? '⚠ Low Stock' : '✓ In Stock' }}
                  </span>
                </td>
                <td style="text-align:right">
                  <button class="edit-stock-btn" (click)="openUpdate(row)">
                    <mat-icon>edit</mat-icon> Update Stock
                  </button>
                </td>
              </tr>
            </tbody>
          </table>
        </div>
      </div>

      <!-- LOW STOCK TAB -->
      <div *ngIf="activeTab===1">
        <div *ngIf="lowStock.length===0" class="empty-box ok">
          <mat-icon style="color:#10b981">check_circle</mat-icon>
          <h3>All items are well stocked!</h3>
          <p>No low stock alerts at this time</p>
        </div>
        <div class="low-stock-grid" *ngIf="lowStock.length>0">
          <div *ngFor="let item of lowStock; let i=index" class="ls-card"
            [style.animation-delay.ms]="i*60">
            <div class="ls-left">
              <div class="ls-icon">⚠️</div>
              <div>
                <strong>{{ item.productName }}</strong>
                <span>{{ item.warehouseLocation || 'Main Warehouse' }}</span>
                <span *ngIf="item.sku" class="ls-sku">SKU: {{ item.sku }}</span>
              </div>
            </div>
            <div class="ls-mid">
              <div class="ls-qty">{{ item.quantityAvailable }}</div>
              <div class="ls-qlabel">Available {{ item.unit || 'units' }}</div>
            </div>
            <div class="ls-mid">
              <div class="ls-thresh">{{ item.lowStockThreshold }}</div>
              <div class="ls-qlabel">Threshold</div>
            </div>
            <div class="ls-bar-wrap">
              <div class="ls-bar">
                <div class="ls-fill"
                  [style.width.%]="getStockPercent(item)"
                  [style.background]="item.quantityAvailable === 0 ? '#ef4444' : '#f59e0b'">
                </div>
              </div>
              <span>{{ getStockPercent(item) }}% of threshold</span>
            </div>
            <button class="restock-btn" (click)="openUpdate(item)">
              <mat-icon>add</mat-icon> Restock
            </button>
          </div>
        </div>
      </div>

      <!-- STOCK UPDATE DIALOG -->
      <div *ngIf="showUpdateForm" class="dialog-bg" (click)="showUpdateForm=false">
        <div class="dialog-card" (click)="$event.stopPropagation()">
          <div class="dlg-hdr">
            <div class="dlg-title">
              <mat-icon>edit_note</mat-icon>
              <h3>Update Stock</h3>
            </div>
            <button class="dlg-close" (click)="showUpdateForm=false">
              <mat-icon>close</mat-icon>
            </button>
          </div>

          <!-- Current stock info -->
          <div class="dlg-info" *ngIf="selectedInventory">
            <div class="dlg-prod">{{ selectedInventory.productName }}</div>
            <div class="dlg-curr">
              Current: <strong>{{ selectedInventory.quantityAvailable }} {{ selectedInventory.unit || 'units' }}</strong>
              · Threshold: {{ selectedInventory.lowStockThreshold }}
              · {{ selectedInventory.warehouseLocation || 'Main Warehouse' }}
            </div>
          </div>

          <form [formGroup]="updateForm" (ngSubmit)="saveStock()">
            <!-- Transaction type -->
            <div class="dlg-field">
              <label>Transaction Type</label>
              <div class="type-btns">
                <button type="button" class="type-btn"
                  [class.t-in]="updateForm.value.transactionType==='IN'"
                  (click)="updateForm.patchValue({transactionType:'IN'})">
                  <mat-icon>add_circle</mat-icon> Stock In
                </button>
                <button type="button" class="type-btn"
                  [class.t-out]="updateForm.value.transactionType==='OUT'"
                  (click)="updateForm.patchValue({transactionType:'OUT'})">
                  <mat-icon>remove_circle</mat-icon> Stock Out
                </button>
                <button type="button" class="type-btn"
                  [class.t-adj]="updateForm.value.transactionType==='ADJUSTMENT'"
                  (click)="updateForm.patchValue({transactionType:'ADJUSTMENT'})">
                  <mat-icon>tune</mat-icon> Adjust
                </button>
              </div>
            </div>

            <div class="dlg-grid">
              <div class="dlg-field">
                <label>Quantity <span class="req">*</span></label>
                <div class="dlg-input">
                  <mat-icon>numbers</mat-icon>
                  <input type="number" formControlName="quantity" min="1" placeholder="Enter quantity">
                  <span class="unit-hint">{{ selectedInventory?.unit || 'units' }}</span>
                </div>
                <!-- Preview -->
                <div class="qty-preview" *ngIf="updateForm.value.quantity > 0 && selectedInventory">
                  <span>After update: </span>
                  <strong [style.color]="getPreviewColor()">{{ getNewQty() }} {{ selectedInventory?.unit || 'units' }}</strong>
                </div>
              </div>

              <div class="dlg-field">
                <label>Low Stock Threshold</label>
                <div class="dlg-input">
                  <mat-icon>warning_amber</mat-icon>
                  <input type="number" formControlName="lowStockThreshold" min="0">
                </div>
              </div>

              <div class="dlg-field full">
                <label>Warehouse Location</label>
                <div class="dlg-input">
                  <mat-icon>location_on</mat-icon>
                  <input formControlName="warehouseLocation" placeholder="e.g. Rack A-1, Main Warehouse">
                </div>
              </div>

              <div class="dlg-field full">
                <label>Remarks / Notes</label>
                <div class="dlg-input">
                  <mat-icon>notes</mat-icon>
                  <input formControlName="remarks" placeholder="Reason for stock change...">
                </div>
              </div>
            </div>

            <div class="dlg-footer">
              <button type="button" class="dlg-cancel" (click)="showUpdateForm=false">Cancel</button>
              <button type="submit" class="dlg-save" [disabled]="saving">
                <mat-spinner *ngIf="saving" diameter="16"></mat-spinner>
                <mat-icon *ngIf="!saving">save</mat-icon>
                {{ saving ? 'Saving...' : 'Update Stock' }}
              </button>
            </div>
          </form>
        </div>
      </div>
    </div>
  `,
  styles: [`
    /* Header */
    .page-hdr {
      display:flex; justify-content:space-between; align-items:center;
      margin-bottom:24px; flex-wrap:wrap; gap:16px;
      animation:fuUp .4s ease-out;
    }
    .page-hdr-left { display:flex; align-items:center; gap:14px; }
    .hdr-ico {
      width:52px; height:52px; border-radius:14px;
      background:linear-gradient(135deg,#3b82f6,#6366f1);
      display:flex; align-items:center; justify-content:center;
      color:white; box-shadow:0 4px 14px rgba(99,102,241,.3);
    }
    .page-hdr h2 { font-size:24px; font-weight:800; color:#1e293b; }
    .page-hdr p  { font-size:14px; color:#64748b; margin-top:2px; }
    .hdr-actions { display:flex; align-items:center; gap:10px; }
    .alert-badge {
      display:flex; align-items:center; gap:6px;
      background:#fef2f2; border:1px solid #fecaca; color:#dc2626;
      padding:6px 14px; border-radius:8px; font-size:13px; font-weight:600;
    }
    .refresh-btn {
      display:flex; align-items:center; gap:6px;
      background:#f1f5f9; border:1px solid #e2e8f0; border-radius:8px;
      padding:8px 16px; cursor:pointer; font-size:13px; font-weight:600; color:#475569;
      transition:all .2s; font-family:'Inter',sans-serif;
    }
    .refresh-btn:hover { background:#e2e8f0; transform:rotate(90deg) scale(1.05); }

    /* Stats */
    .inv-stats {
      display:grid; grid-template-columns:repeat(4,1fr); gap:14px;
      margin-bottom:24px; animation:fuUp .4s ease-out .1s both;
    }
    .ist {
      background:white; border-radius:12px; padding:16px 18px;
      border:1px solid #e2e8f0; border-left:4px solid;
      display:flex; align-items:center; gap:12px;
      box-shadow:0 2px 6px rgba(0,0,0,.04);
    }
    .ist mat-icon { font-size:26px; width:26px; height:26px; }
    .ist strong { display:block; font-size:24px; font-weight:800; color:#1e293b; }
    .ist span   { font-size:12px; color:#64748b; }

    /* Tabs */
    .tab-bar {
      display:flex; gap:8px; margin-bottom:20px;
      border-bottom:2px solid #e2e8f0; padding-bottom:0;
    }
    .tab-btn {
      display:flex; align-items:center; gap:6px;
      padding:10px 20px; border:none; background:none; cursor:pointer;
      font-size:14px; font-weight:600; color:#64748b;
      border-bottom:3px solid transparent; margin-bottom:-2px;
      font-family:'Inter',sans-serif; transition:all .2s;
    }
    .tab-btn:hover { color:#3b82f6; }
    .tab-btn.act { color:#3b82f6; border-bottom-color:#3b82f6; }
    .tab-btn.warn.act { color:#f59e0b; border-bottom-color:#f59e0b; }

    /* Loading / Empty */
    .loading-box { text-align:center; padding:80px 20px; color:#64748b; }
    .spin { width:48px; height:48px; border-radius:50%; border:4px solid #e2e8f0; border-top-color:#3b82f6; margin:0 auto 20px; animation:spin .8s linear infinite; }
    @keyframes spin { to{transform:rotate(360deg);} }
    .empty-box { text-align:center; padding:80px 20px; color:#64748b; animation:fuUp .5s ease-out; }
    .empty-box mat-icon { font-size:56px; width:56px; height:56px; color:#e2e8f0; display:block; margin:0 auto 16px; }
    .empty-box h3 { font-size:20px; font-weight:700; color:#1e293b; margin-bottom:8px; }
    .empty-box.ok mat-icon { color:#10b981; }

    /* Inventory Table */
    .inv-table-card {
      background:white; border-radius:14px; overflow:hidden;
      border:1px solid #e2e8f0; box-shadow:0 2px 8px rgba(0,0,0,.04);
      animation:fuUp .4s ease-out;
    }
    .inv-table { width:100%; border-collapse:collapse; font-size:14px; }
    .inv-table thead tr { background:linear-gradient(135deg,#f8fafc,#f1f5f9); }
    .inv-table th {
      padding:13px 16px; text-align:left; font-size:11px; font-weight:700;
      color:#475569; text-transform:uppercase; letter-spacing:.5px;
      border-bottom:2px solid #e2e8f0;
    }
    .inv-table td { padding:14px 16px; border-bottom:1px solid #f8fafc; vertical-align:middle; }
    .inv-table tr:last-child td { border-bottom:none; }
    .inv-table tr:hover td { background:#f8fafc; }
    .low-row td { background:#fffbeb; }
    .out-row td { background:#fef2f2; }
    .low-row:hover td { background:#fef3c7; }
    .out-row:hover td { background:#fee2e2; }

    .prod-cell { display:flex; align-items:center; gap:10px; }
    .prod-dot  { width:10px; height:10px; border-radius:50%; flex-shrink:0; }
    .prod-cell strong { display:block; font-size:14px; font-weight:600; color:#1e293b; }
    .prod-cell span   { font-size:11px; color:#94a3b8; }
    .qty-val  { font-size:18px; font-weight:800; }
    .qty-unit { font-size:12px; color:#64748b; margin-left:4px; }
    .status-chip {
      padding:4px 10px; border-radius:20px; font-size:12px; font-weight:700;
      white-space:nowrap;
    }
    .s-ok  { background:#f0fdf4; color:#16a34a; border:1px solid #bbf7d0; }
    .s-low { background:#fef9c3; color:#92400e; border:1px solid #fde68a; }
    .s-out { background:#fee2e2; color:#dc2626; border:1px solid #fecaca; }
    .edit-stock-btn {
      display:inline-flex; align-items:center; gap:5px;
      padding:7px 14px; border-radius:8px; border:1px solid #e2e8f0;
      background:white; color:#3b82f6; font-size:12px; font-weight:600;
      cursor:pointer; transition:all .2s; font-family:'Inter',sans-serif;
    }
    .edit-stock-btn:hover { background:#eff6ff; border-color:#3b82f6; }
    .edit-stock-btn mat-icon { font-size:15px; width:15px; height:15px; }

    /* Low stock grid */
    .low-stock-grid { display:flex; flex-direction:column; gap:12px; }
    .ls-card {
      background:white; border-radius:14px; padding:20px 24px;
      border:1px solid #e2e8f0; border-left:5px solid #f59e0b;
      display:flex; align-items:center; gap:20px; flex-wrap:wrap;
      box-shadow:0 2px 6px rgba(0,0,0,.04);
      animation:fuUp .4s ease-out both; transition:all .2s;
    }
    .ls-card:hover { box-shadow:0 6px 16px rgba(0,0,0,.08); }
    .ls-left { display:flex; align-items:center; gap:12px; flex:1; min-width:200px; }
    .ls-icon { font-size:24px; }
    .ls-left strong { display:block; font-size:15px; font-weight:700; color:#1e293b; }
    .ls-left span   { display:block; font-size:12px; color:#64748b; margin-top:2px; }
    .ls-sku { color:#94a3b8 !important; font-size:11px !important; }
    .ls-mid { text-align:center; min-width:80px; }
    .ls-qty    { font-size:28px; font-weight:800; color:#dc2626; line-height:1.2; }
    .ls-thresh { font-size:18px; font-weight:700; color:#64748b; }
    .ls-qlabel { font-size:11px; color:#94a3b8; }
    .ls-bar-wrap { flex:1; min-width:120px; font-size:11px; color:#94a3b8; }
    .ls-bar { height:6px; background:#f1f5f9; border-radius:3px; overflow:hidden; margin-bottom:4px; }
    .ls-fill { height:100%; border-radius:3px; transition:width .3s; }
    .restock-btn {
      display:flex; align-items:center; gap:6px;
      padding:10px 20px; border-radius:10px; border:none;
      background:linear-gradient(135deg,#f59e0b,#d97706); color:white;
      font-size:13px; font-weight:700; cursor:pointer;
      font-family:'Inter',sans-serif; transition:all .2s;
      box-shadow:0 4px 12px rgba(245,158,11,.3);
    }
    .restock-btn:hover { transform:translateY(-2px); box-shadow:0 6px 18px rgba(245,158,11,.4); }

    /* Dialog */
    .dialog-bg {
      position:fixed; inset:0; background:rgba(15,23,42,.6);
      backdrop-filter:blur(6px); z-index:500;
      display:flex; align-items:center; justify-content:center; padding:20px;
      animation:fadeIn .25s ease;
    }
    .dialog-card {
      background:white; border-radius:18px; padding:28px 32px;
      max-width:520px; width:100%;
      box-shadow:0 24px 60px rgba(0,0,0,.25);
      animation:scaleIn .3s ease;
    }
    .dlg-hdr {
      display:flex; justify-content:space-between; align-items:center;
      margin-bottom:20px; padding-bottom:16px; border-bottom:2px solid #f1f5f9;
    }
    .dlg-title { display:flex; align-items:center; gap:8px; }
    .dlg-title mat-icon { color:#3b82f6; }
    .dlg-title h3 { font-size:18px; font-weight:700; color:#1e293b; }
    .dlg-close {
      width:32px; height:32px; border-radius:8px; background:#f1f5f9;
      border:none; cursor:pointer; display:flex; align-items:center; justify-content:center;
      color:#64748b; transition:all .2s;
    }
    .dlg-close:hover { background:#e2e8f0; }
    .dlg-info {
      background:#f8fafc; border-radius:10px; padding:14px 16px;
      margin-bottom:20px; border:1px solid #e2e8f0;
    }
    .dlg-prod { font-size:15px; font-weight:700; color:#1e293b; margin-bottom:4px; }
    .dlg-curr { font-size:13px; color:#64748b; }

    /* Transaction type buttons */
    .type-btns { display:flex; gap:8px; flex-wrap:wrap; }
    .type-btn {
      display:flex; align-items:center; gap:6px;
      padding:9px 16px; border-radius:9px; border:2px solid #e2e8f0;
      background:white; color:#64748b; font-size:13px; font-weight:600;
      cursor:pointer; transition:all .2s; font-family:'Inter',sans-serif;
    }
    .type-btn:hover { border-color:#3b82f6; color:#3b82f6; }
    .type-btn.t-in  { border-color:#10b981; background:#f0fdf4; color:#16a34a; }
    .type-btn.t-out { border-color:#ef4444; background:#fef2f2; color:#dc2626; }
    .type-btn.t-adj { border-color:#f59e0b; background:#fffbeb; color:#92400e; }

    .dlg-grid { display:grid; grid-template-columns:1fr 1fr; gap:14px; margin-top:16px; }
    .dlg-field { display:flex; flex-direction:column; gap:5px; }
    .dlg-field.full { grid-column:span 2; }
    .dlg-field label { font-size:12px; font-weight:700; color:#475569; text-transform:uppercase; letter-spacing:.4px; }
    .req { color:#ef4444; }
    .dlg-input {
      display:flex; align-items:center; gap:8px;
      background:#f8fafc; border:2px solid #e2e8f0; border-radius:10px; padding:0 13px;
      transition:all .2s;
    }
    .dlg-input:focus-within { border-color:#3b82f6; background:white; box-shadow:0 0 0 4px rgba(59,130,246,.07); }
    .dlg-input mat-icon { color:#94a3b8; font-size:19px; flex-shrink:0; }
    .dlg-input input {
      flex:1; border:none; background:transparent; padding:12px 0;
      font-size:14px; font-family:'Inter',sans-serif; outline:none; color:#1e293b;
    }
    .dlg-input input::placeholder { color:#94a3b8; }
    .unit-hint { font-size:12px; color:#94a3b8; padding-right:4px; }
    .qty-preview {
      font-size:12px; color:#64748b; margin-top:4px;
      padding:6px 10px; background:#f8fafc; border-radius:6px;
    }
    .dlg-footer {
      display:flex; justify-content:flex-end; gap:10px;
      margin-top:24px; padding-top:16px; border-top:1px solid #f1f5f9;
    }
    .dlg-cancel {
      padding:10px 22px; border-radius:10px; background:#f1f5f9; border:none;
      color:#475569; font-size:14px; font-weight:600; cursor:pointer;
      font-family:'Inter',sans-serif; transition:all .2s;
    }
    .dlg-cancel:hover { background:#e2e8f0; }
    .dlg-save {
      display:flex; align-items:center; gap:8px;
      padding:10px 28px; border-radius:10px;
      background:linear-gradient(135deg,#3b82f6,#6366f1); color:white;
      border:none; font-size:14px; font-weight:700; cursor:pointer;
      font-family:'Inter',sans-serif; transition:all .2s;
      box-shadow:0 4px 14px rgba(59,130,246,.3);
    }
    .dlg-save:hover:not(:disabled) { transform:translateY(-2px); box-shadow:0 8px 20px rgba(59,130,246,.4); }
    .dlg-save:disabled { opacity:.65; cursor:not-allowed; }

    @keyframes fuUp   { from{opacity:0;transform:translateY(16px);} to{opacity:1;transform:translateY(0);} }
    @keyframes fadeIn { from{opacity:0;} to{opacity:1;} }
    @keyframes scaleIn{ from{opacity:0;transform:scale(.88);} to{opacity:1;transform:scale(1);} }

    @media(max-width:900px) {
      .inv-stats { grid-template-columns:1fr 1fr; }
    }
    @media(max-width:600px) {
      .inv-stats { grid-template-columns:1fr 1fr; }
      .dlg-grid  { grid-template-columns:1fr; }
      .dlg-field.full { grid-column:span 1; }
      .dialog-card { padding:20px 16px; }
      .ls-card { flex-direction:column; align-items:flex-start; }
    }
  `]
})
export class InventoryManagementComponent implements OnInit {
  inventory: any[] = [];
  lowStock: any[] = [];
  loading = false;
  saving = false;
  showUpdateForm = false;
  selectedInventory: any = null;
  activeTab = 0;
  updateForm!: FormGroup;

  constructor(private inventoryService: InventoryService,
              private fb: FormBuilder, private snack: MatSnackBar) {}

  ngOnInit(): void { this.loadAll(); }

  loadAll(): void {
    this.loading = true;
    this.inventoryService.getAll().subscribe({
      next: data => { this.inventory = data || []; this.loading = false; },
      error: (err) => {
        console.error('Inventory load error:', err);
        this.loading = false;
        this.snack.open('Failed to load inventory. Check if inventory-service is running.', 'Close', { duration: 4000 });
      }
    });
    this.inventoryService.getLowStock().subscribe({
      next: data => this.lowStock = data || [],
      error: () => this.lowStock = []
    });
  }

  get inStockCount(): number {
    return this.inventory.filter(i => i.quantityAvailable > i.lowStockThreshold).length;
  }
  get outOfStockCount(): number {
    return this.inventory.filter(i => i.quantityAvailable === 0).length;
  }

  getStockColor(row: any): string {
    if (row.quantityAvailable === 0) return '#ef4444';
    if (row.quantityAvailable <= row.lowStockThreshold) return '#f59e0b';
    return '#10b981';
  }

  getStockPercent(item: any): number {
    if (!item.lowStockThreshold) return 0;
    return Math.min(100, Math.round((item.quantityAvailable / item.lowStockThreshold) * 100));
  }

  getNewQty(): number {
    if (!this.selectedInventory || !this.updateForm) return 0;
    const cur = this.selectedInventory.quantityAvailable || 0;
    const qty = this.updateForm.value.quantity || 0;
    const type = this.updateForm.value.transactionType;
    if (type === 'IN') return cur + qty;
    if (type === 'OUT') return Math.max(0, cur - qty);
    if (type === 'ADJUSTMENT') return qty;
    return cur;
  }

  getPreviewColor(): string {
    const n = this.getNewQty();
    const t = this.updateForm?.value?.lowStockThreshold || this.selectedInventory?.lowStockThreshold || 0;
    if (n === 0) return '#ef4444';
    if (n <= t)  return '#f59e0b';
    return '#10b981';
  }

  openUpdate(item: any): void {
    this.selectedInventory = item;
    this.updateForm = this.fb.group({
      productId:         [item.productId, Validators.required],
      transactionType:   ['IN', Validators.required],
      quantity:          [1, [Validators.required, Validators.min(1)]],
      lowStockThreshold: [item.lowStockThreshold || 20],
      warehouseLocation: [item.warehouseLocation || ''],
      remarks:           ['']
    });
    this.showUpdateForm = true;
  }

  saveStock(): void {
    if (this.updateForm.invalid) return;
    this.saving = true;
    const v = this.updateForm.value;
    const payload = {
      productId:         v.productId,
      productName:       this.selectedInventory?.productName,
      sku:               this.selectedInventory?.sku,
      unit:              this.selectedInventory?.unit,
      transactionType:   v.transactionType,
      quantity:          v.quantity,
      lowStockThreshold: v.lowStockThreshold,
      warehouseLocation: v.warehouseLocation,
      notes:             v.remarks
    };
    this.inventoryService.updateStock(payload).subscribe({
      next: () => {
        this.snack.open('✅ Stock updated successfully!', 'Close', { duration: 2500, panelClass: 'snack-success' });
        this.saving = false;
        this.showUpdateForm = false;
        this.loadAll();
      },
      error: (err) => {
        const msg = err.error?.message || err.error?.error || 'Update failed';
        this.snack.open(msg, 'Close', { duration: 3000, panelClass: 'snack-error' });
        this.saving = false;
      }
    });
  }
}
