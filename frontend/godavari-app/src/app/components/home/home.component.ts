import { Component, OnInit, HostListener } from '@angular/core';
import { Router } from '@angular/router';
import { ProductService } from '../../services/product.service';

@Component({
  selector: 'app-home',
  template: `
    <div class="home-page" [class.dark]="darkMode">

      <!-- STICKY HEADER -->
      <header class="site-header" [class.scrolled]="scrolled">
        <div class="header-inner">
          <div class="logo" (click)="scrollTo('hero')">
            <div class="logo-box">🏗️</div>
            <div>
              <span class="logo-name">Godavari</span>
              <span class="logo-tag">Building Materials</span>
            </div>
          </div>
          <nav class="main-nav">
            <a (click)="scrollTo('categories')">Categories</a>
            <a (click)="scrollTo('products')">Products</a>
            <a (click)="scrollTo('brands')">Brands</a>
            <a (click)="scrollTo('why-us')">About</a>
            <a (click)="scrollTo('contact')">Contact</a>
          </nav>
          <div class="header-actions">
            <button class="icon-btn" (click)="toggleDark()">{{ darkMode ? '☀️' : '🌙' }}</button>
            <button class="outline-btn" routerLink="/login">Sign In</button>
            <button class="primary-btn" routerLink="/register">Get Started</button>
            <button class="ham-btn" (click)="mobileNav=!mobileNav">
              <span></span><span></span><span></span>
            </button>
          </div>
        </div>
        <div class="mobile-menu" [class.open]="mobileNav">
          <a (click)="scrollTo('categories');mobileNav=false">Categories</a>
          <a (click)="scrollTo('products');mobileNav=false">Products</a>
          <a (click)="scrollTo('brands');mobileNav=false">Brands</a>
          <a (click)="scrollTo('why-us');mobileNav=false">About</a>
          <a (click)="scrollTo('contact');mobileNav=false">Contact</a>
          <div class="mob-btns">
            <button routerLink="/login" (click)="mobileNav=false">Sign In</button>
            <button routerLink="/register" (click)="mobileNav=false" class="solid">Register</button>
          </div>
        </div>
      </header>

      <!-- 1. HERO -->
      <section class="hero" id="hero">
        <div class="hero-bg">
          <img src="https://images.unsplash.com/photo-1504307651254-35680f356dfd?w=1920&q=80" alt="Construction site">
          <div class="hero-overlay"></div>
        </div>
        <div class="hero-content">
          <div class="hero-badge"><span class="dot"></span> Nashik's #1 Building Materials Supplier</div>
          <h1 class="hero-title">Build Your Dream<br><span class="g-text">With Premium Materials</span></h1>
          <p class="hero-sub">Cement · Steel · Tiles · Plumbing · Electrical &amp; 500+ products at wholesale prices</p>
          <div class="hero-search">
            <mat-icon>search</mat-icon>
            <input [(ngModel)]="searchQ" placeholder="Search cement, TMT bars, tiles..." (keyup.enter)="goSearch()">
            <button (click)="goSearch()">Search</button>
          </div>
          <div class="hero-btns">
            <button class="hbtn solid" routerLink="/register"><mat-icon>storefront</mat-icon> Shop Now</button>
            <button class="hbtn ghost" (click)="scrollTo('products')"><mat-icon>play_circle</mat-icon> View Products</button>
          </div>
          <div class="hero-stats">
            <div class="hstat" *ngFor="let s of heroStats"><strong>{{s.val}}</strong><span>{{s.label}}</span></div>
          </div>
        </div>
        <div class="scroll-cue" (click)="scrollTo('categories')"><div class="arr"></div></div>
      </section>

      <!-- MARQUEE -->
      <div class="marquee-strip">
        <div class="marquee-track">
          <span *ngFor="let b of marquee">{{b}}</span>
          <span *ngFor="let b of marquee">{{b}}</span>
        </div>
      </div>

      <!-- 2. CATEGORIES BENTO -->
      <section class="sec" id="categories">
        <div class="inner">
          <div class="sec-label">What We Offer</div>
          <h2 class="sec-title">Browse by Category</h2>
          <p class="sec-sub">Everything you need for construction, renovation &amp; interiors</p>
          <div class="bento">
            <div *ngFor="let c of cats; let i=index" class="cat-card" [class.large]="i===0||i===3"
              [style.animation-delay.ms]="i*70" (click)="router.navigate(['/login'])">
              <img [src]="c.img" [alt]="c.name">
              <div class="cat-ov"></div>
              <div class="cat-info">
                <span class="cat-ico">{{c.icon}}</span>
                <h3>{{c.name}}</h3>
                <p>{{c.desc}}</p>
                <span class="cat-cnt">{{c.count}}</span>
              </div>
            </div>
          </div>
        </div>
      </section>

      <!-- 3. FEATURED PRODUCTS -->
      <section class="sec sec-alt" id="products">
        <div class="inner">
          <div class="sec-label">Best Sellers</div>
          <h2 class="sec-title">Featured Products</h2>
          <p class="sec-sub">Top-quality materials trusted by contractors across Nashik</p>
          <div *ngIf="loadingP" class="loading-wrap"><div class="spin"></div></div>
          <div class="prod-grid" *ngIf="!loadingP">
            <div *ngFor="let p of products; let i=index" class="pcard" [style.animation-delay.ms]="i*60">
              <div class="pimg">
                <img [src]="p.imageUrl" [alt]="p.name"
                  onerror="this.src='https://images.unsplash.com/photo-1589939705384-5185137a7f0f?w=400&q=80'">
                <span class="pbadge" *ngIf="p.bulkDiscountPercent>0">-{{p.bulkDiscountPercent}}%</span>
              </div>
              <div class="pbody">
                <span class="pcat">{{p.category?.name}}</span>
                <h4>{{p.name}}</h4>
                <div class="pstars">★★★★★ <span>({{p.reviewCount||0}})</span></div>
                <div class="pprice"><strong>₹{{p.price | number:'1.2-2'}}</strong><span>/{{p.unit}}</span></div>
                <button class="padd" routerLink="/login"><mat-icon>add_shopping_cart</mat-icon> Add to Cart</button>
              </div>
            </div>
          </div>
          <div class="center-btn">
            <button class="view-all" routerLink="/login">View All Products <mat-icon>arrow_forward</mat-icon></button>
          </div>
        </div>
      </section>

      <!-- 4. TOP BRANDS -->
      <section class="sec" id="brands">
        <div class="inner">
          <div class="sec-label">Our Partners</div>
          <h2 class="sec-title">Top Brands We Carry</h2>
          <p class="sec-sub">Authorized dealer for all major construction material brands</p>
          <div class="brands-grid">
            <div *ngFor="let b of brands; let i=index" class="brand-card" [style.animation-delay.ms]="i*50">
              <div class="b-ico">{{b.logo}}</div>
              <div class="b-name">{{b.name}}</div>
              <div class="b-cat">{{b.cat}}</div>
            </div>
          </div>
        </div>
      </section>

      <!-- 5. WHY CHOOSE US -->
      <section class="sec sec-alt" id="why-us">
        <div class="inner">
          <div class="sec-label">Our Advantage</div>
          <h2 class="sec-title">Why Choose Godavari?</h2>
          <p class="sec-sub">15+ years of trust, quality and service in Nashik</p>
          <div class="why-grid">
            <div *ngFor="let w of whyUs; let i=index" class="why-card" [style.animation-delay.ms]="i*70">
              <div class="why-ico" [style.background]="w.bg">{{w.icon}}</div>
              <h3>{{w.title}}</h3>
              <p>{{w.desc}}</p>
            </div>
          </div>
          <div class="cta-band">
            <div>
              <h3>Ready to Start Your Project?</h3>
              <p>Get competitive quotes · Free delivery above ₹5000</p>
            </div>
            <div class="cta-btns">
              <button routerLink="/register">Get Quote Now</button>
              <button class="cta-out" (click)="scrollTo('contact')">Contact Us</button>
            </div>
          </div>
        </div>
      </section>

      <!-- 6. TESTIMONIALS -->
      <section class="sec" id="testimonials">
        <div class="inner">
          <div class="sec-label">Customer Love</div>
          <h2 class="sec-title">What Our Customers Say</h2>
          <div class="testi-grid">
            <div *ngFor="let t of testis; let i=index" class="tcard" [style.animation-delay.ms]="i*80">
              <div class="tquote">"</div>
              <p>{{t.text}}</p>
              <div class="tstars">★★★★★</div>
              <div class="tauthor">
                <div class="tavatar">{{t.name.charAt(0)}}</div>
                <div><strong>{{t.name}}</strong><span>{{t.role}}</span></div>
              </div>
            </div>
          </div>
        </div>
      </section>

      <!-- 7. PROJECT GALLERY -->
      <section class="sec sec-alt" id="gallery">
        <div class="inner">
          <div class="sec-label">Our Work</div>
          <h2 class="sec-title">Project Gallery</h2>
          <p class="sec-sub">Real projects built with Godavari materials</p>
          <div class="gallery-grid">
            <div *ngFor="let g of gallery; let i=index" class="gitem"
              [class.tall]="i===0||i===4" [class.wide]="i===2" [style.animation-delay.ms]="i*60">
              <img [src]="g.url" [alt]="g.label">
              <div class="gov"><span>{{g.label}}</span></div>
            </div>
          </div>
        </div>
      </section>

      <!-- 8. CONTACT -->
      <section class="sec" id="contact">
        <div class="inner">
          <div class="sec-label">Get In Touch</div>
          <h2 class="sec-title">Contact &amp; Location</h2>
          <div class="contact-grid">
            <div class="cinfo">
              <div class="ccard" *ngFor="let c of contactInfo">
                <div class="cico">{{c.icon}}</div>
                <div><strong>{{c.label}}</strong><p>{{c.val}}</p></div>
              </div>
              <div class="hours">
                <h4>🕐 Business Hours</h4>
                <div *ngFor="let h of hours" class="hrow"><span>{{h.day}}</span><span>{{h.time}}</span></div>
              </div>
            </div>
            <div class="cform">
              <h3>Send Us a Message</h3>
              <input [(ngModel)]="cf.name" placeholder="Your Name">
              <input [(ngModel)]="cf.phone" placeholder="Phone Number">
              <input [(ngModel)]="cf.email" placeholder="Email Address" type="email">
              <textarea [(ngModel)]="cf.msg" placeholder="Your requirements..." rows="4"></textarea>
              <button (click)="send()"><mat-icon>send</mat-icon> Send Message</button>
            </div>
          </div>
        </div>
      </section>

      <!-- 9. FOOTER -->
      <footer class="footer">
        <div class="fw">
          <svg viewBox="0 0 1440 70" preserveAspectRatio="none"><path d="M0,35 C360,70 720,0 1080,35 C1260,52 1380,20 1440,35 L1440,70 L0,70 Z"/></svg>
        </div>
        <div class="fb">
          <div class="fg">
            <div class="fcol brand">
              <div class="flogo">🏗️ <span>Godavari</span></div>
              <p class="fsub">Building Materials Nashik</p>
              <p class="fdesc">Your trusted partner for quality construction materials since 2009.</p>
              <div class="socials"><a class="soc">📘</a><a class="soc">📸</a><a class="soc">🐦</a><a class="soc">▶️</a></div>
            </div>
            <div class="fcol">
              <h4>Products</h4>
              <a>Cement &amp; Concrete</a><a>TMT Steel Bars</a><a>Tiles &amp; Flooring</a>
              <a>Plumbing</a><a>Electrical</a><a>Paints</a>
            </div>
            <div class="fcol">
              <h4>Company</h4>
              <a routerLink="/login">Customer Login</a>
              <a routerLink="/register">Register</a>
              <a routerLink="/admin/login">Admin Login</a>
              <a (click)="scrollTo('contact')">Contact</a>
            </div>
            <div class="fcol">
              <h4>Contact</h4>
              <p>📍 Nashik, Maharashtra 422001</p>
              <p>📞 +91-XXXXX-XXXXX</p>
              <p>✉️ info&#64;godavari.com</p>
              <p>🕐 Mon–Sat: 8AM – 8PM</p>
            </div>
          </div>
          <div class="fbot">
            <p>&copy; 2025 Godavari Building Materials Nashik. All rights reserved.</p>
            <p>Made with ❤️ in Nashik</p>
          </div>
        </div>
      </footer>

    </div>
  `,
  styles: [`
    *{box-sizing:border-box;margin:0;padding:0;}
    .home-page{font-family:'Inter',sans-serif;background:#fff;color:#0f172a;transition:background .3s,color .3s;}
    .home-page.dark{background:#050b1a;color:#e2e8f0;}
    .home-page.dark .site-header{background:rgba(5,11,26,.95);border-color:rgba(255,255,255,.06);}
    .home-page.dark .sec-alt{background:#0a1628;}
    .home-page.dark .pcard,.home-page.dark .brand-card,.home-page.dark .why-card,
    .home-page.dark .tcard,.home-page.dark .ccard,.home-page.dark .hours,
    .home-page.dark .cform{background:#0f1f38;border-color:rgba(255,255,255,.07);}
    .home-page.dark .cform input,.home-page.dark .cform textarea{background:rgba(255,255,255,.04);border-color:rgba(255,255,255,.1);color:#e2e8f0;}

    /* HEADER */
    .site-header{position:fixed;top:0;left:0;right:0;z-index:1000;
      background:rgba(255,255,255,.88);backdrop-filter:blur(20px);
      border-bottom:1px solid rgba(0,0,0,.06);transition:all .3s;}
    .site-header.scrolled{box-shadow:0 4px 24px rgba(0,0,0,.08);}
    .header-inner{max-width:1400px;margin:0 auto;display:flex;align-items:center;
      padding:13px 24px;gap:28px;}
    .logo{display:flex;align-items:center;gap:10px;cursor:pointer;flex-shrink:0;}
    .logo-box{width:42px;height:42px;background:linear-gradient(135deg,#1a237e,#3949ab);
      border-radius:10px;display:flex;align-items:center;justify-content:center;
      font-size:22px;box-shadow:0 4px 12px rgba(26,35,126,.3);}
    .logo-name{display:block;font-size:18px;font-weight:800;color:#1a237e;line-height:1.2;}
    .logo-tag{display:block;font-size:11px;color:#64748b;line-height:1.2;}
    .main-nav{display:flex;gap:2px;flex:1;justify-content:center;}
    .main-nav a{padding:8px 15px;border-radius:8px;font-size:14px;font-weight:500;
      color:#475569;cursor:pointer;transition:all .2s;text-decoration:none;}
    .main-nav a:hover{background:#f1f5f9;color:#1a237e;}
    .header-actions{display:flex;align-items:center;gap:10px;}
    .icon-btn{width:38px;height:38px;background:#f1f5f9;border:none;border-radius:8px;
      cursor:pointer;font-size:18px;display:flex;align-items:center;justify-content:center;}
    .icon-btn:hover{background:#e2e8f0;}
    .outline-btn{padding:8px 18px;border-radius:8px;border:2px solid #1a237e;color:#1a237e;
      background:none;font-size:14px;font-weight:600;cursor:pointer;transition:all .2s;}
    .outline-btn:hover{background:#f0f2ff;}
    .primary-btn{padding:9px 18px;border-radius:8px;
      background:linear-gradient(135deg,#1a237e,#3949ab);color:#fff;border:none;
      font-size:14px;font-weight:600;cursor:pointer;
      box-shadow:0 4px 12px rgba(26,35,126,.3);transition:all .2s;}
    .primary-btn:hover{transform:translateY(-1px);box-shadow:0 6px 16px rgba(26,35,126,.4);}
    .ham-btn{display:none;flex-direction:column;gap:5px;background:none;border:none;cursor:pointer;padding:4px;}
    .ham-btn span{display:block;width:22px;height:2px;background:#475569;border-radius:2px;transition:all .3s;}
    .mobile-menu{display:none;flex-direction:column;background:#fff;
      border-top:1px solid #f1f5f9;padding:16px 24px;}
    .mobile-menu.open{display:flex;}
    .mobile-menu a{padding:12px 0;font-size:15px;font-weight:500;color:#475569;
      cursor:pointer;border-bottom:1px solid #f8fafc;text-decoration:none;}
    .mob-btns{display:flex;gap:10px;margin-top:16px;}
    .mob-btns button{flex:1;padding:12px;border-radius:8px;border:2px solid #1a237e;
      color:#1a237e;background:none;font-size:14px;font-weight:600;cursor:pointer;}
    .mob-btns button.solid{background:#1a237e;color:#fff;}

    /* HERO */
    .hero{min-height:100vh;display:flex;align-items:center;justify-content:center;
      position:relative;overflow:hidden;padding:100px 24px 60px;}
    .hero-bg{position:absolute;inset:0;z-index:0;}
    .hero-bg img{width:100%;height:100%;object-fit:cover;
      animation:bgZ 20s ease-in-out infinite alternate;}
    @keyframes bgZ{from{transform:scale(1);}to{transform:scale(1.06);}}
    .hero-overlay{position:absolute;inset:0;
      background:linear-gradient(135deg,rgba(10,15,40,.88) 0%,rgba(26,35,126,.75) 50%,rgba(10,15,40,.82) 100%);}
    .hero-content{position:relative;z-index:2;max-width:800px;text-align:center;
      animation:fuUp .8s ease-out both;}
    @keyframes fuUp{from{opacity:0;transform:translateY(30px);}to{opacity:1;transform:translateY(0);}}
    .hero-badge{display:inline-flex;align-items:center;gap:8px;
      background:rgba(255,255,255,.1);border:1px solid rgba(255,255,255,.2);
      color:rgba(255,255,255,.9);padding:8px 20px;border-radius:40px;
      font-size:13px;font-weight:500;margin-bottom:24px;backdrop-filter:blur(10px);}
    .dot{width:8px;height:8px;border-radius:50%;background:#4ade80;
      box-shadow:0 0 8px #4ade80;animation:blink 2s infinite;}
    @keyframes blink{0%,100%{opacity:1;}50%{opacity:.3;}}
    .hero-title{font-size:clamp(2.4rem,6vw,4.4rem);font-weight:900;color:#fff;
      line-height:1.15;margin-bottom:20px;letter-spacing:-1px;}
    .g-text{background:linear-gradient(135deg,#60a5fa,#a78bfa,#34d399);
      -webkit-background-clip:text;-webkit-text-fill-color:transparent;background-clip:text;}
    .hero-sub{font-size:18px;color:rgba(255,255,255,.75);margin-bottom:32px;line-height:1.6;}
    .hero-search{display:flex;align-items:center;gap:12px;
      background:rgba(255,255,255,.1);border:2px solid rgba(255,255,255,.2);
      border-radius:50px;padding:8px 8px 8px 20px;margin:0 auto 28px;max-width:520px;
      backdrop-filter:blur(10px);transition:all .2s;}
    .hero-search:focus-within{border-color:rgba(255,255,255,.5);background:rgba(255,255,255,.15);}
    .hero-search mat-icon{color:rgba(255,255,255,.6);flex-shrink:0;}
    .hero-search input{flex:1;background:none;border:none;outline:none;color:#fff;
      font-size:15px;font-family:'Inter',sans-serif;}
    .hero-search input::placeholder{color:rgba(255,255,255,.4);}
    .hero-search button{background:linear-gradient(135deg,#ff6d00,#ff9100);color:#fff;
      border:none;padding:10px 24px;border-radius:40px;font-size:14px;font-weight:700;
      cursor:pointer;transition:transform .2s;}
    .hero-search button:hover{transform:scale(1.04);}
    .hero-btns{display:flex;gap:14px;justify-content:center;flex-wrap:wrap;margin-bottom:40px;}
    .hbtn{display:flex;align-items:center;gap:8px;padding:14px 32px;border-radius:12px;
      font-size:15px;font-weight:700;cursor:pointer;transition:all .2s;border:none;
      font-family:'Inter',sans-serif;}
    .hbtn.solid{background:linear-gradient(135deg,#ff6d00,#ff9100);color:#fff;
      box-shadow:0 8px 24px rgba(255,109,0,.4);}
    .hbtn.solid:hover{transform:translateY(-3px);box-shadow:0 12px 32px rgba(255,109,0,.5);}
    .hbtn.ghost{background:rgba(255,255,255,.1);color:#fff;
      border:2px solid rgba(255,255,255,.3);backdrop-filter:blur(10px);}
    .hbtn.ghost:hover{background:rgba(255,255,255,.2);}
    .hero-stats{display:flex;gap:32px;justify-content:center;flex-wrap:wrap;}
    .hstat{text-align:center;}
    .hstat strong{display:block;font-size:28px;font-weight:800;color:#fff;}
    .hstat span{font-size:13px;color:rgba(255,255,255,.55);}
    .scroll-cue{position:absolute;bottom:32px;left:50%;transform:translateX(-50%);
      cursor:pointer;z-index:2;}
    .arr{width:26px;height:26px;border-right:2px solid rgba(255,255,255,.6);
      border-bottom:2px solid rgba(255,255,255,.6);transform:rotate(45deg);
      animation:bounce 2s infinite;}
    @keyframes bounce{0%,100%{transform:rotate(45deg) translateY(0);}50%{transform:rotate(45deg) translateY(6px);}}

    /* MARQUEE */
    .marquee-strip{background:linear-gradient(135deg,#1a237e,#3949ab);overflow:hidden;padding:14px 0;}
    .marquee-track{display:flex;gap:0;animation:marq 25s linear infinite;white-space:nowrap;}
    .marquee-track span{color:rgba(255,255,255,.8);font-size:14px;font-weight:600;
      padding:0 24px;border-right:1px solid rgba(255,255,255,.2);}
    @keyframes marq{from{transform:translateX(0);}to{transform:translateX(-50%);}}

    /* SECTIONS */
    .sec{padding:88px 24px;}
    .sec-alt{background:#f8fafc;}
    .inner{max-width:1400px;margin:0 auto;}
    .sec-label{display:inline-block;color:#1a237e;font-size:12px;font-weight:700;
      text-transform:uppercase;letter-spacing:2px;background:#e8eaf6;
      padding:5px 14px;border-radius:20px;margin-bottom:12px;}
    .sec-title{font-size:clamp(1.8rem,4vw,2.8rem);font-weight:800;margin-bottom:10px;letter-spacing:-.5px;}
    .sec-sub{font-size:16px;color:#64748b;margin-bottom:44px;}

    /* BENTO CATEGORIES */
    .bento{display:grid;grid-template-columns:repeat(4,1fr);grid-template-rows:repeat(2,240px);gap:14px;}
    .cat-card{position:relative;overflow:hidden;border-radius:16px;cursor:pointer;
      animation:fuUp .5s ease-out both;}
    .cat-card.large{grid-row:span 2;}
    .cat-card img{width:100%;height:100%;object-fit:cover;transition:transform .5s;}
    .cat-card:hover img{transform:scale(1.08);}
    .cat-ov{position:absolute;inset:0;
      background:linear-gradient(to top,rgba(0,0,0,.75) 0%,transparent 60%);transition:.3s;}
    .cat-card:hover .cat-ov{background:linear-gradient(to top,rgba(26,35,126,.8) 0%,rgba(0,0,0,.3) 60%);}
    .cat-info{position:absolute;bottom:0;left:0;right:0;padding:18px;}
    .cat-ico{font-size:26px;display:block;margin-bottom:6px;}
    .cat-info h3{color:#fff;font-size:17px;font-weight:700;margin-bottom:4px;}
    .cat-info p{color:rgba(255,255,255,.75);font-size:11px;}
    .cat-cnt{display:inline-block;margin-top:7px;background:rgba(255,255,255,.2);
      color:#fff;padding:3px 10px;border-radius:12px;font-size:11px;}

    /* PRODUCTS */
    .loading-wrap{text-align:center;padding:60px;}
    .spin{width:48px;height:48px;border-radius:50%;border:4px solid #e2e8f0;
      border-top-color:#1a237e;margin:0 auto;animation:spin .8s linear infinite;}
    @keyframes spin{to{transform:rotate(360deg);}}
    .prod-grid{display:grid;grid-template-columns:repeat(auto-fill,minmax(250px,1fr));gap:22px;}
    .pcard{background:#fff;border-radius:16px;border:1px solid #e2e8f0;overflow:hidden;
      transition:all .3s;animation:fuUp .5s ease-out both;}
    .pcard:hover{transform:translateY(-8px);box-shadow:0 20px 40px rgba(0,0,0,.1);border-color:transparent;}
    .pimg{position:relative;height:195px;overflow:hidden;}
    .pimg img{width:100%;height:100%;object-fit:cover;transition:transform .4s;}
    .pcard:hover .pimg img{transform:scale(1.06);}
    .pbadge{position:absolute;top:12px;right:12px;background:#ef4444;color:#fff;
      padding:4px 10px;border-radius:6px;font-size:12px;font-weight:700;}
    .pbody{padding:16px;}
    .pcat{font-size:11px;font-weight:700;text-transform:uppercase;color:#1a237e;
      background:#e8eaf6;padding:3px 10px;border-radius:12px;}
    .pbody h4{font-size:14px;font-weight:600;color:#1a1a2e;margin:10px 0 6px;}
    .pstars{color:#f59e0b;font-size:13px;margin-bottom:8px;}
    .pstars span{color:#94a3b8;font-size:12px;}
    .pprice strong{font-size:20px;font-weight:800;color:#1a237e;}
    .pprice span{font-size:13px;color:#64748b;}
    .padd{display:flex;align-items:center;justify-content:center;gap:7px;width:100%;
      margin-top:12px;padding:11px;background:linear-gradient(135deg,#1a237e,#3949ab);
      color:#fff;border:none;border-radius:10px;font-size:14px;font-weight:600;
      cursor:pointer;transition:all .2s;font-family:'Inter',sans-serif;}
    .padd:hover{transform:translateY(-2px);box-shadow:0 6px 16px rgba(26,35,126,.35);}
    .center-btn{text-align:center;margin-top:40px;}
    .view-all{display:inline-flex;align-items:center;gap:8px;padding:14px 32px;
      border-radius:12px;background:transparent;border:2px solid #1a237e;color:#1a237e;
      font-size:15px;font-weight:700;cursor:pointer;transition:all .2s;
      font-family:'Inter',sans-serif;}
    .view-all:hover{background:#1a237e;color:#fff;}

    /* BRANDS */
    .brands-grid{display:grid;grid-template-columns:repeat(auto-fill,minmax(150px,1fr));gap:14px;}
    .brand-card{background:#fff;border:1px solid #e2e8f0;border-radius:14px;padding:22px 14px;
      text-align:center;cursor:pointer;transition:all .25s;animation:fuUp .4s ease-out both;}
    .brand-card:hover{border-color:#1a237e;box-shadow:0 8px 24px rgba(26,35,126,.1);transform:translateY(-4px);}
    .b-ico{font-size:34px;margin-bottom:10px;}
    .b-name{font-size:14px;font-weight:700;color:#1a1a2e;}
    .b-cat{font-size:11px;color:#64748b;margin-top:4px;}

    /* WHY US */
    .why-grid{display:grid;grid-template-columns:repeat(auto-fill,minmax(230px,1fr));gap:18px;margin-bottom:36px;}
    .why-card{background:#fff;border-radius:16px;padding:26px 22px;text-align:center;
      border:1px solid #e2e8f0;transition:all .25s;animation:fuUp .5s ease-out both;}
    .why-card:hover{transform:translateY(-6px);box-shadow:0 16px 40px rgba(0,0,0,.08);border-color:transparent;}
    .why-ico{width:62px;height:62px;border-radius:18px;font-size:28px;
      display:flex;align-items:center;justify-content:center;margin:0 auto 14px;}
    .why-card h3{font-size:16px;font-weight:700;margin-bottom:8px;}
    .why-card p{font-size:13px;color:#64748b;line-height:1.6;}
    .cta-band{background:linear-gradient(135deg,#1a237e,#3949ab,#5c6bc0);border-radius:20px;
      padding:38px 44px;display:flex;align-items:center;justify-content:space-between;
      gap:20px;flex-wrap:wrap;}
    .cta-band h3{font-size:22px;font-weight:800;color:#fff;margin-bottom:5px;}
    .cta-band p{color:rgba(255,255,255,.75);font-size:14px;}
    .cta-btns{display:flex;gap:12px;flex-wrap:wrap;}
    .cta-btns button{padding:12px 26px;border-radius:10px;background:#ff6d00;color:#fff;
      border:none;font-size:14px;font-weight:700;cursor:pointer;transition:all .2s;
      font-family:'Inter',sans-serif;}
    .cta-btns button:hover{transform:translateY(-2px);}
    .cta-out{background:rgba(255,255,255,.12)!important;
      border:2px solid rgba(255,255,255,.3)!important;}

    /* TESTIMONIALS */
    .testi-grid{display:grid;grid-template-columns:repeat(auto-fill,minmax(290px,1fr));gap:18px;}
    .tcard{background:#fff;border:1px solid #e2e8f0;border-radius:16px;padding:26px 22px;
      position:relative;overflow:hidden;animation:fuUp .5s ease-out both;transition:all .25s;}
    .tcard:hover{transform:translateY(-4px);box-shadow:0 12px 32px rgba(0,0,0,.07);}
    .tquote{position:absolute;top:12px;right:18px;font-size:70px;font-weight:900;color:#e8eaf6;line-height:1;}
    .tcard p{font-size:14px;line-height:1.7;color:#475569;margin-bottom:14px;}
    .tstars{color:#f59e0b;font-size:16px;margin-bottom:14px;}
    .tauthor{display:flex;align-items:center;gap:12px;}
    .tavatar{width:42px;height:42px;border-radius:50%;
      background:linear-gradient(135deg,#1a237e,#3949ab);color:#fff;
      font-size:18px;font-weight:700;display:flex;align-items:center;justify-content:center;flex-shrink:0;}
    .tauthor strong{display:block;font-size:14px;font-weight:700;}
    .tauthor span{font-size:12px;color:#64748b;}

    /* GALLERY */
    .gallery-grid{display:grid;grid-template-columns:repeat(4,1fr);grid-auto-rows:200px;gap:12px;}
    .gitem{border-radius:12px;overflow:hidden;position:relative;cursor:pointer;animation:fuUp .4s ease-out both;}
    .gitem.tall{grid-row:span 2;}
    .gitem.wide{grid-column:span 2;}
    .gitem img{width:100%;height:100%;object-fit:cover;transition:transform .5s;}
    .gitem:hover img{transform:scale(1.08);}
    .gov{position:absolute;inset:0;background:linear-gradient(to top,rgba(0,0,0,.65) 0%,transparent 60%);
      opacity:0;transition:.3s;display:flex;align-items:flex-end;padding:14px;}
    .gitem:hover .gov{opacity:1;}
    .gov span{color:#fff;font-size:13px;font-weight:600;}

    /* CONTACT */
    .contact-grid{display:grid;grid-template-columns:1fr 1.3fr;gap:36px;}
    .ccard{display:flex;align-items:flex-start;gap:14px;background:#fff;
      border:1px solid #e2e8f0;border-radius:14px;padding:18px;margin-bottom:12px;}
    .cico{font-size:26px;}
    .ccard strong{display:block;font-size:14px;font-weight:700;}
    .ccard p{font-size:13px;color:#64748b;margin-top:2px;}
    .hours{background:#fff;border:1px solid #e2e8f0;border-radius:14px;padding:18px;}
    .hours h4{font-size:15px;font-weight:700;margin-bottom:12px;}
    .hrow{display:flex;justify-content:space-between;font-size:13px;padding:7px 0;
      border-bottom:1px solid #f8fafc;color:#475569;}
    .cform{background:#fff;border:1px solid #e2e8f0;border-radius:16px;padding:30px;}
    .cform h3{font-size:20px;font-weight:700;margin-bottom:22px;}
    .cform input,.cform textarea{display:block;width:100%;padding:12px 15px;
      border:2px solid #e2e8f0;border-radius:10px;font-size:14px;
      font-family:'Inter',sans-serif;outline:none;transition:all .2s;
      background:#fafafa;margin-bottom:12px;}
    .cform input:focus,.cform textarea:focus{border-color:#1a237e;background:#fff;
      box-shadow:0 0 0 4px rgba(26,35,126,.08);}
    .cform textarea{resize:vertical;}
    .cform button{display:flex;align-items:center;gap:8px;width:100%;padding:13px;
      background:linear-gradient(135deg,#1a237e,#3949ab);color:#fff;border:none;
      border-radius:10px;font-size:15px;font-weight:700;cursor:pointer;
      justify-content:center;transition:all .2s;font-family:'Inter',sans-serif;
      box-shadow:0 6px 16px rgba(26,35,126,.3);}
    .cform button:hover{transform:translateY(-2px);box-shadow:0 10px 24px rgba(26,35,126,.4);}

    /* FOOTER */
    .footer{position:relative;margin-top:20px;}
    .fw svg{display:block;width:100%;height:60px;fill:#0d1642;}
    .fb{background:#0d1642;color:rgba(255,255,255,.75);padding:44px 24px 20px;}
    .fg{max-width:1400px;margin:0 auto;display:grid;
      grid-template-columns:1.5fr 1fr 1fr 1fr;gap:36px;}
    .flogo{font-size:22px;font-weight:800;color:#fff;display:flex;align-items:center;gap:8px;margin-bottom:7px;}
    .flogo span{color:#fff;}
    .fsub{font-size:13px;color:rgba(255,255,255,.5);margin-bottom:3px;}
    .fdesc{font-size:13px;color:rgba(255,255,255,.4);line-height:1.6;margin-bottom:14px;}
    .socials{display:flex;gap:9px;}
    .soc{width:34px;height:34px;border-radius:8px;background:rgba(255,255,255,.08);
      border:1px solid rgba(255,255,255,.1);display:flex;align-items:center;justify-content:center;
      font-size:15px;cursor:pointer;text-decoration:none;transition:all .2s;}
    .soc:hover{background:rgba(255,255,255,.15);}
    .fcol h4{color:#fff;font-size:13px;font-weight:700;margin-bottom:14px;
      text-transform:uppercase;letter-spacing:.5px;}
    .fcol a,.fcol p{display:block;color:rgba(255,255,255,.5);font-size:13px;
      margin-bottom:9px;text-decoration:none;cursor:pointer;transition:color .2s;}
    .fcol a:hover{color:#60a5fa;}
    .fbot{max-width:1400px;margin:28px auto 0;padding-top:18px;
      border-top:1px solid rgba(255,255,255,.07);display:flex;
      justify-content:space-between;align-items:center;flex-wrap:wrap;gap:8px;}
    .fbot p{font-size:12px;color:rgba(255,255,255,.3);}

    /* RESPONSIVE */
    @media(max-width:1024px){
      .bento{grid-template-columns:repeat(3,1fr);grid-template-rows:auto;}
      .cat-card.large{grid-row:span 1;}
      .gallery-grid{grid-template-columns:repeat(3,1fr);}
      .contact-grid{grid-template-columns:1fr;}
      .fg{grid-template-columns:1fr 1fr;}
    }
    @media(max-width:768px){
      .main-nav,.outline-btn,.primary-btn{display:none;}
      .ham-btn{display:flex;}
      .bento{grid-template-columns:1fr 1fr;}
      .gallery-grid{grid-template-columns:1fr 1fr;}
      .cta-band{flex-direction:column;padding:26px;}
      .fg{grid-template-columns:1fr 1fr;gap:22px;}
      .hero-title{font-size:2.2rem;}
    }
    @media(max-width:480px){
      .bento{grid-template-columns:1fr;}
      .gallery-grid{grid-template-columns:1fr;}
      .gitem.wide,.gitem.tall{grid-column:span 1;grid-row:span 1;}
      .fg{grid-template-columns:1fr;}
      .hero-stats{gap:18px;}
      .hero-title{font-size:1.9rem;}
    }
  `]
})
export class HomeComponent implements OnInit {
  scrolled = false;
  darkMode = false;
  mobileNav = false;
  searchQ = '';
  loadingP = false;
  products: any[] = [];
  cf = { name:'', phone:'', email:'', msg:'' };

  heroStats = [
    { val:'500+', label:'Products' },
    { val:'5000+', label:'Happy Customers' },
    { val:'15+', label:'Years Experience' },
    { val:'50+', label:'Top Brands' }
  ];

  marquee = [
    '🏗️ UltraTech Cement','🔩 Tata Steel','🎨 Asian Paints','🪟 Kajaria Tiles',
    '🔧 Supreme Pipes','⚡ Havells','🧱 ACC Cement','💧 Finolex','🏠 Astral',
    '🔑 Godrej Hardware','🌿 Berger Paints','🏭 JSW Steel'
  ];

  cats = [
    { name:'Cement & Concrete', icon:'🧱', desc:'OPC, PPC, Ready Mix', count:'45 products',
      img:'https://images.unsplash.com/photo-1578662996442-48f60103fc96?w=600&q=80' },
    { name:'TMT Steel Bars', icon:'🔩', desc:'Fe500, Fe550 grade', count:'30 products',
      img:'https://images.unsplash.com/photo-1621905252507-b35492cc74b4?w=600&q=80' },
    { name:'Tiles & Flooring', icon:'🪟', desc:'Ceramic, Vitrified, Marble', count:'120 products',
      img:'https://images.unsplash.com/photo-1615873968403-89e068629265?w=600&q=80' },
    { name:'Plumbing & Pipes', icon:'🔧', desc:'CPVC, PVC, PPR pipes', count:'80 products',
      img:'https://images.unsplash.com/photo-1504917595217-d4dc5ebe6122?w=600&q=80' },
    { name:'Electrical', icon:'⚡', desc:'Wires, switches, MCBs', count:'95 products',
      img:'https://images.unsplash.com/photo-1558618666-fcd25c85cd64?w=600&q=80' },
    { name:'Paints & Finishes', icon:'🎨', desc:'Interior & exterior paints', count:'65 products',
      img:'https://images.unsplash.com/photo-1589939705384-5185137a7f0f?w=600&q=80' },
    { name:'Sand & Aggregates', icon:'🏔️', desc:'River sand, stone chips', count:'20 products',
      img:'https://images.unsplash.com/photo-1567604130957-b90aa01d1e7e?w=600&q=80' },
    { name:'Hardware & Tools', icon:'🔨', desc:'Power tools, fasteners', count:'150 products',
      img:'https://images.unsplash.com/photo-1530124566582-a618bc2615dc?w=600&q=80' }
  ];

  brands = [
    { logo:'🏭', name:'UltraTech', cat:'Cement' },{ logo:'⚙️', name:'Tata Steel', cat:'TMT Bars' },
    { logo:'🎨', name:'Asian Paints', cat:'Paints' },{ logo:'🪟', name:'Kajaria', cat:'Tiles' },
    { logo:'🔧', name:'Supreme', cat:'Pipes' },{ logo:'⚡', name:'Havells', cat:'Electrical' },
    { logo:'🧱', name:'ACC', cat:'Cement' },{ logo:'💧', name:'Finolex', cat:'Plumbing' },
    { logo:'🏠', name:'Astral', cat:'Pipes' },{ logo:'🔑', name:'Godrej', cat:'Hardware' },
    { logo:'🌿', name:'Berger', cat:'Paints' },{ logo:'🏭', name:'JSW', cat:'Steel' }
  ];

  whyUs = [
    { icon:'✅', title:'ISI Certified Quality', desc:'All products meet BIS standards. We never compromise on quality.', bg:'linear-gradient(135deg,#dcfce7,#bbf7d0)' },
    { icon:'💰', title:'Wholesale Prices', desc:'Direct from manufacturers. Best prices with bulk discounts.', bg:'linear-gradient(135deg,#fef9c3,#fde047)' },
    { icon:'🚚', title:'Fast Delivery', desc:'Same-day delivery in Nashik. On-site delivery across Maharashtra.', bg:'linear-gradient(135deg,#dbeafe,#93c5fd)' },
    { icon:'🏆', title:'15+ Years Trust', desc:'Serving contractors & builders since 2009. 5000+ happy customers.', bg:'linear-gradient(135deg,#fce7f3,#fbcfe8)' },
    { icon:'📋', title:'GST Invoicing', desc:'Proper GST bills for all purchases. Easy for your accounting.', bg:'linear-gradient(135deg,#ede9fe,#c4b5fd)' },
    { icon:'🤝', title:'Expert Support', desc:'Our team helps you choose the right materials for your project.', bg:'linear-gradient(135deg,#ffedd5,#fdba74)' }
  ];

  testis = [
    { text:'Best quality cement and steel in Nashik. I have been buying from Godavari for 8 years. Prices are unbeatable and delivery is always on time.', name:'Rajesh Patil', role:'Civil Contractor, Nashik' },
    { text:'The online ordering system is very convenient. I can place bulk orders and get instant quotations. Saved me a lot of time and money.', name:'Suresh Sharma', role:'Builder, Nashik' },
    { text:'Excellent customer service and genuine products. UltraTech cement prices here are cheaper than anywhere else in the city.', name:'Priya Deshmukh', role:'Interior Designer' },
    { text:'I needed tiles for my entire apartment renovation. Godavari had the best collection at great prices. Highly recommended!', name:'Amit Kulkarni', role:'Homeowner, Nashik' },
    { text:'Fast delivery and quality products. The quotation system helps me plan my project budget accurately.', name:'Ramesh Jadhav', role:'Contractor' },
    { text:'Their TMT steel quality is excellent. All products come with proper certification. My go-to supplier for all projects.', name:'Mohan Bhosale', role:'Civil Engineer' }
  ];

  gallery = [
    { url:'https://images.unsplash.com/photo-1486325212027-8081e485255e?w=600&q=80', label:'Commercial Building' },
    { url:'https://images.unsplash.com/photo-1558618666-fcd25c85cd64?w=400&q=80', label:'Electrical Works' },
    { url:'https://images.unsplash.com/photo-1504307651254-35680f356dfd?w=800&q=80', label:'Construction Site' },
    { url:'https://images.unsplash.com/photo-1615873968403-89e068629265?w=400&q=80', label:'Tile Flooring' },
    { url:'https://images.unsplash.com/photo-1503387762-592deb58ef4e?w=600&q=80', label:'Villa Project' },
    { url:'https://images.unsplash.com/photo-1541888946425-d81bb19240f5?w=400&q=80', label:'Foundation Work' },
    { url:'https://images.unsplash.com/photo-1589939705384-5185137a7f0f?w=400&q=80', label:'Interior Painting' }
  ];

  contactInfo = [
    { icon:'📍', label:'Address', val:'Near MIDC, Nashik - 422010, Maharashtra' },
    { icon:'📞', label:'Phone', val:'+91-XXXXX-XXXXX (8AM-8PM)' },
    { icon:'✉️', label:'Email', val:'info@godavari.com' },
    { icon:'🚚', label:'Free Delivery', val:'Above ₹5000 within Nashik' }
  ];

  hours = [
    { day:'Monday – Friday', time:'8:00 AM – 8:00 PM' },
    { day:'Saturday', time:'8:00 AM – 6:00 PM' },
    { day:'Sunday', time:'10:00 AM – 4:00 PM' }
  ];

  constructor(private productService: ProductService, public router: Router) {}

  ngOnInit(): void { this.loadProducts(); }

  @HostListener('window:scroll')
  onScroll(): void { this.scrolled = window.scrollY > 50; }

  loadProducts(): void {
    this.loadingP = true;
    this.productService.getAll(0, 8).subscribe({
      next: (d: any) => { this.products = d.content?.length ? d.content : this.placeholders(); this.loadingP = false; },
      error: () => { this.products = this.placeholders(); this.loadingP = false; }
    });
  }

  placeholders(): any[] {
    return [
      { name:'UltraTech OPC 53 Cement', price:380, unit:'bag', gstRate:28, reviewCount:245, bulkDiscountPercent:5, category:{name:'Cement'}, imageUrl:'https://images.unsplash.com/photo-1578662996442-48f60103fc96?w=400&q=80' },
      { name:'Tata Tiscon Fe500 TMT Bar 10mm', price:65, unit:'kg', gstRate:18, reviewCount:189, bulkDiscountPercent:8, category:{name:'Steel'}, imageUrl:'https://images.unsplash.com/photo-1621905252507-b35492cc74b4?w=400&q=80' },
      { name:'Kajaria Vitrified Tile 600x600', price:48, unit:'sq ft', gstRate:18, reviewCount:312, bulkDiscountPercent:10, category:{name:'Tiles'}, imageUrl:'https://images.unsplash.com/photo-1615873968403-89e068629265?w=400&q=80' },
      { name:'Asian Paints Royale Emulsion 20L', price:3200, unit:'can', gstRate:18, reviewCount:156, bulkDiscountPercent:0, category:{name:'Paints'}, imageUrl:'https://images.unsplash.com/photo-1589939705384-5185137a7f0f?w=400&q=80' },
      { name:'Supreme CPVC Pipe 1 inch', price:185, unit:'piece', gstRate:18, reviewCount:98, bulkDiscountPercent:5, category:{name:'Plumbing'}, imageUrl:'https://images.unsplash.com/photo-1504917595217-d4dc5ebe6122?w=400&q=80' },
      { name:'Havells 2.5 sq mm Wire 90m', price:1250, unit:'roll', gstRate:18, reviewCount:203, bulkDiscountPercent:0, category:{name:'Electrical'}, imageUrl:'https://images.unsplash.com/photo-1558618666-fcd25c85cd64?w=400&q=80' },
      { name:'River Sand (Washed) 1 Brass', price:2800, unit:'brass', gstRate:5, reviewCount:67, bulkDiscountPercent:3, category:{name:'Aggregates'}, imageUrl:'https://images.unsplash.com/photo-1567604130957-b90aa01d1e7e?w=400&q=80' },
      { name:'Bosch GSB 500W Drill Machine', price:2400, unit:'piece', gstRate:18, reviewCount:142, bulkDiscountPercent:0, category:{name:'Tools'}, imageUrl:'https://images.unsplash.com/photo-1530124566582-a618bc2615dc?w=400&q=80' }
    ];
  }

  scrollTo(id: string): void {
    document.getElementById(id)?.scrollIntoView({ behavior:'smooth', block:'start' });
  }

  toggleDark(): void { this.darkMode = !this.darkMode; }
  goSearch(): void { if (this.searchQ.trim()) this.router.navigate(['/login']); }
  send(): void { alert(`Thank you ${this.cf.name}! We will contact you soon.`); this.cf = {name:'',phone:'',email:'',msg:''}; }
}
