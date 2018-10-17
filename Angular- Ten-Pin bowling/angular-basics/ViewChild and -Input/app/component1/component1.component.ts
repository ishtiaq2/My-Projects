import {Component, Input, OnInit} from '@angular/core';
import {NgForm} from '@angular/forms';

@Component({
  selector: 'app-component1',
  templateUrl: './component1.component.html',
  styleUrls: ['./component1.component.scss']
})
export class Component1Component implements OnInit {
  @Input() firstName: string;
  @Input() lastName: string;
  @Input() id: number;
  txt = 'this is comp1 value';
  constructor() { }

  ngOnInit() {
  }

  changeName(name: string) {
    console.log(this.lastName);
  }

}
