export interface Review {
  id?: number;
  itemId: number;
  rating: number;
  comment: string;
  createdAt?: Date;
}
