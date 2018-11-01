import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { IVendorAccount } from 'app/shared/model/vendor-account.model';
import { Principal } from 'app/core';
import { VendorAccountService } from './vendor-account.service';

@Component({
  selector: 'jhi-vendor-account',
  templateUrl: './vendor-account.component.html'
})
export class VendorAccountComponent implements OnInit, OnDestroy {
  vendorAccounts: IVendorAccount[];
  currentAccount: any;
  eventSubscriber: Subscription;

  constructor(
    private vendorAccountService: VendorAccountService,
    private jhiAlertService: JhiAlertService,
    private eventManager: JhiEventManager,
    private principal: Principal
  ) {}

  loadAll() {
    this.vendorAccountService.query().subscribe(
      (res: HttpResponse<IVendorAccount[]>) => {
        this.vendorAccounts = res.body;
      },
      (res: HttpErrorResponse) => this.onError(res.message)
    );
  }

  ngOnInit() {
    this.loadAll();
    this.principal.identity().then(account => {
      this.currentAccount = account;
    });
    this.registerChangeInVendorAccounts();
  }

  ngOnDestroy() {
    this.eventManager.destroy(this.eventSubscriber);
  }

  trackId(index: number, item: IVendorAccount) {
    return item.id;
  }

  registerChangeInVendorAccounts() {
    this.eventSubscriber = this.eventManager.subscribe('vendorAccountListModification', response => this.loadAll());
  }

  private onError(errorMessage: string) {
    this.jhiAlertService.error(errorMessage, null, null);
  }
}
