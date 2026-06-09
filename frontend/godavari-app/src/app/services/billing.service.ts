import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { environment } from '../../environments/environment';

@Injectable({ providedIn: 'root' })
export class BillingService {
  private base = `${environment.apiUrl}/billing`;
  constructor(private http: HttpClient) {}

  generate(req: any): Observable<any> {
    return this.http.post<any>(`${this.base}/invoices`, req);
  }

  getAll(): Observable<any[]> {
    return this.http.get<any[]>(`${this.base}/invoices`);
  }

  getById(id: number): Observable<any> {
    return this.http.get<any>(`${this.base}/invoices/${id}`);
  }

  getByOrder(orderId: number): Observable<any> {
    return this.http.get<any>(`${this.base}/invoices/order/${orderId}`);
  }

  getByUser(userId: number): Observable<any[]> {
    return this.http.get<any[]>(`${this.base}/invoices/user/${userId}`);
  }

  markPaid(id: number): Observable<any> {
    return this.http.put<any>(`${this.base}/invoices/${id}/pay`, null);
  }
}
