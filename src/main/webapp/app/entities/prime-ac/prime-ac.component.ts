import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { IPrimeAC } from 'app/shared/model/prime-ac.model';
import { Principal } from 'app/core';
import { PrimeACService } from './prime-ac.service';

@Component({
  selector: 'jhi-prime-ac',
  templateUrl: './prime-ac.component.html'
})
export class PrimeACComponent implements OnInit, OnDestroy {
  primeACS: IPrimeAC[];
  currentAccount: any;
  eventSubscriber: Subscription;

  constructor(
    private primeACService: PrimeACService,
    private jhiAlertService: JhiAlertService,
    private eventManager: JhiEventManager,
    private principal: Principal
  ) {}

  loadAll() {
    this.primeACService.query().subscribe(
      (res: HttpResponse<IPrimeAC[]>) => {
        this.primeACS = res.body;
      },
      (res: HttpErrorResponse) => this.onError(res.message)
    );
  }

  ngOnInit() {
    this.loadAll();
    this.principal.identity().then(account => {
      this.currentAccount = account;
    });
    this.registerChangeInPrimeACS();
  }

  ngOnDestroy() {
    this.eventManager.destroy(this.eventSubscriber);
  }

  trackId(index: number, item: IPrimeAC) {
    return item.id;
  }

  registerChangeInPrimeACS() {
    this.eventSubscriber = this.eventManager.subscribe('primeACListModification', response => this.loadAll());
  }

  private onError(errorMessage: string) {
    this.jhiAlertService.error(errorMessage, null, null);
  }
}
