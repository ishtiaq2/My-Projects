import {Directive, EventEmitter, HostBinding, HostListener, OnInit, Output} from '@angular/core';
import { Subject } from 'rxjs';
import { switchMap, takeUntil, repeat, take } from 'rxjs/operators';

@Directive({
  selector: '[appMovableItem]'
})
export class MovableItemDirective implements OnInit {
  @HostBinding('class.graphDirective') graphDirective = false;
  @HostBinding('class.dragging') dragging = false;

  @Output() dragStart = new EventEmitter<PointerEvent>();
  @Output() dragMove = new EventEmitter<any>();
  @Output() dragEnd = new EventEmitter<any>();

  private pointerDown = new Subject<PointerEvent>();
  private pointerMove = new Subject<PointerEvent>();
  private pointerUp = new Subject<PointerEvent>();

  @HostListener('pointerdown', ['$event'])
  onPointerDown(event: PointerEvent): void {
    this.pointerDown.next(event);
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
    this.pointerDown.asObservable()
      .subscribe(event => {
        this.dragging = true;
        this.graphDirective = true;
        this.dragStart.emit(event)
      });

    this.pointerDown.pipe(
      switchMap( () => this.pointerMove),
      takeUntil(this.pointerUp),
      repeat()
    ).subscribe(event => {
        this.dragMove.emit(event);
    });

    this.pointerDown.pipe(
      switchMap( () => this.pointerUp),
      take(1),
      repeat() /**switchMap( () => this.pointerUp.pipe(take(1))));*/
    ).subscribe(event => {
        this.dragging = false;
        this.graphDirective = false;
        this.dragEnd.emit(event);
    });
  }

}
