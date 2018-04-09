import { FileService } from './../file.service';
import { Component, OnInit } from '@angular/core';
import { FileIface } from '../FFileIface';

@Component({
  selector: 'app-comments',
  templateUrl: './comments.component.html',
  styleUrls: ['./comments.component.css']
})
export class CommentsComponent implements OnInit {

  commentsList: string[];
  file: FileIface;
  cookie: any;
  constructor(private fileService: FileService) {}

  ngOnInit() {
    this.fileService.commentsList.subscribe(commentsList => this.commentsList = commentsList);
    this.fileService.currentFile.subscribe(file => this.file = file);
  }

  comment(id: string, comment: string) {
    if (this.commentsList[0] === 'No Comment') {
      this.commentsList.length = 0;
    }
    this.commentsList.push(comment);
    this.fileService.saveComment(id, comment);
  }
}
