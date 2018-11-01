import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { IMainAC } from 'app/shared/model/main-ac.model';
import { Principal } from 'app/core';
import { MainACService } from './main-ac.service';

@Component({
  selector: 'jhi-main-ac',
  templateUrl: './main-ac.component.html'
})
export class MainACComponent implements OnInit, OnDestroy {
  mainACS: IMainAC[];
  currentAccount: any;
  eventSubscriber: Subscription;

  constructor(
    private mainACService: MainACService,
    private jhiAlertService: JhiAlertService,
    private eventManager: JhiEventManager,
    private principal: Principal
  ) {}

  loadAll() {
    this.mainACService.query().subscribe(
      (res: HttpResponse<IMainAC[]>) => {
        this.mainACS = res.body;
      },
      (res: HttpErrorResponse) => this.onError(res.message)
    );
  }

  ngOnInit() {
    this.loadAll();
    this.principal.identity().then(account => {
      this.currentAccount = account;
    });
    this.registerChangeInMainACS();
  }

  ngOnDestroy() {
    this.eventManager.destroy(this.eventSubscriber);
  }

  trackId(index: number, item: IMainAC) {
    return item.id;
  }

  registerChangeInMainACS() {
    this.eventSubscriber = this.eventManager.subscribe('mainACListModification', response => this.loadAll());
  }

  private onError(errorMessage: string) {
    this.jhiAlertService.error(errorMessage, null, null);
  }
}
