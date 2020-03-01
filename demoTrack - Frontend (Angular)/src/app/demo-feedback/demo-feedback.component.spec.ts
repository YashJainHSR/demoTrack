import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { DemoFeedbackComponent } from './demo-feedback.component';

describe('DemoFeedbackComponent', () => {
  let component: DemoFeedbackComponent;
  let fixture: ComponentFixture<DemoFeedbackComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ DemoFeedbackComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(DemoFeedbackComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
