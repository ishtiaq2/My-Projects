import { Injectable } from '@angular/core';
import { HttpClient, HttpParams, HttpHeaders } from '@angular/common/http';
import { Router } from '@angular/router';
import { FileClass } from './FileClass';
import { FileIface } from './FFileIface';
import { BehaviorSubject } from 'rxjs/BehaviorSubject';
import { Http, Response, Headers, URLSearchParams, RequestOptions } from '@angular/http';
import { LoginService } from './login.service';

@Injectable()
export class FileService {

  private fileSource = new BehaviorSubject<FileIface>(defaultFile);
  currentFile = this.fileSource.asObservable();
  private commentsSource = new BehaviorSubject<string[]>(defaultComments);
  commentsList = this.commentsSource.asObservable();
  private messageSource = new BehaviorSubject<string>('');
  currentMessage = this.messageSource.asObservable();

  fileList: FileIface[] = [];
  rootURL = 'http://localhost:8080/nfsapp/nfsapp/file';
  res: any;
  constructor(private http: HttpClient, private router: Router, private loginService: LoginService) { }

  getFileList(): FileIface[] {
    // const fileList: FileIface[] = [];
    this.http.get<FileIface[]>(this.rootURL + '/getFileList')
      .subscribe(
        file => this.fileList.push(...file),
        err => console.log(err),
        () => this.updateFooterStatusStr('File list updated')
      );
      return this.fileList;
  }

  getFile(id) {
    let params = new HttpParams();
    params = params.append('id', id);
    params = params.append('uid' , this.loginService.getUId());

    const file: FileIface = <FileIface> {};
    this.http.get<FileIface>(this.rootURL + '/getFile', {params: params})
      .subscribe(
        ffile => Object.assign(file, ffile),
        err => console.log(err),
        () => this.navigate(file)
      );
  }

  createFile(title: string, contents: string) {
    // const params = new HttpParams().set('uid' , this.loginService.getUId()).set('temp', 'tempid');
    const params = new HttpParams().set('uid' , this.loginService.getUId());
    const file = new FileClass();
    file.id = 0;
    file.title = title;
    file.contents = contents;
    this.http.post(this.rootURL + '/createFile', file, {params: params})
    .subscribe(response => this.res = response,
    err => (err),
    () => this.updateFooterStatus(this.res)
    );
  }

  updateFile(id: number, title: string, contents: string) {
    const params = new HttpParams().set('uid' , this.loginService.getUId());
    const file = new FileClass();
    file.id = id;
    file.title = title;
    file.contents = contents;
    this.http.put(this.rootURL + '/updateFile', file, {params: params})
    .subscribe(response => this.res = response,
      err => (err),
      () => this.updateFooterStatus(this.res)
    );
  }

  saveComment(id: string, comment: string) {
    const params = new HttpParams().set('uid' , this.loginService.getUId());
    this.http.put(this.rootURL + '/commentFile' + '/' + id + '/' + comment + '/' + this.loginService.getUId(), {})
      .subscribe(response => this.res = response,
      err => (err),
      () => this.updateFooterStatusStr(this.res.title)
    );
  }

  deleteFile(title: string) {
    const params = new HttpParams().set('uid' , this.loginService.getUId());
    this.http.delete(this.rootURL + '/deleteFile/' + title, {params: params})
    .subscribe(response => this.res = response,
    err => (err),
    () => this.updateFooterStatus(this.res)
    );
  }

  uploadFile() {
    this.updateFooterStatusStr('File Upload Not yet Supported');
  }

  sendCookie(): any {
    const params = new HttpParams().set('uid' , 'abc123').set('temp', 'tempid');
    return this.http.get(this.rootURL + '/readCookie', { params: params })
    .subscribe(response => this.res = response,
    err => (err),
    () => console.log(this.res)
    );
  }

  navigate(file: FileIface) {
    this.fileSource.next(file);
    this.commentsSource.next(file.comments);
    this.router.navigate([{outlets: {primary: 'file'}}]);
    this.messageSource.next('File ' + ' ' + file.title + '  Retrieved');
  }

  updateFooterStatus(file: FileIface) {
    this.messageSource.next(file.title);
  }
  updateFooterStatusStr(str: string) {
    this.messageSource.next(str);
  }
}

const defaultComments: string[] = ['No Comment'];
const defaultFile = new FileClass();
defaultFile.title = 'Read ME';
defaultFile.id = 0;
defaultFile.owner = '';
defaultFile.comments = defaultComments;
defaultFile.contents = 'Choose File from the list to display. '
+ ' You can create new file by entering title and contents. '
+ ' You can Update existing file by title, this will append to the end of the file.'
+ ' File upload is not yet supported';

