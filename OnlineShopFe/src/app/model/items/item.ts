import {CategoryDto} from './category';

export interface ItemDto {
  id?: number;
  name: string;
  description: string;
  price: string;
  quantity: string;
  categoryName?: string;
  category: CategoryDto;
}
