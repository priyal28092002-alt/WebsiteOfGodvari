import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { AuthGuard } from './guards/auth.guard';
import { AdminGuard } from './guards/admin.guard';
import { SuperAdminGuard } from './guards/super-admin.guard';

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
import { UserManagementComponent } from './components/admin/user-management.component';

import { LogViewerComponent } from './components/logs/log-viewer.component';

const routes: Routes = [
  { path: '', component: HomeComponent },
  { path: 'login', component: LoginComponent },
  { path: 'register', component: RegisterComponent },
  { path: 'admin/login',     component: AdminLoginComponent },
  { path: 'admin/register',  component: AdminRegisterComponent, canActivate: [SuperAdminGuard] },
  { path: 'products', component: ProductListComponent, canActivate: [AuthGuard] },
  { path: 'cart', component: CartComponent, canActivate: [AuthGuard] },
  { path: 'orders', component: OrderListComponent, canActivate: [AuthGuard] },
  { path: 'dashboard', component: DashboardComponent, canActivate: [AuthGuard] },
  { path: 'quotations', component: QuotationComponent, canActivate: [AuthGuard] },
  { path: 'invoices', component: InvoiceListComponent, canActivate: [AuthGuard] },
  { path: 'admin/dashboard', component: DashboardComponent, canActivate: [AdminGuard] },
  { path: 'admin/products',   component: ProductManagementComponent,  canActivate: [AdminGuard] },
  { path: 'admin/inventory',  component: InventoryManagementComponent, canActivate: [AdminGuard] },
  { path: 'admin/users',      component: UserManagementComponent,      canActivate: [AdminGuard] },
  { path: 'admin/logs',       component: LogViewerComponent,           canActivate: [AdminGuard] },
  { path: '**', redirectTo: '/' }
];

@NgModule({
  imports: [RouterModule.forRoot(routes, { scrollPositionRestoration: 'top' })],
  exports: [RouterModule]
})
export class AppRoutingModule {}
