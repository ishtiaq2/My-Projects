import { RestServiceService } from './../rest-service.service';
import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-debit-credit-wallet',
  templateUrl: './debit-credit-wallet.component.html',
  styleUrls: ['./debit-credit-wallet.component.css']
})
export class DebitCreditWalletComponent implements OnInit {

  constructor(private restService: RestServiceService) { }

  ngOnInit() {
  }

  debitWallet(walletId: string, transactionId: string, amount: number) {
    this.restService.debitWallet(walletId, transactionId, amount);
  }

  creditWallet(walletId: string, transactionId: string, amount: number) {
    this.restService.creditWallet(walletId, transactionId, amount);
  }
}
