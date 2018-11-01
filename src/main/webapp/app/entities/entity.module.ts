import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';

import { MyapphipPrimeACModule } from './prime-ac/prime-ac.module';
import { MyapphipGeneralACModule } from './general-ac/general-ac.module';
import { MyapphipMainACModule } from './main-ac/main-ac.module';
import { MyapphipBillingACModule } from './billing-ac/billing-ac.module';
import { MyapphipAccountCatagoryModule } from './account-catagory/account-catagory.module';
import { MyapphipOwnerAccountModule } from './owner-account/owner-account.module';
import { MyapphipVendorAccountModule } from './vendor-account/vendor-account.module';
import { MyapphipOwnerModule } from './owner/owner.module';
import { MyapphipAllAccountCodeModule } from './all-account-code/all-account-code.module';
import { MyapphipBillingRelateModule } from './billing-relate/billing-relate.module';
/* jhipster-needle-add-entity-module-import - JHipster will add entity modules imports here */

@NgModule({
  imports: [
    MyapphipPrimeACModule,
    MyapphipGeneralACModule,
    MyapphipMainACModule,
    MyapphipBillingACModule,
    MyapphipAccountCatagoryModule,
    MyapphipOwnerAccountModule,
    MyapphipVendorAccountModule,
    MyapphipOwnerModule,
    MyapphipAllAccountCodeModule,
    MyapphipBillingRelateModule
    /* jhipster-needle-add-entity-module - JHipster will add entity modules here */
  ],
  declarations: [],
  entryComponents: [],
  providers: [],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class MyapphipEntityModule {}
