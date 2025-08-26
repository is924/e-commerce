import { Component } from '@angular/core';
import { RouterModule } from '@angular/router';

@Component({
  selector: 'app-home',
  standalone: true,
  imports: [RouterModule],  // <-- add this here
  templateUrl: './home.html',
  styleUrls: ['./home.css']
})
export class HomeComponent {}
