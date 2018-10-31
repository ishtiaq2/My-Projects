import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';


import { AppComponent } from './app.component';
import { IndexComponent } from './index/index.component';
import { UploadFileComponent } from './upload-file/upload-file.component';
import {RestService} from './services/rest.service';
import {HttpClient, HttpClientModule} from '@angular/common/http';
import { CreateAccountComponent } from './user/create-account/create-account.component';
import { ChangePasswordComponent } from './user/change-password/change-password.component';


@NgModule({
  declarations: [
    AppComponent,
    IndexComponent,
    UploadFileComponent,
    CreateAccountComponent,
    ChangePasswordComponent,
 ],
  imports: [
    BrowserModule,
    HttpClientModule
  ],
  providers: [RestService, HttpClient],
  bootstrap: [AppComponent]
})
export class AppModule { }
