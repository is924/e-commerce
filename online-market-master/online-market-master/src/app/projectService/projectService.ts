import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { environment } from '../../environments/environment';

@Injectable({
  providedIn: 'root'
})
export class ProjectService {
  constructor(private http: HttpClient) {}

  getProducts() {
    return this.http.get(environment.apiUrl + '/products');
  }

  getusers() {   
     return this.http.get(environment.apiUrl + '/users');
  }

  getOrders() {
    return this.http.get(environment.apiUrl + '/orders');
  }

  getReviews() {
    return this.http.get(environment.apiUrl + '/reviews');
  } 
}
