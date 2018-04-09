import { Component, OnInit } from '@angular/core';
import { UserService } from '../user.service';

@Component({
  selector: 'app-after-login',
  templateUrl: './after-login.component.html',
  styleUrls: ['./after-login.component.css']
})
export class AfterLoginComponent implements OnInit {

  msg: string;
  constructor(private userService: UserService) { }

  ngOnInit() {
    this.userService.currentMessage.subscribe(message => this.msg = message);
  }

  logout() {
    this.userService.logout();
  }
}
