import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import {environment} from '../../environments/environment';
import {ItemDto} from '../model/items/item';

@Injectable({
  providedIn: 'root'
})
export class ItemService {

  public host: string = environment.apiUrl;

  constructor(private http: HttpClient) { }

  getItems(): Observable<ItemDto[]> {
    return this.http.get<ItemDto[]>(`${this.host}/items`);
  }

  getItemsById(id: number): Observable<ItemDto[]> {
    return this.http.get<ItemDto[]>(`${this.host}/items?itemId=${id}`);
  }

  public addItems(newItem: ItemDto): Observable<any> {
    return this.http.post<any>(`${this.host}/items`, newItem);
  }

  updateItem(updatedItem: ItemDto): Observable<any> {
    return this.http.get<any>(`${this.host}/items/update?itemId=${updatedItem.id}&quantity=${updatedItem.quantity}&name=${updatedItem.name}&price=${updatedItem.price}&description=${updatedItem.description}
    &categoryName=${updatedItem.categoryName}`);
  }
}
