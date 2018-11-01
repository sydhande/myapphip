import { IGeneralAC } from 'app/shared/model//general-ac.model';
import { IBillingAC } from 'app/shared/model//billing-ac.model';

export interface IMainAC {
  id?: number;
  accountName?: string;
  generalAC?: IGeneralAC;
  billingacs?: IBillingAC[];
}

export class MainAC implements IMainAC {
  constructor(public id?: number, public accountName?: string, public generalAC?: IGeneralAC, public billingacs?: IBillingAC[]) {}
}
