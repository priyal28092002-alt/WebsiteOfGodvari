import { Component } from '@angular/core';

@Component({
  selector: 'app-footer',
  template: `
    <footer class="footer">
      <div class="footer-wave">
        <svg viewBox="0 0 1440 60" preserveAspectRatio="none">
          <path d="M0,30 C360,60 720,0 1080,30 C1260,45 1380,15 1440,30 L1440,60 L0,60 Z" fill="#050b1a"/>
        </svg>
      </div>
      <div class="footer-body">
        <div class="footer-grid">
          <div class="f-brand">
            <div class="f-logo">🏗️ <span>Godavari</span></div>
            <p class="f-sub">Building Materials · Nashik</p>
            <p class="f-desc">Your trusted partner for quality construction materials since 2009.</p>
            <div class="socials">
              <a class="soc">📘</a><a class="soc">📸</a><a class="soc">🐦</a><a class="soc">▶️</a>
            </div>
          </div>
          <div class="f-col">
            <h4>Products</h4>
            <a>Cement &amp; Concrete</a><a>TMT Steel Bars</a>
            <a>Tiles &amp; Flooring</a><a>Plumbing</a><a>Electrical</a>
          </div>
          <div class="f-col">
            <h4>Company</h4>
            <a routerLink="/login">Customer Login</a>
            <a routerLink="/register">Register</a>
            <a routerLink="/admin/login">Admin Login</a>
          </div>
          <div class="f-col">
            <h4>Contact</h4>
            <p>📍 Nashik, Maharashtra 422001</p>
            <p>📞 +91-XXXXX-XXXXX</p>
            <p>✉️ info&#64;godavari.com</p>
            <p>🕐 Mon–Sat: 8AM – 8PM</p>
          </div>
        </div>
        <div class="f-bottom">
          <p>&copy; 2025 Godavari Building Materials Nashik. All rights reserved.</p>
          <p>Made with ❤️ in Nashik</p>
        </div>
      </div>
    </footer>
  `,
  styles: [`
    .footer { position:relative; margin-top:40px; }
    .footer-wave svg { display:block; width:100%; height:50px; }
    .footer-body { background:#050b1a; border-top:1px solid rgba(255,255,255,0.06); padding:40px 24px 20px; }
    .footer-grid {
      max-width:1440px; margin:0 auto;
      display:grid; grid-template-columns:1.5fr 1fr 1fr 1fr; gap:36px;
    }
    .f-logo { font-size:20px; font-weight:800; color:white; display:flex; align-items:center; gap:8px; margin-bottom:7px; }
    .f-sub  { font-size:12px; color:rgba(255,255,255,0.35); margin-bottom:4px; }
    .f-desc { font-size:12px; color:rgba(255,255,255,0.3); line-height:1.7; margin-bottom:14px; }
    .socials { display:flex; gap:8px; }
    .soc {
      width:32px; height:32px; border-radius:8px;
      background:rgba(255,255,255,0.06); border:1px solid rgba(255,255,255,0.08);
      display:flex; align-items:center; justify-content:center;
      font-size:14px; cursor:pointer; text-decoration:none; transition:all .2s;
    }
    .soc:hover { background:rgba(255,255,255,0.12); border-color:rgba(255,255,255,0.18); }
    .f-col h4 { color:rgba(255,255,255,0.6); font-size:11px; font-weight:700; text-transform:uppercase; letter-spacing:1px; margin-bottom:14px; }
    .f-col a, .f-col p {
      display:block; color:rgba(255,255,255,0.35);
      font-size:13px; margin-bottom:9px;
      text-decoration:none; cursor:pointer; transition:color .2s;
    }
    .f-col a:hover { color:rgba(255,255,255,0.7); }
    .f-bottom {
      max-width:1440px; margin:28px auto 0;
      padding-top:18px; border-top:1px solid rgba(255,255,255,0.06);
      display:flex; justify-content:space-between; flex-wrap:wrap; gap:8px;
    }
    .f-bottom p { font-size:11px; color:rgba(255,255,255,0.2); }
    @media(max-width:768px) {
      .footer-grid { grid-template-columns:1fr 1fr; gap:24px; }
      .f-bottom { flex-direction:column; text-align:center; }
    }
    @media(max-width:480px) { .footer-grid { grid-template-columns:1fr; } }
  `]
})
export class FooterComponent {}
