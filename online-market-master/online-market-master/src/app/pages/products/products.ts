import { Component, OnInit } from '@angular/core';
import { RouterModule } from '@angular/router';
import { CommonModule } from '@angular/common';             // ✅ Required
import { HttpClientModule } from '@angular/common/http';   // ✅ Required
import { ProjectService } from '../../projectService/projectService';

@Component({
  selector: 'app-products',
  standalone: true,
  imports: [
    CommonModule,         // ✅ Needed for *ngFor, etc.
    HttpClientModule,     // ✅ Needed for HttpClient to work properly
    RouterModule
  ],
  templateUrl: './products.html',
  styleUrls: ['./products.css'],
})
export class ProductsComponent implements OnInit {
  products: any[] = [];

  constructor(private productService: ProjectService) {}

  ngOnInit() {
    this.fetchProducts();
  }

  fetchProducts() {
    this.productService.getProducts().subscribe(
      (data: any) => {
        this.products = data;
      },
      (error: any) => {
        console.error('Error fetching products:', error);
      }
    );
  }
}
