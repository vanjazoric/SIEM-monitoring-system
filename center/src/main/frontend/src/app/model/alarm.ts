export class Alarm {
    public id : string;
    public description : string;
    public numOfItems : number;
    public duration : number;
    public conditions : string;
    public conditionsForShow: string;
    public type: string;
    public date: Date;

    constructor(description : string, numOfItems : number, duration : number, conditions : string){
        this.description = description;
        this.numOfItems = numOfItems;
        this.duration = duration;
        this.conditions = conditions;
    }

}
