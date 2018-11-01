export interface IAllAccountCode {
  id?: number;
  accountcode?: string;
}

export class AllAccountCode implements IAllAccountCode {
  constructor(public id?: number, public accountcode?: string) {}
}
