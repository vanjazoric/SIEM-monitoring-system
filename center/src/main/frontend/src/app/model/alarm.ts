export class Alarm {
    public id : string;
    public description : string;
    public numOfItems : number;
    public duration : number;
    public conditions : string;

    constructor(description : string, numOfItems : number, duration : number, conditions : string){
        this.description = description;
        this.numOfItems = numOfItems;
        this.duration = duration;
        this.conditions = conditions;
    }

}
