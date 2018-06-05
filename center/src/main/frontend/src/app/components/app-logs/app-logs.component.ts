import { Component, OnInit } from '@angular/core';
import { Router, ActivatedRoute } from '@angular/router';
import {CenterService} from "../../services/center.service";

@Component({
    selector: 'app-app-logs',
    templateUrl: './app-logs.component.html',
    styleUrls: ['./app-logs.component.css'],
    providers: [CenterService]
})
export class AppLogsComponent implements OnInit {
    private oneLog: any;
    logs: any[];
    numOfLogs: number;
    id: string;
    message: string;
    app: string;
    priority: string;
    error: string;
    startDate: string;
    endDate: string;

    constructor(private router: Router,
        private centerService: CenterService) {
        setTimeout(function() {
            location.reload();
        }, 50000); //svakih 30 sekundi se dobavljaju novi logovi
    }


    searchById() {
        this.centerService.searchAppLogsById(this.id)
            .subscribe(
            data => {
                this.oneLog = data;
                this.numOfLogs = 1;
                this.logs.length = 0;
                this.logs.push(this.oneLog);
            });
    }

    searchByApp() {
        this.centerService.searchAppLogsByApp(this.app)
            .subscribe(
            data => {
                this.logs = data;
                this.numOfLogs = this.logs.length;
            });
    }

    searchByMessage() {
        this.centerService.searchAppLogsByMessage(this.message)
            .subscribe(
            data => {
                this.logs = data;
                this.numOfLogs = this.logs.length;
            });
    }

    searchByPriority() {
        this.centerService.searchAppLogsByPriority(this.priority)
            .subscribe(
            data => {
                this.logs = data;
                this.numOfLogs = this.logs.length;
            },
            error => { this.error = "Uneseni podaci nisu ispravni. Pokušajte ponovo."; });
    }

    searchLogs() {
        this.centerService.searchAppLogsByDate(this.startDate, this.endDate)
            .subscribe(
            data => {
                this.logs = data;
                console.log(this.logs.length);
                this.numOfLogs = this.logs.length;

            });
    }

    ngOnInit() {
        this.centerService.getAppLogs()
            .subscribe(
            data => {
                this.logs = data;
                this.numOfLogs = this.logs.length;
                console.log(this.numOfLogs);
            });
    }
}