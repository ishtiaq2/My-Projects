import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { AppComponent } from './app.component';
import { UiModule } from './ui/ui.module';
import { ExampleComponent } from './example/example.component';
import { Router1Component } from './router1/router1.component';
import { Router2Component } from './router2/router2.component';
import { Router3Component } from './router3/router3.component';



const appRoutes: Routes = [
  { path: 'router1', component: Router1Component},
  { path: 'router2', component: Router2Component},
  { path: 'router3', component: Router3Component},
  { path: 'child', outlet: 'child', component: Router1Component}
];

@NgModule({
  declarations: [
    AppComponent,
    ExampleComponent,
    Router1Component,
    Router2Component,
    Router3Component
  ],
  imports: [
    BrowserModule,
    UiModule,
    RouterModule.forRoot(appRoutes)
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }


