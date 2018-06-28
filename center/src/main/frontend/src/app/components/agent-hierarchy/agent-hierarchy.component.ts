import { Component, OnInit } from '@angular/core';

import { Router, ActivatedRoute } from '@angular/router';
import {AgentHierarchyService} from "../../services/agent-hierarchy.service";

@Component({
  selector: 'app-agent-hierarchy',
  templateUrl: './agent-hierarchy.component.html',
  styleUrls: ['./agent-hierarchy.component.css'],
  providers: [AgentHierarchyService]
})
export class AgentHierarchyComponent implements OnInit {
  private agents: any[];
  numOfAgents: number;

  constructor(private router: Router,
    private agentHierarchyService: AgentHierarchyService) { }

  ngOnInit() {
      this.agentHierarchyService.getAgents()
          .subscribe(
          data => {
              this.agents = data;
              this.numOfAgents = this.agents.length;
              console.log(this.agents);
          });
  }

}
