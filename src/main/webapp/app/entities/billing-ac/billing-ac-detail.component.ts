import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IBillingAC } from 'app/shared/model/billing-ac.model';

@Component({
  selector: 'jhi-billing-ac-detail',
  templateUrl: './billing-ac-detail.component.html'
})
export class BillingACDetailComponent implements OnInit {
  billingAC: IBillingAC;

  constructor(private activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ billingAC }) => {
      this.billingAC = billingAC;
    });
  }

  previousState() {
    window.history.back();
  }
}
