import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { FetchWalletComponent } from './fetch-wallet.component';

describe('FetchWalletComponent', () => {
  let component: FetchWalletComponent;
  let fixture: ComponentFixture<FetchWalletComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ FetchWalletComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(FetchWalletComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
