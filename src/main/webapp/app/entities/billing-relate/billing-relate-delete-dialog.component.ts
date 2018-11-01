import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IBillingRelate } from 'app/shared/model/billing-relate.model';
import { BillingRelateService } from './billing-relate.service';

@Component({
  selector: 'jhi-billing-relate-delete-dialog',
  templateUrl: './billing-relate-delete-dialog.component.html'
})
export class BillingRelateDeleteDialogComponent {
  billingRelate: IBillingRelate;

  constructor(
    private billingRelateService: BillingRelateService,
    public activeModal: NgbActiveModal,
    private eventManager: JhiEventManager
  ) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.billingRelateService.delete(id).subscribe(response => {
      this.eventManager.broadcast({
        name: 'billingRelateListModification',
        content: 'Deleted an billingRelate'
      });
      this.activeModal.dismiss(true);
    });
  }
}

@Component({
  selector: 'jhi-billing-relate-delete-popup',
  template: ''
})
export class BillingRelateDeletePopupComponent implements OnInit, OnDestroy {
  private ngbModalRef: NgbModalRef;

  constructor(private activatedRoute: ActivatedRoute, private router: Router, private modalService: NgbModal) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ billingRelate }) => {
      setTimeout(() => {
        this.ngbModalRef = this.modalService.open(BillingRelateDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
        this.ngbModalRef.componentInstance.billingRelate = billingRelate;
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
