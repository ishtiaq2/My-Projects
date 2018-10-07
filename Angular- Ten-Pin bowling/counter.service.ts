import { CounterIFace } from './CounterIFace';
import { Injectable } from '@angular/core';
import { Counter } from './Counter';
import { HttpClient, HttpParams, HttpHeaders } from '@angular/common/http';
import { Http, Response, Headers, URLSearchParams, RequestOptions } from '@angular/http';
import { BehaviorSubject } from 'rxjs/BehaviorSubject';

@Injectable()
export class CounterService {

  private serverResponse = new BehaviorSubject<string>('');
  currentResponse = this.serverResponse.asObservable();

  rootURL = 'http://localhost:8080/apsis/counter/counterservice';
  response: any;
  counterList: CounterIFace[] = [];

  constructor(private http: HttpClient) { }

  createCounter(name: string) {
    const counter = new Counter();
    counter.name = name;
    counter.counterValue = 0;

    this.http.post(this.rootURL + '/createcounter', counter)
    .subscribe(res => this.response = res,
    err => (err),
    () => this.displayResponse(this.response.name)
    );
  }

  updateCounter(name: string) {
    this.http.put(this.rootURL + '/updatecountervalue/' + name, {})
     .subscribe(res => this.response = res,
     err => (err),
     () => this.displayResponse(this.response.name)
   );
  }

  getCounterValue(name: string) {
    this.http.get(this.rootURL + '/getcountervalue/' + name)
     .subscribe(res => this.response = res,
      err => (err),
      () => this.displayResponse(this.response.name)
    );
  }

  getListOfCounter(): CounterIFace[] {
    this.http.get<CounterIFace[]>(this.rootURL + '/getlistofcounter')
      .subscribe(
        counter => this.counterList.push(...counter),
        err => console.log(err),
        () => this.displayResponse('Counter List Retrieved')
      );
    return this.counterList;
  }

  deleteCounter(name: string) {
    // const params = new HttpParams().set('uid' , this.loginService.getUId());
    this.http.delete(this.rootURL + '/deletecounter/' + name)
    .subscribe(res => this.response = res,
    err => (err),
    () => this.displayResponse(this.response.name)
    );
  }

  displayResponse(res: string) {
    this.serverResponse.next(res);
  }
}

const defaultCounterList: CounterIFace[] = [];


// Finalized
