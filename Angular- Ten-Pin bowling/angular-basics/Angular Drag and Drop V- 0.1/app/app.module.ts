import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';

import { AppComponent } from './app.component';
import { Component1Component } from './component1/component1.component';
import { DraggableModule } from './draggable/draggable.module';

@NgModule({
  declarations: [
    AppComponent,
    Component1Component
  ],
  imports: [
    BrowserModule,
    DraggableModule
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
