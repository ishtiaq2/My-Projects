import { Component } from '@angular/core';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent {
  title = 'app';

  rows: Array<any> = [];
  cols: Array<any> = [];
  data: Array<string> = ['data-0', 'data-1', 'data-2', 'data-3'];
  constructor() {
    this.fillArray();
  }

  fillArray() {
    for ( let i = 0; i < 4; i++ ) {
      this.rows.push('row: ' + i);
    }

    for ( let i = 0; i < 4; i++ ) {
      this.cols.push('col: ' + i);
    }
  }
}
