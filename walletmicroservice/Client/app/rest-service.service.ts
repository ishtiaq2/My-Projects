import { Injectable } from '@angular/core';
import { HttpClient, HttpParams, HttpHeaders } from '@angular/common/http';
import { Http, Response, Headers, URLSearchParams, RequestOptions } from '@angular/http';
import { WalletIface } from './WalletIface';
import { BehaviorSubject } from 'rxjs/BehaviorSubject';
import { WalletClass } from './WalletClass';

@Injectable()
export class RestServiceService {

  private walletSource = new BehaviorSubject<WalletIface>(defaultWallet);
  currentWallet = this.walletSource.asObservable();

  rootURL = 'http://localhost:8080/wallet/walletmicroservice/wallet';
  walletList: WalletIface[] = [];
  res: any;
  constructor(private http: HttpClient) { }

  getWallet(id) {
    let params = new HttpParams();
    params = params.append('id', id);

    const wallet: WalletIface = <WalletIface> {};
    this.http.get<WalletIface>(this.rootURL + '/getWallet', {params: params})
      .subscribe(
        wwallet => Object.assign(wallet, wwallet),
        err => console.log(err),
        () => this.navigate(wallet)
      );
  }

  getWalletList(): WalletIface[] {
    this.http.get<WalletIface[]>(this.rootURL + '/getWalletList')
      .subscribe(
        wallet => this.walletList.push(...wallet),
        err => console.log(err),
        () => this.updateWalletList('wallet list response')
      );
    return this.walletList;
  }

  createWallet(name: string, balance: number) {
    // const params = new HttpParams().set('uid' , this.loginService.getUId()).set('temp', 'tempid');
    // const params = new HttpParams().set('uid' , this.loginService.getUId());
    const wallet = new WalletClass();
    wallet.id = 'null';
    wallet.name = name;
    wallet.currentBalance = balance;
    this.http.post(this.rootURL + '/createWallet', wallet)
    .subscribe(response => this.res = response,
    err => (err),
    () => this.updateWalletList(this.res)
    );
  }

  deleteWallet(id: string) {
    // const params = new HttpParams().set('uid' , this.loginService.getUId());
    this.http.delete(this.rootURL + '/deleteWallet/' + id)
    .subscribe(response => this.res = response,
    err => (err),
    () => this.updateWalletList(this.res)
    );
  }

  debitWallet(walletId: string, transactionId: string, amount: number) {
    this.http.put(this.rootURL + '/debitWallet' + '/' + walletId + '/' + transactionId + '/' + amount, {})
      .subscribe(response => this.res = response,
      err => (err),
      () => this.updateWalletList(this.res)
    );
  }

  creditWallet(walletId: string, transactionId: string, amount: number) {
    this.http.put(this.rootURL + '/creditWallet' + '/' + walletId + '/' + transactionId + '/' + amount, {})
      .subscribe(response => this.res = response,
      err => (err),
      () => this.updateWalletList(this.res)
    );
  }

  navigate(wallet: WalletIface) {
    this.walletSource.next(wallet);
  }

  updateWalletList(response: any) {
    console.log(response);
  }

  navigate2(wallet: WalletIface) {
    console.log(wallet.currentBalance);
    console.log(wallet.id);
  }
}

const defaultWallet = new WalletClass();
defaultWallet.id = 'No Wallet Fetched';
defaultWallet.currentBalance = 0;
const defaultWallets: WalletIface[] = [];
