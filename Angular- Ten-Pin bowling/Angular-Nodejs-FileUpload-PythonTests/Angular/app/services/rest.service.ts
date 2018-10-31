import { Injectable } from '@angular/core';
import {HttpClient} from '@angular/common/http';
import 'rxjs/add/operator/map';
import 'rxjs/add/operator/catch';
import 'rxjs/add/observable/timer';

@Injectable()
export class RestService {

  constructor(private http: HttpClient) { }

  uploadFile(file: File)/**: Observable<string>*/ {
    const formData = new FormData();
    formData.append('Document', file, file.name);
   return this.http.post('/api/uploadFile', formData);
  }
}
