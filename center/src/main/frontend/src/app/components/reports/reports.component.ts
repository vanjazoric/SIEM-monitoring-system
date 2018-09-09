import { Component, OnInit } from '@angular/core';
import { ReportServiceService } from '../../services/report-service.service';
import { Report } from '../../model/report';

@Component({
  selector: 'app-reports',
  templateUrl: './reports.component.html',
  styleUrls: ['./reports.component.css']
})
export class ReportsComponent implements OnInit {

  reports: Report[];

  constructor(private reportService: ReportServiceService) { }

  ngOnInit() {
    this.reportService.getReports().subscribe(
      res => this.reports = res
    );
  }

}
