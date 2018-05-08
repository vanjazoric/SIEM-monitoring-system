import { Agent } from './agent';

export class Log {
    constructor (
        public id: number, 
        public priority: string, 
        public version: string, 
        public timeStamp: Date, 
        public hostName: string, 
        public application: string, 
        public processId: number,
        public messageId: number, 
        public agent: Agent){ }
}