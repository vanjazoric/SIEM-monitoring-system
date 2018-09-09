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
        return this.http.get<Log[]>('/api/logs');
    }

    searchLogs(startDate: string, endDate: string) {
        return this.http.get<Log[]>("/api/logs/" + startDate + "/" + endDate);
    }

    getAppLogs() {
        return this.http.get<ApplicationLog[]>('/api/applicationLogs');
    }

    searchAppLogsById(id: string) {
        console.log(id);
        return this.http.get<ApplicationLog[]>("/api/applicationLogs/" + id);
    }

    searchAppLogsByApp(app: string) {
        console.log(app);
        const params = new HttpParams().set('app', app);
        return this.http.get<ApplicationLog[]>("/api/applicationLogs", { params });
    }

    searchAppLogsByMessage(message: string) {
        console.log(message);
        const params = new HttpParams().set('message', message);
        return this.http.get<ApplicationLog[]>("/api/applicationLogs", { params });
    }

    searchAppLogsByPriority(priority: string) {
        var uri = "/api/applicationLogs";
        var encoded = encodeURI(uri);
        const params = new HttpParams().set('priority', priority);
        return this.http.get<ApplicationLog[]>(encoded, { params });
    }

    searchAppLogs(startDate: string, endDate: string) {
        return this.http.get<ApplicationLog[]>("/api/applicationLogs/" + startDate + "/" + endDate);
    }

    searchAppLogsByDate(startDate: string, endDate: string) {
        return this.http.get<Log[]>("/api/applicationLogs/" + startDate + "/" + endDate);
    }

    getServerLogs() {
        return this.http.get<LogServer[]>('/api/logserver');
    }

    searchServerLogsByIP(ip: string) {
        console.log(ip);
        return this.http.get<LogServer[]>("/api/logserver?ip=" + ip);
    }

    searchServerLogsByHTTP(http: string) {
        var uri = "/api/logserver?http=" + http;
        var encoded = encodeURI(uri);
        return this.http.get<LogServer[]>(encoded);
    }

    searchServerLogsByMethod(method: string) {
        var uri = "/api/logserver?method=" + method;
        var encoded = encodeURI(uri);
        return this.http.get<LogServer[]>(encoded);
    }

    searchServerLogsByDate(startDate: string, endDate: string) {
        return this.http.get<LogServer[]>("/api/logserver/" + startDate + "/" + endDate);
    }

    getOSLogs() {
        return this.http.get<OperatingSystemLog[]>('/api/OSlogs');
    }

    searchOSLogsBySource(source: string) {
        return this.http.get<OperatingSystemLog[]>("/api/OSlogs?source=" + source);
    }

    searchOSLogsByEventId(eventId: number) {
        return this.http.get<OperatingSystemLog[]>("/api/OSlogs?eventId=" + eventId);
    }

    searchOSLogsByLevel(level: string) {
        var uri = "/api/OSlogs?level=" + level;
        var encoded = encodeURI(uri);
        return this.http.get<OperatingSystemLog[]>(encoded);
    }

    searchOSLogsByDate(startDate: string, endDate: string) {
        return this.http.get<OperatingSystemLog[]>("/api/OSlogs/" + startDate + "/" + endDate);
    }

    getFirewallLogs() {
        return this.http.get<LogFirewall[]>('/api/logfirewall');
    }

    searchFirewallLogsByProtocol(protocol: string) {
        var uri = "/api/logfirewall?protocol=" + protocol;
        var encoded = encodeURI(uri);
        return this.http.get<LogFirewall[]>(encoded);
    }

    searchFirewallLogsByAction(action: string) {
        var uri = "/api/logfirewall?action=" + action;
        var encoded = encodeURI(uri);
        return this.http.get<LogFirewall[]>(encoded);
    }

    searchFirewallLogsByDstIp(dstIp: string) {
        return this.http.get<LogFirewall[]>("/api/logfirewall?dstIp=" + dstIp);
    }

    searchFirewallLogsBySrcIp(srcIp: string) {
        return this.http.get<LogFirewall[]>("/api/logfirewall?srcIp=" + srcIp);
    }

    searchFirewallLogsByDate(startDate: string, endDate: string) {
        return this.http.get<LogFirewall[]>("/api/logfirewall/" + startDate + "/" + endDate);
    }


}
