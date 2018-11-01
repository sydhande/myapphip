import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { JhiAlertService } from 'ng-jhipster';

import { IAccountCatagory } from 'app/shared/model/account-catagory.model';
import { AccountCatagoryService } from './account-catagory.service';
import { IOwnerAccount } from 'app/shared/model/owner-account.model';
import { OwnerAccountService } from 'app/entities/owner-account';
import { IVendorAccount } from 'app/shared/model/vendor-account.model';
import { VendorAccountService } from 'app/entities/vendor-account';

@Component({
  selector: 'jhi-account-catagory-update',
  templateUrl: './account-catagory-update.component.html'
})
export class AccountCatagoryUpdateComponent implements OnInit {
  accountCatagory: IAccountCatagory;
  isSaving: boolean;

  owneraccounts: IOwnerAccount[];

  vendoraccounts: IVendorAccount[];

  constructor(
    private jhiAlertService: JhiAlertService,
    private accountCatagoryService: AccountCatagoryService,
    private ownerAccountService: OwnerAccountService,
    private vendorAccountService: VendorAccountService,
    private activatedRoute: ActivatedRoute
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ accountCatagory }) => {
      this.accountCatagory = accountCatagory;
    });
    this.ownerAccountService.query({ filter: 'accountcatagory-is-null' }).subscribe(
      (res: HttpResponse<IOwnerAccount[]>) => {
        if (!this.accountCatagory.owneraccount || !this.accountCatagory.owneraccount.id) {
          this.owneraccounts = res.body;
        } else {
          this.ownerAccountService.find(this.accountCatagory.owneraccount.id).subscribe(
            (subRes: HttpResponse<IOwnerAccount>) => {
              this.owneraccounts = [subRes.body].concat(res.body);
            },
            (subRes: HttpErrorResponse) => this.onError(subRes.message)
          );
        }
      },
      (res: HttpErrorResponse) => this.onError(res.message)
    );
    this.vendorAccountService.query({ filter: 'accountcatagory-is-null' }).subscribe(
      (res: HttpResponse<IVendorAccount[]>) => {
        if (!this.accountCatagory.vendoraccount || !this.accountCatagory.vendoraccount.id) {
          this.vendoraccounts = res.body;
        } else {
          this.vendorAccountService.find(this.accountCatagory.vendoraccount.id).subscribe(
            (subRes: HttpResponse<IVendorAccount>) => {
              this.vendoraccounts = [subRes.body].concat(res.body);
            },
            (subRes: HttpErrorResponse) => this.onError(subRes.message)
          );
        }
      },
      (res: HttpErrorResponse) => this.onError(res.message)
    );
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    if (this.accountCatagory.id !== undefined) {
      this.subscribeToSaveResponse(this.accountCatagoryService.update(this.accountCatagory));
    } else {
      this.subscribeToSaveResponse(this.accountCatagoryService.create(this.accountCatagory));
    }
  }

  private subscribeToSaveResponse(result: Observable<HttpResponse<IAccountCatagory>>) {
    result.subscribe((res: HttpResponse<IAccountCatagory>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
  }

  private onSaveSuccess() {
    this.isSaving = false;
    this.previousState();
  }

  private onSaveError() {
    this.isSaving = false;
  }

  private onError(errorMessage: string) {
    this.jhiAlertService.error(errorMessage, null, null);
  }

  trackOwnerAccountById(index: number, item: IOwnerAccount) {
    return item.id;
  }

  trackVendorAccountById(index: number, item: IVendorAccount) {
    return item.id;
  }
}
