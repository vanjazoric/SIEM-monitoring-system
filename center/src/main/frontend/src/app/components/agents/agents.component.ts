import { Component, OnInit } from '@angular/core';
import {CenterService} from '../../services/center.service';
import {Agent} from '../../model/agent';
import {ActivatedRoute, Params} from '@angular/router';

@Component({
  selector: 'app-agents',
  templateUrl: './agents.component.html',
  styleUrls: ['./agents.component.css'],
  providers: [ CenterService ]
})
export class AgentsComponent implements OnInit {

  agent: any;
  agentPathsKeys: string[];
  newPath: string;
  allAgents: any[];
  allAgentsObjects: any[];
  availableForParent: string[];
  availableForChild: string[];
  chosenChildren: string[];
  chosenParent: string;
  error: string = "";
  numOfAgents: number;
  selectedAgent: any;
  newAgent: any;
  fwLogValue : string;
  serverLogValue : string;
  appLogValue : string;

  constructor(private centerService : CenterService) { }

  ngOnInit() {
    this.getAllAgents();
    this.selectedAgent = null;
  }

  getAllAgents() {
    this.centerService.getAgents()
        .subscribe(
        data => {
            this.error = "";
            this.allAgents = data;
            this.numOfAgents = this.allAgents.length;
        },
        error => {
            this.numOfAgents = 0;
            this.error = "Nije pronađen nijedan agent.";
        });
  }

  changeParent(agent : any){
    this.selectedAgent = agent;
  }

  acceptChild(agent : any){
    this.selectedAgent.parentName = agent.name;
    //this.newAgent = new Agent(this.selectedAgent.id, this.selectedAgent.name, null, this.selectedAgent.parentName, null, null, null, null, null, null, null);
    this.centerService.changeParent(this.selectedAgent.name, this.selectedAgent.parentName)
        .subscribe(
        data => {
            this.error = "";
        },
        error => {
            this.error = "";
        });
    this.selectedAgent = null;
  }

  changeFwLogSource(agentName : string){
    let newValue = (<HTMLInputElement>document.getElementById('fwLogSrc')).value;
    this.centerService.changeFwLogs(agentName, newValue)
        .subscribe(
        data => {
            this.error = "";
        },
        error => {
            this.error = "";
        });
  }

  changeAppLogSource(agentName : string){
    let newValue = (<HTMLInputElement>document.getElementById('appLogSrc')).value;
    this.centerService.changeAppLogs(agentName, newValue)
        .subscribe(
        data => {
            this.error = "";
        },
        error => {
            this.error = "";
        });
  }

  changeAppServerLogSource(agentName : string){
    let newValue = (<HTMLInputElement>document.getElementById('appLogSrc')).value;
    this.centerService.changeServerLogs(agentName, newValue)
        .subscribe(
        data => {
            this.error = "";
        },
        error => {
            this.error = "";
        });
  }

  toCenter(agent : any){
    //this.newAgent = new Agent(this.selectedAgent.id, this.selectedAgent.name, null, this.selectedAgent.parentName, null, null, null, null, null, null, null);
    agent.parentName = "center"
    this.centerService.toCenter(agent.name)
        .subscribe(
        data => {
            this.error = "";
        },
        error => {
            this.error = "";
        });
    this.selectedAgent = null;
  }
}
