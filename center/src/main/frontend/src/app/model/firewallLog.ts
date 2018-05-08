import { Agent } from './agent';
import {Log} from './log';

class LogFirewall extends Log {
    public id: number;
    public timeStamp: Date;
    public agent: Agent;
    public action: string;
    public protocol: string;
    public srcIp: string;
    public dstIp: string;
    public srcPort: string;
    public dstPort: string;
    public size: number;
    public tcpflags: string;
    public tcpsync: string;


    constructor(
        id: number,
        timeStamp: Date,
        agent: Agent,
        action: string,
        protocol: string,
        srcIp: string,
        dstIp: string,
        srcPort: string,
        dstPort: string,
        size: number,
        tcpflags: string,
        tcpsync: string) {
        super(id, timeStamp, agent);
        this.action = action;
        this.protocol = protocol;
        this.srcIp = srcIp;
        this.dstIp = dstIp;
        this.srcPort = srcPort;
        this.dstPort = dstPort;
        this.size = size;
        this.tcpflags = tcpflags;
        this.tcpsync = tcpsync;
    }
}