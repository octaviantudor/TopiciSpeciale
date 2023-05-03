import {Component, OnInit} from '@angular/core';
import {FormBuilder, FormGroup, Validators} from '@angular/forms';
import {Router} from '@angular/router';
import {CategoryDto} from '../../model/items/category';
import {ItemDto} from '../../model/items/item';
import {ItemService} from '../../service/items.service';
import {CategoryService} from '../../service/category.service';
import {AuthenticationService} from '../../service/authentication.service';

@Component({
  selector: 'app-add-item',
  templateUrl: './item.component.html',
  styleUrls: ['./item.component.css']
})
export class AddItemComponent implements OnInit {
  public itemForm: FormGroup;
  public categories: CategoryDto[];
  isUserAdmin = false;

  constructor(
    private formBuilder: FormBuilder,
    private itemService: ItemService,
    private categoryService: CategoryService,
    private router: Router,
    private authService: AuthenticationService
  ) {
  }

  ngOnInit(): void {
    this.categoryService.getCategories().subscribe((categories: CategoryDto[]) => {
      this.categories = categories;
    });

    this.isUserAdmin = this.authService.isUserAdmin();

    this.itemForm = this.formBuilder.group({
      name: ['', Validators.required],
      description: ['', Validators.required],
      price: ['', [Validators.required, Validators.pattern(/^\d+(\.\d{1,2})?$/)]],
      quantity: ['', [Validators.required, Validators.pattern(/^\d+$/)]],
      categoryName: ['', Validators.required]
    });
  }

  public onSubmit(): void {
    const newItem: ItemDto = {
      name: this.itemForm.value.name,
      description: this.itemForm.value.description,
      price: this.itemForm.value.price,
      quantity: this.itemForm.value.quantity,
      categoryName: this.itemForm.value.categoryName,
      category: null
    };

    this.itemService.addItems(newItem).subscribe(() => {
      this.router.navigate(['/home']);
    });
  }


  isUserLoggedIn(): boolean {
    return this.authService.isUserLoggedIn();
  }

  onLogout(): void {
    this.authService.logout();
  }
}
