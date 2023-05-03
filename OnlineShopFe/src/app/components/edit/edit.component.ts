import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import {ItemService} from '../../service/items.service';
import {ItemDto} from '../../model/items/item';
import {CategoryDto} from '../../model/items/category';
import {CategoryService} from '../../service/category.service';
import {AuthenticationService} from '../../service/authentication.service';

@Component({
  selector: 'app-edit-item',
  templateUrl: './edit.component.html',
  styleUrls: ['./edit.component.css']
})
export class EditItemComponent implements OnInit {
  item: ItemDto;
  categories: CategoryDto[];
  itemForm: FormGroup;
  isUserAdmin = false;
  isDataLoaded = false;

  constructor(
    private route: ActivatedRoute,
    private router: Router,
    private apiService: ItemService,
    private categoryService: CategoryService,
    private fb: FormBuilder,
    private authService: AuthenticationService
  ) { }

  ngOnInit(): void {
    this.getItem();
    this.getCategories();
    this.isUserAdmin = this.authService.isUserAdmin();
    this.itemForm = this.fb.group({
      name: ['', Validators.required],
      description: [''],
      price: ['', Validators.required],
      quantity: ['', Validators.required],
      categoryName: ['', Validators.required]
    });
  }

  getItem(): void {
    const id = +this.route.snapshot.paramMap.get('id');
    this.apiService.getItemsById(id).subscribe(item => {
      this.item = item[0];
      this.itemForm.patchValue({
        name: item[0].name,
        description: item[0].description,
        price: item[0].price,
        quantity: item[0].quantity,
        categoryName: item[0].category.name
      });
      this.isDataLoaded = true;
    });
  }

  getCategories(): void {
    this.categoryService.getCategories().subscribe(categories => {
      this.categories = categories;
    });
  }

  onSubmit(): void {
    const updatedItem: ItemDto = {
      id: this.item.id,
      name: this.itemForm.get('name').value,
      description: this.itemForm.get('description').value,
      price: this.itemForm.get('price').value,
      quantity: this.itemForm.get('quantity').value,
      categoryName: this.itemForm.get('categoryName').value,
      category: null
    };

    console.log(updatedItem);

    this.apiService.updateItem(updatedItem).subscribe(() => {
      this.router.navigate(['/home']);
    });
  }

  isUserLoggedIn(): boolean{
    return this.authService.isUserLoggedIn();
  }

  onLogout(): void{
    this.authService.logout();
  }
}
