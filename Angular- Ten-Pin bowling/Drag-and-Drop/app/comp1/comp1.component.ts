import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-comp1',
  templateUrl: './comp1.component.html',
  styleUrls: ['./comp1.component.css']
})
export class Comp1Component implements OnInit {

  list: Array<string> = ['list-1', 'list-2', 'list-3'];
  show:boolean = true;
  showSub:boolean = true;
  arrow1 = 'glyphicon glyphicon-menu-right';
  arrow2 = 'glyphicon glyphicon-menu-down';

  arrowSub1 = 'glyphicon glyphicon-menu-right';
  arrowSub2 = 'glyphicon glyphicon-menu-down';
  constructor() { }

  ngOnInit() {
  }

  allowDrop(ev) {
    ev.preventDefault();
  }

  drag(ev) {
    ev.dataTransfer.setData("text", ev.target.id);
  }

  drop(ev) {
    ev.preventDefault();
    var data = ev.dataTransfer.getData("text");
    ev.target.appendChild(document.getElementById(data));
  }

  showContent(value: any) {
    console.log(value);
  }
}

