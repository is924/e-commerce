import { Component } from '@angular/core';
import { RouterModule } from '@angular/router';

@Component({
  selector: 'app-sidebar',
  standalone: true,
  imports: [RouterModule],
  template: `
    <aside class="sidebar">
      <h2>Online Market</h2>
      <nav>
        <a routerLink="/" routerLinkActive="active">🏠 Home</a>
        <a routerLink="/products" routerLinkActive="active">📦 Products</a>
        <a routerLink="/orders" routerLinkActive="active">🧾 Orders</a>
        <a routerLink="/users" routerLinkActive="active">👤 Users</a>
        <a routerLink="/reviews" routerLinkActive="active">🌟 Reviews</a>
      </nav>
    </aside>
  `,
  styles: [`
    .sidebar {
      width: 200px;
      background: #f4f4f4;
      padding: 1rem;
      height: 100vh;
    }
    nav a {
      display: block;
      padding: 0.5rem;
      color: #333;
      text-decoration: none;
    }
    nav a.active {
      font-weight: bold;
      color: #007bff;
    }
  `]
})
export class SidebarComponent {}
