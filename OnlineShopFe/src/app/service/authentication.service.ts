import {Injectable} from '@angular/core';
import {environment} from '../../environments/environment';
import {HttpClient, HttpErrorResponse} from '@angular/common/http';
import {Observable} from 'rxjs';
import {User} from '../model/user';
import {JwtHelperService} from '@auth0/angular-jwt';

@Injectable({
  providedIn: 'root'
})
export class AuthenticationService {

  public host: string = environment.apiUrl;
  private token: string;
  private loggedInUsername: string;
  private jwtHelper = new JwtHelperService();

  constructor(private httpClient: HttpClient) {
  }

  public login(user: User): Observable<User> {
    return this.httpClient.post<User>(`${this.host}/login`, user);
  }

  public register(user: User): Observable<User | HttpErrorResponse> {
    return this.httpClient.post<User | HttpErrorResponse>
    (`${this.host}/register`, user);
  }

  public logout(): void {
    this.token = null;
    this.loggedInUsername = null;
    localStorage.removeItem('user');
    localStorage.removeItem('token');
    localStorage.removeItem('users');
  }

  public saveToken(token: string): void {
    this.token = token;
    localStorage.setItem('token', token);
  }

  public addUserToLocalStorage(user: User): void {
    localStorage.setItem('user', JSON.stringify(user));
  }

  public loadToken(): void {
    this.token = localStorage.getItem('token');
  }

  public getToken(): string {
    return this.token;
  }

  public isUserLoggedIn(): boolean {
    this.loadToken();
    if (this.token != null && this.token !== '') {
      const username = this.jwtHelper.decodeToken(this.token).sub;
      if ((username != null || '') && !this.jwtHelper.isTokenExpired(this.token)) {
        this.loggedInUsername = username;
        return true;
      }
      return true;
    } else {
      this.logout();
      return false;
    }
  }

  public getUserFromLocalCache(): User {
    return JSON.parse(localStorage.getItem('user'));
  }

  public isUserAdmin(): boolean {
    const user = this.getUserFromLocalCache();
    console.log(user);

    return user.userDetails.roles[0] === 'ROLE_ADMIN';
  }

}
