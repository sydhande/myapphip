import { IOwnerAccount } from 'app/shared/model//owner-account.model';
import { IVendorAccount } from 'app/shared/model//vendor-account.model';

export const enum Catuser {
  OWNER = 'OWNER',
  VENDOR = 'VENDOR',
  CUSTOMER = 'CUSTOMER'
}

export interface IAccountCatagory {
  id?: number;
  accountType?: string;
  accountUser?: Catuser;
  owneraccount?: IOwnerAccount;
  vendoraccount?: IVendorAccount;
}

export class AccountCatagory implements IAccountCatagory {
  constructor(
    public id?: number,
    public accountType?: string,
    public accountUser?: Catuser,
    public owneraccount?: IOwnerAccount,
    public vendoraccount?: IVendorAccount
  ) {}
}
