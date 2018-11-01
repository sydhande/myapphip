import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IAllAccountCode } from 'app/shared/model/all-account-code.model';
import { AllAccountCodeService } from './all-account-code.service';

@Component({
  selector: 'jhi-all-account-code-delete-dialog',
  templateUrl: './all-account-code-delete-dialog.component.html'
})
export class AllAccountCodeDeleteDialogComponent {
  allAccountCode: IAllAccountCode;

  constructor(
    private allAccountCodeService: AllAccountCodeService,
    public activeModal: NgbActiveModal,
    private eventManager: JhiEventManager
  ) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.allAccountCodeService.delete(id).subscribe(response => {
      this.eventManager.broadcast({
        name: 'allAccountCodeListModification',
        content: 'Deleted an allAccountCode'
      });
      this.activeModal.dismiss(true);
    });
  }
}

@Component({
  selector: 'jhi-all-account-code-delete-popup',
  template: ''
})
export class AllAccountCodeDeletePopupComponent implements OnInit, OnDestroy {
  private ngbModalRef: NgbModalRef;

  constructor(private activatedRoute: ActivatedRoute, private router: Router, private modalService: NgbModal) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ allAccountCode }) => {
      setTimeout(() => {
        this.ngbModalRef = this.modalService.open(AllAccountCodeDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
        this.ngbModalRef.componentInstance.allAccountCode = allAccountCode;
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
