import { Component, OnInit, Output, EventEmitter } from '@angular/core';

@Component({
  selector: 'app-component1',
  templateUrl: './component1.component.html',
  styleUrls: ['./component1.component.css']
})
export class Component1Component implements OnInit {

  public w_size: string;
  public x_position: string;

  constructor() { 
    this.w_size = '100px'
    this.x_position = '300px';
  }

  ngOnInit() {
  }

  onDragStart(): void {
    console.log('got drag start');
  }

  onDragMove(event: PointerEvent): void {
    this.x_position = (Math.round(event.clientX)) + "px";
    console.log('got drag move: x: ' + Math.round(event.clientX) +', y: '+ Math.round(event.clientY));
  }

  onDragEnd(event: PointerEvent): void {
    let center = (Math.round(event.clientX)) + 100 / 2;
    let start = (Math.round(event.clientX));
    let stop = (Math.round(event.clientX)) + 100;
    console.log('got drag end');
    console.log('start: ' + start + ', center: ' + center + ', stop: ' + stop);
  }

}
