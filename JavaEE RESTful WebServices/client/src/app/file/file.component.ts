import { FileService } from './../file.service';
import { ListComponent } from './../list/list.component';
import { Component, OnInit } from '@angular/core';
import { FileIface } from './../FFileIface';

@Component({
  selector: 'app-file',
  templateUrl: './file.component.html',
  styleUrls: ['./file.component.css']
})
export class FileComponent implements OnInit {

  file: FileIface;
  constructor(private fileService: FileService) { }

  ngOnInit() {
    this.fileService.currentFile.subscribe(file => this.file = file);
  }

}
