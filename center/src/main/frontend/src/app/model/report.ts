export class Report {
    id: string;
    title: string;
    startDate: Date;
    endDate: Date;
    amount: number;
    items: string;
    condition: string;
    machineName: string;
    constructor(title: string, startDate: Date, endDate: Date, items: string, condition: string){
        this.title = title;
        this.startDate = startDate;
        this.endDate = endDate;
        this.items = items;
        this.condition = condition;
    }
}
