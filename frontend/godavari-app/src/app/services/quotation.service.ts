import { Injectable } from '@angular/core';
import { HttpClient, HttpParams } from '@angular/common/http';
import { Observable } from 'rxjs';
import { environment } from '../../environments/environment';

@Injectable({ providedIn: 'root' })
export class QuotationService {
  private base = `${environment.apiUrl}/quotations`;
  constructor(private http: HttpClient) {}

  create(payload: any): Observable<any> {
    return this.http.post<any>(this.base, payload);
  }

  // Backend: GET /quotations/user/{userId}
  getByUser(userId: number, page = 0, size = 20): Observable<any> {
    const params = new HttpParams().set('page', page).set('size', size);
    return this.http.get<any>(`${this.base}/user/${userId}`, { params });
  }

  // Backend: GET /quotations
  getAll(page = 0, size = 50): Observable<any> {
    const params = new HttpParams().set('page', page).set('size', size);
    return this.http.get<any>(this.base, { params });
  }

  // Backend: PUT /quotations/{id}/status?status=X&adminRemarks=Y
  updateStatus(id: number, status: string, adminRemarks?: string): Observable<any> {
    let params = new HttpParams().set('status', status);
    if (adminRemarks) params = params.set('adminRemarks', adminRemarks);
    return this.http.put<any>(`${this.base}/${id}/status`, null, { params });
  }

  getById(id: number): Observable<any> {
    return this.http.get<any>(`${this.base}/${id}`);
  }
}
