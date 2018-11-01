import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { JhiAlertService } from 'ng-jhipster';

import { IOwnerAccount } from 'app/shared/model/owner-account.model';
import { OwnerAccountService } from './owner-account.service';
import { IOwner } from 'app/shared/model/owner.model';
import { OwnerService } from 'app/entities/owner';

@Component({
  selector: 'jhi-owner-account-update',
  templateUrl: './owner-account-update.component.html'
})
export class OwnerAccountUpdateComponent implements OnInit {
  ownerAccount: IOwnerAccount;
  isSaving: boolean;

  owners: IOwner[];

  constructor(
    private jhiAlertService: JhiAlertService,
    private ownerAccountService: OwnerAccountService,
    private ownerService: OwnerService,
    private activatedRoute: ActivatedRoute
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ ownerAccount }) => {
      this.ownerAccount = ownerAccount;
    });
    this.ownerService.query().subscribe(
      (res: HttpResponse<IOwner[]>) => {
        this.owners = res.body;
      },
      (res: HttpErrorResponse) => this.onError(res.message)
    );
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    if (this.ownerAccount.id !== undefined) {
      this.subscribeToSaveResponse(this.ownerAccountService.update(this.ownerAccount));
    } else {
      this.subscribeToSaveResponse(this.ownerAccountService.create(this.ownerAccount));
    }
  }

  private subscribeToSaveResponse(result: Observable<HttpResponse<IOwnerAccount>>) {
    result.subscribe((res: HttpResponse<IOwnerAccount>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
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

  trackOwnerById(index: number, item: IOwner) {
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
