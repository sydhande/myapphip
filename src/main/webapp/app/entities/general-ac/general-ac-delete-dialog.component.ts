import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IGeneralAC } from 'app/shared/model/general-ac.model';
import { GeneralACService } from './general-ac.service';

@Component({
  selector: 'jhi-general-ac-delete-dialog',
  templateUrl: './general-ac-delete-dialog.component.html'
})
export class GeneralACDeleteDialogComponent {
  generalAC: IGeneralAC;

  constructor(private generalACService: GeneralACService, public activeModal: NgbActiveModal, private eventManager: JhiEventManager) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.generalACService.delete(id).subscribe(response => {
      this.eventManager.broadcast({
        name: 'generalACListModification',
        content: 'Deleted an generalAC'
      });
      this.activeModal.dismiss(true);
    });
  }
}

@Component({
  selector: 'jhi-general-ac-delete-popup',
  template: ''
})
export class GeneralACDeletePopupComponent implements OnInit, OnDestroy {
  private ngbModalRef: NgbModalRef;

  constructor(private activatedRoute: ActivatedRoute, private router: Router, private modalService: NgbModal) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ generalAC }) => {
      setTimeout(() => {
        this.ngbModalRef = this.modalService.open(GeneralACDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
        this.ngbModalRef.componentInstance.generalAC = generalAC;
        this.ngbModalRef.result.then(
          result => {
            this.router.navigate([{ outlets: { popup: null } }], { replaceUrl: true, queryParamsHandling: 'merge' });
            this.ngbModalRef = null;
          },
          reason => {
            this.router.navigate([{ outlets: { popup: null } }], { replaceUrl: true, queryParamsHandling: 'merge' });
            this.ngbModalRef = null;
          }
        );
      }, 0);
    });
  }

  ngOnDestroy() {
    this.ngbModalRef = null;
  }
}
