import { Component, OnInit } from '@angular/core';
import { Router, ActivatedRoute } from '@angular/router';
import {CenterService} from "../../services/center.service";

@Component({
    selector: 'app-os-logs',
    templateUrl: './os-logs.component.html',
    styleUrls: ['./os-logs.component.css'],
    providers: [CenterService]
})
export class OSLogsComponent implements OnInit {
    private oneLog: any;
    logs: any[];
    numOfLogs: number;
    source: string;
    eventId: number;
    level: string;
    error: string = "";
    startDate: string;
    endDate: string;

    constructor(private router: Router,
        private centerService: CenterService) {
        setTimeout(function() {
            location.reload();
        }, 50000); //svakih 30 sekundi se dobavljaju novi logovi 
    }


    searchBySource() {
        this.centerService.searchOSLogsBySource(this.source)
            .subscribe(
            data => {
                this.error = "";
                this.logs = data;
                this.numOfLogs = this.logs.length;
            },
            error => {
                this.numOfLogs = 0;
                this.error = "Nije pronađen nijedan log.";
            });
    }

    searchByLevel() {
        this.centerService.searchOSLogsByLevel(this.level)
            .subscribe(
            data => {
                this.error = "";
                this.numOfLogs = 0;
                this.logs = data;
                this.numOfLogs = this.logs.length;
            },
            error => {
                this.numOfLogs = 0;
                this.error = "Nije pronađen nijedan log.";
            });
    }

    searchByEventId() {
        this.centerService.searchOSLogsByEventId(this.eventId)
            .subscribe(
            data => {
                this.numOfLogs = 0;
                this.error = "";
                this.logs = data;
                this.numOfLogs = this.logs.length;
            },

            error => {
                this.numOfLogs = 0;
                this.error = "Nije pronađen nijedan log.";
            });
    }

    searchLogs() {
        this.centerService.searchOSLogsByDate(this.startDate, this.endDate)
            .subscribe(
            data => {
                this.logs = data;
                console.log(this.logs.length);
                this.numOfLogs = this.logs.length;
            });
    }

    ngOnInit() {
        this.centerService.getOSLogs()
            .subscribe(
            data => {
                this.logs = data;
                this.numOfLogs = this.logs.length;
                console.log(this.numOfLogs);
            });
    }
}
