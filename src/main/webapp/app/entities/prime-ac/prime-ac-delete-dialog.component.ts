import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IPrimeAC } from 'app/shared/model/prime-ac.model';
import { PrimeACService } from './prime-ac.service';

@Component({
  selector: 'jhi-prime-ac-delete-dialog',
  templateUrl: './prime-ac-delete-dialog.component.html'
})
export class PrimeACDeleteDialogComponent {
  primeAC: IPrimeAC;

  constructor(private primeACService: PrimeACService, public activeModal: NgbActiveModal, private eventManager: JhiEventManager) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.primeACService.delete(id).subscribe(response => {
      this.eventManager.broadcast({
        name: 'primeACListModification',
        content: 'Deleted an primeAC'
      });
      this.activeModal.dismiss(true);
    });
  }
}

@Component({
  selector: 'jhi-prime-ac-delete-popup',
  template: ''
})
export class PrimeACDeletePopupComponent implements OnInit, OnDestroy {
  private ngbModalRef: NgbModalRef;

  constructor(private activatedRoute: ActivatedRoute, private router: Router, private modalService: NgbModal) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ primeAC }) => {
      setTimeout(() => {
        this.ngbModalRef = this.modalService.open(PrimeACDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
        this.ngbModalRef.componentInstance.primeAC = primeAC;
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
