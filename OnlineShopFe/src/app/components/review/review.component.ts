import {Component, OnInit} from '@angular/core';
import {ActivatedRoute, Router} from '@angular/router';
import {FormBuilder, FormGroup, Validators} from '@angular/forms';
import {Review} from '../../model/items/review';
import {ReviewService} from '../../service/review.service';
import {AuthenticationService} from '../../service/authentication.service';
import {ItemService} from '../../service/items.service';
import {ItemDto} from '../../model/items/item';

@Component({
  selector: 'app-review',
  templateUrl: './review.component.html',
  styleUrls: ['./review.component.css']
})
export class ReviewComponent implements OnInit {
  itemId: number;
  reviewForm: FormGroup;
  submitted = false;
  loading = false;
  ratingOptions = [1, 2, 3, 4, 5];
  item: ItemDto;
  error = '';
  isItemLoaded = false;

  constructor(
    private route: ActivatedRoute,
    private formBuilder: FormBuilder,
    private reviewService: ReviewService,
    private authService: AuthenticationService,
    private itemService: ItemService,
    private router: Router
  ) {
  }

  ngOnInit(): void {
    this.itemId = this.route.snapshot.params.id;

    this.itemService.getItemsById(this.itemId).subscribe((data) => {
      this.item = data[0];
      this.isItemLoaded = true;
    });

    this.reviewForm = this.formBuilder.group({
      rating: ['', Validators.required],
      comment: ['', Validators.required],
    });
  }

  // convenience getter for easy access to form fields
  get f(): any {
    return this.reviewForm.controls;
  }

  onSubmit(): void {
    this.submitted = true;

    // stop here if form is invalid
    if (this.reviewForm.invalid) {
      return;
    }

    this.loading = true;

    const review: Review = {
      itemId: this.itemId,
      rating: this.f.rating.value,
      comment: this.f.comment.value
    };

    this.reviewService.addReview(review.itemId, review.rating, review.comment).subscribe(
      () => {
        this.loading = false;
        this.router.navigate(['/items', review.itemId, 'details']);
      },
      (error) => {
        this.loading = false;
        this.error = error;
      }
    );
  }

  isUserLoggedIn(): boolean {
    return this.authService.isUserLoggedIn();
  }

  onLogout(): void {
    this.authService.logout();
  }
}
