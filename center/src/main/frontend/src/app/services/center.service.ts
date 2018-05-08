import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders, HttpParams } from '@angular/common/http';
import { Log } from '../model/log'

@Injectable({
  providedIn: 'root'
})
export class CenterService {

  constructor(private http: HttpClient) { }

    getLogs() {
        return this.http.get<Log[]>('http://localhost:8888/logs');
    }
}