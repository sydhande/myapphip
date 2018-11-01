import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { of } from 'rxjs';
import { map } from 'rxjs/operators';
import { PrimeAC } from 'app/shared/model/prime-ac.model';
import { PrimeACService } from './prime-ac.service';
import { PrimeACComponent } from './prime-ac.component';
import { PrimeACDetailComponent } from './prime-ac-detail.component';
import { PrimeACUpdateComponent } from './prime-ac-update.component';
import { PrimeACDeletePopupComponent } from './prime-ac-delete-dialog.component';
import { IPrimeAC } from 'app/shared/model/prime-ac.model';

@Injectable({ providedIn: 'root' })
export class PrimeACResolve implements Resolve<IPrimeAC> {
  constructor(private service: PrimeACService) {}

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot) {
    const id = route.params['id'] ? route.params['id'] : null;
    if (id) {
      return this.service.find(id).pipe(map((primeAC: HttpResponse<PrimeAC>) => primeAC.body));
    }
    return of(new PrimeAC());
  }
}

export const primeACRoute: Routes = [
  {
    path: 'prime-ac',
    component: PrimeACComponent,
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'PrimeACS'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'prime-ac/:id/view',
    component: PrimeACDetailComponent,
    resolve: {
      primeAC: PrimeACResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'PrimeACS'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'prime-ac/new',
    component: PrimeACUpdateComponent,
    resolve: {
      primeAC: PrimeACResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'PrimeACS'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'prime-ac/:id/edit',
    component: PrimeACUpdateComponent,
    resolve: {
      primeAC: PrimeACResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'PrimeACS'
    },
    canActivate: [UserRouteAccessService]
  }
];

export const primeACPopupRoute: Routes = [
  {
    path: 'prime-ac/:id/delete',
    component: PrimeACDeletePopupComponent,
    resolve: {
      primeAC: PrimeACResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'PrimeACS'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  }
];
