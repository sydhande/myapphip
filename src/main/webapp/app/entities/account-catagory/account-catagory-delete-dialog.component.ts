import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IAccountCatagory } from 'app/shared/model/account-catagory.model';
import { AccountCatagoryService } from './account-catagory.service';

@Component({
  selector: 'jhi-account-catagory-delete-dialog',
  templateUrl: './account-catagory-delete-dialog.component.html'
})
export class AccountCatagoryDeleteDialogComponent {
  accountCatagory: IAccountCatagory;

  constructor(
    private accountCatagoryService: AccountCatagoryService,
    public activeModal: NgbActiveModal,
    private eventManager: JhiEventManager
  ) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.accountCatagoryService.delete(id).subscribe(response => {
      this.eventManager.broadcast({
        name: 'accountCatagoryListModification',
        content: 'Deleted an accountCatagory'
      });
      this.activeModal.dismiss(true);
    });
  }
}

@Component({
  selector: 'jhi-account-catagory-delete-popup',
  template: ''
})
export class AccountCatagoryDeletePopupComponent implements OnInit, OnDestroy {
  private ngbModalRef: NgbModalRef;

  constructor(private activatedRoute: ActivatedRoute, private router: Router, private modalService: NgbModal) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ accountCatagory }) => {
      setTimeout(() => {
        this.ngbModalRef = this.modalService.open(AccountCatagoryDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
        this.ngbModalRef.componentInstance.accountCatagory = accountCatagory;
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
