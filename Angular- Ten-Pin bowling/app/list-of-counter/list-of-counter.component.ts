import { Component, OnInit } from '@angular/core';
import { CounterIFace } from '../CounterIFace';
import { CounterService } from '../counter.service';

@Component({
  selector: 'app-list-of-counter',
  templateUrl: './list-of-counter.component.html',
  styleUrls: ['./list-of-counter.component.css']
})
export class ListOfCounterComponent implements OnInit {
  counterList: CounterIFace[] = [];

  constructor(private counterService: CounterService) { }

  ngOnInit() {
    // this.counterService.currentListResponse.subscribe(res => this.counterList = res);
    this.getListOfCounter();
  }

  getListOfCounter() {
    if (this.counterList.length > 0) {
      this.counterList.length = 0;
      this.counterList = this.counterService.getListOfCounter();
    } else {
      this.counterList = this.counterService.getListOfCounter();
    }
  }

}
