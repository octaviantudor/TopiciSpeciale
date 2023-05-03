import {HttpClient} from '@angular/common/http';
import {Injectable} from '@angular/core';
import {Observable} from 'rxjs';
import {OrderedItem} from '../model/items/ordered-item';
import {Order} from '../model/items/orders';

@Injectable({
  providedIn: 'root'
})
export class OrdersService {
  private baseUrl = 'http://localhost:8080';
  private createOrderUrl = `${this.baseUrl}/orders?userId=1`;
  private ordersListUrl = `${this.baseUrl}/orders/ordered-items?userId=1`;
  private customerOrdersListUrl = `${this.baseUrl}/orders/ordered-items`;

  constructor(private http: HttpClient) {
  }

  createOrder(): Observable<any> {
    return this.http.post<any>(this.createOrderUrl, {});
  }

  getAllOrders(): Observable<any>{
    return this.http.get<any>(`${this.baseUrl}/orders`);
  }

  getOrderedItems(): Observable<OrderedItem[]> {
    return this.http.get<OrderedItem[]>(this.ordersListUrl);
  }


  getAllCustomerOrders(): Observable<any>{
    return this.http.get<any>(`${this.baseUrl}/orders/all`);
  }

  getCustomerOrderedItems(): Observable<OrderedItem[]> {
    return this.http.get<OrderedItem[]>(this.customerOrdersListUrl + '/all');
  }

  public markAsDone(id: string): Observable<any> {
    return this.http.get<any>(`${this.baseUrl}/orders/${id}/done`);
  }
}
