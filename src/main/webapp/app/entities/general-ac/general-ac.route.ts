import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { of } from 'rxjs';
import { map } from 'rxjs/operators';
import { GeneralAC } from 'app/shared/model/general-ac.model';
import { GeneralACService } from './general-ac.service';
import { GeneralACComponent } from './general-ac.component';
import { GeneralACDetailComponent } from './general-ac-detail.component';
import { GeneralACUpdateComponent } from './general-ac-update.component';
import { GeneralACDeletePopupComponent } from './general-ac-delete-dialog.component';
import { IGeneralAC } from 'app/shared/model/general-ac.model';

@Injectable({ providedIn: 'root' })
export class GeneralACResolve implements Resolve<IGeneralAC> {
  constructor(private service: GeneralACService) {}

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot) {
    const id = route.params['id'] ? route.params['id'] : null;
    if (id) {
      return this.service.find(id).pipe(map((generalAC: HttpResponse<GeneralAC>) => generalAC.body));
    }
    return of(new GeneralAC());
  }
}

export const generalACRoute: Routes = [
  {
    path: 'general-ac',
    component: GeneralACComponent,
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'GeneralACS'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'general-ac/:id/view',
    component: GeneralACDetailComponent,
    resolve: {
      generalAC: GeneralACResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'GeneralACS'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'general-ac/new',
    component: GeneralACUpdateComponent,
    resolve: {
      generalAC: GeneralACResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'GeneralACS'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'general-ac/:id/edit',
    component: GeneralACUpdateComponent,
    resolve: {
      generalAC: GeneralACResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'GeneralACS'
    },
    canActivate: [UserRouteAccessService]
  }
];

export const generalACPopupRoute: Routes = [
  {
    path: 'general-ac/:id/delete',
    component: GeneralACDeletePopupComponent,
    resolve: {
      generalAC: GeneralACResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'GeneralACS'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  }
];
