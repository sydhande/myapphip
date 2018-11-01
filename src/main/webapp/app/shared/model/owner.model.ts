import { IAllAccountCode } from 'app/shared/model//all-account-code.model';
import { IOwnerAccount } from 'app/shared/model//owner-account.model';

export interface IOwner {
  id?: number;
  accountcode?: string;
  flat?: string;
  building?: string;
  firstname?: string;
  middlename?: string;
  lastname?: string;
  allaccountcode?: IAllAccountCode;
  owneraccounts?: IOwnerAccount[];
}

export class Owner implements IOwner {
  constructor(
    public id?: number,
    public accountcode?: string,
    public flat?: string,
    public building?: string,
    public firstname?: string,
    public middlename?: string,
    public lastname?: string,
    public allaccountcode?: IAllAccountCode,
    public owneraccounts?: IOwnerAccount[]
  ) {}
}
