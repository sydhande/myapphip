import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IAccountCatagory } from 'app/shared/model/account-catagory.model';

@Component({
  selector: 'jhi-account-catagory-detail',
  templateUrl: './account-catagory-detail.component.html'
})
export class AccountCatagoryDetailComponent implements OnInit {
  accountCatagory: IAccountCatagory;

  constructor(private activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ accountCatagory }) => {
      this.accountCatagory = accountCatagory;
    });
  }

  previousState() {
    window.history.back();
  }
}
