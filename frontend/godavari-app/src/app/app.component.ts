import { Component } from '@angular/core';
import { Router, NavigationEnd } from '@angular/router';
import { AuthService } from './services/auth.service';
import { filter } from 'rxjs/operators';

@Component({
  selector: 'app-root',
  template: `
    <div class="app-shell">
      <app-navbar *ngIf="showShell"></app-navbar>
      <main [class.with-nav]="showShell">
        <router-outlet></router-outlet>
      </main>
      <app-footer *ngIf="showShell"></app-footer>
    </div>
  `,
  styles: [`
    .app-shell { min-height:100vh; display:flex; flex-direction:column; background:#f5f3f0; }
    main { flex:1; }
    main.with-nav { background:#f5f3f0; }
  `]
})
export class AppComponent {
  showShell = false;
  private publicRoutes   = ['/', '/login', '/register', '/admin/login'];
  private fullScreenRoutes = ['/admin/logs'];

  constructor(public auth: AuthService, private router: Router) {
    this.router.events.pipe(filter(e => e instanceof NavigationEnd))
      .subscribe((e: any) => {
        const url = e.urlAfterRedirects.split('?')[0];
        const isPublic     = this.publicRoutes.includes(url);
        const isFullScreen = this.fullScreenRoutes.includes(url);
        this.showShell = !isPublic && !isFullScreen && this.auth.isLoggedIn;
      });
  }
}
