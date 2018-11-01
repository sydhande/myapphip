import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { IGeneralAC } from 'app/shared/model/general-ac.model';
import { Principal } from 'app/core';
import { GeneralACService } from './general-ac.service';

@Component({
  selector: 'jhi-general-ac',
  templateUrl: './general-ac.component.html'
})
export class GeneralACComponent implements OnInit, OnDestroy {
  generalACS: IGeneralAC[];
  currentAccount: any;
  eventSubscriber: Subscription;

  constructor(
    private generalACService: GeneralACService,
    private jhiAlertService: JhiAlertService,
    private eventManager: JhiEventManager,
    private principal: Principal
  ) {}

  loadAll() {
    this.generalACService.query().subscribe(
      (res: HttpResponse<IGeneralAC[]>) => {
        this.generalACS = res.body;
      },
      (res: HttpErrorResponse) => this.onError(res.message)
    );
  }

  ngOnInit() {
    this.loadAll();
    this.principal.identity().then(account => {
      this.currentAccount = account;
    });
    this.registerChangeInGeneralACS();
  }

  ngOnDestroy() {
    this.eventManager.destroy(this.eventSubscriber);
  }

  trackId(index: number, item: IGeneralAC) {
    return item.id;
  }

  registerChangeInGeneralACS() {
    this.eventSubscriber = this.eventManager.subscribe('generalACListModification', response => this.loadAll());
  }

  private onError(errorMessage: string) {
    this.jhiAlertService.error(errorMessage, null, null);
  }
}
