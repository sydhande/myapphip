import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { MyapphipSharedModule } from 'app/shared';
import {
  BillingACComponent,
  BillingACDetailComponent,
  BillingACUpdateComponent,
  BillingACDeletePopupComponent,
  BillingACDeleteDialogComponent,
  billingACRoute,
  billingACPopupRoute
} from './';

const ENTITY_STATES = [...billingACRoute, ...billingACPopupRoute];

@NgModule({
  imports: [MyapphipSharedModule, RouterModule.forChild(ENTITY_STATES)],
  declarations: [
    BillingACComponent,
    BillingACDetailComponent,
    BillingACUpdateComponent,
    BillingACDeleteDialogComponent,
    BillingACDeletePopupComponent
  ],
  entryComponents: [BillingACComponent, BillingACUpdateComponent, BillingACDeleteDialogComponent, BillingACDeletePopupComponent],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class MyapphipBillingACModule {}
