import { CommonModule } from '@angular/common';
import { Component, inject } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { ProductService } from '../../services/product.service';
import { Router } from '@angular/router';
import { HttpClient } from '@angular/common/http';

@Component({
  selector: 'app-product-create',
  standalone: true,
  templateUrl: './product-create.component.html',
  imports: [CommonModule, FormsModule],
})
export class ProductCreateComponent {
  private productService = inject(ProductService);
  private router = inject(Router);
  private http = inject(HttpClient);

  product: any = {
    productName: '',
    price: null,
    specialPrice: null,
    description: '',
    productImage: '',
    categoryId: '',
    discount: null,
    quantity: null,
  };

  selectedFile: File | null = null;
  imagePreview: string | null = null;
  fileUploadStatus: string = '';

  categories: any[] = [];

  ngOnInit(): void {
    this.productService.getAllCategories().subscribe({
      next: (res: any[]) => this.categories = res,
      error: (err) => {
        console.error('Failed to load categories:', err);
        alert('Failed to load categories');
      }
    });
  }

  onFileSelected(event: any): void {
    const file = event.target.files[0];
    this.processFile(file);
  }

  onDragOver(event: DragEvent): void {
    event.preventDefault();
    event.stopPropagation();
    const target = event.currentTarget as HTMLElement;
    target.classList.add('dragover');
  }

  onDragLeave(event: DragEvent): void {
    event.preventDefault();
    event.stopPropagation();
    const target = event.currentTarget as HTMLElement;
    target.classList.remove('dragover');
  }

  onDrop(event: DragEvent): void {
    event.preventDefault();
    event.stopPropagation();
    
    const target = event.currentTarget as HTMLElement;
    target.classList.remove('dragover');
    
    const files = event.dataTransfer?.files;
    if (files && files.length > 0) {
      this.processFile(files[0]);
    }
  }

  processFile(file: File): void {
    if (file) {
      // Validate file type
      if (!file.type.startsWith('image/')) {
        this.fileUploadStatus = 'Please select an image file (JPEG, PNG, GIF, etc.)';
        return;
      }

      // Validate file size (max 5MB)
      if (file.size > 5 * 1024 * 1024) {
        this.fileUploadStatus = 'File size should be less than 5MB';
        return;
      }

      this.selectedFile = file;
      this.fileUploadStatus = 'Image selected successfully';
      
      // Create preview
      const reader = new FileReader();
      reader.onload = (e: any) => {
        this.imagePreview = e.target.result;
      };
      reader.readAsDataURL(file);
    }
  }

  removeImage(): void {
    this.selectedFile = null;
    this.imagePreview = null;
    this.fileUploadStatus = '';
  }

  createProduct(): void {
    if (!this.product.categoryId) {
      alert("Please select a category.");
      return;
    }

    if (this.selectedFile) {
      // Create product with image upload
      const formData = new FormData();
      formData.append('productName', this.product.productName);
      formData.append('price', this.product.price.toString());
      formData.append('discount', this.product.discount.toString());
      formData.append('quantity', this.product.quantity?.toString() || '0');
      formData.append('description', this.product.description);
      formData.append('image', this.selectedFile);

      this.http.post(`http://localhost:8080/api/products/categories/${this.product.categoryId}/with-image`, formData)
        .subscribe({
          next: () => {
            alert('✅ Product created with image!');
            this.router.navigate(['/products']);
          },
          error: (err) => {
            const msg = err.error?.message || 'Unknown error';
            alert('❌ Failed: ' + msg);
          }
        });
    } else {
      // Create product without image (existing method)
      this.productService.createProduct(this.product.categoryId, this.product).subscribe({
        next: () => {
          alert('✅ Product created!');
          this.router.navigate(['/products']);
        },
        error: (err) => {
          const msg = err.error?.message || 'Unknown error';
          alert('❌ Failed: ' + msg);
        }
      });
    }
  }
}
