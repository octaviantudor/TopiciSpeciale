import { Component, OnInit } from '@angular/core';
import {ActivatedRoute, Router} from '@angular/router';
import {ItemDto} from '../../model/items/item';
import {Review} from '../../model/items/review';
import {ItemService} from '../../service/items.service';
import {ReviewService} from '../../service/review.service';
import {AuthenticationService} from '../../service/authentication.service';
import {ShoppingCartService} from '../../service/shopping-cart.service';


@Component({
  selector: 'app-item-details',
  templateUrl: './details.component.html',
  styleUrls: ['./details.component.css']
})
export class ItemDetailsComponent implements OnInit {
  item: ItemDto;
  reviews: Review[];
  isDataLoaded = false;

  constructor(
    private route: ActivatedRoute,
    private itemService: ItemService,
    private reviewService: ReviewService,
    private authService: AuthenticationService,
    private shoppingCartService: ShoppingCartService,
    private router: Router
  ) {}

  ngOnInit(): void {
    this.getItemDetails();
    this.getItemReviews();
  }

  private getItemDetails(): void {
    const itemId = Number(this.route.snapshot.paramMap.get('id'));
    this.itemService.getItemsById(itemId)
      .subscribe(item => {this.item = item[0]; this.isDataLoaded = true});
  }

  private getItemReviews(): void {
    const itemId = Number(this.route.snapshot.paramMap.get('id'));
    this.reviewService.getItemReviews(itemId)
      .subscribe(reviews => this.reviews = reviews);
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

  isUserLoggedIn(): boolean{
    return this.authService.isUserLoggedIn();
  }

  onLogout(): void{
    this.authService.logout();
  }
}
