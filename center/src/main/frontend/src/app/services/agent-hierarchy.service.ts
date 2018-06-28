import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders, HttpParams } from '@angular/common/http';
import { Agent } from '../model/agent'

@Injectable({
  providedIn: 'root'
})
export class AgentHierarchyService {

  constructor(private http: HttpClient) { }

    getAgents() {
        return this.http.get<Agent[]>('http://localhost:8888/agent/getAll');
    }
    
}
