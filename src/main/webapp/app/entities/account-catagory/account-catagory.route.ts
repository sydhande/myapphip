import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { of } from 'rxjs';
import { map } from 'rxjs/operators';
import { AccountCatagory } from 'app/shared/model/account-catagory.model';
import { AccountCatagoryService } from './account-catagory.service';
import { AccountCatagoryComponent } from './account-catagory.component';
import { AccountCatagoryDetailComponent } from './account-catagory-detail.component';
import { AccountCatagoryUpdateComponent } from './account-catagory-update.component';
import { AccountCatagoryDeletePopupComponent } from './account-catagory-delete-dialog.component';
import { IAccountCatagory } from 'app/shared/model/account-catagory.model';

@Injectable({ providedIn: 'root' })
export class AccountCatagoryResolve implements Resolve<IAccountCatagory> {
  constructor(private service: AccountCatagoryService) {}

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot) {
    const id = route.params['id'] ? route.params['id'] : null;
    if (id) {
      return this.service.find(id).pipe(map((accountCatagory: HttpResponse<AccountCatagory>) => accountCatagory.body));
    }
    return of(new AccountCatagory());
  }
}

export const accountCatagoryRoute: Routes = [
  {
    path: 'account-catagory',
    component: AccountCatagoryComponent,
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'AccountCatagories'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'account-catagory/:id/view',
    component: AccountCatagoryDetailComponent,
    resolve: {
      accountCatagory: AccountCatagoryResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'AccountCatagories'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'account-catagory/new',
    component: AccountCatagoryUpdateComponent,
    resolve: {
      accountCatagory: AccountCatagoryResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'AccountCatagories'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'account-catagory/:id/edit',
    component: AccountCatagoryUpdateComponent,
    resolve: {
      accountCatagory: AccountCatagoryResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'AccountCatagories'
    },
    canActivate: [UserRouteAccessService]
  }
];

export const accountCatagoryPopupRoute: Routes = [
  {
    path: 'account-catagory/:id/delete',
    component: AccountCatagoryDeletePopupComponent,
    resolve: {
      accountCatagory: AccountCatagoryResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'AccountCatagories'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  }
];
