import { Agent } from './agent';
import { Log } from './log';

class OperatingSystemLog extends Log {
    public id: number;
    public timeStamp: Date;
    public agent: Agent;
  	public level: string;
	public eventId : number;
	public taskCategory : string;
	public source : string;

    constructor(
        id: number,
        timeStamp: Date,
        level: string,
	    eventId : number,
	    taskCategory : string,
	    source : string,
        agent: Agent ) {
        super(id, timeStamp, agent);
        this.eventId = eventId;
		this.level = level;
		this.source = source;
		this.taskCategory = taskCategory;
    }
}