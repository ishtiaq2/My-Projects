import {AfterContentInit, ContentChildren, Directive, ElementRef, QueryList} from '@angular/core';
import {PositionItemDirective} from './position-item.directive';

@Directive({
  selector: '[appMovableArea2]'
})
export class MovableAreaDirective implements AfterContentInit {

  @ContentChildren(PositionItemDirective) movables: QueryList<PositionItemDirective>;

  private boundaries: Boundaries;

  constructor(private element: ElementRef) {}

  ngAfterContentInit(): void {
    this.movables.forEach(movable => {
      movable.dragStart.subscribe(() => this.measureBoundaries(movable));
      movable.dragMove.subscribe(() => this.maintainBoundaries(movable));
    });
  }

  private measureBoundaries(movable: PositionItemDirective) {
    const viewRect: ClientRect = this.element.nativeElement.getBoundingClientRect();
     const movableClientRect: ClientRect = movable.element.nativeElement.getBoundingClientRect();

     this.boundaries = {
      minX: (viewRect.left) - movableClientRect.left + movable.position.x,
      maxX: (viewRect.right) - movableClientRect.right + movable.position.x,
      minY: viewRect.top - movableClientRect.top + movable.position.y,
      maxY: viewRect.bottom - movableClientRect.bottom + movable.position.y
    };
  }

  private maintainBoundaries(movable: PositionItemDirective) {
    movable.position.x = Math.max(this.boundaries.minX, movable.position.x);
     console.log('min: ' + movable.position.x);

     movable.position.x = Math.min(this.boundaries.maxX, movable.position.x);
     console.log('max: ' + movable.position.x);

     movable.position.y = Math.max(this.boundaries.minY, movable.position.y);
     movable.position.y = Math.min(this.boundaries.maxY, movable.position.y);
  }
}

interface Boundaries {
  minX: number;
  maxX: number;
  minY: number;
  maxY: number;
}
