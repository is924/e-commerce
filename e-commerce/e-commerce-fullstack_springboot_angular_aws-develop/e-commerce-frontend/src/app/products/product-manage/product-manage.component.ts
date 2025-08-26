import { Component, OnInit, inject } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterLink } from '@angular/router';
import { ProductService } from '../../services/product.service';
import { HttpClient } from '@angular/common/http';
import { FormsModule } from '@angular/forms';

@Component({
  selector: 'app-product-manage',
  standalone: true,
  imports: [CommonModule, RouterLink, FormsModule],
  templateUrl: './product-manage.component.html',
  styleUrls: ['./product-manage.component.css']
})
export class ProductManageComponent implements OnInit {
  private productService = inject(ProductService);
  private http = inject(HttpClient);

  products: any[] = [];
  loading = true;
  error: string = '';
  
  page = 0;
  size = 2;
  totalPages = 1;
  pageSizeOptions = [2, 4, 6, 12]; // You can adjust these

  ngOnInit(): void {
    this.loading = false;
    this.fetchMyProducts();
  }

  fetchMyProducts() {
    this.loading = true;
    const params = {
      pageNumber: this.page,
      pageSize: this.size,
      sortBy: 'productName',
      sortOrder: 'asc'
    };

    this.productService.getMyProducts(params).subscribe({
      next: (res: any) => {
        this.products = res.content || res;
        this.totalPages = res.totalPages || 1;
        this.loading = false;
      },
      error: () => {
        this.error = 'Failed to load your products';
        this.loading = false;
      }
    });
  }

  deleteProduct(productId: number) {
    if (!confirm('Are you sure you want to delete this product?')) return;

    this.productService.deleteProduct(productId).subscribe({
      next: () => {
        alert('Product deleted');
        this.products = this.products.filter(p => p.productId !== productId);
      },
      error: () => {
        alert('Failed to delete product');
      }
    });
  }

  editProduct(productId: number) {
    // Navigate to product edit page (to be created separately)
  }

  nextPage(): void {
    this.page++;
    this.fetchMyProducts();
  }

  previousPage(): void {
    if (this.page > 0) {
      this.page--;
      this.fetchMyProducts();
    }
  }

  onPageSizeChange(): void {
    this.page = 0;
    this.fetchMyProducts();
  }
}
