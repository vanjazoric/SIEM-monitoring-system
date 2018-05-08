import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { OSLogsComponent } from './os-logs.component';

describe('OSLogsComponent', () => {
  let component: OSLogsComponent;
  let fixture: ComponentFixture<OSLogsComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ OSLogsComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(OSLogsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
