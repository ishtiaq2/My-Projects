import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { Router3Component } from './router3.component';

describe('Router3Component', () => {
  let component: Router3Component;
  let fixture: ComponentFixture<Router3Component>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ Router3Component ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(Router3Component);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
