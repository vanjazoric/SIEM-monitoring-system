import { TestBed, inject } from '@angular/core/testing';

import { AgentHierarchyService } from './agent-hierarchy.service';

describe('AgentHierarchyService', () => {
  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [AgentHierarchyService]
    });
  });

  it('should be created', inject([AgentHierarchyService], (service: AgentHierarchyService) => {
    expect(service).toBeTruthy();
  }));
});
