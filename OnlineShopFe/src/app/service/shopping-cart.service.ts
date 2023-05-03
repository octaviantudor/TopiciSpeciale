import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import {ShoppingCartItem} from '../model/items/shopping-cart-item';

@Injectable({
  providedIn: 'root'
})
export class ShoppingCartService {
  private baseUrl = 'http://localhost:8080';
  private itemsUrl = `${this.baseUrl}/shopping-cart/items/1`;
  private clearShoppingCartUrl = `${this.baseUrl}/shopping-cart/clear/1`;

  constructor(private http: HttpClient) { }

  addItemToCart(itemId: number): Observable<any> {
    const body = { itemId, quantity: 1 };
    return this.http.post(`${this.baseUrl}/shopping-cart`, body);
  }

  addItemToCartWithQuantity(itemId: number, quantityNr: number): Observable<any> {
    const body = { itemId, quantity: quantityNr };
    return this.http.post(`${this.baseUrl}/shopping-cart`, body);
  }

  getItems(): Observable<ShoppingCartItem[]> {
    return this.http.get<ShoppingCartItem[]>(this.itemsUrl);
  }

  clearShoppingCart(): Observable<any>  {
    return this.http.put<any>(this.clearShoppingCartUrl, {});
  }
}
