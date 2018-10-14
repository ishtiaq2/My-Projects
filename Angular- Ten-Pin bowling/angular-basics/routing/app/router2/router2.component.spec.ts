import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { Router2Component } from './router2.component';

describe('Router2Component', () => {
  let component: Router2Component;
  let fixture: ComponentFixture<Router2Component>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ Router2Component ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(Router2Component);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
