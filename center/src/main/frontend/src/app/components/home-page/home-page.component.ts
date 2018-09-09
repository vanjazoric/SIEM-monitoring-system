import { Component, OnInit } from '@angular/core';
import { Router, ActivatedRoute } from '@angular/router';
import { CenterService } from "../../services/center.service";
import { WebsocketService } from '../../services/websocket.service';
import { Log } from '../../model/log';

@Component({
    selector: 'app-home-page',
    templateUrl: './home-page.component.html',
    styleUrls: ['./home-page.component.css'],
    providers: [CenterService]
})
export class HomePageComponent implements OnInit {

    ngOnInit() {
    }
}

