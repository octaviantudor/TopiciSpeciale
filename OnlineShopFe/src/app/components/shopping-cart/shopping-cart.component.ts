import { Component, OnInit } from '@angular/core';

import {ShoppingCartItem} from '../../model/items/shopping-cart-item';
import {ShoppingCartService} from '../../service/shopping-cart.service';
import {AuthenticationService} from '../../service/authentication.service';
import {Router} from '@angular/router';
import {OrdersService} from '../../service/orders.service';

@Component({
  selector: 'app-shopping-cart',
  templateUrl: './shopping-cart.component.html',
  styleUrls: ['./shopping-cart.component.css']
})
export class ShoppingCartComponent implements OnInit {
  items: ShoppingCartItem[] = [];
  quantityOptions = [1, 2, 3, 4, 5, 6, 7, 8, 9, 10];
  constructor(private shoppingCartService: ShoppingCartService,
              private authService: AuthenticationService,
              private router: Router,
              private orderService: OrdersService) { }

  ngOnInit(): void {
    this.getItems();
  }

  getItems(): void {
    this.shoppingCartService.getItems().subscribe(items => this.items = items);
  }

  getTotal(): number {
    return this.items.reduce((total, item) => total + item.price * item.quantity, 0);
  }

  isUserLoggedIn(): boolean{
    return this.authService.isUserLoggedIn();
  }

  onLogout(): void{
    this.authService.logout();
  }

  onClearShoppingCart(): void {
    this.shoppingCartService.clearShoppingCart().subscribe();
  }

  onCheckout(): void {
    if (this.authService.isUserLoggedIn()) {
      this.orderService.createOrder().subscribe(() => {
      });
    } else {
      this.router.navigate(['/login']);
    }
  }

  updateQuantity(item: ShoppingCartItem): void {
      this.shoppingCartService.addItemToCartWithQuantity(item.itemId, item.quantity).subscribe();
  }

  getQuantityOptions(item: any): number[] {
    return Array(item.quantity).fill(0).map((_, index) => index + 1);
  }
}
