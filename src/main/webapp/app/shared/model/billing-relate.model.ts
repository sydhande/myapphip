import { IBillingAC } from 'app/shared/model//billing-ac.model';

export interface IBillingRelate {
  id?: number;
  draccount?: number;
  craccount?: number;
  billingAC?: IBillingAC;
  billingAC?: IBillingAC;
}

export class BillingRelate implements IBillingRelate {
  constructor(
    public id?: number,
    public draccount?: number,
    public craccount?: number,
    public billingAC?: IBillingAC,
    public billingAC?: IBillingAC
  ) {}
}
