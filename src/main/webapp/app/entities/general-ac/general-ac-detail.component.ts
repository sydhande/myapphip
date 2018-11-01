import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IGeneralAC } from 'app/shared/model/general-ac.model';

@Component({
  selector: 'jhi-general-ac-detail',
  templateUrl: './general-ac-detail.component.html'
})
export class GeneralACDetailComponent implements OnInit {
  generalAC: IGeneralAC;

  constructor(private activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ generalAC }) => {
      this.generalAC = generalAC;
    });
  }

  previousState() {
    window.history.back();
  }
}
