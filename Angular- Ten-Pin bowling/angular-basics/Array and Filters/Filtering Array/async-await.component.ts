import { Component, OnInit } from '@angular/core';
import { tick } from '@angular/core/testing';
import { delay, min } from 'rxjs/operators'

@Component({
  selector: 'app-async-await',
  templateUrl: './async-await.component.html',
  styleUrls: ['./async-await.component.css']
})


export class AsyncAwaitComponent implements OnInit {
  
  public people: Person[] = [
    { name: 'Ishtiaq', age: 34 , gender: Gender.MALE },
    { name: 'Hoor', age: 24 , gender: Gender.FEMALE },
    { name: 'Mano', age: 2, gender: Gender.FEMALE },
    { name: 'Rayan', age: 1, gender: Gender.MALE } 
  ];
  
  constructor() { }

  ngOnInit() { 
    console.table(this.people);
    const minMax = { min: 1, max: 2}
    const filteredByAge = this.filterByAge(this.people, minMax)
    console.log('%c Filtered between ' + minMax.min + ' and ' + minMax.max, 'color: orange; font-weight: bold');
    console.table(filteredByAge);
  }

  public filterByAge = (people: Person[], range: { min: number, max: number }) => 
    people.filter(person => person.age >= range.min && person.age <= range.max);
    
}


enum Gender {
  MALE, 
  FEMALE
}

interface Person {
  name: string;
  age: number;
  gender: Gender;
}