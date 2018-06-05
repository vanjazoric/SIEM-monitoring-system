import { Agent } from './agent';

export class Log {
    public id: number;
    public priority: string;
    public version: string;
    public timeStamp: Date;
    public hostName: string;
    public application: string;
    public processId: number;
    public messageId: number;
    public agent: Agent;

    constructor(
        id: number,
        timeStamp: Date,
        agent: Agent) {
        this.id = id;
        this.timeStamp = timeStamp;
        this.agent = agent;
    }

  }