import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { ListOfCounterComponent } from './list-of-counter.component';

describe('ListOfCounterComponent', () => {
  let component: ListOfCounterComponent;
  let fixture: ComponentFixture<ListOfCounterComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ ListOfCounterComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(ListOfCounterComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
