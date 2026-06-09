import { Injectable } from '@angular/core';
import { HttpClient, HttpParams } from '@angular/common/http';
import { Observable } from 'rxjs';
import { environment } from '../../environments/environment';

@Injectable({ providedIn: 'root' })
export class OrderService {
  private base = `${environment.apiUrl}/orders`;
  constructor(private http: HttpClient) {}

  create(order: any): Observable<any> { return this.http.post<any>(this.base, order); }
  getById(id: number): Observable<any> { return this.http.get<any>(`${this.base}/${id}`); }
  getByUser(userId: number, page = 0, size = 10): Observable<any> {
    const params = new HttpParams().set('page', page).set('size', size);
    return this.http.get<any>(`${this.base}/user/${userId}`, { params });
  }
  getAll(page = 0, size = 10): Observable<any> {
    const params = new HttpParams().set('page', page).set('size', size);
    return this.http.get<any>(this.base, { params });
  }
  updateStatus(id: number, status: string): Observable<any> {
    return this.http.put<any>(`${this.base}/${id}/status`, null, { params: { status } });
  }
  getDashboardStats(): Observable<any> { return this.http.get<any>(`${this.base}/dashboard/stats`); }
}
