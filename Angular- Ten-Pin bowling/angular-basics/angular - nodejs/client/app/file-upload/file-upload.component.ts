import { Component, OnInit } from '@angular/core';
import {RestService} from '../services/rest.service';

@Component({
  selector: 'app-file-upload',
  templateUrl: './file-upload.component.html',
  styleUrls: ['./file-upload.component.css']
})
export class FileUploadComponent implements OnInit {

  constructor(private service: RestService, ) { }

  ngOnInit() {
  }
}
