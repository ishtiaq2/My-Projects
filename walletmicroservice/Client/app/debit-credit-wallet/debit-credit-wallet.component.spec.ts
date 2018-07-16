import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { DebitCreditWalletComponent } from './debit-credit-wallet.component';

describe('DebitCreditWalletComponent', () => {
  let component: DebitCreditWalletComponent;
  let fixture: ComponentFixture<DebitCreditWalletComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ DebitCreditWalletComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(DebitCreditWalletComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
