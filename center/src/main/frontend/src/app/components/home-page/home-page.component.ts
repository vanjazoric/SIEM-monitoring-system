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
	private logs : any[];
	numOfLogs : number;

  constructor(private router: Router,
        private centerService: CenterService) { }

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

