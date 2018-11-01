import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { of } from 'rxjs';
import { map } from 'rxjs/operators';
import { BillingAC } from 'app/shared/model/billing-ac.model';
import { BillingACService } from './billing-ac.service';
import { BillingACComponent } from './billing-ac.component';
import { BillingACDetailComponent } from './billing-ac-detail.component';
import { BillingACUpdateComponent } from './billing-ac-update.component';
import { BillingACDeletePopupComponent } from './billing-ac-delete-dialog.component';
import { IBillingAC } from 'app/shared/model/billing-ac.model';

@Injectable({ providedIn: 'root' })
export class BillingACResolve implements Resolve<IBillingAC> {
  constructor(private service: BillingACService) {}

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot) {
    const id = route.params['id'] ? route.params['id'] : null;
    if (id) {
      return this.service.find(id).pipe(map((billingAC: HttpResponse<BillingAC>) => billingAC.body));
    }
    return of(new BillingAC());
  }
}

export const billingACRoute: Routes = [
  {
    path: 'billing-ac',
    component: BillingACComponent,
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'BillingACS'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'billing-ac/:id/view',
    component: BillingACDetailComponent,
    resolve: {
      billingAC: BillingACResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'BillingACS'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'billing-ac/new',
    component: BillingACUpdateComponent,
    resolve: {
      billingAC: BillingACResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'BillingACS'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'billing-ac/:id/edit',
    component: BillingACUpdateComponent,
    resolve: {
      billingAC: BillingACResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'BillingACS'
    },
    canActivate: [UserRouteAccessService]
  }
];

export const billingACPopupRoute: Routes = [
  {
    path: 'billing-ac/:id/delete',
    component: BillingACDeletePopupComponent,
    resolve: {
      billingAC: BillingACResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'BillingACS'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  }
];
