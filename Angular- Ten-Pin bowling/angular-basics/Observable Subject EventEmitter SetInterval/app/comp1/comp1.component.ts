import {Component, EventEmitter, OnInit, Output} from '@angular/core';
import {Observable, Subject} from 'rxjs';
import {Serve1Service} from '../service/serve1.service';

@Component({
  selector: 'app-comp1',
  templateUrl: './comp1.component.html',
  styleUrls: ['./comp1.component.css']
})
export class Comp1Component implements OnInit {

  myData: Observable<number[]>;
  interval: any;
  counter: number;
  service: Serve1Service;

  object = {2: 'this is two value', 1: 'this is one value'};
  object2: string = JSON.stringify(this.object);

  subject = new Subject<number[]>();
  subjectValue: number[] = [];
  @Output() subjectValueEmitter = new EventEmitter<any>();

  constructor(private service1: Serve1Service) {
    this.service = service1;
    this.counter = 0;
    this.subject.subscribe(value => this.subjectValue = value);
    this.startListening();
  }

  startListening(): void {
    const temp = this;
    temp.interval = setInterval(function() {
      temp.myData = temp.service.getData(temp.counter, temp.subject);
      temp.subjectValueEmitter.emit(temp.myData);
      temp.counter++;
      if (temp.counter > 2) {
        temp.counter = 0;
      }
    }, 3000);
  }


  ngOnInit() {
  }

}
