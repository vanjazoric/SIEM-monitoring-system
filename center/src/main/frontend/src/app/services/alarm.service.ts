import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Alarm } from '../model/alarm';

@Injectable({
  providedIn: 'root'
})
export class AlarmService {
  public sendTo : string = '/api/alarms'; 

  constructor(private httpClient : HttpClient) { 

  }

  public getAlarms(){
    return this.httpClient.get<Alarm[]>(this.sendTo);
  }

  createAlarm(alarm : Alarm){
    return this.httpClient.post<Alarm>(this.sendTo, alarm);
  }

  updateAlarm(alarm: Alarm){
    return this.httpClient.put<Alarm>(this.sendTo, alarm);
  }

  deleteAlarm(alarmId: string){
    return this.httpClient.delete(this.sendTo + "/" + alarmId, {responseType : "text"});
  }

  getTriggeredAlarms(){
    return this.httpClient.get<Alarm[]>(this.sendTo + "/triggeredAlarms");
  }

}
