import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';


import { AppComponent } from './app.component';
import { BowlingComponent } from './bowling/bowling.component';
import { RecursiveControllerService } from './recursive-controller.service';


@NgModule({
  declarations: [
    AppComponent,
    BowlingComponent
  ],
  imports: [
    BrowserModule
  ],
  providers: [RecursiveControllerService],
  bootstrap: [AppComponent]
})
export class AppModule { }
