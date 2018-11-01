import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { MyapphipSharedModule } from 'app/shared';
import {
  AllAccountCodeComponent,
  AllAccountCodeDetailComponent,
  AllAccountCodeUpdateComponent,
  AllAccountCodeDeletePopupComponent,
  AllAccountCodeDeleteDialogComponent,
  allAccountCodeRoute,
  allAccountCodePopupRoute
} from './';

const ENTITY_STATES = [...allAccountCodeRoute, ...allAccountCodePopupRoute];

@NgModule({
  imports: [MyapphipSharedModule, RouterModule.forChild(ENTITY_STATES)],
  declarations: [
    AllAccountCodeComponent,
    AllAccountCodeDetailComponent,
    AllAccountCodeUpdateComponent,
    AllAccountCodeDeleteDialogComponent,
    AllAccountCodeDeletePopupComponent
  ],
  entryComponents: [
    AllAccountCodeComponent,
    AllAccountCodeUpdateComponent,
    AllAccountCodeDeleteDialogComponent,
    AllAccountCodeDeletePopupComponent
  ],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class MyapphipAllAccountCodeModule {}
