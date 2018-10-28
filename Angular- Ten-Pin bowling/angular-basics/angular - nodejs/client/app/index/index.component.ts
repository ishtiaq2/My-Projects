import { Component, OnInit } from '@angular/core';
import {RestService} from '../services/rest.service';
import {Subject} from 'rxjs';
import {CreateUser, JsonData} from '../models/json';

@Component({
  selector: 'app-index',
  templateUrl: './index.component.html',
  styleUrls: ['./index.component.css']
})
export class IndexComponent implements OnInit {

  subject = new Subject<JsonData>();
  response = new JsonData();
  createUser = new CreateUser();

  constructor(private service: RestService) {
    this.subject.subscribe(value => this.response = value);
  }

  ngOnInit() {
  }

  postUserData(name: string, pass: string) {
    this.createUser.userName = name;
    this.createUser.password = pass;
    console.log(this.createUser.userName + ' ' + this.createUser.password);
    this.service.postUserData(this.createUser, this.subject);
  }

}
