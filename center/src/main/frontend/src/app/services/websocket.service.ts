import { Injectable } from '@angular/core';
declare var require: any;
var SockJs = require("sockjs-client");
var Stomp = require("stompjs/lib/stomp.js").Stomp;

@Injectable({
  providedIn: 'root'
})
export class WebsocketService {

  public connect() {
    let socket = new SockJs("/api/socket");
    let stompClient = Stomp.over(socket);
    return stompClient;
  }
  
}
