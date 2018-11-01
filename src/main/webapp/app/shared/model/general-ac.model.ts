import { IPrimeAC } from 'app/shared/model//prime-ac.model';
import { IMainAC } from 'app/shared/model//main-ac.model';

export interface IGeneralAC {
  id?: number;
  accountName?: string;
  accountType?: string;
  transctionType?: string;
  primeAC?: IPrimeAC;
  mainacs?: IMainAC[];
}

export class GeneralAC implements IGeneralAC {
  constructor(
    public id?: number,
    public accountName?: string,
    public accountType?: string,
    public transctionType?: string,
    public primeAC?: IPrimeAC,
    public mainacs?: IMainAC[]
  ) {}
}
