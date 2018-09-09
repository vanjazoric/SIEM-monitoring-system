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
        return this.http.get<Log[]>('/api/logs', { headers: this.jwt()});
    }

    searchLogs(startDate: string, endDate: string) {
        return this.http.get<Log[]>("/api/logs/" + startDate + "/" + endDate, { headers: this.jwt()});
    }

    getAppLogs() {
        return this.http.get<ApplicationLog[]>('/api/applicationLogs', { headers: this.jwt()});
    }

    searchAppLogsById(id: string) {
        console.log(id);
        return this.http.get<ApplicationLog[]>("/api/applicationLogs/" + id, { headers: this.jwt()});
    }

    searchAppLogsByApp(app: string) {
        console.log(app);
        const param = new HttpParams().set('app', app);
        return this.http.get<ApplicationLog[]>("/api/applicationLogs", { params : param, headers: this.jwt() });
    }

    searchAppLogsByMessage(message: string) {
        console.log(message);
        const param = new HttpParams().set('message', message);
        return this.http.get<ApplicationLog[]>("/api/applicationLogs", { params : param, headers: this.jwt()});
    }

    searchAppLogsByPriority(priority: string) {
        var uri = "/api/applicationLogs";
        var encoded = encodeURI(uri);
        const param = new HttpParams().set('priority', priority);
        return this.http.get<ApplicationLog[]>(encoded, { params : param, headers: this.jwt()});
    }

    searchAppLogs(startDate: string, endDate: string) {
        return this.http.get<ApplicationLog[]>("/api/applicationLogs/" + startDate + "/" + endDate, { headers: this.jwt()});
    }

    searchAppLogsByDate(startDate: string, endDate: string) {
        return this.http.get<Log[]>("/api/applicationLogs/" + startDate + "/" + endDate, { headers: this.jwt()});
    }

    getServerLogs() {
        return this.http.get<LogServer[]>('/api/logserver', { headers: this.jwt()});
    }

    searchServerLogsByIP(ip: string) {
        console.log(ip);
        return this.http.get<LogServer[]>("/api/logserver?ip=" + ip, { headers: this.jwt()});
    }

    searchServerLogsByHTTP(http: string) {
        var uri = "/api/logserver?http=" + http;
        var encoded = encodeURI(uri);
        return this.http.get<LogServer[]>(encoded, { headers: this.jwt()});
    }

    searchServerLogsByMethod(method: string) {
        var uri = "/api/logserver?method=" + method;
        var encoded = encodeURI(uri);
        return this.http.get<LogServer[]>(encoded, { headers: this.jwt()});
    }

    searchServerLogsByDate(startDate: string, endDate: string) {
        return this.http.get<LogServer[]>("/api/logserver/" + startDate + "/" + endDate, { headers: this.jwt()});
    }

    getOSLogs() {
        return this.http.get<OperatingSystemLog[]>('/api/OSlogs', { headers: this.jwt()});
    }

    searchOSLogsBySource(source: string) {
        return this.http.get<OperatingSystemLog[]>("/api/OSlogs?source=" + source, { headers: this.jwt()});
    }

    searchOSLogsByEventId(eventId: number) {
        return this.http.get<OperatingSystemLog[]>("/api/OSlogs?eventId=" + eventId, { headers: this.jwt()});
    }

    searchOSLogsByLevel(level: string) {
        var uri = "/api/OSlogs?level=" + level;
        var encoded = encodeURI(uri);
        return this.http.get<OperatingSystemLog[]>(encoded, { headers: this.jwt()});
    }

    searchOSLogsByDate(startDate: string, endDate: string) {
        return this.http.get<OperatingSystemLog[]>("/api/OSlogs/" + startDate + "/" + endDate, { headers: this.jwt()});
    }

    getFirewallLogs() {
        return this.http.get<LogFirewall[]>('/api/logfirewall', { headers: this.jwt()});
    }

    searchFirewallLogsByProtocol(protocol: string) {
        var uri = "/api/logfirewall?protocol=" + protocol;
        var encoded = encodeURI(uri);
        return this.http.get<LogFirewall[]>(encoded, { headers: this.jwt()});
    }

    searchFirewallLogsByAction(action: string) {
        var uri = "/api/logfirewall?action=" + action;
        var encoded = encodeURI(uri);
        return this.http.get<LogFirewall[]>(encoded, { headers: this.jwt()});
    }

    searchFirewallLogsByDstIp(dstIp: string) {
        return this.http.get<LogFirewall[]>("/api/logfirewall?dstIp=" + dstIp, { headers: this.jwt()});
    }

    searchFirewallLogsBySrcIp(srcIp: string) {
        return this.http.get<LogFirewall[]>("/api/logfirewall?srcIp=" + srcIp, { headers: this.jwt()});
    }

    searchFirewallLogsByDate(startDate: string, endDate: string) {
        return this.http.get<LogFirewall[]>("/api/logfirewall/" + startDate + "/" + endDate, { headers: this.jwt()});
    }
    
     private jwt() {
        let token = localStorage.getItem("currentUser");
        var user = JSON.parse(token);
        return new HttpHeaders({ 'X-Auth-Token': user.token });
    }
}
