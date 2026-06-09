import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Router } from '@angular/router';
import { BehaviorSubject, Observable, tap } from 'rxjs';
import { environment } from '../../environments/environment';

export interface AuthUser {
  token: string;
  email: string;
  name: string;
  role: string;
  userId: number;
  phone?: string;
}

@Injectable({ providedIn: 'root' })
export class AuthService {

  private currentUserSubject = new BehaviorSubject<AuthUser | null>(this.getStoredUser());
  currentUser$ = this.currentUserSubject.asObservable();

  constructor(private http: HttpClient, private router: Router) {}

  login(email: string, password: string): Observable<AuthUser> {
    return this.http.post<AuthUser>(`${environment.apiUrl}/auth/login`, { email, password })
      .pipe(tap(user => this.setUser(user)));
  }

  register(data: any): Observable<AuthUser> {
    return this.http.post<AuthUser>(`${environment.apiUrl}/auth/register`, data)
      .pipe(tap(user => this.setUser(user)));
  }

  // Register a new staff/admin without affecting current session
  registerStaff(data: any): Observable<any> {
    return this.http.post<any>(`${environment.apiUrl}/auth/register`, data);
  }

  logout(): void {
    const isAdmin = this.isManager;
    localStorage.removeItem('godavari_user');
    this.currentUserSubject.next(null);
    this.router.navigate([isAdmin ? '/admin/login' : '/login']);
  }

  get currentUser(): AuthUser | null { return this.currentUserSubject.value; }
  get token(): string | null { return this.currentUser?.token ?? null; }
  get role(): string | null { return this.currentUser?.role ?? null; }
  get isLoggedIn(): boolean { return !!this.currentUser; }
  get isAdmin(): boolean { return this.role === 'ADMIN'; }
  get isManager(): boolean { return ['ADMIN','MANAGER'].includes(this.role ?? ''); }

  private setUser(user: AuthUser): void {
    localStorage.setItem('godavari_user', JSON.stringify(user));
    this.currentUserSubject.next(user);
  }

  private getStoredUser(): AuthUser | null {
    const stored = localStorage.getItem('godavari_user');
    return stored ? JSON.parse(stored) : null;
  }
}
