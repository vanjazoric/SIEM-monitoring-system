import { Component, OnInit } from '@angular/core';
import {LoginService} from "../../services/login.service";
import {Router} from "@angular/router";


@Component({
  selector: 'app-change-password',
  templateUrl: './change-password.component.html',
  styleUrls: ['./change-password.component.css']
})
export class ChangePasswordComponent implements OnInit {
	oldPassword: string = '';
	newPassword: string = '';
	username: string = '';
	user:any;
	error: string = '';
	success: string = "";
	

  constructor(private loginService: LoginService, private router: Router) {
  
  }

  ngOnInit() {
  }
  
  changePassword(){
  	var user = this.loginService.changePassword(this.oldPassword, this.newPassword, this.username).subscribe(
            res=>{
                this.user=res;
                this.success = "Uspesno ste promenili lozinku.";
                this.router.navigate(['/login']);
                
                
            },error=>{
                this.error = "Kredencijali nisu postojeci.";
            });

  }
  

}
