import { Component, OnInit } from '@angular/core';
import {Router, Routes} from '@angular/router';

@Component({
  selector: 'app-header',
  templateUrl: './header.component.html',
  styleUrls: ['./header.component.scss']
})
export class HeaderComponent implements OnInit {

  constructor(private router: Router) { }

  ngOnInit() {
  }

  navigateRoute(routerNameme: string) {
    if (routerNameme === 'child1') {
      this.router.navigate([{ outlets: {child: 'child'}}]);
    } else {
      this.router.navigate([{outlets: {primary: 'router3'}}]);
      this.router.navigate([{ outlets: {child: null}}]);
    }
  }
}
