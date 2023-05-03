import {Component, OnInit} from '@angular/core';
import {OrderedItem} from '../../model/items/ordered-item';
import {OrdersService} from '../../service/orders.service';
import {AuthenticationService} from '../../service/authentication.service';
import {Order} from '../../model/items/orders';
import {Router} from '@angular/router';

@Component({
  selector: 'app-order-history',
  templateUrl: './orders.component.html',
  styleUrls: ['./orders.component.css']
})
export class OrderComponent implements OnInit {

  orderedItems: OrderedItem[] = [];
  orders: Order[] = [];
  showDetails: { [key: string]: boolean } = {};

  constructor(private orderService: OrdersService, private authService: AuthenticationService,
              private router: Router) {
  }

  ngOnInit(): void {
    // Get order history for current user
    this.orderService.getOrderedItems().subscribe((data: OrderedItem[]) => {
      this.orderedItems = data;
    });

    this.orderService.getAllOrders().subscribe((data1: Order[]) => {
      this.orders = data1;
      console.log(this.orders);
    });


  }

  getOrderedItemsByOrderId(orderId: string): OrderedItem[] {
    return this.orderedItems.filter(item => item.orderId === orderId);
  }

  toggleDetails(orderId: string): void {
    this.showDetails[orderId] = !this.showDetails[orderId];
  }

  isUserLoggedIn(): boolean{
    return this.authService.isUserLoggedIn();
  }

  getTotalByOrderId(orderId: string): number {
    const orderedItems = this.getOrderedItemsByOrderId(orderId);
    return orderedItems.reduce((total, item) => total + item.quantity * item.price, 0);
  }

  onLogout(): void{
    this.authService.logout();
  }

  toggleReview(item: OrderedItem): void {
    this.router.navigate(['/reviews/' + item.itemId]);
  }
}
