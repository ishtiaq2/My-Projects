import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { Router1Component } from './router1.component';

describe('Router1Component', () => {
  let component: Router1Component;
  let fixture: ComponentFixture<Router1Component>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ Router1Component ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(Router1Component);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
