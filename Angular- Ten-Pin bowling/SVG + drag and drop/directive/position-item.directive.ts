import { Directive, ElementRef, HostBinding, HostListener, Input, Output, EventEmitter } from '@angular/core';
import { DomSanitizer, SafeStyle } from '@angular/platform-browser';
import { MovableItemDirective } from './movable-item.directive';

interface Position {
  x: number;
  y: number;
}

@Directive({
  selector: '[appPositionItem]'
})
export class PositionItemDirective extends MovableItemDirective {

  @HostBinding('style.transform') get transform(): SafeStyle {
    return this.sanitizer.bypassSecurityTrustStyle(
      `translateX(${this.position.x}px) translateY(${this.position.y}px)`
    );
  }

  @HostBinding('style.WebkitTransform') get transform_opera(): SafeStyle {
    return this.sanitizer.bypassSecurityTrustStyle(
      `translateX(${this.position.x}px) translateY(${this.position.y}px)`
    );
  }

  public position: Position = {x: 0, y: 0};
  public startPosition: Position;
  // private reset = false;
  @Input('appMovableReset') reset = false;
  @HostBinding('class.movable') movable = true;
  @Output() dragEnded = new EventEmitter<Position>();

  constructor(private sanitizer: DomSanitizer, public element: ElementRef) {
    super();
    console.log(element);
  }

  @HostListener('dragStart', ['$event'])
  onDragStart(event: PointerEvent) {
    this.startPosition = {
      x: event.clientX - this.position.x,
      y: event.clientY - this.position.y
    }
    console.log('drag: start original: ' + event.clientX); //  + ', ' + event.clientY
    console.log('drag: start startPosition: ' + this.startPosition.x); // + ', ' + this.startPosition.y
    console.log('drag: start Position: ' + this.position.x); //  + ', ' + this.position.y
  }

  @HostListener('dragMove', ['$event'])
  onDragMove(event: PointerEvent) {
    this.position.x = event.clientX - this.startPosition.x;
    this.position.y = 0; // this.position.y = Math.round(event.clientY - this.startPosition.y);
    console.log('drag: move original: ' + event.clientX); //  + ', ' + event.clientY
    console.log('drag: move Position: ' + this.position.x); //  + ', ' + this.position.y
  }

  @HostListener('dragEnd', ['event'])
  onDragEnd(event: PointerEvent) {
    if (this.reset) {
      this.position = {x: 0, y: 0};
    }
    this.dragEnded.emit(this.position);
    console.log('drag: end Position: ' + Math.round(this.position.x)); //  + ', ' + this.position.y
  }

  @HostListener('window:resize', ['$event'])
  public onResize(event): void {
    // this.position.x = 30;
  }

}
