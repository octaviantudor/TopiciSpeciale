import {Component, OnInit} from '@angular/core';
import {OrderedItem} from '../../model/items/ordered-item';
import {OrdersService} from '../../service/orders.service';
import {AuthenticationService} from '../../service/authentication.service';
import {Order} from '../../model/items/orders';
import {Router} from '@angular/router';

@Component({
  selector: 'app-order-history',
  templateUrl: './customer-orders.component.html',
  styleUrls: ['./customer-orders.component.css']
})
export class CustomerOrdersComponent implements OnInit {

  orderedItems: OrderedItem[] = [];
  orders: Order[] = [];
  showDetails: { [key: string]: boolean } = {};
  isUserAdmin = false;

  constructor(private orderService: OrdersService, private authService: AuthenticationService,
              private router: Router) {
  }

  ngOnInit(): void {
    // Get order history for current user
    this.orderService.getCustomerOrderedItems().subscribe((data: OrderedItem[]) => {
      this.orderedItems = data;
    });

    this.orderService.getAllCustomerOrders().subscribe((data1: Order[]) => {
      this.orders = data1;
      console.log(this.orders);
    });
    this.isUserAdmin = this.authService.isUserAdmin();

  }

  getOrderedItemsByOrderId(orderId: string): OrderedItem[] {
    return this.orderedItems.filter(item => item.orderId === orderId);
  }

  toggleDetails(orderId: string): void {
    this.showDetails[orderId] = !this.showDetails[orderId];
  }

  isUserLoggedIn(): boolean {
    return this.authService.isUserLoggedIn();
  }

  getTotalByOrderId(orderId: string): number {
    const orderedItems = this.getOrderedItemsByOrderId(orderId);
    return orderedItems.reduce((total, item) => total + item.quantity * item.price, 0);
  }

  onLogout(): void {
    this.authService.logout();
  }

  toggleFinishOrder(id: string): void {

    this.orderService.markAsDone(id).subscribe(() => {
      this.router.navigate(['/home']);
    });
  }
}
