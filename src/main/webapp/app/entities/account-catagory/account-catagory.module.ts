import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { MyapphipSharedModule } from 'app/shared';
import {
  AccountCatagoryComponent,
  AccountCatagoryDetailComponent,
  AccountCatagoryUpdateComponent,
  AccountCatagoryDeletePopupComponent,
  AccountCatagoryDeleteDialogComponent,
  accountCatagoryRoute,
  accountCatagoryPopupRoute
} from './';

const ENTITY_STATES = [...accountCatagoryRoute, ...accountCatagoryPopupRoute];

@NgModule({
  imports: [MyapphipSharedModule, RouterModule.forChild(ENTITY_STATES)],
  declarations: [
    AccountCatagoryComponent,
    AccountCatagoryDetailComponent,
    AccountCatagoryUpdateComponent,
    AccountCatagoryDeleteDialogComponent,
    AccountCatagoryDeletePopupComponent
  ],
  entryComponents: [
    AccountCatagoryComponent,
    AccountCatagoryUpdateComponent,
    AccountCatagoryDeleteDialogComponent,
    AccountCatagoryDeletePopupComponent
  ],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class MyapphipAccountCatagoryModule {}
