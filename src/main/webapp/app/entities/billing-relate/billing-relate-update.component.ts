import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { JhiAlertService } from 'ng-jhipster';

import { IBillingRelate } from 'app/shared/model/billing-relate.model';
import { BillingRelateService } from './billing-relate.service';
import { IBillingAC } from 'app/shared/model/billing-ac.model';
import { BillingACService } from 'app/entities/billing-ac';

@Component({
  selector: 'jhi-billing-relate-update',
  templateUrl: './billing-relate-update.component.html'
})
export class BillingRelateUpdateComponent implements OnInit {
  billingRelate: IBillingRelate;
  isSaving: boolean;

  billingacs: IBillingAC[];

  constructor(
    private jhiAlertService: JhiAlertService,
    private billingRelateService: BillingRelateService,
    private billingACService: BillingACService,
    private activatedRoute: ActivatedRoute
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ billingRelate }) => {
      this.billingRelate = billingRelate;
    });
    this.billingACService.query().subscribe(
      (res: HttpResponse<IBillingAC[]>) => {
        this.billingacs = res.body;
      },
      (res: HttpErrorResponse) => this.onError(res.message)
    );
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    if (this.billingRelate.id !== undefined) {
      this.subscribeToSaveResponse(this.billingRelateService.update(this.billingRelate));
    } else {
      this.subscribeToSaveResponse(this.billingRelateService.create(this.billingRelate));
    }
  }

  private subscribeToSaveResponse(result: Observable<HttpResponse<IBillingRelate>>) {
    result.subscribe((res: HttpResponse<IBillingRelate>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
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

  trackBillingACById(index: number, item: IBillingAC) {
    return item.id;
  }
}
