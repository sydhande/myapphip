import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IOwnerAccount } from 'app/shared/model/owner-account.model';

@Component({
  selector: 'jhi-owner-account-detail',
  templateUrl: './owner-account-detail.component.html'
})
export class OwnerAccountDetailComponent implements OnInit {
  ownerAccount: IOwnerAccount;

  constructor(private activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ ownerAccount }) => {
      this.ownerAccount = ownerAccount;
    });
  }

  previousState() {
    window.history.back();
  }
}
