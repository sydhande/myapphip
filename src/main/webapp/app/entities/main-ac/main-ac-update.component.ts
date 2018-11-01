import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { JhiAlertService } from 'ng-jhipster';

import { IMainAC } from 'app/shared/model/main-ac.model';
import { MainACService } from './main-ac.service';
import { IGeneralAC } from 'app/shared/model/general-ac.model';
import { GeneralACService } from 'app/entities/general-ac';

@Component({
  selector: 'jhi-main-ac-update',
  templateUrl: './main-ac-update.component.html'
})
export class MainACUpdateComponent implements OnInit {
  mainAC: IMainAC;
  isSaving: boolean;

  generalacs: IGeneralAC[];

  constructor(
    private jhiAlertService: JhiAlertService,
    private mainACService: MainACService,
    private generalACService: GeneralACService,
    private activatedRoute: ActivatedRoute
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ mainAC }) => {
      this.mainAC = mainAC;
    });
    this.generalACService.query().subscribe(
      (res: HttpResponse<IGeneralAC[]>) => {
        this.generalacs = res.body;
      },
      (res: HttpErrorResponse) => this.onError(res.message)
    );
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    if (this.mainAC.id !== undefined) {
      this.subscribeToSaveResponse(this.mainACService.update(this.mainAC));
    } else {
      this.subscribeToSaveResponse(this.mainACService.create(this.mainAC));
    }
  }

  private subscribeToSaveResponse(result: Observable<HttpResponse<IMainAC>>) {
    result.subscribe((res: HttpResponse<IMainAC>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
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

  trackGeneralACById(index: number, item: IGeneralAC) {
    return item.id;
  }
}
