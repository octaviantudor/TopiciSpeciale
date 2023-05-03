import { Component, OnInit } from '@angular/core';
import {ItemDto} from '../../model/items/item';
import {ItemService} from '../../service/items.service';
import {CategoryDto} from '../../model/items/category';
import {CategoryService} from '../../service/category.service';
import {ShoppingCartService} from '../../service/shopping-cart.service';
import {AuthenticationService} from '../../service/authentication.service';
import {Router} from '@angular/router';


@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.css']
})
export class HomeComponent implements OnInit {

  items: ItemDto[];
  filteredItems: ItemDto[];
  categories: CategoryDto[];
  searchName = '';
  selectedCategory = null;
  isUserAdmin = false;

  constructor(private itemService: ItemService, private categoryService: CategoryService,
              private shoppingCartService: ShoppingCartService,
              private authService: AuthenticationService,
              private router: Router) { }

  ngOnInit(): void  {
    this.getItems();
    this.getCategories();
    this.isUserAdmin = this.authService.isUserAdmin();
    console.log(this.isUserAdmin);
  }

  getItems(): void  {
    this.itemService.getItems().subscribe(items => {
      this.items = items;
      console.log(this.items);
      this.filteredItems = items;
    });

  }

  getCategories(): void {
    this.categoryService.getCategories().subscribe(categories => {
      this.categories = categories;
    });
  }

  search(): void  {
    console.log(this.searchName);
    if (this.searchName) {
      this.filteredItems = this.items.filter(item => item.name.toLowerCase().includes(this.searchName.toLowerCase()));
    } else {
      this.filteredItems = this.items;
    }
  }

  filterByCategory(): void {
    if (this.selectedCategory) {
      const categoryId = +this.selectedCategory; // convert to number
      this.filteredItems = this.items.filter(item => item.category.id === categoryId);
    } else {
      this.filteredItems = this.items;
    }
  }

  addToCart(item: ItemDto): void  {
    if (this.authService.isUserLoggedIn()) {
      this.shoppingCartService.addItemToCart(item.id).subscribe(() => {
        this.router.navigate(['/shopping-cart']);
      });
    } else {
      this.router.navigate(['/login']);
    }
  }

  clearFilters(): void {
    // clear all filters
    this.searchName = '';
    this.selectedCategory = -1;
    this.getItems();
  }

  isUserLoggedIn(): boolean{
    return this.authService.isUserLoggedIn();
  }

  onLogout(): void{
    this.authService.logout();
  }

  goToItemDetails(id: number): void {
    this.router.navigate(['/items', id, 'details']);
  }

  goToEdit(item: ItemDto): void {
    this.router.navigate(['/items', item.id, 'edit']);
  }
}
