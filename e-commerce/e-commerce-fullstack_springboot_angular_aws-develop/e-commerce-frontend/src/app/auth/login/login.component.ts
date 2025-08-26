import { CommonModule } from '@angular/common';
import { Component } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { AuthService } from '../../services/auth.service';
import { Router, RouterLink } from '@angular/router';

@Component({
  selector: 'app-login',
  standalone: true,
  imports: [CommonModule, FormsModule, RouterLink],
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css'],
})
export class LoginComponent {
  username: string = '';
  password: string = '';
  loginErrorMessage: string = '';
  successMessage: string = '';
  loading: boolean = false;

  constructor(private auth: AuthService, private router: Router) {}
  
  ngOnInit() {
    if (this.auth.isLoggedIn()) {
      // Check stored roles and navigate accordingly
      const storedRoles = localStorage.getItem('roles');
      if (storedRoles) {
        const roles = JSON.parse(storedRoles);
        if (roles.includes('ROLE_ADMIN') || roles.includes('ROLE_SELLER')) {
          this.router.navigate(['/products/manage']);
        } else {
          this.router.navigate(['/products']);
        }
      } else {
        this.router.navigate(['/products']);
      }
    }
  }

  login() {
    this.loginErrorMessage = '';
    this.successMessage = '';

    if (!this.username || !this.password) {
      this.loginErrorMessage = 'Please enter both username and password.';
      return;
    }

    this.loading = true;
  
    this.auth.login({ username: this.username, password: this.password }).subscribe({
      next: (res: any) => {
        console.log('Login successful:', res);
        localStorage.setItem('token', res.jwtToken);
        localStorage.setItem('username', res.username);
        localStorage.setItem('roles', JSON.stringify(res.roles));
        localStorage.setItem('userId', JSON.stringify(res.id));
        
        this.successMessage = 'Login successful! Redirecting...';
        this.loading = false;
        
        setTimeout(() => {
          // Navigate based on user role
          const roles = res.roles;
          if (roles.includes('ROLE_ADMIN') || roles.includes('ROLE_SELLER')) {
            this.router.navigate(['/products/manage']);
          } else {
            this.router.navigate(['/products']);
          }
          // Reload page to ensure header updates with new user state
          window.location.reload();
        }, 1000);
      },
      error: (err) => {
        console.error('Login error:', err);
        this.loading = false;
        
        if (err.status === 404 || err.error?.message === 'Bad credentials') {
          this.loginErrorMessage = 'Invalid username or password. Please try again.';
        } else {
          this.loginErrorMessage = 'Something went wrong. Please try again.';
        }
      }      
    });
  }

  clearMessages() {
    this.loginErrorMessage = '';
    this.successMessage = '';
  }
  

  // login() {
  //   console.log('Email:', this.email);
  //   console.log('PasswordL', this.password);
  //   console.log('Username:', this.username);

  //   this.auth.login({ username: this.username, password: this.password }).subscribe((res: any) => {
  //     console.log('Login successful:', res);
  //     localStorage.setItem('token', res.token);

  //     // Store JWT and username
  //   localStorage.setItem('token', res.jwtToken);
  //   localStorage.setItem('username', res.username);

  //   // Optional: Store roles if needed
  //   localStorage.setItem('roles', JSON.stringify(res.roles));

  //   // Navigate to home or products
  //   this.router.navigate(['/products']);
  //   window.location.reload();
  //   });
  // }
}