import {Component, OnInit, ViewChild} from '@angular/core';
import {Comp1Component} from '../comp1/comp1.component';

@Component({
  selector: 'app-comp2',
  templateUrl: './comp2.component.html',
  styleUrls: ['./comp2.component.css']
})
export class Comp2Component implements OnInit {

  value: number[];
  constructor() { }

  ngOnInit() {
  }

  comp1SubjectValue(v: any) {
    this.value = v.value;
  }
}
