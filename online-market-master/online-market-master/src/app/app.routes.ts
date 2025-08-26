import { Routes } from '@angular/router';

import { HomeComponent } from './pages/home/home';
import { OrdersComponent } from './pages/orders/orders';
import { UsersComponent } from './pages/users/users';
import { ReviewsComponent } from './pages/reviews/reviews';
import { LoginComponent } from './pages/login/login';
import { RegisterComponent } from './pages/register/register';
import { ProductsComponent } from './pages/products/products';

export const routes: Routes = [
  { path: '', component: HomeComponent },
  { path: 'products', component: ProductsComponent },
  { path: 'orders', component: OrdersComponent },
  { path: 'users', component: UsersComponent },
  { path: 'reviews', component: ReviewsComponent },
  { path: 'login', component: LoginComponent },
  { path: 'register', component: RegisterComponent },
  // Optional: redirect unknown paths to home
  { path: '**', redirectTo: '' }
];
