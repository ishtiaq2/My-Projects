import { CounterService } from './../counter.service';
import { Component, OnInit } from '@angular/core';
import { CounterIFace } from '../CounterIFace';

@Component({
  selector: 'app-operation-view',
  templateUrl: './operation-view.component.html',
  styleUrls: ['./operation-view.component.css']
})
export class OperationViewComponent implements OnInit {

  constructor(private counterService: CounterService) { }

  ngOnInit() {
  }

  createCounter(name: string) {
    this.counterService.createCounter(name);
    setTimeout(() => {
    }, 100 );
  }

  updateCounter(name: string) {
    this.counterService.updateCounter(name);
    setTimeout(() => {
    }, 100 );
  }

  getCounterValue(name: string) {
    this.counterService.getCounterValue(name);
    setTimeout(() => {
    }, 100 );
  }

  deleteCounter(name: string) {
    console.log('delete');
    this.counterService.deleteCounter(name);
    setTimeout(() => {
    }, 100 );
  }
}
