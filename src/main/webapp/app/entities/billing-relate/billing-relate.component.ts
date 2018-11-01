import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { IBillingRelate } from 'app/shared/model/billing-relate.model';
import { Principal } from 'app/core';
import { BillingRelateService } from './billing-relate.service';

@Component({
  selector: 'jhi-billing-relate',
  templateUrl: './billing-relate.component.html'
})
export class BillingRelateComponent implements OnInit, OnDestroy {
  billingRelates: IBillingRelate[];
  currentAccount: any;
  eventSubscriber: Subscription;

  constructor(
    private billingRelateService: BillingRelateService,
    private jhiAlertService: JhiAlertService,
    private eventManager: JhiEventManager,
    private principal: Principal
  ) {}

  loadAll() {
    this.billingRelateService.query().subscribe(
      (res: HttpResponse<IBillingRelate[]>) => {
        this.billingRelates = res.body;
      },
      (res: HttpErrorResponse) => this.onError(res.message)
    );
  }

  ngOnInit() {
    this.loadAll();
    this.principal.identity().then(account => {
      this.currentAccount = account;
    });
    this.registerChangeInBillingRelates();
  }

  ngOnDestroy() {
    this.eventManager.destroy(this.eventSubscriber);
  }

  trackId(index: number, item: IBillingRelate) {
    return item.id;
  }

  registerChangeInBillingRelates() {
    this.eventSubscriber = this.eventManager.subscribe('billingRelateListModification', response => this.loadAll());
  }

  private onError(errorMessage: string) {
    this.jhiAlertService.error(errorMessage, null, null);
  }
}
