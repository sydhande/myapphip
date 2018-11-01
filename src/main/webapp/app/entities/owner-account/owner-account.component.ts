import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { IOwnerAccount } from 'app/shared/model/owner-account.model';
import { Principal } from 'app/core';
import { OwnerAccountService } from './owner-account.service';

@Component({
  selector: 'jhi-owner-account',
  templateUrl: './owner-account.component.html'
})
export class OwnerAccountComponent implements OnInit, OnDestroy {
  ownerAccounts: IOwnerAccount[];
  currentAccount: any;
  eventSubscriber: Subscription;

  constructor(
    private ownerAccountService: OwnerAccountService,
    private jhiAlertService: JhiAlertService,
    private eventManager: JhiEventManager,
    private principal: Principal
  ) {}

  loadAll() {
    this.ownerAccountService.query().subscribe(
      (res: HttpResponse<IOwnerAccount[]>) => {
        this.ownerAccounts = res.body;
      },
      (res: HttpErrorResponse) => this.onError(res.message)
    );
  }

  ngOnInit() {
    this.loadAll();
    this.principal.identity().then(account => {
      this.currentAccount = account;
    });
    this.registerChangeInOwnerAccounts();
  }

  ngOnDestroy() {
    this.eventManager.destroy(this.eventSubscriber);
  }

  trackId(index: number, item: IOwnerAccount) {
    return item.id;
  }

  registerChangeInOwnerAccounts() {
    this.eventSubscriber = this.eventManager.subscribe('ownerAccountListModification', response => this.loadAll());
  }

  private onError(errorMessage: string) {
    this.jhiAlertService.error(errorMessage, null, null);
  }
}
