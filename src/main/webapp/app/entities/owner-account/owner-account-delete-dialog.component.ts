import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IOwnerAccount } from 'app/shared/model/owner-account.model';
import { OwnerAccountService } from './owner-account.service';

@Component({
  selector: 'jhi-owner-account-delete-dialog',
  templateUrl: './owner-account-delete-dialog.component.html'
})
export class OwnerAccountDeleteDialogComponent {
  ownerAccount: IOwnerAccount;

  constructor(
    private ownerAccountService: OwnerAccountService,
    public activeModal: NgbActiveModal,
    private eventManager: JhiEventManager
  ) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.ownerAccountService.delete(id).subscribe(response => {
      this.eventManager.broadcast({
        name: 'ownerAccountListModification',
        content: 'Deleted an ownerAccount'
      });
      this.activeModal.dismiss(true);
    });
  }
}

@Component({
  selector: 'jhi-owner-account-delete-popup',
  template: ''
})
export class OwnerAccountDeletePopupComponent implements OnInit, OnDestroy {
  private ngbModalRef: NgbModalRef;

  constructor(private activatedRoute: ActivatedRoute, private router: Router, private modalService: NgbModal) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ ownerAccount }) => {
      setTimeout(() => {
        this.ngbModalRef = this.modalService.open(OwnerAccountDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
        this.ngbModalRef.componentInstance.ownerAccount = ownerAccount;
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
