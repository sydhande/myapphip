export interface IVendorAccount {
  id?: number;
  accountType?: string;
}

export class VendorAccount implements IVendorAccount {
  constructor(public id?: number, public accountType?: string) {}
}
