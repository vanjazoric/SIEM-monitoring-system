import { Component, OnInit } from '@angular/core';
import { ReportServiceService } from '../../services/report-service.service';
import { Report } from '../../model/report';
import { Router } from '@angular/router';
declare var $: any;

@Component({
  selector: 'app-create-report',
  templateUrl: './create-report.component.html',
  styleUrls: ['./create-report.component.css']
})
export class CreateReportComponent implements OnInit {

  radioSelectedTerm: string;
  reportTitle: string;
  startDate: string;
  endDate: string;
  reportType: string;
  mashineName: string;

  constructor(private reportService: ReportServiceService, private router : Router) {
   }

  ngOnInit() {
    this.radioSelectedTerm = "Machine";
  }

  create(){
    let start = new Date(this.startDate);
    let end = new Date(this.endDate);
    let report = new Report(this.reportTitle, start, end, this.reportType, this.radioSelectedTerm);
    if(this.radioSelectedTerm == "Machine"){
      report.machineName = this.mashineName;
    }
    this.reportService.createReport(report).subscribe(
      res =>{
        console.log(res);
        this.router.navigate(['../reports']);
      }
    );
  }

}
