import {CategoryDto} from './category';

export interface ShoppingCartItem {
  itemId: number;
  name: string;
  description: string;
  price: number;
  quantity: number;
}
