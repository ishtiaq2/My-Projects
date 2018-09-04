import { RecursiveControllerService } from './../recursive-controller.service';
import { Component, OnInit } from '@angular/core';
import { Roll } from '../Roll';
import { Rolls } from '../Rolls';
import { Score } from '../score';

@Component({
  selector: 'app-bowling',
  templateUrl: './bowling.component.html',
  styleUrls: ['./bowling.component.css']
})
export class BowlingComponent implements OnInit {

  rolls = new Rolls();
  request: String = 'No Request Made';
  response: String = 'No Score to Display';
  frameNumber: Number = 0;
  score = new Score();
  constructor(private recursiveControlelr: RecursiveControllerService) { }

  ngOnInit() {
  }

  roll(roll1: string, roll2: string) {
    if ( (Number(roll1) + Number(roll2) ) > 10 ) {
      alert('The sum of Roll1 and Roll2 should not be greater than 10');
    } else {
      const roll = new Roll();
      roll.first = Number(roll1);
      roll.second = Number(roll2);

      const rollsIndex = this.rolls.frames.length;
      this.rolls.frames[rollsIndex] = roll;

      if (!this.score.gameEnd) {
        this.score = this.recursiveControlelr.acceptBowlingScores(this.rolls);
        this.request = JSON.stringify(this.rolls);
        this.response = JSON.stringify(this.score);
        this.frameNumber = +this.frameNumber + 1 ;
      }
    }
  }
}


