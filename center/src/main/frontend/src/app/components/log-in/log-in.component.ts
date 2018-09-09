import { Component, OnInit } from '@angular/core';
import { Router, ActivatedRoute } from '@angular/router';
import {LoginService} from "../../services/login.service";

@Component({
    selector: 'LogIn',
    templateUrl: './log-in.component.html',
    styleUrls: ['./log-in.component.css']
})
export class LogInComponent {

  password: string;
  username: string;
  error: string;

  constructor(private route: ActivatedRoute,
    private router: Router,private logInService: LoginService) {
    this.logInService.logout();
  }

  ngOnInit() {
  }

  login() {
    this.logInService.logIn(this.password, this.username).then(
        data => {
        	console.log("AAAAAA"+data);
            localStorage.setItem('currentUser', JSON.stringify({
            username: data.username,
            role: data.role,
            token: data.token
          }));
            console.log("Uspesno logovanje");
            this.router.navigate(['/']);
        },
        error => {
            this.error = "Uneseni podaci nisu ispravni. Pokušajte ponovo.";
        }
    );
  }
}
