import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { DemoRequestsComponent } from './demo-requests.component';

describe('DemoFeedbackComponent', () => {
  let component: DemoRequestsComponent;
  let fixture: ComponentFixture<DemoRequestsComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ DemoRequestsComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(DemoRequestsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
