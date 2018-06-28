import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { AgentHierarchyComponent } from './agent-hierarchy.component';

describe('AgentHierarchyComponent', () => {
  let component: AgentHierarchyComponent;
  let fixture: ComponentFixture<AgentHierarchyComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ AgentHierarchyComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(AgentHierarchyComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
