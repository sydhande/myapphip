import { IOwner } from 'app/shared/model//owner.model';

export interface IOwnerAccount {
  id?: number;
  accountType?: string;
  owners?: IOwner[];
}

export class OwnerAccount implements IOwnerAccount {
  constructor(public id?: number, public accountType?: string, public owners?: IOwner[]) {}
}
