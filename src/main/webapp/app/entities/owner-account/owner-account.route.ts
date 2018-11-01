import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { of } from 'rxjs';
import { map } from 'rxjs/operators';
import { OwnerAccount } from 'app/shared/model/owner-account.model';
import { OwnerAccountService } from './owner-account.service';
import { OwnerAccountComponent } from './owner-account.component';
import { OwnerAccountDetailComponent } from './owner-account-detail.component';
import { OwnerAccountUpdateComponent } from './owner-account-update.component';
import { OwnerAccountDeletePopupComponent } from './owner-account-delete-dialog.component';
import { IOwnerAccount } from 'app/shared/model/owner-account.model';

@Injectable({ providedIn: 'root' })
export class OwnerAccountResolve implements Resolve<IOwnerAccount> {
  constructor(private service: OwnerAccountService) {}

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot) {
    const id = route.params['id'] ? route.params['id'] : null;
    if (id) {
      return this.service.find(id).pipe(map((ownerAccount: HttpResponse<OwnerAccount>) => ownerAccount.body));
    }
    return of(new OwnerAccount());
  }
}

export const ownerAccountRoute: Routes = [
  {
    path: 'owner-account',
    component: OwnerAccountComponent,
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'OwnerAccounts'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'owner-account/:id/view',
    component: OwnerAccountDetailComponent,
    resolve: {
      ownerAccount: OwnerAccountResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'OwnerAccounts'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'owner-account/new',
    component: OwnerAccountUpdateComponent,
    resolve: {
      ownerAccount: OwnerAccountResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'OwnerAccounts'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'owner-account/:id/edit',
    component: OwnerAccountUpdateComponent,
    resolve: {
      ownerAccount: OwnerAccountResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'OwnerAccounts'
    },
    canActivate: [UserRouteAccessService]
  }
];

export const ownerAccountPopupRoute: Routes = [
  {
    path: 'owner-account/:id/delete',
    component: OwnerAccountDeletePopupComponent,
    resolve: {
      ownerAccount: OwnerAccountResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'OwnerAccounts'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  }
];
