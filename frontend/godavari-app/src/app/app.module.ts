import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { HttpClientModule, HTTP_INTERCEPTORS } from '@angular/common/http';
import { CommonModule } from '@angular/common';

import { MatToolbarModule } from '@angular/material/toolbar';
import { MatButtonModule } from '@angular/material/button';
import { MatIconModule } from '@angular/material/icon';
import { MatCardModule } from '@angular/material/card';
import { MatInputModule } from '@angular/material/input';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatSelectModule } from '@angular/material/select';
import { MatTableModule } from '@angular/material/table';
import { MatPaginatorModule } from '@angular/material/paginator';
import { MatBadgeModule } from '@angular/material/badge';
import { MatChipsModule } from '@angular/material/chips';
import { MatProgressSpinnerModule } from '@angular/material/progress-spinner';
import { MatSnackBarModule } from '@angular/material/snack-bar';
import { MatDialogModule } from '@angular/material/dialog';
import { MatDividerModule } from '@angular/material/divider';
import { MatMenuModule } from '@angular/material/menu';
import { MatSidenavModule } from '@angular/material/sidenav';
import { MatListModule } from '@angular/material/list';
import { MatTooltipModule } from '@angular/material/tooltip';
import { MatTabsModule } from '@angular/material/tabs';
import { MatCheckboxModule } from '@angular/material/checkbox';
import { MatSortModule } from '@angular/material/sort';

import { AppRoutingModule } from './app-routing.module';
import { AuthInterceptor } from './guards/auth.interceptor';

import { UserManagementComponent } from './components/admin/user-management.component';
import { LogViewerComponent } from './components/logs/log-viewer.component';

import { AppComponent } from './app.component';
import { NavbarComponent } from './components/layout/navbar.component';
import { FooterComponent } from './components/layout/footer.component';
import { HomeComponent } from './components/home/home.component';
import { LoginComponent } from './components/auth/login.component';
import { RegisterComponent } from './components/auth/register.component';
import { AdminLoginComponent } from './components/auth/admin-login.component';
import { AdminRegisterComponent } from './components/auth/admin-register.component';
import { ProductListComponent } from './components/products/product-list.component';
import { CartComponent } from './components/cart/cart.component';
import { OrderListComponent } from './components/orders/order-list.component';
import { DashboardComponent } from './components/dashboard/dashboard.component';
import { QuotationComponent } from './components/quotation/quotation.component';
import { InvoiceListComponent } from './components/invoice/invoice-list.component';
import { ProductManagementComponent } from './components/products/product-management.component';
import { InventoryManagementComponent } from './components/inventory/inventory-management.component';

const MAT = [
  MatToolbarModule, MatButtonModule, MatIconModule, MatCardModule,
  MatInputModule, MatFormFieldModule, MatSelectModule, MatTableModule,
  MatPaginatorModule, MatBadgeModule, MatChipsModule, MatProgressSpinnerModule,
  MatSnackBarModule, MatDialogModule, MatDividerModule, MatMenuModule,
  MatSidenavModule, MatListModule, MatTooltipModule, MatTabsModule,
  MatCheckboxModule, MatSortModule
];

@NgModule({
  declarations: [
    AppComponent, NavbarComponent, FooterComponent,
    HomeComponent, LoginComponent, RegisterComponent, AdminLoginComponent, AdminRegisterComponent,
    ProductListComponent, CartComponent, OrderListComponent, DashboardComponent,
    QuotationComponent, InvoiceListComponent, ProductManagementComponent,
    InventoryManagementComponent, UserManagementComponent, LogViewerComponent
  ],
  imports: [
    BrowserModule, BrowserAnimationsModule, CommonModule,
    FormsModule, ReactiveFormsModule, HttpClientModule, AppRoutingModule, ...MAT
  ],
  providers: [
    { provide: HTTP_INTERCEPTORS, useClass: AuthInterceptor, multi: true }
  ],
  bootstrap: [AppComponent]
})
export class AppModule {}
