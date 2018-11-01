import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IMainAC } from 'app/shared/model/main-ac.model';
import { MainACService } from './main-ac.service';

@Component({
  selector: 'jhi-main-ac-delete-dialog',
  templateUrl: './main-ac-delete-dialog.component.html'
})
export class MainACDeleteDialogComponent {
  mainAC: IMainAC;

  constructor(private mainACService: MainACService, public activeModal: NgbActiveModal, private eventManager: JhiEventManager) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.mainACService.delete(id).subscribe(response => {
      this.eventManager.broadcast({
        name: 'mainACListModification',
        content: 'Deleted an mainAC'
      });
      this.activeModal.dismiss(true);
    });
  }
}

@Component({
  selector: 'jhi-main-ac-delete-popup',
  template: ''
})
export class MainACDeletePopupComponent implements OnInit, OnDestroy {
  private ngbModalRef: NgbModalRef;

  constructor(private activatedRoute: ActivatedRoute, private router: Router, private modalService: NgbModal) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ mainAC }) => {
      setTimeout(() => {
        this.ngbModalRef = this.modalService.open(MainACDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
        this.ngbModalRef.componentInstance.mainAC = mainAC;
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
