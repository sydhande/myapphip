import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IAllAccountCode } from 'app/shared/model/all-account-code.model';

@Component({
  selector: 'jhi-all-account-code-detail',
  templateUrl: './all-account-code-detail.component.html'
})
export class AllAccountCodeDetailComponent implements OnInit {
  allAccountCode: IAllAccountCode;

  constructor(private activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ allAccountCode }) => {
      this.allAccountCode = allAccountCode;
    });
  }

  previousState() {
    window.history.back();
  }
}
