import {Injectable} from '@angular/core';
import {HttpEvent, HttpHandler, HttpInterceptor, HttpRequest} from '@angular/common/http';
import {Observable} from 'rxjs';
import {AuthenticationService} from '../service/authentication.service';

@Injectable()
export class AuthInterceptor implements HttpInterceptor {

  constructor(private authenticationSevice: AuthenticationService) {}

  intercept(request: HttpRequest<unknown>, next: HttpHandler): Observable<HttpEvent<unknown>> {
    if (request.url.includes(`${this.authenticationSevice.host}/login`)) {
      return next.handle(request);
    }



    this.authenticationSevice.loadToken();
    const token = this.authenticationSevice.getToken();

    return next.handle(request.clone({setHeaders: {Authorization: `Bearer ${token}`, 'Access-Control-Allow-Origin': '*' }}));
  }
}
