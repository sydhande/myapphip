import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { IVendorAccount } from 'app/shared/model/vendor-account.model';
import { VendorAccountService } from './vendor-account.service';

@Component({
  selector: 'jhi-vendor-account-update',
  templateUrl: './vendor-account-update.component.html'
})
export class VendorAccountUpdateComponent implements OnInit {
  vendorAccount: IVendorAccount;
  isSaving: boolean;

  constructor(private vendorAccountService: VendorAccountService, private activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ vendorAccount }) => {
      this.vendorAccount = vendorAccount;
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    if (this.vendorAccount.id !== undefined) {
      this.subscribeToSaveResponse(this.vendorAccountService.update(this.vendorAccount));
    } else {
      this.subscribeToSaveResponse(this.vendorAccountService.create(this.vendorAccount));
    }
  }

  private subscribeToSaveResponse(result: Observable<HttpResponse<IVendorAccount>>) {
    result.subscribe((res: HttpResponse<IVendorAccount>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
  }

  private onSaveSuccess() {
    this.isSaving = false;
    this.previousState();
  }

  private onSaveError() {
    this.isSaving = false;
  }
}
