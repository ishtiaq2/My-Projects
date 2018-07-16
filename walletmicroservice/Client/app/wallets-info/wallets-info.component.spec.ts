import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { WalletsInfoComponent } from './wallets-info.component';

describe('WalletsInfoComponent', () => {
  let component: WalletsInfoComponent;
  let fixture: ComponentFixture<WalletsInfoComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ WalletsInfoComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(WalletsInfoComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
