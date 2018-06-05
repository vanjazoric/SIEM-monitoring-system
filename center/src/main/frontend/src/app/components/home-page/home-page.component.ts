import { Component, OnInit } from '@angular/core';

import { Router, ActivatedRoute } from '@angular/router';
import {CenterService} from "../../services/center.service";

@Component({
    selector: 'app-home-page',
    templateUrl: './home-page.component.html',
    styleUrls: ['./home-page.component.css'],
    providers: [CenterService]
})
export class HomePageComponent implements OnInit {
    private logs: any[];
    numOfLogs: number;
    startDate: string;
    endDate: string;

    constructor(private router: Router,
        private centerService: CenterService) {
        setTimeout(function() {
            location.reload();
        }, 50000); //svakih 30 sekundi se dobavljaju novi logovi    
    }


    searchLogs() {
        this.centerService.searchLogs(this.startDate, this.endDate)
            .subscribe(
            data => {
                this.logs = data;
                console.log(this.logs.length);
                this.numOfLogs = this.logs.length;

            });
    }

    ngOnInit() {
        console.log("HALOOOOO");
        this.centerService.getLogs()
            .subscribe(
            data => {
                this.logs = data;
                this.numOfLogs = this.logs.length;
            });
    }
}

