import { FileService } from './../file.service';
import { Router } from '@angular/router';
import { UserService } from './../user.service';
import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-footer',
  templateUrl: './footer.component.html',
  styleUrls: ['./footer.component.css']
})
export class FooterComponent implements OnInit {

  userInfo: string;
  fileInfo: string;
  constructor(private userService: UserService, private router: Router, private fileService: FileService) { }

  ngOnInit() {
    this.userService.currentMessage.subscribe(message => this.userInfo = message);
    this.fileService.currentMessage.subscribe(message => this.fileInfo = message);

  }

  navigate() {
    console.log('navgiate footer');
    this.router.navigate(['file']);
  }
}
