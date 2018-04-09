import { LoginService } from './login.service';
import { FileService } from './file.service';
import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';

import { AppComponent } from './app.component';
import { NavComponent } from './nav/nav.component';
import { ListComponent } from './list/list.component';
import { FooterComponent } from './footer/footer.component';
import { CommentsComponent } from './comments/comments.component';
import { FileComponent } from './file/file.component';
import { AppRoutes } from './routing';
import { RouterModule } from '@angular/router';
import { LoginComponent } from './login/login.component';
import { UserService } from './user.service';
import { HttpClientModule } from '@angular/common/http';
import { AfterLoginComponent } from './after-login/after-login.component';

@NgModule({
  declarations: [
    AppComponent,
    NavComponent,
    ListComponent,
    FooterComponent,
    CommentsComponent,
    FileComponent,
    LoginComponent,
    AfterLoginComponent
  ],
  imports: [
    BrowserModule,
    HttpClientModule,
    RouterModule.forRoot(AppRoutes)
  ],
  providers: [UserService, FileService, LoginService],
  bootstrap: [AppComponent]
})
export class AppModule { }
