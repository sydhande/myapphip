import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { of } from 'rxjs';
import { map } from 'rxjs/operators';
import { AllAccountCode } from 'app/shared/model/all-account-code.model';
import { AllAccountCodeService } from './all-account-code.service';
import { AllAccountCodeComponent } from './all-account-code.component';
import { AllAccountCodeDetailComponent } from './all-account-code-detail.component';
import { AllAccountCodeUpdateComponent } from './all-account-code-update.component';
import { AllAccountCodeDeletePopupComponent } from './all-account-code-delete-dialog.component';
import { IAllAccountCode } from 'app/shared/model/all-account-code.model';

@Injectable({ providedIn: 'root' })
export class AllAccountCodeResolve implements Resolve<IAllAccountCode> {
  constructor(private service: AllAccountCodeService) {}

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot) {
    const id = route.params['id'] ? route.params['id'] : null;
    if (id) {
      return this.service.find(id).pipe(map((allAccountCode: HttpResponse<AllAccountCode>) => allAccountCode.body));
    }
    return of(new AllAccountCode());
  }
}

export const allAccountCodeRoute: Routes = [
  {
    path: 'all-account-code',
    component: AllAccountCodeComponent,
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'AllAccountCodes'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'all-account-code/:id/view',
    component: AllAccountCodeDetailComponent,
    resolve: {
      allAccountCode: AllAccountCodeResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'AllAccountCodes'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'all-account-code/new',
    component: AllAccountCodeUpdateComponent,
    resolve: {
      allAccountCode: AllAccountCodeResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'AllAccountCodes'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'all-account-code/:id/edit',
    component: AllAccountCodeUpdateComponent,
    resolve: {
      allAccountCode: AllAccountCodeResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'AllAccountCodes'
    },
    canActivate: [UserRouteAccessService]
  }
];

export const allAccountCodePopupRoute: Routes = [
  {
    path: 'all-account-code/:id/delete',
    component: AllAccountCodeDeletePopupComponent,
    resolve: {
      allAccountCode: AllAccountCodeResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'AllAccountCodes'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  }
];
