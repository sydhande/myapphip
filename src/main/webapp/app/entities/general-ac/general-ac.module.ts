import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { MyapphipSharedModule } from 'app/shared';
import {
  GeneralACComponent,
  GeneralACDetailComponent,
  GeneralACUpdateComponent,
  GeneralACDeletePopupComponent,
  GeneralACDeleteDialogComponent,
  generalACRoute,
  generalACPopupRoute
} from './';

const ENTITY_STATES = [...generalACRoute, ...generalACPopupRoute];

@NgModule({
  imports: [MyapphipSharedModule, RouterModule.forChild(ENTITY_STATES)],
  declarations: [
    GeneralACComponent,
    GeneralACDetailComponent,
    GeneralACUpdateComponent,
    GeneralACDeleteDialogComponent,
    GeneralACDeletePopupComponent
  ],
  entryComponents: [GeneralACComponent, GeneralACUpdateComponent, GeneralACDeleteDialogComponent, GeneralACDeletePopupComponent],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class MyapphipGeneralACModule {}
