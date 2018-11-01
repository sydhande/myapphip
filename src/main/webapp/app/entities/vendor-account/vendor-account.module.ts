import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { MyapphipSharedModule } from 'app/shared';
import {
  VendorAccountComponent,
  VendorAccountDetailComponent,
  VendorAccountUpdateComponent,
  VendorAccountDeletePopupComponent,
  VendorAccountDeleteDialogComponent,
  vendorAccountRoute,
  vendorAccountPopupRoute
} from './';

const ENTITY_STATES = [...vendorAccountRoute, ...vendorAccountPopupRoute];

@NgModule({
  imports: [MyapphipSharedModule, RouterModule.forChild(ENTITY_STATES)],
  declarations: [
    VendorAccountComponent,
    VendorAccountDetailComponent,
    VendorAccountUpdateComponent,
    VendorAccountDeleteDialogComponent,
    VendorAccountDeletePopupComponent
  ],
  entryComponents: [
    VendorAccountComponent,
    VendorAccountUpdateComponent,
    VendorAccountDeleteDialogComponent,
    VendorAccountDeletePopupComponent
  ],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class MyapphipVendorAccountModule {}
