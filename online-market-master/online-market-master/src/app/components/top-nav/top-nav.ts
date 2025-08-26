import { Component } from '@angular/core';
import { MatToolbarModule } from '@angular/material/toolbar';
import { MatButtonModule } from '@angular/material/button';
import { RouterModule } from '@angular/router';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-top-nav',
  standalone: true,
  imports: [MatToolbarModule, MatButtonModule, RouterModule, CommonModule],
  templateUrl: './top-nav.html',
  styleUrls: ['./top-nav.css']
})
export class Topnav {
  isLoggedIn = false;

  logout() {
    this.isLoggedIn = false;
  }
}
