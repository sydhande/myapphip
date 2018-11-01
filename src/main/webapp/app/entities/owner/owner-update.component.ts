import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { JhiAlertService } from 'ng-jhipster';

import { IOwner } from 'app/shared/model/owner.model';
import { OwnerService } from './owner.service';
import { IAllAccountCode } from 'app/shared/model/all-account-code.model';
import { AllAccountCodeService } from 'app/entities/all-account-code';
import { IOwnerAccount } from 'app/shared/model/owner-account.model';
import { OwnerAccountService } from 'app/entities/owner-account';

@Component({
  selector: 'jhi-owner-update',
  templateUrl: './owner-update.component.html'
})
export class OwnerUpdateComponent implements OnInit {
  owner: IOwner;
  isSaving: boolean;

  allaccountcodes: IAllAccountCode[];

  owneraccounts: IOwnerAccount[];

  constructor(
    private jhiAlertService: JhiAlertService,
    private ownerService: OwnerService,
    private allAccountCodeService: AllAccountCodeService,
    private ownerAccountService: OwnerAccountService,
    private activatedRoute: ActivatedRoute
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ owner }) => {
      this.owner = owner;
    });
    this.allAccountCodeService.query({ filter: 'owner-is-null' }).subscribe(
      (res: HttpResponse<IAllAccountCode[]>) => {
        if (!this.owner.allaccountcode || !this.owner.allaccountcode.id) {
          this.allaccountcodes = res.body;
        } else {
          this.allAccountCodeService.find(this.owner.allaccountcode.id).subscribe(
            (subRes: HttpResponse<IAllAccountCode>) => {
              this.allaccountcodes = [subRes.body].concat(res.body);
            },
            (subRes: HttpErrorResponse) => this.onError(subRes.message)
          );
        }
      },
      (res: HttpErrorResponse) => this.onError(res.message)
    );
    this.ownerAccountService.query().subscribe(
      (res: HttpResponse<IOwnerAccount[]>) => {
        this.owneraccounts = res.body;
      },
      (res: HttpErrorResponse) => this.onError(res.message)
    );
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    if (this.owner.id !== undefined) {
      this.subscribeToSaveResponse(this.ownerService.update(this.owner));
    } else {
      this.subscribeToSaveResponse(this.ownerService.create(this.owner));
    }
  }

  private subscribeToSaveResponse(result: Observable<HttpResponse<IOwner>>) {
    result.subscribe((res: HttpResponse<IOwner>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
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

  trackAllAccountCodeById(index: number, item: IAllAccountCode) {
    return item.id;
  }

  trackOwnerAccountById(index: number, item: IOwnerAccount) {
    return item.id;
  }

  getSelected(selectedVals: Array<any>, option: any) {
    if (selectedVals) {
      for (let i = 0; i < selectedVals.length; i++) {
        if (option.id === selectedVals[i].id) {
          return selectedVals[i];
        }
      }
    }
    return option;
  }
}
