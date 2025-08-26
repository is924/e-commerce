import { Component } from '@angular/core';
import { RouterOutlet } from '@angular/router';
import { SidebarComponent } from './components/sidebar/sidebar';
import { Topnav } from './components/top-nav/top-nav'; // make sure this exists

@Component({
  selector: 'app-root',
  standalone: true,
  imports: [RouterOutlet, SidebarComponent, Topnav],
  template: `
    <div class="app-container">
      <app-sidebar></app-sidebar>
      <div class="main-content">
        <app-top-nav></app-top-nav>
        <router-outlet></router-outlet>
      </div>
    </div>
  `,
  styles: [`
    .app-container {
      display: flex;
      height: 100vh;
    }

    .main-content {
      flex: 1;
      display: flex;
      flex-direction: column;
    }

    app-topnav {
      height: 60px;
      background-color: #f8f9fa;
      border-bottom: 1px solid #ddd;
    }

    router-outlet {
      flex: 1;
      padding: 1rem;
    }
  `]
})
export class App {}

