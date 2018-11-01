import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { IPrimeAC } from 'app/shared/model/prime-ac.model';
import { PrimeACService } from './prime-ac.service';

@Component({
  selector: 'jhi-prime-ac-update',
  templateUrl: './prime-ac-update.component.html'
})
export class PrimeACUpdateComponent implements OnInit {
  primeAC: IPrimeAC;
  isSaving: boolean;

  constructor(private primeACService: PrimeACService, private activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ primeAC }) => {
      this.primeAC = primeAC;
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    if (this.primeAC.id !== undefined) {
      this.subscribeToSaveResponse(this.primeACService.update(this.primeAC));
    } else {
      this.subscribeToSaveResponse(this.primeACService.create(this.primeAC));
    }
  }

  private subscribeToSaveResponse(result: Observable<HttpResponse<IPrimeAC>>) {
    result.subscribe((res: HttpResponse<IPrimeAC>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
  }

  private onSaveSuccess() {
    this.isSaving = false;
    this.previousState();
  }

  private onSaveError() {
    this.isSaving = false;
  }
}
