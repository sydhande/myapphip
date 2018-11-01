import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IMainAC } from 'app/shared/model/main-ac.model';

@Component({
  selector: 'jhi-main-ac-detail',
  templateUrl: './main-ac-detail.component.html'
})
export class MainACDetailComponent implements OnInit {
  mainAC: IMainAC;

  constructor(private activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ mainAC }) => {
      this.mainAC = mainAC;
    });
  }

  previousState() {
    window.history.back();
  }
}
