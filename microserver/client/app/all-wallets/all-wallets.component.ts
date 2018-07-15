import { RestServiceService } from './../rest-service.service';
import { Component, OnInit } from '@angular/core';
import { WalletIface } from '../WalletIface';

@Component({
  selector: 'app-all-wallets',
  templateUrl: './all-wallets.component.html',
  styleUrls: ['./all-wallets.component.css']
})
export class AllWalletsComponent implements OnInit {

  walletList: WalletIface[] = [];
  constructor(private restService: RestServiceService) { }

  ngOnInit() {
    this.getWalletList();
  }

  getWalletList() {
    if (this.walletList.length > 0) {
      this.walletList.length = 0;
      this.walletList = this.restService.getWalletList();
    } else {
      this.walletList = this.restService.getWalletList();
    }
  }
}
