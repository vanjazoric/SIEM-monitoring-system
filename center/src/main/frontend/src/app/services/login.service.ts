import { Injectable } from '@angular/core';
import {Http,Headers} from '@angular/http';
import {LoginDTO} from '../model/loginDTO';

@Injectable({
  providedIn: 'root'
})
export class LoginService {

  public currentUser:LoginDTO;

  constructor(private http:Http) {}

  logIn(password: string, username:string){
    this.currentUser = new LoginDTO(username, password);
    var param = JSON.stringify(this.currentUser);
    var headers = new Headers();
    headers.append('Content-Type','application/json');
    console.log(param);
    return this.http.post("https://localhost:8888/login", param, {headers:headers});
  }

  logout(){
    localStorage.removeItem("currentUser");  
  }

}
