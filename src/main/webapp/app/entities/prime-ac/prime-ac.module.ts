import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { MyapphipSharedModule } from 'app/shared';
import {
  PrimeACComponent,
  PrimeACDetailComponent,
  PrimeACUpdateComponent,
  PrimeACDeletePopupComponent,
  PrimeACDeleteDialogComponent,
  primeACRoute,
  primeACPopupRoute
} from './';

const ENTITY_STATES = [...primeACRoute, ...primeACPopupRoute];

@NgModule({
  imports: [MyapphipSharedModule, RouterModule.forChild(ENTITY_STATES)],
  declarations: [
    PrimeACComponent,
    PrimeACDetailComponent,
    PrimeACUpdateComponent,
    PrimeACDeleteDialogComponent,
    PrimeACDeletePopupComponent
  ],
  entryComponents: [PrimeACComponent, PrimeACUpdateComponent, PrimeACDeleteDialogComponent, PrimeACDeletePopupComponent],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class MyapphipPrimeACModule {}
