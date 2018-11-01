import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { IAllAccountCode } from 'app/shared/model/all-account-code.model';
import { AllAccountCodeService } from './all-account-code.service';

@Component({
  selector: 'jhi-all-account-code-update',
  templateUrl: './all-account-code-update.component.html'
})
export class AllAccountCodeUpdateComponent implements OnInit {
  allAccountCode: IAllAccountCode;
  isSaving: boolean;

  constructor(private allAccountCodeService: AllAccountCodeService, private activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ allAccountCode }) => {
      this.allAccountCode = allAccountCode;
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    if (this.allAccountCode.id !== undefined) {
      this.subscribeToSaveResponse(this.allAccountCodeService.update(this.allAccountCode));
    } else {
      this.subscribeToSaveResponse(this.allAccountCodeService.create(this.allAccountCode));
    }
  }

  private subscribeToSaveResponse(result: Observable<HttpResponse<IAllAccountCode>>) {
    result.subscribe((res: HttpResponse<IAllAccountCode>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
  }

  private onSaveSuccess() {
    this.isSaving = false;
    this.previousState();
  }

  private onSaveError() {
    this.isSaving = false;
  }
}
