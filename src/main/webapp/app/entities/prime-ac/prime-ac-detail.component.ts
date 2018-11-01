import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IPrimeAC } from 'app/shared/model/prime-ac.model';

@Component({
  selector: 'jhi-prime-ac-detail',
  templateUrl: './prime-ac-detail.component.html'
})
export class PrimeACDetailComponent implements OnInit {
  primeAC: IPrimeAC;

  constructor(private activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ primeAC }) => {
      this.primeAC = primeAC;
    });
  }

  previousState() {
    window.history.back();
  }
}
