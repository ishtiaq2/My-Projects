import { Injectable } from '@angular/core';
import { Rolls } from './Rolls';
import { Score } from './score';
@Injectable()
export class RecursiveControllerService {

  rolls: Rolls;
  totalScore: Number = 0;
  finalScore: Number = 0;
  p1: Number = 0;
  p2: Number = 0;
  c1: Number = 0;
  c2: Number = 0;
  gameEnd: Boolean = false;

  constructor() { }

  acceptBowlingScores(rolls: Rolls): Score {
    this.totalScore = 0;
    this.p1 = 0;
    this.p2 = 0;
    this.c1 = 0;
    this.c2 = 0;
    this.rolls = rolls;
    const score = new Score();
    score.score = this.calculateScore(+this.rolls.frames.length - 1, score);
    return score;
  }

  calculateScore<Number>(frameNo: number, score: Score) {
    if (frameNo < 0) {
      this.finalScore = this.totalScore;
      return this.totalScore;

    } else if (frameNo < 9) {
        if ( this.rolls.frames[frameNo].first === 10) {
          this.c1 = this.rolls.frames[frameNo].first;
          this.totalScore = +this.totalScore + +this.c1 + +this.p1 + +this.p2;
          this.p2 = this.p1;
          this.p1 = this.c1;
        } else if ( +this.rolls.frames[frameNo].first + +this.rolls.frames[frameNo].second === 10) {
          this.c1 = this.rolls.frames[frameNo].first;
          this.c2 = this.rolls.frames[frameNo].second;
          this.totalScore = +this.totalScore + +this.c1 + +this.c2 + +this.p1;
          this.p1 = this.c1;
          this.p2 = this.c2;
        } else if ( +this.rolls.frames[frameNo].first + +this.rolls.frames[frameNo].second < 10 ) {
          this.c1 = this.rolls.frames[frameNo].first;
          this.c2 = this.rolls.frames[frameNo].second;
          this.totalScore = +this.totalScore + +this.c1 + +this.c2;
          this.p1 = this.c1;
          this.p2 = this.c2;
        }

    } else if (frameNo >= 9) {
      if (frameNo === 9) {
          if ( this.rolls.frames[frameNo].first === 10) {
            this.c1 = this.rolls.frames[frameNo].first;
            this.totalScore = +this.totalScore + +this.c1;
            this.p2 = this.p1;
            this.p1 = this.c1;
          } else if (+this.rolls.frames[frameNo].first + +this.rolls.frames[frameNo].second === 10) {
            this.c1 = this.rolls.frames[frameNo].first;
            this.c2 = this.rolls.frames[frameNo].second;
            this.totalScore = 0;
            this.totalScore = +this.totalScore + +this.c1 + +this.c2 + +this.p1;
            this.p1 = this.c1;
            this.p2 = this.c2;
          } else if (+this.rolls.frames[frameNo].first + +this.rolls.frames[frameNo].second < 10) {
            this.c1 = this.rolls.frames[frameNo].first;
            this.c2 = this.rolls.frames[frameNo].second;
            this.totalScore = 0;
            this.totalScore = +this.totalScore + +this.c1 + +this.c2;
            this.p1 = this.c1;
            this.p2 = this.c2;
            score.gameEnd = true;
            console.log('game end');
          }

      } else if (frameNo === 10) {
          if (this.rolls.frames[frameNo].first === 10) {
            this.c1 = this.rolls.frames[frameNo].first;
            this.c2 = this.rolls.frames[frameNo].second;
            this.totalScore = +this.totalScore + +this.c1;
            this.p1 = this.c1;

          } else if (+this.rolls.frames[frameNo].first + +this.rolls.frames[frameNo].second === 10) {
            this.c1 = this.rolls.frames[frameNo].first;
            this.c2 = this.rolls.frames[frameNo].second;
            this.totalScore = 0;
            this.totalScore = +this.totalScore + +this.c1 + + this.c2;
            this.p1 = this.c1;
            score.gameEnd = true;
            console.log('game end');

          } else if (+this.rolls.frames[frameNo].first + +this.rolls.frames[frameNo].second < 10) {
            this.c1 = this.rolls.frames[frameNo].first;
            this.c2 = this.rolls.frames[frameNo].second;
            this.totalScore = 0;
            this.totalScore = +this.totalScore + +this.c1 + +this.c2;
            this.p1 = this.c1;
            score.gameEnd = true;
            console.log('game end');
          }

        } else if (frameNo === 11) {
            this.c1 = this.rolls.frames[frameNo].first;
            this.totalScore = +this.totalScore + +this.c1;
            score.gameEnd = true;
            console.log('game end');
        }

    } else {
        frameNo = -1;
        score.gameEnd = true;
        console.log('game end');
    }
      return this.calculateScore(frameNo - 1, score);
  }
}
