import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IBillingRelate } from 'app/shared/model/billing-relate.model';

@Component({
  selector: 'jhi-billing-relate-detail',
  templateUrl: './billing-relate-detail.component.html'
})
export class BillingRelateDetailComponent implements OnInit {
  billingRelate: IBillingRelate;

  constructor(private activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ billingRelate }) => {
      this.billingRelate = billingRelate;
    });
  }

  previousState() {
    window.history.back();
  }
}
