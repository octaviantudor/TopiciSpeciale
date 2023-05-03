import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import {LoginComponent} from './components/login/login.component';
import {RegisterComponent} from './components/register/register.component';
import {UserComponent} from './components/user/user.component';
import {AuthGuard} from './guard/auth.guard';
import {HomeComponent} from './components/home/home.component';
import {ShoppingCartComponent} from './components/shopping-cart/shopping-cart.component';
import {OrderComponent} from './components/orders/orders.component';
import {ReviewComponent} from './components/review/review.component';
import {ItemDetailsComponent} from './components/details/details.component';
import {AddItemComponent} from './components/item/item.component';
import {EditItemComponent} from './components/edit/edit.component';
import {CustomerOrdersComponent} from './components/customer-orders/customer-orders.component';

const routes: Routes = [
  {path: 'login', component: LoginComponent},
  {path: 'register', component: RegisterComponent},
  {path: 'home', component: HomeComponent},
  {path: 'user', component: UserComponent, canActivate: [AuthGuard]},
  {path: 'shopping-cart', component: ShoppingCartComponent, canActivate: [AuthGuard]},
  {path: 'orders', component: OrderComponent, canActivate: [AuthGuard]},
  {path: 'reviews/:id', component: ReviewComponent, canActivate: [AuthGuard]},
  {path: 'items/:id/details', component: ItemDetailsComponent, canActivate: [AuthGuard]},
  {path: 'items/new', component: AddItemComponent, canActivate: [AuthGuard]},
  {path: 'items/:id/edit', component: EditItemComponent, canActivate: [AuthGuard]},
  {path: 'customer/orders', component: CustomerOrdersComponent, canActivate: [AuthGuard]},
  {path: '', redirectTo: '/home', pathMatch: 'full'}
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
