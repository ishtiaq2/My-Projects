import { RestServiceService } from './rest-service.service';
import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import { HttpClientModule } from '@angular/common/http';

import { AppComponent } from './app.component';
import { CreateWalletComponent } from './create-wallet/create-wallet.component';
import { DebitCreditWalletComponent } from './debit-credit-wallet/debit-credit-wallet.component';
import { FetchWalletComponent } from './fetch-wallet/fetch-wallet.component';
import { DeleteWalletComponent } from './delete-wallet/delete-wallet.component';
import { WalletsInfoComponent } from './wallets-info/wallets-info.component';
import { AllWalletsComponent } from './all-wallets/all-wallets.component';


@NgModule({
  declarations: [
    AppComponent,
    CreateWalletComponent,
    DebitCreditWalletComponent,
    FetchWalletComponent,
    DeleteWalletComponent,
    WalletsInfoComponent,
    AllWalletsComponent
  ],
  imports: [
    BrowserModule,
    HttpClientModule
  ],
  providers: [RestServiceService],
  bootstrap: [AppComponent]
})
export class AppModule { }
