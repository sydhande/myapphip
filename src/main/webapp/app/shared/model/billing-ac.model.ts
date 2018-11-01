import { IMainAC } from 'app/shared/model//main-ac.model';
import { IAllAccountCode } from 'app/shared/model//all-account-code.model';
import { IBillingRelate } from 'app/shared/model//billing-relate.model';

export interface IBillingAC {
  id?: number;
  accountName?: string;
  accountCode?: string;
  mainAC?: IMainAC;
  allaccountcode?: IAllAccountCode;
  relatedracs?: IBillingRelate[];
  relatecracs?: IBillingRelate[];
}

export class BillingAC implements IBillingAC {
  constructor(
    public id?: number,
    public accountName?: string,
    public accountCode?: string,
    public mainAC?: IMainAC,
    public allaccountcode?: IAllAccountCode,
    public relatedracs?: IBillingRelate[],
    public relatecracs?: IBillingRelate[]
  ) {}
}
