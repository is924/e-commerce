import { Component } from '@angular/core';
import { RouterModule } from '@angular/router';

@Component({
  selector: 'app-orders',
  standalone: true,
  imports: [RouterModule],  // Import RouterModule here
  templateUrl: './orders.html',
  styleUrls: ['./orders.css']
})
export class OrdersComponent {}
