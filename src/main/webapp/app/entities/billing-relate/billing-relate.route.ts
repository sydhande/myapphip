import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { of } from 'rxjs';
import { map } from 'rxjs/operators';
import { BillingRelate } from 'app/shared/model/billing-relate.model';
import { BillingRelateService } from './billing-relate.service';
import { BillingRelateComponent } from './billing-relate.component';
import { BillingRelateDetailComponent } from './billing-relate-detail.component';
import { BillingRelateUpdateComponent } from './billing-relate-update.component';
import { BillingRelateDeletePopupComponent } from './billing-relate-delete-dialog.component';
import { IBillingRelate } from 'app/shared/model/billing-relate.model';

@Injectable({ providedIn: 'root' })
export class BillingRelateResolve implements Resolve<IBillingRelate> {
  constructor(private service: BillingRelateService) {}

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot) {
    const id = route.params['id'] ? route.params['id'] : null;
    if (id) {
      return this.service.find(id).pipe(map((billingRelate: HttpResponse<BillingRelate>) => billingRelate.body));
    }
    return of(new BillingRelate());
  }
}

export const billingRelateRoute: Routes = [
  {
    path: 'billing-relate',
    component: BillingRelateComponent,
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'BillingRelates'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'billing-relate/:id/view',
    component: BillingRelateDetailComponent,
    resolve: {
      billingRelate: BillingRelateResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'BillingRelates'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'billing-relate/new',
    component: BillingRelateUpdateComponent,
    resolve: {
      billingRelate: BillingRelateResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'BillingRelates'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'billing-relate/:id/edit',
    component: BillingRelateUpdateComponent,
    resolve: {
      billingRelate: BillingRelateResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'BillingRelates'
    },
    canActivate: [UserRouteAccessService]
  }
];

export const billingRelatePopupRoute: Routes = [
  {
    path: 'billing-relate/:id/delete',
    component: BillingRelateDeletePopupComponent,
    resolve: {
      billingRelate: BillingRelateResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'BillingRelates'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  }
];
