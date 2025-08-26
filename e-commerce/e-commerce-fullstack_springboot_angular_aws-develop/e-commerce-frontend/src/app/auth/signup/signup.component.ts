import { CommonModule } from '@angular/common';
import { Component } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { Router, RouterLink } from '@angular/router';
import { AuthService } from '../../services/auth.service';


@Component({
  selector: 'app-signup',
  standalone: true,
  imports: [CommonModule, FormsModule, RouterLink],
  templateUrl: './signup.component.html',
  styleUrls: ['./signup.component.css']
})
export class SignupComponent {
  username = '';
  email = '';
  password = '';
  confirmPassword = '';
  selectedRole = 'USER';
  errorMessage = '';
  successMessage = '';
  loading = false;

  roles = [
    { value: 'USER', label: 'Customer', description: 'Browse and purchase products' },
    { value: 'SELLER', label: 'Seller', description: 'Sell your products on our platform' },
    { value: 'ADMIN', label: 'Administrator', description: 'Manage the entire platform' }
  ];

  constructor(private authService: AuthService, private router: Router) {}

  ngOnInit(): void {}

  signup() {
    this.errorMessage = '';
    this.successMessage = '';

    // Validation
    if (!this.username || !this.email || !this.password || !this.confirmPassword) {
      this.errorMessage = 'All fields are required!';
      return;
    }

    if (this.password !== this.confirmPassword) {
      this.errorMessage = 'Passwords do not match!';
      return;
    }

    if (this.password.length < 6) {
      this.errorMessage = 'Password must be at least 6 characters long!';
      return;
    }

    this.loading = true;
  
    const payload = {
      username: this.username,
      email: this.email,
      password: this.password,
      role: [this.selectedRole]
    };
  
    this.authService.signup(payload).subscribe({
      next: (res) => {
        this.successMessage = 'Account created successfully! Please login.';
        this.loading = false;
        setTimeout(() => {
          this.router.navigate(['/login']);
        }, 2000);
      },
      error: (err) => {
        this.errorMessage = err.error?.message || 'Registration failed! Please try again.';
        this.loading = false;
      }
    });
  }

  clearMessages() {
    this.errorMessage = '';
    this.successMessage = '';
  }
}
