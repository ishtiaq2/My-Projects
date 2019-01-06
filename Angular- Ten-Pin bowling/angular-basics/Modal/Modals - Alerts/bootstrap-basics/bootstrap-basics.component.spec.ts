import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { BootstrapBasicsComponent } from './bootstrap-basics.component';

describe('BootstrapBasicsComponent', () => {
  let component: BootstrapBasicsComponent;
  let fixture: ComponentFixture<BootstrapBasicsComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ BootstrapBasicsComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(BootstrapBasicsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
