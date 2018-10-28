import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';

import { AppComponent } from './app.component';
import { FileUploadComponent } from './file-upload/file-upload.component';
import {RestService} from './services/rest.service';
import {HttpClient, HttpClientModule} from '@angular/common/http';
import { IndexComponent } from './index/index.component';

@NgModule({
  declarations: [
    AppComponent,
    FileUploadComponent,
    IndexComponent
  ],
  imports: [
    BrowserModule,
    HttpClientModule
  ],
  providers: [RestService, HttpClient],
  bootstrap: [AppComponent]
})
export class AppModule { }
