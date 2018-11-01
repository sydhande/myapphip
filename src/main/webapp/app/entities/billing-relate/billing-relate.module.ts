import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { MyapphipSharedModule } from 'app/shared';
import {
  BillingRelateComponent,
  BillingRelateDetailComponent,
  BillingRelateUpdateComponent,
  BillingRelateDeletePopupComponent,
  BillingRelateDeleteDialogComponent,
  billingRelateRoute,
  billingRelatePopupRoute
} from './';

const ENTITY_STATES = [...billingRelateRoute, ...billingRelatePopupRoute];

@NgModule({
  imports: [MyapphipSharedModule, RouterModule.forChild(ENTITY_STATES)],
  declarations: [
    BillingRelateComponent,
    BillingRelateDetailComponent,
    BillingRelateUpdateComponent,
    BillingRelateDeleteDialogComponent,
    BillingRelateDeletePopupComponent
  ],
  entryComponents: [
    BillingRelateComponent,
    BillingRelateUpdateComponent,
    BillingRelateDeleteDialogComponent,
    BillingRelateDeletePopupComponent
  ],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class MyapphipBillingRelateModule {}
