import { CounterService } from './counter.service';
import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';


import { AppComponent } from './app.component';
import { OperationViewComponent } from './operation-view/operation-view.component';
import { ListOfCounterComponent } from './list-of-counter/list-of-counter.component';
import { StatusViewComponent } from './status-view/status-view.component';
import { HttpClientModule } from '@angular/common/http';


@NgModule({
  declarations: [
    AppComponent,
    OperationViewComponent,
    ListOfCounterComponent,
    StatusViewComponent
  ],
  imports: [
    BrowserModule,
    HttpClientModule
  ],
  providers: [CounterService],
  bootstrap: [AppComponent]
})
export class AppModule { }
