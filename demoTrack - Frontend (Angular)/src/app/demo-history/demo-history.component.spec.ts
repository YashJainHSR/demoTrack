import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { DemoHistoryComponent } from './demo-history.component';

describe('DemoHistoryComponent', () => {
  let component: DemoHistoryComponent;
  let fixture: ComponentFixture<DemoHistoryComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ DemoHistoryComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(DemoHistoryComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
