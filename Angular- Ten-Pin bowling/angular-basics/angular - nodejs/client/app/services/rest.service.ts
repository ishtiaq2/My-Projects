import { Injectable } from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {Subject} from 'rxjs';
import {CreateUser, JsonData} from '../models/json';

@Injectable({
  providedIn: 'root'
})
export class RestService {
  temp: any;
  constructor(private http: HttpClient) { }

  postUserData(createUser: CreateUser, subject: Subject<any>) {
    console.log(createUser.userName + ' : ' + createUser.password);
    this.http.post('/api/user', createUser)
      .subscribe(data => {
          console.log('Res: ' + JSON.stringify(data));
          subject.next(data);
    },
    err =>
      console.log('can not get user0', err.status, err.url, err),
        () => console.log('user retrieved')
      );
  }
}

