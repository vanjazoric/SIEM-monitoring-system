import {Log} from './log';
import {Agent} from './agent'

export class ApplicationLog extends Log {
    public id: number;
    public timeStamp: Date;
    public agent: Agent;
    public eventId: number;
    public priority: string;
    public application: string;
    public messageId: number;
    public message: string;

    constructor(
        id: number,
        timeStamp: Date,
        agent: Agent,
        eventId: number,
        priority: string,
        application: string,
        messageId: number,
        message: string) {
        super(id, timeStamp, agent);
        this.eventId = eventId;
        this.application = application;
        this.message = message;
        this.messageId = messageId;
    }
}