import { Component, OnInit } from '@angular/core';
import { Router, ActivatedRoute } from '@angular/router';
import {LoginService} from "../../services/login.service";

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css'],
  providers: [LoginService]
})
export class LoginComponent implements OnInit {

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
    this.logInService.logIn(this.password, this.username).subscribe(
        data => {
            let token = data.text();
            localStorage.setItem('jwt', token);
            console.log("Uspesno logovanje");
            this.router.navigate(['/center']);
        },
        error => {
            this.error = "Uneseni podaci nisu ispravni. Pokušajte ponovo.";
        }
    );
  }

}
