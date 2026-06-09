import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { environment } from '../../environments/environment';

@Injectable({ providedIn: 'root' })
export class InventoryService {
  private base = `${environment.apiUrl}/inventory`;
  constructor(private http: HttpClient) {}

  getAll(): Observable<any[]> { return this.http.get<any[]>(this.base); }
  getByProduct(productId: number): Observable<any> { return this.http.get<any>(`${this.base}/product/${productId}`); }
  getLowStock(): Observable<any[]> { return this.http.get<any[]>(`${this.base}/low-stock`); }
  updateStock(req: any): Observable<any> { return this.http.post<any>(`${this.base}/update`, req); }
}
