import {Component, ViewChild, AfterViewInit} from '@angular/core';
import {Component1Component} from '../component1/component1.component';

@Component({
  selector: 'app-component2',
  templateUrl: './component2.component.html',
  styleUrls: ['./component2.component.scss']
})
export class Component2Component implements AfterViewInit {

  @ViewChild(Component1Component)
  comp1: Component1Component;

  constructor() { }

  ngAfterViewInit() {
  }

  changeName() {
    this.comp1.firstName= 'ISHTIAQ';
    console.log(this.comp1.txt);
  }

}
