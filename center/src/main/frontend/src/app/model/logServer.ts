import { Agent } from './agent';
import {Log} from './log';

export class LogServer extends Log {
    public id: number;
    public timeStamp: Date;
    public clientIp: string;
    public logHost: string;
    public agent: Agent;
    public method: string;
    public resourceRequest: string;
    public httpStatus: number;
    public sizeOfReturnedObj: number;
    public messageId: number;

    constructor(
        id: number,
        timeStamp: Date,
        clientiIp: string,
        logHostL: string,
        agent: Agent,
        method: string,
        resourceRequest: string,
        httpStatus: number,
        sizeOfReturnedObj: number,
        messageId: number) {
        super(id, timeStamp, agent);
        this.clientIp = clientiIp;
        this.logHost = logHostL
        this.method = method;
        this.resourceRequest = resourceRequest;
        this.httpStatus = httpStatus;
        this.sizeOfReturnedObj = sizeOfReturnedObj;
        this.messageId = messageId;
    }
}