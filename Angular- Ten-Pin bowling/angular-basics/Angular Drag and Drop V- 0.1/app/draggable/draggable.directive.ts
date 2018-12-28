import { Directive, HostBinding, HostListener, Output, EventEmitter } from '@angular/core';

@Directive({
  selector: '[appDraggable]'
})
export class DraggableDirective {

  @HostBinding('class.draggable') draggable = true;
  @HostBinding('class.dragging') dragging = false;

  @Output() dragStart = new EventEmitter<PointerEvent>();
  @Output() dragMove = new EventEmitter<PointerEvent>();
  @Output() dragEnd = new EventEmitter<PointerEvent>();

  @HostListener('pointerdown', ['$event'])
  onPointerDown(event: PointerEvent): void {
    this.dragging = true;
    this.dragStart.emit(event);
    console.log('drag start!');
  }

  @HostListener('document:pointermove', ['$event'])
  onPointerMove(event: PointerEvent): void {
    if (!this.dragging) {
      return;
    }
    this.dragMove.emit(event);
    console.log('drag move!');
  }

  @HostListener('document:pointerup', ['$event'])
  onpointerup(event: PointerEvent): void {
    if (!this.dragging) {
      return
    }

    this.dragging = false;
    this.dragEnd.emit(event);
  }
}
