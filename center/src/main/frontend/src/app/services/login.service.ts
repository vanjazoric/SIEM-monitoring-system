import { Injectable } from '@angular/core';
import {HttpClient, HttpHeaders, HttpParams} from '@angular/common/http';
import {LoginDTO} from '../model/loginDTO';
import { CurrentUser } from '../model/current-user';

@Injectable({
  providedIn: 'root'
})
export class LoginService {

  public currentUser:LoginDTO;
  public headers = new HttpHeaders({ 'Content-Type': 'application/json' });

  constructor(private http: HttpClient) {}

  logIn(password: string, username:string):Promise<CurrentUser>{
    this.currentUser = new LoginDTO(username, password);
    
    return this.http.post('/api/login', this.currentUser, {headers:this.headers}).toPromise()
      .then(res => res as CurrentUser);
  }

  logout(){
    localStorage.removeItem("currentUser");  
  }

}
