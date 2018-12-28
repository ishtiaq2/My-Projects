import { Directive, HostBinding, HostListener, Output, EventEmitter, OnInit } from '@angular/core';
import { Subject } from 'rxjs';
import { switchMap, takeUntil, repeat, take } from 'rxjs/operators';

@Directive({
  selector: '[appDraggableRx]'
})
export class DraggableRxDirective implements OnInit {

  @HostBinding('class.draggable') draggable = true;
  @HostBinding('class.dragging') dragging = false;
  @HostBinding('style.fill') fillColor: string;

  @Output() dragStart = new EventEmitter<PointerEvent>();
  @Output() dragMove = new EventEmitter<PointerEvent>();
  @Output() dragEnd = new EventEmitter<PointerEvent>();

  private pointerDown = new Subject<PointerEvent>();
  private pointerMove = new Subject<PointerEvent>();
  private pointerUp = new Subject<PointerEvent>();

  @HostListener('pointerdown', ['$event'])
  onPointerDown(event: PointerEvent): void {
    this.pointerDown.next(event);
    this.fillColor = 'blue';
  }

  @HostListener('document:pointermove', ['$event'])
  onPointerMove(event: PointerEvent): void {
    this.pointerMove.next(event);
  }

  @HostListener('document:pointerup', ['$event'])
  onPointerUp(event: PointerEvent): void {
    this.pointerUp.next(event);
  }

  ngOnInit(): void {
    /**const dragStart$ = */ this.pointerDown.asObservable()
      .subscribe(event => {
        this.dragging = true;
        this.dragStart.emit(event)
      });
    // dragStart$.subscribe(() => console.log('got a drag start'));

    this.pointerDown.pipe(
      switchMap( () => this.pointerMove),
      takeUntil(this.pointerUp),
      repeat()
    ).subscribe(event => this.dragMove.emit(event));

    this.pointerDown.pipe(
      switchMap( () => this.pointerUp),
      take(1),
      repeat() /**switchMap( () => this.pointerUp.pipe(take(1))));*/
    ).subscribe(event => {
      this.dragging = false;
      this.fillColor = '#40B498';
      this.dragEnd.emit(event)
    });
    // dragEnd$.subscribe( () => console.log('got dragged'));

  }
}
