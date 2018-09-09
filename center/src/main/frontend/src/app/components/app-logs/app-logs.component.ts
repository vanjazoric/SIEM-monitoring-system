import { Component, OnInit } from '@angular/core';
import { Router, ActivatedRoute } from '@angular/router';
import { CenterService } from "../../services/center.service";
import { WebsocketService } from "../../services/websocket.service";

@Component({
    selector: 'app-app-logs',
    templateUrl: './app-logs.component.html',
    styleUrls: ['./app-logs.component.css'],
    providers: [CenterService]
})
export class AppLogsComponent implements OnInit {
    private oneLog: any;
    logs: any[];
    _log : any;
    numOfLogs: number;
    id: string;
    message: string;
    app: string;
    priority: string;
    error: string = "";
    startDate: string;
    endDate: string;

    constructor(private router: Router, private centerService: CenterService, private webSocketService : WebsocketService) {
        let stompClient = this.webSocketService.connect();
        stompClient.connect({}, frame => {
            stompClient.subscribe("/logs/applogs", saved => {
                this._log = JSON.parse(saved.body);
                this.logs.push(this._log);
            });
        });
    }


    searchById() {
        this.centerService.searchAppLogsById(this.id)
            .subscribe(
            data => {
                this.oneLog = data;
                this.numOfLogs = 1;
                this.logs.length = 0;
                this.error = "";
                this.logs.push(this.oneLog);
			},
            error => {
                this.numOfLogs = 0;
                console.log("USAAOOOO");
                this.error = "Nije pronađen nijedan log.";
            });
    }

    searchByApp() {
        this.centerService.searchAppLogsByApp(this.app)
            .subscribe(
            data => {
                this.logs = data;
                this.numOfLogs = this.logs.length;
           	},
            error => {
                this.numOfLogs = 0;
                console.log("USAAOOOO");
                this.error = "Nije pronađen nijedan log.";
            });
    }

    searchByMessage() {
        this.centerService.searchAppLogsByMessage(this.message)
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

    searchByPriority() {
        this.centerService.searchAppLogsByPriority(this.priority)
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