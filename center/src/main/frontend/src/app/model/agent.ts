export class Agent {
    constructor(public id: number,
        public name: string,
        public port: string,
        public parentName: string,
        public parentIp: string,
        public parentPort: string,
        public centerIp: string,
        public childrenAgents: string[],
        public fwLogsDest : string,
        public apLogsDest : string,
        public serverLogsDest : string){} 
}
