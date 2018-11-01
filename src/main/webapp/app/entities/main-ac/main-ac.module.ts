import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { MyapphipSharedModule } from 'app/shared';
import {
  MainACComponent,
  MainACDetailComponent,
  MainACUpdateComponent,
  MainACDeletePopupComponent,
  MainACDeleteDialogComponent,
  mainACRoute,
  mainACPopupRoute
} from './';

const ENTITY_STATES = [...mainACRoute, ...mainACPopupRoute];

@NgModule({
  imports: [MyapphipSharedModule, RouterModule.forChild(ENTITY_STATES)],
  declarations: [MainACComponent, MainACDetailComponent, MainACUpdateComponent, MainACDeleteDialogComponent, MainACDeletePopupComponent],
  entryComponents: [MainACComponent, MainACUpdateComponent, MainACDeleteDialogComponent, MainACDeletePopupComponent],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class MyapphipMainACModule {}
