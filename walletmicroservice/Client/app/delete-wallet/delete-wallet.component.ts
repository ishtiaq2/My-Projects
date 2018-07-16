import { Component, OnInit } from '@angular/core';
import { RestServiceService } from '../rest-service.service';

@Component({
  selector: 'app-delete-wallet',
  templateUrl: './delete-wallet.component.html',
  styleUrls: ['./delete-wallet.component.css']
})
export class DeleteWalletComponent implements OnInit {

  constructor(private restService: RestServiceService) { }

  ngOnInit() {
  }

  deleteWallet(id: string) {
    this.restService.deleteWallet(id);
    setTimeout(() => {
      // this.getFileList();
    }, 100 );
  }
}
