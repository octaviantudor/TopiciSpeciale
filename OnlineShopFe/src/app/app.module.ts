import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import {LoginComponent} from './components/login/login.component';
import {Register} from 'ts-node';
import {UserComponent} from './components/user/user.component';
import {FormsModule, ReactiveFormsModule} from '@angular/forms';
import {HTTP_INTERCEPTORS, HttpClientModule} from '@angular/common/http';
import {NotificationModule} from './notification/notification.module';
import {NotificationService} from './notification/notification.service';
import {AuthenticationService} from './service/authentication.service';
import {UserService} from './service/user.service';
import {AuthGuard} from './guard/auth.guard';
import {AuthInterceptor} from './interceptors/auth.interceptor';
import {RegisterComponent} from './components/register/register.component';
import {HomeComponent} from './components/home/home.component';
import {ItemService} from './service/items.service';
import {CategoryService} from './service/category.service';
import {ShoppingCartService} from './service/shopping-cart.service';
import {ShoppingCartComponent} from './components/shopping-cart/shopping-cart.component';
import {OrdersService} from './service/orders.service';
import {OrderComponent} from './components/orders/orders.component';
import {ReviewComponent} from './components/review/review.component';
import {ReviewService} from './service/review.service';
import {ItemDetailsComponent} from './components/details/details.component';
import {AddItemComponent} from './components/item/item.component';
import {EditItemComponent} from './components/edit/edit.component';
import {CustomerOrdersComponent} from './components/customer-orders/customer-orders.component';

@NgModule({
  declarations: [
    AppComponent,
    LoginComponent,
    RegisterComponent,
    UserComponent,
    HomeComponent,
    ShoppingCartComponent,
    OrderComponent,
    ReviewComponent,
    ItemDetailsComponent,
    AddItemComponent,
    EditItemComponent,
    CustomerOrdersComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    FormsModule,
    HttpClientModule,
    NotificationModule,
    ReactiveFormsModule
  ],
  providers: [
    NotificationService,
    AuthenticationService,
    ItemService,
    CategoryService,
    ShoppingCartService,
    UserService,
    OrdersService,
    ReviewService,
    AuthGuard,
    { provide: HTTP_INTERCEPTORS, useClass: AuthInterceptor, multi: true },
  ],
  bootstrap: [AppComponent]
})
export class AppModule { }
