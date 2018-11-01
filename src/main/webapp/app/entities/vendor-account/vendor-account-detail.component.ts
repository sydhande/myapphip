import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IVendorAccount } from 'app/shared/model/vendor-account.model';

@Component({
  selector: 'jhi-vendor-account-detail',
  templateUrl: './vendor-account-detail.component.html'
})
export class VendorAccountDetailComponent implements OnInit {
  vendorAccount: IVendorAccount;

  constructor(private activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ vendorAccount }) => {
      this.vendorAccount = vendorAccount;
    });
  }

  previousState() {
    window.history.back();
  }
}
