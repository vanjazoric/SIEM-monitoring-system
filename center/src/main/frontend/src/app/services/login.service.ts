import { Injectable } from '@angular/core';
import {HttpClient, HttpHeaders, HttpParams} from '@angular/common/http';
import {LoginDTO} from '../model/loginDTO';
import { CurrentUser } from '../model/current-user';
import { Router } from '@angular/router';

@Injectable({
  providedIn: 'root'
})
export class LoginService {

  public currentUser:LoginDTO;
  public headers = new HttpHeaders({ 'Content-Type': 'application/json' });

  constructor(private http: HttpClient, private router: Router) {}

  logIn(password: string, username:string):Promise<CurrentUser>{
    this.currentUser = new LoginDTO(username, password);
    
    return this.http.post('/api/login', this.currentUser, {headers:this.headers}).toPromise()
      .then(res => res as CurrentUser);
  }

  logout(){
    localStorage.removeItem("currentUser");  
     this.router.navigate(['/login']);
  }
  
  getCurrentUser(){
  	var userJSON = localStorage.getItem('currentUser');
	var user = JSON.parse(userJSON);
if (user!= null){
	return user.role;
}
	}
	
	changePassword(oldPassword: string, newPassword: string, username: string){
    const url = '/api/change-password';
    return this.http.post(url, {oldPassword: oldPassword, newPassword: newPassword, username: username});
	}
	
	   private jwt() {
        let token = localStorage.getItem("currentUser");
        var user = JSON.parse(token);
        return new HttpHeaders({ 'X-Auth-Token': user.token });
    }
}