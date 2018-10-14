import { Component } from '@angular/core';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.scss']
})
export class AppComponent {
  title = 'ModalExample';
  inputText: any;

  saveChanges(changes: string) {
    console.log(changes);
    console.log('inputText: ' + this.inputText);
  }
}
