import { Injectable } from '@angular/core';
import { BehaviorSubject } from 'rxjs';

export interface CartItem {
  productId: number;
  productName: string;
  sku: string;
  unit: string;
  unitPrice: number;
  gstRate: number;
  quantity: number;
  imageUrl: string;
}

@Injectable({ providedIn: 'root' })
export class CartService {

  private cartSubject = new BehaviorSubject<CartItem[]>(this.getStoredCart());
  cart$ = this.cartSubject.asObservable();

  get items(): CartItem[] { return this.cartSubject.value; }

  get totalItems(): number { return this.items.reduce((s, i) => s + i.quantity, 0); }

  get subtotal(): number {
    return this.items.reduce((s, i) => s + i.unitPrice * i.quantity, 0);
  }

  get totalGst(): number {
    return this.items.reduce((s, i) => s + (i.unitPrice * i.quantity * i.gstRate / 100), 0);
  }

  get deliveryCharge(): number { return this.subtotal >= 5000 ? 0 : 500; }

  get grandTotal(): number { return this.subtotal + this.totalGst + this.deliveryCharge; }

  addToCart(product: any, quantity = 1): void {
    const items = [...this.items];
    const idx = items.findIndex(i => i.productId === product.id);
    if (idx > -1) {
      items[idx] = { ...items[idx], quantity: items[idx].quantity + quantity };
    } else {
      items.push({
        productId: product.id,
        productName: product.name,
        sku: product.sku,
        unit: product.unit,
        unitPrice: product.price,
        gstRate: product.gstRate,
        quantity,
        imageUrl: product.imageUrl
      });
    }
    this.save(items);
  }

  updateQuantity(productId: number, quantity: number): void {
    if (quantity <= 0) { this.remove(productId); return; }
    const items = this.items.map(i => i.productId === productId ? { ...i, quantity } : i);
    this.save(items);
  }

  remove(productId: number): void {
    this.save(this.items.filter(i => i.productId !== productId));
  }

  clear(): void { this.save([]); }

  private save(items: CartItem[]): void {
    localStorage.setItem('godavari_cart', JSON.stringify(items));
    this.cartSubject.next(items);
  }

  private getStoredCart(): CartItem[] {
    const s = localStorage.getItem('godavari_cart');
    return s ? JSON.parse(s) : [];
  }
}
