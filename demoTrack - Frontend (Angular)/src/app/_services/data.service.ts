import { Injectable } from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {DemoRequest} from '../_models/demo-request';
import {environment} from '../../environments/environment';
import {Product} from '../_models/product-details';
import {User} from '../_models/user';

@Injectable({
  providedIn: 'root'
})
export class DataService {

  constructor(private http: HttpClient) { }

  createDemoRequest(request) {
    return this.http.post<DemoRequest>(`${environment.apiUrl}/demoRequests`, request);
  }
  getAllDemoRequests() {
    return this.http.get<DemoRequest[]>(`${environment.apiUrl}/demoRequests`);
  }
  getDemoRequestById(id: number) {
    return this.http.get<DemoRequest>(`${environment.apiUrl}/demoRequests/${id}`);
  }
  getDemoRequestByUser(requesterUserId: string) {
    return this.http.get<DemoRequest[]>(`${environment.apiUrl}/demoRequests/user/${requesterUserId}`);
  }
  updateDemoRequests(request) {
    return this.http.post<any>(`${environment.apiUrl}/demoRequests`, request);
  }
  getAllProducts() {
    return this.http.get<Product[]>(`${environment.apiUrl}/products`);
  }
  createNewUser(user) {
    return this.http.post<User>(`${environment.apiUrl}/userDetails`, user);
  }
  createNewProduct(product) {
    return this.http.post<Product>(`${environment.apiUrl}/products`, product);
  }


}
