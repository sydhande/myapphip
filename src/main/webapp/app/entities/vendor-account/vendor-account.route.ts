import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { of } from 'rxjs';
import { map } from 'rxjs/operators';
import { VendorAccount } from 'app/shared/model/vendor-account.model';
import { VendorAccountService } from './vendor-account.service';
import { VendorAccountComponent } from './vendor-account.component';
import { VendorAccountDetailComponent } from './vendor-account-detail.component';
import { VendorAccountUpdateComponent } from './vendor-account-update.component';
import { VendorAccountDeletePopupComponent } from './vendor-account-delete-dialog.component';
import { IVendorAccount } from 'app/shared/model/vendor-account.model';

@Injectable({ providedIn: 'root' })
export class VendorAccountResolve implements Resolve<IVendorAccount> {
  constructor(private service: VendorAccountService) {}

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot) {
    const id = route.params['id'] ? route.params['id'] : null;
    if (id) {
      return this.service.find(id).pipe(map((vendorAccount: HttpResponse<VendorAccount>) => vendorAccount.body));
    }
    return of(new VendorAccount());
  }
}

export const vendorAccountRoute: Routes = [
  {
    path: 'vendor-account',
    component: VendorAccountComponent,
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'VendorAccounts'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'vendor-account/:id/view',
    component: VendorAccountDetailComponent,
    resolve: {
      vendorAccount: VendorAccountResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'VendorAccounts'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'vendor-account/new',
    component: VendorAccountUpdateComponent,
    resolve: {
      vendorAccount: VendorAccountResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'VendorAccounts'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'vendor-account/:id/edit',
    component: VendorAccountUpdateComponent,
    resolve: {
      vendorAccount: VendorAccountResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'VendorAccounts'
    },
    canActivate: [UserRouteAccessService]
  }
];

export const vendorAccountPopupRoute: Routes = [
  {
    path: 'vendor-account/:id/delete',
    component: VendorAccountDeletePopupComponent,
    resolve: {
      vendorAccount: VendorAccountResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'VendorAccounts'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  }
];
