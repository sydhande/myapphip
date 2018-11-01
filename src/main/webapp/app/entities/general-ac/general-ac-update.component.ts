import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { JhiAlertService } from 'ng-jhipster';

import { IGeneralAC } from 'app/shared/model/general-ac.model';
import { GeneralACService } from './general-ac.service';
import { IPrimeAC } from 'app/shared/model/prime-ac.model';
import { PrimeACService } from 'app/entities/prime-ac';

@Component({
  selector: 'jhi-general-ac-update',
  templateUrl: './general-ac-update.component.html'
})
export class GeneralACUpdateComponent implements OnInit {
  generalAC: IGeneralAC;
  isSaving: boolean;

  primeacs: IPrimeAC[];

  constructor(
    private jhiAlertService: JhiAlertService,
    private generalACService: GeneralACService,
    private primeACService: PrimeACService,
    private activatedRoute: ActivatedRoute
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ generalAC }) => {
      this.generalAC = generalAC;
    });
    this.primeACService.query().subscribe(
      (res: HttpResponse<IPrimeAC[]>) => {
        this.primeacs = res.body;
      },
      (res: HttpErrorResponse) => this.onError(res.message)
    );
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    if (this.generalAC.id !== undefined) {
      this.subscribeToSaveResponse(this.generalACService.update(this.generalAC));
    } else {
      this.subscribeToSaveResponse(this.generalACService.create(this.generalAC));
    }
  }

  private subscribeToSaveResponse(result: Observable<HttpResponse<IGeneralAC>>) {
    result.subscribe((res: HttpResponse<IGeneralAC>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
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

  trackPrimeACById(index: number, item: IPrimeAC) {
    return item.id;
  }
}
