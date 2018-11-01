import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IVendorAccount } from 'app/shared/model/vendor-account.model';
import { VendorAccountService } from './vendor-account.service';

@Component({
  selector: 'jhi-vendor-account-delete-dialog',
  templateUrl: './vendor-account-delete-dialog.component.html'
})
export class VendorAccountDeleteDialogComponent {
  vendorAccount: IVendorAccount;

  constructor(
    private vendorAccountService: VendorAccountService,
    public activeModal: NgbActiveModal,
    private eventManager: JhiEventManager
  ) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.vendorAccountService.delete(id).subscribe(response => {
      this.eventManager.broadcast({
        name: 'vendorAccountListModification',
        content: 'Deleted an vendorAccount'
      });
      this.activeModal.dismiss(true);
    });
  }
}

@Component({
  selector: 'jhi-vendor-account-delete-popup',
  template: ''
})
export class VendorAccountDeletePopupComponent implements OnInit, OnDestroy {
  private ngbModalRef: NgbModalRef;

  constructor(private activatedRoute: ActivatedRoute, private router: Router, private modalService: NgbModal) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ vendorAccount }) => {
      setTimeout(() => {
        this.ngbModalRef = this.modalService.open(VendorAccountDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
        this.ngbModalRef.componentInstance.vendorAccount = vendorAccount;
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
