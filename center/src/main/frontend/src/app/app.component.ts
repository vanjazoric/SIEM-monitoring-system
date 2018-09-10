import { Component } from '@angular/core';
import { AlarmService } from './services/alarm.service';
import { LoginService } from './services/login.service';
import { WebsocketService } from './services/websocket.service';
import { Alarm } from './model/alarm';
import { Router, Event, NavigationEnd } from '@angular/router';
declare var $: any;

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent {

  alarms: Alarm[];
  alarm: Alarm; 
  role: string = '';
  currentLocation: string;

  constructor(private alarmService: AlarmService, private loginService: LoginService, private webSocketService: WebsocketService, private router: Router){
    this.role = this.loginService.getCurrentUser();
    this.router.events.subscribe(
      (event: Event) =>{ 
        this.currentLocation = (event as NavigationEnd).url;
      }
    );    
    let stompClient = this.webSocketService.connect();
    stompClient.connect({}, frame => {
        stompClient.subscribe("/logs/alarms", saved => {
            this.alarm = JSON.parse(saved.body);
            this.alarms.push(this.alarm);
        });
    });
  }

  triggeredAlarms(){
    $("#showAlarms").modal("show");
    this.alarmService.getTriggeredAlarms().subscribe(
      res => this.alarms = res
    );
  }

logout(){
	this.loginService.logout();
}

}
