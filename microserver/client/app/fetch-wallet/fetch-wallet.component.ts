import { RestServiceService } from './../rest-service.service';
import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-fetch-wallet',
  templateUrl: './fetch-wallet.component.html',
  styleUrls: ['./fetch-wallet.component.css']
})
export class FetchWalletComponent implements OnInit {

  constructor(private restService: RestServiceService) { }

  ngOnInit() {
  }

  fetchWallet(id) {
    this.restService.getWallet(id);
  }
}
