import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import {Review} from '../model/items/review';

@Injectable({
  providedIn: 'root'
})
export class ReviewService {
  private baseUrl = 'http://localhost:8080';

  constructor(private http: HttpClient) { }

  // Get all reviews for an item
  getItemReviews(itemId: number): Observable<Review[]> {
    return this.http.get<Review[]>(`${this.baseUrl}/items/${itemId}/reviews`);
  }

  // Add a new review for an item
  addReview(itemId: number, rating: number, comment: string): Observable<Review> {
    const body = { itemId, rating, comment };
    return this.http.post<Review>(`${this.baseUrl}/items/${itemId}/reviews`, body);
  }
}
