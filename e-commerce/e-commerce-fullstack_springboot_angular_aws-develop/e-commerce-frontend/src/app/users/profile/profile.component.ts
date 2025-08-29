import { CommonModule } from '@angular/common';
import { HttpClient } from '@angular/common/http';
import { Component, inject } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { Router } from '@angular/router';

@Component({
  selector: 'app-profile',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './profile.component.html',
  styleUrls: ['./profile.component.css']
})
export class ProfileComponent {
  private http = inject(HttpClient);
  private router = inject(Router);

  user: any = {};
  updatedUser: any = {};
  loading = true;
  selectedAvatar: File | null = null;

  successMessage = '';
  errorMessage = '';

  addresses: any[] = [];
  newAddress: any = {
    street: '',
    buildingName: '',
    city: '',
    state: '',
    country: '',
    pincode: ''
  };

  showDeleteConfirmation = false;
  deleteAddressId: any = null;

  ngOnInit(): void {
    this.fetchCurrentUser();
    this.fetchAddresses();
  }

  fetchCurrentUser() {
    this.http.get<any>('http://localhost:8080/api/users').subscribe({
      next: (res) => {
        this.user = res;
        this.updatedUser = {
          userName: res.userName,
          email: res.email
        };
        this.loading = false;
      },
      error: (err) => {
        console.error('Failed to fetch user', err);
        this.errorMessage = 'Failed to load profile';
        this.loading = false;
      }
    });
  }

  updateProfile() {
    this.http.put(`http://localhost:8080/api/users/${this.user.userId}`, this.updatedUser).subscribe({
      next: () => {
        this.successMessage = 'Profile updated successfully ✅';
        this.errorMessage = '';
        if (this.selectedAvatar) {
          // Update profileImage field to file name to reflect avatar change (server should handle storing actual file in a real upload endpoint)
          const form = new FormData();
          form.append('image', this.selectedAvatar);
          // Placeholder: reuse update endpoint to save filename; real upload endpoint can be added later
          this.http.put(`http://localhost:8080/api/users/${this.user.userId}`, { profileImage: this.selectedAvatar.name }).subscribe();
        }
        // window.location.reload();
        // this.fetchCurrentUser();
      },
      error: (err) => {
        console.error('Failed to update profile', err);
        this.errorMessage = err.error?.message || 'Failed to update profile';
        this.successMessage = '';
      }
    });
  }

  onAvatarSelected(event: any) {
    const file = event.target.files[0];
    if (!file) return;
    if (!file.type.startsWith('image/')) {
      alert('Please select an image file');
      return;
    }
    this.selectedAvatar = file;

    // Upload immediately so the preview updates
    const form = new FormData();
    form.append('image', file, file.name);
    this.http
      .put<any>(`http://localhost:8080/api/users/${this.user.userId}/avatar`, form)
      .subscribe({
        next: (res) => {
          // Backend returns full URL in profileImage
          if (res?.profileImage) {
            this.user.profileImage = res.profileImage;
            this.successMessage = 'Avatar updated ✅';
          }
        },
        error: (err) => {
          console.error('Failed to upload avatar', err);
          this.errorMessage = 'Failed to upload avatar';
        }
      });
  }

  fetchAddresses() {
    this.http.get<any>('http://localhost:8080/api/addresses/users/current').subscribe({
      next: (res: any) => {
        console.log('Addresses:', res);
        this.addresses = res.content || res;
      },
      error: () => {
        console.error('Failed to load addresses');
      }
    });
  }

  addAddress() {
    this.http.post(`http://localhost:8080/api/addresses`, this.newAddress).subscribe({
      next: () => {
        alert('Address added ✅');
        this.fetchAddresses();
      },
      error: (err) => {
        alert('Failed to add address: ' + (err.error?.message || 'Unknown error'));
      }
    });
  }

  deleteAddress(addressId: number) {
    if (confirm('Are you sure you want to delete this address?')) {
      this.http.delete(`http://localhost:8080/api/addresses/${addressId}`).subscribe({
        next: () => {
          this.fetchAddresses();
          this.showDeleteConfirmation = false;
          alert('Address deleted ✅');
        },
        error: () => {
          alert('Failed to delete address');
        }
      });
    }
  }

  confirmDeleteAddress(addressId: string) {
    this.showDeleteConfirmation = true;
    this.deleteAddressId = addressId;
  }

  cancelDelete() {
    this.showDeleteConfirmation = false;
    this.deleteAddressId = null;
  }
}
