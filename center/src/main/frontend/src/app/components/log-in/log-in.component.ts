import { Component, OnInit } from '@angular/core';
import { Router, ActivatedRoute } from '@angular/router';
import {AuthenticationService} from "../../services/authentication.service";

@Component({
    moduleId: module.id,
    selector: 'LogIn',
    templateUrl: './log-in.component.html',
    styleUrls: ['./log-in.component.css'],
    providers: [AuthenticationService]
})
export class LogInComponent {

    password: string;
    username: string;
    error: string;

    constructor(private route: ActivatedRoute,
        private router: Router, private authenticationService: AuthenticationService) {
        this.authenticationService.logout();
    }


    login() {
        this.authenticationService.logIn(this.password, this.username).subscribe(
            data => {
                let token = data.text();
                localStorage.setItem('jwt', token);
                console.log("Uspesno logovanje");
                this.router.navigate(['/admin']);
            },
            error => {
                this.error = "Uneseni podaci nisu ispravni. Pokušajte ponovo.";
            }
        );
    }
}
