import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Report } from '../model/report';

@Injectable({
  providedIn: 'root'
})
export class ReportServiceService {

  public sendTo : string = '/api/reports';

  constructor(private httpClient: HttpClient) {

   }

   public createReport(report: Report){
     return this.httpClient.post<Report>(this.sendTo, report);
   }

   public getReports(){
     return this.httpClient.get<Report[]>(this.sendTo);
   }

}
