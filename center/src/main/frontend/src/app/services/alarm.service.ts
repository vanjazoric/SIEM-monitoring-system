import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Alarm } from '../model/alarm';

@Injectable({
  providedIn: 'root'
})
export class AlarmService {
  public sendTo : string = '/api/alarms'; 

  constructor(private httpClient : HttpClient) { 

  }

  public getAlarms(){
    return this.httpClient.get<Alarm[]>(this.sendTo, { headers: this.jwt()});
  }

  createAlarm(alarm : Alarm){
    return this.httpClient.post<Alarm>(this.sendTo, alarm, { headers: this.jwt()});
  }

  updateAlarm(alarm: Alarm){
    return this.httpClient.put<Alarm>(this.sendTo, alarm, { headers: this.jwt()});
  }

  deleteAlarm(alarmId: string){
    return this.httpClient.delete(this.sendTo + "/" + alarmId, {responseType : "text",  headers: this.jwt()});
  }

  getTriggeredAlarms(){
    return this.httpClient.get<Alarm[]>(this.sendTo + "/triggeredAlarms", { headers: this.jwt()});
  }
  
     private jwt() {
        let token = localStorage.getItem("currentUser");
        var user = JSON.parse(token);
        return new HttpHeaders({ 'X-Auth-Token': user.token });
	}
}
