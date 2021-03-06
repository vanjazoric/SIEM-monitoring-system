import { Component, OnInit } from '@angular/core';
import { Router, ActivatedRoute } from '@angular/router';
import {CenterService} from "../../services/center.service";
import { WebsocketService } from "../../services/websocket.service";

@Component({
    selector: 'app-server-logs',
    templateUrl: './server-logs.component.html',
    styleUrls: ['./server-logs.component.css'],
    providers: [CenterService]
})
export class ServerLogsComponent implements OnInit {
    private oneLog: any;
    logs: any[];
    _log:any;
    numOfLogs: number;
    ip: string;
    httpStatus: string;
    method: string;
    error: string = "";
    startDate: string;
    endDate: string;

    constructor(private router: Router, private centerService: CenterService, private webSocketService : WebsocketService) {
        let stompClient = this.webSocketService.connect();
        stompClient.connect({}, frame => {
            stompClient.subscribe("/logs/serverLogs", saved => {
                this._log = JSON.parse(saved.body);
                this.logs.push(this._log);
            });
        });
    }


    searchByIP() {
        this.centerService.searchServerLogsByIP(this.ip)
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

    searchByMethod() {
        this.centerService.searchServerLogsByMethod(this.method)
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

    searchByHTTP() {
        this.centerService.searchServerLogsByHTTP(this.httpStatus)
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
        this.centerService.searchServerLogsByDate(this.startDate, this.endDate)
            .subscribe(
            data => {
                this.logs = data;
                console.log(this.logs.length);
                this.numOfLogs = this.logs.length;
            });
    }
         
        
    ngOnInit() {
           this.centerService.getServerLogs()
            .subscribe(
            data => {
                this.logs = data;
                this.numOfLogs = this.logs.length;
                console.log(this.numOfLogs);
            });
        }
}
