import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { IAllAccountCode } from 'app/shared/model/all-account-code.model';
import { Principal } from 'app/core';
import { AllAccountCodeService } from './all-account-code.service';

@Component({
  selector: 'jhi-all-account-code',
  templateUrl: './all-account-code.component.html'
})
export class AllAccountCodeComponent implements OnInit, OnDestroy {
  allAccountCodes: IAllAccountCode[];
  currentAccount: any;
  eventSubscriber: Subscription;

  constructor(
    private allAccountCodeService: AllAccountCodeService,
    private jhiAlertService: JhiAlertService,
    private eventManager: JhiEventManager,
    private principal: Principal
  ) {}

  loadAll() {
    this.allAccountCodeService.query().subscribe(
      (res: HttpResponse<IAllAccountCode[]>) => {
        this.allAccountCodes = res.body;
      },
      (res: HttpErrorResponse) => this.onError(res.message)
    );
  }

  ngOnInit() {
    this.loadAll();
    this.principal.identity().then(account => {
      this.currentAccount = account;
    });
    this.registerChangeInAllAccountCodes();
  }

  ngOnDestroy() {
    this.eventManager.destroy(this.eventSubscriber);
  }

  trackId(index: number, item: IAllAccountCode) {
    return item.id;
  }

  registerChangeInAllAccountCodes() {
    this.eventSubscriber = this.eventManager.subscribe('allAccountCodeListModification', response => this.loadAll());
  }

  private onError(errorMessage: string) {
    this.jhiAlertService.error(errorMessage, null, null);
  }
}
