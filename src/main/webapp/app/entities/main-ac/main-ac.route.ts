import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { of } from 'rxjs';
import { map } from 'rxjs/operators';
import { MainAC } from 'app/shared/model/main-ac.model';
import { MainACService } from './main-ac.service';
import { MainACComponent } from './main-ac.component';
import { MainACDetailComponent } from './main-ac-detail.component';
import { MainACUpdateComponent } from './main-ac-update.component';
import { MainACDeletePopupComponent } from './main-ac-delete-dialog.component';
import { IMainAC } from 'app/shared/model/main-ac.model';

@Injectable({ providedIn: 'root' })
export class MainACResolve implements Resolve<IMainAC> {
  constructor(private service: MainACService) {}

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot) {
    const id = route.params['id'] ? route.params['id'] : null;
    if (id) {
      return this.service.find(id).pipe(map((mainAC: HttpResponse<MainAC>) => mainAC.body));
    }
    return of(new MainAC());
  }
}

export const mainACRoute: Routes = [
  {
    path: 'main-ac',
    component: MainACComponent,
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'MainACS'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'main-ac/:id/view',
    component: MainACDetailComponent,
    resolve: {
      mainAC: MainACResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'MainACS'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'main-ac/new',
    component: MainACUpdateComponent,
    resolve: {
      mainAC: MainACResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'MainACS'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'main-ac/:id/edit',
    component: MainACUpdateComponent,
    resolve: {
      mainAC: MainACResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'MainACS'
    },
    canActivate: [UserRouteAccessService]
  }
];

export const mainACPopupRoute: Routes = [
  {
    path: 'main-ac/:id/delete',
    component: MainACDeletePopupComponent,
    resolve: {
      mainAC: MainACResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'MainACS'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  }
];
