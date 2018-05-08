import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { AppLogsComponent } from './app-logs.component';

describe('AppLogsComponent', () => {
  let component: AppLogsComponent;
  let fixture: ComponentFixture<AppLogsComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ AppLogsComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(AppLogsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
