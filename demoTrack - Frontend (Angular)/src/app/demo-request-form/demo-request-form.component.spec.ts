import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { DemoRequestFormComponent } from './demo-request-form.component';

describe('DemoRequestComponent', () => {
  let component: DemoRequestFormComponent;
  let fixture: ComponentFixture<DemoRequestFormComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ DemoRequestFormComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(DemoRequestFormComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
