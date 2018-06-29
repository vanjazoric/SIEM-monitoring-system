import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders, HttpParams } from '@angular/common/http';
import {LoginDTO} from '../model/loginDTO';

@Injectable({
    providedIn: 'root'
})
export class AuthenticationService {

    public currentUser: LoginDTO;

    constructor(private http: HttpClient) { }

    logIn(password: string, username: string) {
        this.currentUser = new LoginDTO(username, password);
        var param = JSON.stringify(this.currentUser);
        console.log(param);
        var headers = new HttpHeaders({'Content-Type':'application/json'});
        //headers.append('Content-Type', 'application/json');
        console.log(param);
        return this.http.post("http://localhost:8888/login", param, { headers: headers });
    }

    logout() {
        localStorage.removeItem("currentUser");
    }
}
