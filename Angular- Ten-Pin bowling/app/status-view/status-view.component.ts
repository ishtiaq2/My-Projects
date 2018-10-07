import { CounterService } from './../counter.service';
import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-status-view',
  templateUrl: './status-view.component.html',
  styleUrls: ['./status-view.component.css']
})
export class StatusViewComponent implements OnInit {

  response: string;

  constructor(private counterService: CounterService) { }

  ngOnInit() {
    this.counterService.currentResponse.subscribe(res => this.response = res);
  }

}
