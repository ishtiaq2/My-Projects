import {Component, ElementRef, HostBinding, OnInit, ViewChild} from '@angular/core';
import {Observable} from "rxjs";
import {DomSanitizer, SafeStyle} from "@angular/platform-browser";
import {NodesService} from "../_services";

interface Position {
  x: number;
  y: number;
}

@Component({
  selector: 'app-temp',
  templateUrl: './temp.component.html',
  styleUrls: ['./temp.component.scss']
})
export class TempComponent implements OnInit {

  public fruits: string[];
  public vegs: string[];

  public pickFruits: string[];
  public pickVegs: string[];

  public garbage: string[];

  position: Position = {x: 0, y: 0};
  private startPosition: Position;

  @ViewChild('movableArea') movableArea: ElementRef;
  @ViewChild('movableItem') movableItem: ElementRef;
  @ViewChild('movableSVG') movableSVG: ElementRef;
  public movableSVGWidth = 200;
  public movableSVGHeight = 200;

  public bandWidth: number;

  constructor(private _nodeService: NodesService) {
    this.fruits = [];
    this.vegs = [];
    this.pickFruits = [];
    this.pickVegs = [];
    this.garbage = [];
    this.bandWidth = 200;
  }

  ngOnInit() {
    this.fruits = ['Apple', 'Mango', 'Orange'];
    this.vegs = ['Broccoli', 'Potato', 'Spinach'];
    this.bandWidth = 200;
  }

  public onItemDrop(event: any, category: string): void {
    console.log('dragndrop: dragEnd ' + event.dragData + category);
    if (category === 'veg') {
      if (this.pickVegs.find(item => item === event.dragData ) === undefined) {
        this.pickVegs.push(event.dragData);
      }
    } else {
      if (this.pickFruits.find(item => item === event.dragData ) === undefined) {
        this.pickFruits.push(event.dragData);
      }
    }
    this.garbage.push(event.dragData);
  }

  public onItemGarbage(event: any): void {
    if (this.pickVegs.find( item => item === event.dragData) !== undefined) {
      this.pickVegs.splice(this.pickVegs.indexOf(event.dragData), 1);
    } else {
      this.pickFruits.splice(this.pickFruits.indexOf(event.dragData), 1);
    }
  }

  public dragStart(event: any): void {
    // console.log('drag start: ' +  event.clientX + ', ' +  event.clientY);
  }

  public getWidth(): object {
    return {
      width: this.bandWidth + 'px'
    };
  }
  public appPositionDragEnded(event: any): void {
    console.log('drag end appPosition: ' +  (Math.round(event.x) + 2));
    console.log('drag end appPosition offsetWidth: ' + this.movableArea.nativeElement.offsetWidth);
    let totalBandwidth = 20000000;
    let resolution = Math.round(totalBandwidth / this.movableArea.nativeElement.offsetWidth);
    this.bandWidth = 201;
    let currentPositionX1 = Math.round(event.x + 2) * resolution;
    let currentPositionX2 = (Math.round(event.x + 2) * resolution) + (this.movableItem.nativeElement.offsetWidth * resolution);
    console.log('%c drag end result: X1: ' + currentPositionX1 +
      ', X2: ' + currentPositionX2 + ', resolution: ' +
      resolution + ', movableItem: ' + (this.movableItem.nativeElement.offsetWidth), 'color: blue; font-weight: bold');
  }

  public appPositionDragEndedSVG(event: any): void {
    console.log('drag end svg appPosition: ' +  (Math.round(event.x) + 1));
    let currentPositionX1 = Math.round(event.x + 1);
    let currentPositionX2 = currentPositionX1 + this.movableSVGWidth;
    console.log('%c drag end svg result: X1: ' + currentPositionX1 +
      ', X2: ' + currentPositionX2, 'color: blue; font-weight: bold');
  }

  public getSVGData(data: string): void {
    console.log('svg: ' + data);
  }
}

