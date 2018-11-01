import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { IAccountCatagory } from 'app/shared/model/account-catagory.model';
import { Principal } from 'app/core';
import { AccountCatagoryService } from './account-catagory.service';

@Component({
  selector: 'jhi-account-catagory',
  templateUrl: './account-catagory.component.html'
})
export class AccountCatagoryComponent implements OnInit, OnDestroy {
  accountCatagories: IAccountCatagory[];
  currentAccount: any;
  eventSubscriber: Subscription;

  constructor(
    private accountCatagoryService: AccountCatagoryService,
    private jhiAlertService: JhiAlertService,
    private eventManager: JhiEventManager,
    private principal: Principal
  ) {}

  loadAll() {
    this.accountCatagoryService.query().subscribe(
      (res: HttpResponse<IAccountCatagory[]>) => {
        this.accountCatagories = res.body;
      },
      (res: HttpErrorResponse) => this.onError(res.message)
    );
  }

  ngOnInit() {
    this.loadAll();
    this.principal.identity().then(account => {
      this.currentAccount = account;
    });
    this.registerChangeInAccountCatagories();
  }

  ngOnDestroy() {
    this.eventManager.destroy(this.eventSubscriber);
  }

  trackId(index: number, item: IAccountCatagory) {
    return item.id;
  }

  registerChangeInAccountCatagories() {
    this.eventSubscriber = this.eventManager.subscribe('accountCatagoryListModification', response => this.loadAll());
  }

  private onError(errorMessage: string) {
    this.jhiAlertService.error(errorMessage, null, null);
  }
}
