import { Injectable } from '@angular/core';
import { HttpClient, HttpParams } from '@angular/common/http';
import { Observable } from 'rxjs';
import { environment } from '../../environments/environment';

@Injectable({ providedIn: 'root' })
export class ProductService {

  private base = `${environment.apiUrl}/products`;

  constructor(private http: HttpClient) {}

  getAll(page = 0, size = 12, sortBy = 'createdAt'): Observable<any> {
    const params = new HttpParams().set('page', page).set('size', size).set('sortBy', sortBy);
    return this.http.get<any>(this.base, { params });
  }

  getByCategory(categoryId: number, page = 0, size = 12): Observable<any> {
    const params = new HttpParams().set('categoryId', categoryId).set('page', page).set('size', size);
    return this.http.get<any>(this.base, { params });
  }

  search(query: string, page = 0, size = 12): Observable<any> {
    const params = new HttpParams().set('search', query).set('page', page).set('size', size);
    return this.http.get<any>(this.base, { params });
  }

  getById(id: number): Observable<any> {
    return this.http.get<any>(`${this.base}/${id}`);
  }

  getCategories(): Observable<any[]> {
    return this.http.get<any[]>(`${this.base}/categories`);
  }

  createCategory(category: any): Observable<any> {
    return this.http.post<any>(`${this.base}/categories`, category);
  }

  create(product: any): Observable<any> {
    return this.http.post<any>(this.base, product);
  }

  update(id: number, product: any): Observable<any> {
    return this.http.put<any>(`${this.base}/${id}`, product);
  }

  delete(id: number): Observable<any> {
    return this.http.delete<any>(`${this.base}/${id}`);
  }
}
