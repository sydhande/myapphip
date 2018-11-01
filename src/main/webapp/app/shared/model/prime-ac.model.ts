import { IGeneralAC } from 'app/shared/model//general-ac.model';

export interface IPrimeAC {
  id?: number;
  accountName?: string;
  accountType?: string;
  generalacs?: IGeneralAC[];
}

export class PrimeAC implements IPrimeAC {
  constructor(public id?: number, public accountName?: string, public accountType?: string, public generalacs?: IGeneralAC[]) {}
}
