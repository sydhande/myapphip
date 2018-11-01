import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { JhiAlertService } from 'ng-jhipster';

import { IBillingAC } from 'app/shared/model/billing-ac.model';
import { BillingACService } from './billing-ac.service';
import { IMainAC } from 'app/shared/model/main-ac.model';
import { MainACService } from 'app/entities/main-ac';
import { IAllAccountCode } from 'app/shared/model/all-account-code.model';
import { AllAccountCodeService } from 'app/entities/all-account-code';

@Component({
  selector: 'jhi-billing-ac-update',
  templateUrl: './billing-ac-update.component.html'
})
export class BillingACUpdateComponent implements OnInit {
  billingAC: IBillingAC;
  isSaving: boolean;

  mainacs: IMainAC[];

  allaccountcodes: IAllAccountCode[];

  constructor(
    private jhiAlertService: JhiAlertService,
    private billingACService: BillingACService,
    private mainACService: MainACService,
    private allAccountCodeService: AllAccountCodeService,
    private activatedRoute: ActivatedRoute
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ billingAC }) => {
      this.billingAC = billingAC;
    });
    this.mainACService.query().subscribe(
      (res: HttpResponse<IMainAC[]>) => {
        this.mainacs = res.body;
      },
      (res: HttpErrorResponse) => this.onError(res.message)
    );
    this.allAccountCodeService.query({ filter: 'billingac-is-null' }).subscribe(
      (res: HttpResponse<IAllAccountCode[]>) => {
        if (!this.billingAC.allaccountcode || !this.billingAC.allaccountcode.id) {
          this.allaccountcodes = res.body;
        } else {
          this.allAccountCodeService.find(this.billingAC.allaccountcode.id).subscribe(
            (subRes: HttpResponse<IAllAccountCode>) => {
              this.allaccountcodes = [subRes.body].concat(res.body);
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
    if (this.billingAC.id !== undefined) {
      this.subscribeToSaveResponse(this.billingACService.update(this.billingAC));
    } else {
      this.subscribeToSaveResponse(this.billingACService.create(this.billingAC));
    }
  }

  private subscribeToSaveResponse(result: Observable<HttpResponse<IBillingAC>>) {
    result.subscribe((res: HttpResponse<IBillingAC>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
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

  trackMainACById(index: number, item: IMainAC) {
    return item.id;
  }

  trackAllAccountCodeById(index: number, item: IAllAccountCode) {
    return item.id;
  }
}
