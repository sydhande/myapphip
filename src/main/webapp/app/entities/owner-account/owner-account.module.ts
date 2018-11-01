import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { MyapphipSharedModule } from 'app/shared';
import {
  OwnerAccountComponent,
  OwnerAccountDetailComponent,
  OwnerAccountUpdateComponent,
  OwnerAccountDeletePopupComponent,
  OwnerAccountDeleteDialogComponent,
  ownerAccountRoute,
  ownerAccountPopupRoute
} from './';

const ENTITY_STATES = [...ownerAccountRoute, ...ownerAccountPopupRoute];

@NgModule({
  imports: [MyapphipSharedModule, RouterModule.forChild(ENTITY_STATES)],
  declarations: [
    OwnerAccountComponent,
    OwnerAccountDetailComponent,
    OwnerAccountUpdateComponent,
    OwnerAccountDeleteDialogComponent,
    OwnerAccountDeletePopupComponent
  ],
  entryComponents: [
    OwnerAccountComponent,
    OwnerAccountUpdateComponent,
    OwnerAccountDeleteDialogComponent,
    OwnerAccountDeletePopupComponent
  ],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class MyapphipOwnerAccountModule {}
