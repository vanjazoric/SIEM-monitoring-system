import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Alarm } from '../model/alarm';

@Injectable({
  providedIn: 'root'
})
export class AlarmService {
  public sendTo : string = 'http://localhost:8888/alarms'; 

  constructor(private httpClient : HttpClient) { 

  }

  public getAlarms(){
    return this.httpClient.get<Alarm[]>(this.sendTo);
  }

  createAlarm(alarm : Alarm){
    return this.httpClient.post<Alarm>(this.sendTo, alarm);
  }

}
