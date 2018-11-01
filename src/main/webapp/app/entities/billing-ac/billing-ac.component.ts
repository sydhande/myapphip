import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { IBillingAC } from 'app/shared/model/billing-ac.model';
import { Principal } from 'app/core';
import { BillingACService } from './billing-ac.service';

@Component({
  selector: 'jhi-billing-ac',
  templateUrl: './billing-ac.component.html'
})
export class BillingACComponent implements OnInit, OnDestroy {
  billingACS: IBillingAC[];
  currentAccount: any;
  eventSubscriber: Subscription;

  constructor(
    private billingACService: BillingACService,
    private jhiAlertService: JhiAlertService,
    private eventManager: JhiEventManager,
    private principal: Principal
  ) {}

  loadAll() {
    this.billingACService.query().subscribe(
      (res: HttpResponse<IBillingAC[]>) => {
        this.billingACS = res.body;
      },
      (res: HttpErrorResponse) => this.onError(res.message)
    );
  }

  ngOnInit() {
    this.loadAll();
    this.principal.identity().then(account => {
      this.currentAccount = account;
    });
    this.registerChangeInBillingACS();
  }

  ngOnDestroy() {
    this.eventManager.destroy(this.eventSubscriber);
  }

  trackId(index: number, item: IBillingAC) {
    return item.id;
  }

  registerChangeInBillingACS() {
    this.eventSubscriber = this.eventManager.subscribe('billingACListModification', response => this.loadAll());
  }

  private onError(errorMessage: string) {
    this.jhiAlertService.error(errorMessage, null, null);
  }
}
