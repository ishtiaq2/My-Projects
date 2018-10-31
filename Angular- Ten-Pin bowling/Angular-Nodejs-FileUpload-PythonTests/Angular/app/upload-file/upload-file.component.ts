import { Component, OnInit } from '@angular/core';
import {RestService} from '../services/rest.service';
import {FileUpload} from '../models/data_maps';
import {Subject} from 'rxjs/Subject';

@Component({
  selector: 'app-upload-file',
  templateUrl: './upload-file.component.html',
  styleUrls: ['./upload-file.component.css']
})
export class UploadFileComponent implements OnInit {

  response: FileUpload = new FileUpload();
  subject = new Subject<any>();
  file: File = null;
  constructor(private service: RestService) {
    this.subject.subscribe(res => this.response = res);
  }

  fileSelected(event) {
    this.file = event.target.files[0];
  }

  uploadFile() {
    this.service.uploadFile(this.file).subscribe(resp => {
        this.subject.next(resp);
        this.callBack();
      },
      err => console.log('can not create file'),
      () => console.log('file created')
    );
  }

  callBack() {
    console.log('callBac: ' + this.response.status);
    console.log('callBac: ' + this.response.fileName);
  }

  ngOnInit() {
  }
}
