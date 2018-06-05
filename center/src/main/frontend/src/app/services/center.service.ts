import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders, HttpParams } from '@angular/common/http';
import { Log } from '../model/log'
import { ApplicationLog } from '../model/appLog';
import { OperatingSystemLog } from '../model/OSlog'
import { LogFirewall } from '../model/firewallLog'
import { LogServer } from '../model/logServer';

@Injectable({
    providedIn: 'root'
})
export class CenterService {

    constructor(private http: HttpClient) { }

    getLogs() {
        return this.http.get<Log[]>('http://localhost:8888/logs');
    }

    searchLogs(startDate: string, endDate: string) {
        return this.http.get<Log[]>("http://localhost:8888/logs/" + startDate + "/" + endDate);
    }

    getAppLogs() {
        return this.http.get<ApplicationLog[]>('http://localhost:8888/applicationLogs');
    }

    searchAppLogsById(id: string) {
        console.log(id);
        return this.http.get<ApplicationLog[]>("http://localhost:8888/applicationLogs/" + id);
    }

    searchAppLogsByApp(app: string) {
        console.log(app);
        const params = new HttpParams().set('app', app);
        return this.http.get<ApplicationLog[]>("http://localhost:8888/applicationLogs/", { params });
    }

    searchAppLogsByMessage(message: string) {
        console.log(message);
        const params = new HttpParams().set('message', message);
        return this.http.get<ApplicationLog[]>("http://localhost:8888/applicationLogs/", { params });
    }

    searchAppLogsByPriority(priority: string) {
        var uri = "http://localhost:8888/applicationLogs";
        var encoded = encodeURI(uri);
        const params = new HttpParams().set('priority', priority);
        return this.http.get<ApplicationLog[]>(encoded, { params });
    }

    searchAppLogs(startDate: string, endDate: string) {
        return this.http.get<ApplicationLog[]>("http://localhost:8888/applicationLogs/" + startDate + "/" + endDate);
    }

    searchAppLogsByDate(startDate: string, endDate: string) {
        return this.http.get<Log[]>("http://localhost:8888/applicationLogs/" + startDate + "/" + endDate);
    }

    getServerLogs() {
        return this.http.get<LogServer[]>('http://localhost:8888/logserver');
    }

    searchServerLogsByIP(ip: string) {
        console.log(ip);
        return this.http.get<LogServer[]>("http://localhost:8888/logserver?ip=" + ip);
    }

    searchServerLogsByHTTP(http: string) {
        var uri = "http://localhost:8888/logserver?http=" + http;
        var encoded = encodeURI(uri);
        return this.http.get<LogServer[]>(encoded);
    }

    searchServerLogsByMethod(method: string) {
        var uri = "http://localhost:8888/logserver?method=" + method;
        var encoded = encodeURI(uri);
        return this.http.get<LogServer[]>(encoded);
    }

    searchServerLogsByDate(startDate: string, endDate: string) {
        return this.http.get<LogServer[]>("http://localhost:8888/logserver/" + startDate + "/" + endDate);
    }

    getOSLogs() {
        return this.http.get<OperatingSystemLog[]>('http://localhost:8888/OSlogs');
    }

    searchOSLogsBySource(source: string) {
        return this.http.get<OperatingSystemLog[]>("http://localhost:8888/OSlogs?ip=" + source);
    }

    searchOSLogsByEventId(eventId: number) {
        return this.http.get<OperatingSystemLog[]>("http://localhost:8888/OSlogs?http=" + eventId);
    }

    searchOSLogsByLevel(method: string) {
        var uri = "http://localhost:8888/OSlogs?method=" + method;
        var encoded = encodeURI(uri);
        return this.http.get<OperatingSystemLog[]>(encoded);
    }

    searchOSLogsByDate(startDate: string, endDate: string) {
        return this.http.get<OperatingSystemLog[]>("http://localhost:8888/OSlogs/" + startDate + "/" + endDate);
    }

    getFirewallLogs() {
        return this.http.get<LogFirewall[]>('http://localhost:8888/logfirewall');
    }

    searchFirewallLogsByProtocol(protocol: string) {
        var uri = "http://localhost:8888/logfirewall?protocol=" + protocol;
        var encoded = encodeURI(uri);
        return this.http.get<LogFirewall[]>(encoded);
    }

    searchFirewallLogsByAction(action: string) {
        var uri = "http://localhost:8888/logfirewall?action=" + action;
        var encoded = encodeURI(uri);
        return this.http.get<LogFirewall[]>(encoded);
    }

    searchFirewallLogsByDstIp(dstIp: string) {
        return this.http.get<LogFirewall[]>("http://localhost:8888/logfirewall?dstIp=" + dstIp);
    }

    searchFirewallLogsBySrcIp(srcIp: string) {
        return this.http.get<LogFirewall[]>("http://localhost:8888/logfirewall?srcIp=" + srcIp);
    }

    searchFirewallLogsByDate(startDate: string, endDate: string) {
        return this.http.get<LogFirewall[]>("http://localhost:8888/logfirewall/" + startDate + "/" + endDate);
    }


}
