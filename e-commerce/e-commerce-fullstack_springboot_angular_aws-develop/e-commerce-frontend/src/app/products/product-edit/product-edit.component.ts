import { CommonModule } from '@angular/common';
import { Component, inject } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { ProductService } from '../../services/product.service';

@Component({
  selector: 'app-product-edit',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './product-edit.component.html'
})
export class ProductEditComponent {
  private route = inject(ActivatedRoute);
  private router = inject(Router);
  private productService = inject(ProductService);

  productId!: number;
  product: any = {};
  loading = true;
  saving = false;
  error = '';

  selectedFile: File | null = null;
  imagePreview: string | null = null;

  ngOnInit() {
    this.productId = Number(this.route.snapshot.paramMap.get('id'));
    this.loadProduct();
  }

  loadProduct() {
    this.loading = true;
    this.productService.getById(this.productId).subscribe({
      next: (res: any) => {
        this.product = res;
        this.imagePreview = res.productImage || null;
        this.loading = false;
      },
      error: () => {
        this.error = 'Failed to load product';
        this.loading = false;
      }
    });
  }

  onFileSelected(event: any) {
    const file = event.target.files[0];
    if (!file) return;
    if (!file.type.startsWith('image/')) {
      alert('Please select a valid image');
      return;
    }
    this.selectedFile = file;
    const reader = new FileReader();
    reader.onload = (e: any) => this.imagePreview = e.target.result;
    reader.readAsDataURL(file);
  }

  save() {
    this.saving = true;
    const update$ = this.productService.update(this.productId, {
      productName: this.product.productName,
      description: this.product.description,
      price: this.product.price,
      discount: this.product.discount,
      quantity: this.product.quantity
    });

    update$.subscribe({
      next: () => {
        if (this.selectedFile) {
          this.productService.updateImage(this.productId, this.selectedFile).subscribe({
            next: () => this.finishSave(),
            error: () => this.finishSave('Failed to update image')
          });
        } else {
          this.finishSave();
        }
      },
      error: () => this.finishSave('Failed to update product')
    });
  }

  private finishSave(errMsg?: string) {
    this.saving = false;
    if (errMsg) {
      alert(errMsg);
      return;
    }
    alert('Product updated');
    this.router.navigate(['/products/manage']);
  }
}


