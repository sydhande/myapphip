import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IBillingAC } from 'app/shared/model/billing-ac.model';
import { BillingACService } from './billing-ac.service';

@Component({
  selector: 'jhi-billing-ac-delete-dialog',
  templateUrl: './billing-ac-delete-dialog.component.html'
})
export class BillingACDeleteDialogComponent {
  billingAC: IBillingAC;

  constructor(private billingACService: BillingACService, public activeModal: NgbActiveModal, private eventManager: JhiEventManager) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.billingACService.delete(id).subscribe(response => {
      this.eventManager.broadcast({
        name: 'billingACListModification',
        content: 'Deleted an billingAC'
      });
      this.activeModal.dismiss(true);
    });
  }
}

@Component({
  selector: 'jhi-billing-ac-delete-popup',
  template: ''
})
export class BillingACDeletePopupComponent implements OnInit, OnDestroy {
  private ngbModalRef: NgbModalRef;

  constructor(private activatedRoute: ActivatedRoute, private router: Router, private modalService: NgbModal) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ billingAC }) => {
      setTimeout(() => {
        this.ngbModalRef = this.modalService.open(BillingACDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
        this.ngbModalRef.componentInstance.billingAC = billingAC;
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
