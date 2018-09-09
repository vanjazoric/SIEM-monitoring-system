import { Component, OnInit } from '@angular/core';
import { Router, ActivatedRoute } from '@angular/router';
import { CenterService } from "../../services/center.service";
import { WebsocketService } from '../../services/websocket.service';

@Component({
    selector: 'app-firewall-logs',
    templateUrl: './firewall-logs.component.html',
    styleUrls: ['./firewall-logs.component.css'],
    providers: [CenterService]
})
export class FirewallLogsComponent implements OnInit {
    private oneLog: any;
    logs: any[];
    _log : any;
    numOfLogs: number;
    action: string;
    protocol: string;
    srcIp: string;
    dstIp: string;
    srcPort: string;
    dstPort: string;
    size: number;
    tcpflags: string;
    tcpsync: string; 
    error: string = "";
    startDate: string;
    endDate: string;

    constructor(private router: Router,private centerService: CenterService, private webSocketService : WebsocketService) { 
        let stompClient = this.webSocketService.connect();
        stompClient.connect({}, frame => {
            stompClient.subscribe("/logs/firewallLogs", saved => {
                this._log = JSON.parse(saved.body);
                this.logs.push(this._log);
            });
        });
    }


    searchByAction() {
        this.centerService.searchFirewallLogsByAction(this.action)
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

    searchByProtocol() {
        this.centerService.searchFirewallLogsByProtocol(this.protocol)
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

    searchBySrcIp() {
        this.centerService.searchFirewallLogsBySrcIp(this.srcIp)
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

    searchByDstIp() {
        this.centerService.searchFirewallLogsByDstIp(this.dstIp)
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
        this.centerService.searchFirewallLogsByDate(this.startDate, this.endDate)
            .subscribe(
            data => {
                this.logs = data;
                console.log(this.logs.length);
                this.numOfLogs = this.logs.length;
            });
    }

    ngOnInit() {
        this.centerService.getFirewallLogs()
            .subscribe(
            data => {
                this.logs = data;
                this.numOfLogs = this.logs.length;
                console.log(this.numOfLogs);
            });
    }
}
