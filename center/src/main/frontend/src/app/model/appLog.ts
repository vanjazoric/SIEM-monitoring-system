import {Log} from './log';
import {Agent} from './agent'

class ApplicationLog extends Log {
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
        super(id,null,null, timeStamp,null,null,null,null, agent);
        this.eventId = eventId;
        this.application = application;
        this.message = message;
    }
}