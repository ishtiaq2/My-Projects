import { RestServiceService } from './../rest-service.service';
import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-create-wallet',
  templateUrl: './create-wallet.component.html',
  styleUrls: ['./create-wallet.component.css']
})
export class CreateWalletComponent implements OnInit {

  constructor(private restService: RestServiceService) { }

  ngOnInit() {
  }

  createWallet(name: string, balance: number) {
    this.restService.createWallet(name, balance);
    setTimeout(() => {
      // this.getFileList();
    }, 100 );
  }
}
