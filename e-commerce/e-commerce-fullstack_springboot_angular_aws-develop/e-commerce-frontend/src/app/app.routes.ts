import { Routes } from '@angular/router';
import { LoginComponent } from './auth/login/login.component';
import { SignupComponent } from './auth/signup/signup.component';
import { ProductListComponent } from './products/product-list/product-list.component';
import { ProductCreateComponent } from './products/product-create/product-create.component';
import { Component } from '@angular/core';
import { CartListComponent } from './cart/cart-list/cart-list.component';
import { OrderSummaryComponent } from './orders/order-summary/order-summary.component';
import { AuthGuard } from './shared/auth.guard';
import { AppComponent } from './app.component';
import { MyOrdersComponent } from './orders/my-orders/my-orders.component';
import { roleGuard } from './shared/role.guard';
import { ProfileComponent } from './users/profile/profile.component';
import { ProductManageComponent } from './products/product-manage/product-manage.component';
import { WishlistComponent } from './wishlist/wishlist.component';
import { PaymentComponent } from './payment/payment.component';
import { OrderPlaceSuccessComponent } from './orders/order-place-success/order-place-success.component';

export const  routes: Routes = [
    { path: '', redirectTo: 'login', pathMatch: 'full' },
    { path: 'login', component: LoginComponent },
    { path: 'signup', component: SignupComponent },
    { path: 'products', component: ProductListComponent },
    { path: 'products/create', component: ProductCreateComponent },
    { path: 'products/edit/:id', loadComponent: () => import('./products/product-edit/product-edit.component').then(m => m.ProductEditComponent), canActivate: [roleGuard] },
    { path: 'cart', component: CartListComponent },
    { path: 'orders', component: OrderSummaryComponent },
    { path: 'my-orders', component: MyOrdersComponent },
    { path: 'products/create', component: ProductCreateComponent, canActivate: [roleGuard] },
    { path: 'profile', component: ProfileComponent },
    { path: 'products/manage', component: ProductManageComponent, canActivate: [AuthGuard]},
    { path: 'wishlist', component: WishlistComponent},
    { path: 'payment', component: PaymentComponent },
    { path: 'order-place-success', component: OrderPlaceSuccessComponent } 
    ,{ path: 'admin/users', loadComponent: () => import('./users/users-list/users-list.component').then(m => m.UsersListComponent), canActivate: [roleGuard] }
  ];