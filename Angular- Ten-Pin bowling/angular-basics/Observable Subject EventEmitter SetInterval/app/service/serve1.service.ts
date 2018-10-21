import { Injectable } from '@angular/core';
import {Observable, of, Subject} from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class Serve1Service {

  data: number[];
  dataArray: number[][] = [];

  constructor() {
    this.data = [1, 2, 3, 4];
    this.dataArray[0] = this.data;
    this.data = [5, 6, 7, 8];
    this.dataArray[1] = this.data;
    this.data = [9, 10, 11, 12];
    this.dataArray[2] = this.data;
  }

  getData(counter: number, subject: Subject<number[]>): Observable<number[]> {
    // console.log(counter);
    subject.next(this.dataArray[counter]);
    return of(this.dataArray[counter]);

  }
}
