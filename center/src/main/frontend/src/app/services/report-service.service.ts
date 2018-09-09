import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders} from '@angular/common/http';
import { Report } from '../model/report';
import {LoginDTO} from '../model/loginDTO';
 
@Injectable({
  providedIn: 'root'
})
export class ReportServiceService {

  public sendTo : string = '/api/reports';

  constructor(private httpClient: HttpClient) {

   }

   public createReport(report: Report){
     return this.httpClient.post<Report>(this.sendTo, report, { headers: this.jwt()});
   }

   public getReports(){
     return this.httpClient.get<Report[]>(this.sendTo,{ headers: this.jwt()});
   }
   
   private jwt() {
        let token = localStorage.getItem("currentUser");
        var user = JSON.parse(token);
        return new HttpHeaders({ 'X-Auth-Token': user.token });
    }

}
