import { RestServiceService } from './../rest-service.service';
import { Component, OnInit } from '@angular/core';
import { WalletIface } from '../WalletIface';

@Component({
  selector: 'app-wallets-info',
  templateUrl: './wallets-info.component.html',
  styleUrls: ['./wallets-info.component.css']
})
export class WalletsInfoComponent implements OnInit {

  wallet: WalletIface;
  transactionIds: string[];

  constructor(private restService: RestServiceService) { }

  ngOnInit() {
    this.restService.currentWallet.subscribe(fetchedWallet => this.wallet = fetchedWallet);
    this.restService.transactionIds.subscribe(transIds => this.transactionIds = transIds);
  }


}
