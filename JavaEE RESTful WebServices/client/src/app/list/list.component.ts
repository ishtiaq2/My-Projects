import { FileClass } from './../FileClass';
import { Component, OnInit } from '@angular/core';
import { FileIface } from './../FfileIface';
import { FileService } from '../file.service';
import { BehaviorSubject } from 'rxjs/BehaviorSubject';

@Component({
  selector: 'app-list',
  templateUrl: './list.component.html',
  styleUrls: ['./list.component.css']
})
export class ListComponent implements OnInit {

  fileList: FileIface[] = [];
  file: FileIface;

  constructor(private fileService: FileService) { }

  ngOnInit() {
    this.getFileList();
  }

  getFile(id) {
    this.fileService.getFile(id);

  }

  getFileList() {
    if (this.fileList.length > 0) {
      this.fileList.length = 0;
      this.fileList = this.fileService.getFileList();
    } else {
      this.fileList = this.fileService.getFileList();
    }
  }

  createFile(title: string, contents: string) {
    this.fileService.createFile(title, contents);
    setTimeout(() => {
      // this.getFileList();
    }, 100 );

  }

  updateFile(title: string, contents: string) {
    let id = 0;
    for (const entry of this.fileList) {
      if (entry.title === title) {
        id = entry.id;
        console.log(entry.title);
        this.fileService.updateFile(id, title, contents);
        return;
      }
    }
    alert('File with this title not does not exist');
  }

  deleteFile(title: string) {
    this.fileService.deleteFile(title);
    setTimeout(() => {
      // this.getFileList();
    }, 100 );
  }

  uploadFile() {
    this.fileService.uploadFile();
  }

}

/* SetTimeout() example
refreshFileList() {
  const res = this.function1(function CallBack() {
    console.log('call back executed');
  });
}

function1(cb) {
setTimeout(() => {
  cb();
}, 1000);

console.log('funct1');
return 'done';
}*/

/*
getFileList() {
    setTimeout(() => {
      this.fileList = this.fileService.getFileList();
    }, 1000);
      this.fileList.length = 0;
      for (let i = 0; i < this.fileList.length; i ++) {
        console.log(this.fileList[i].title);
      }
  }
*/
