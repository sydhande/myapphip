import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IOwnerAccount } from 'app/shared/model/owner-account.model';

type EntityResponseType = HttpResponse<IOwnerAccount>;
type EntityArrayResponseType = HttpResponse<IOwnerAccount[]>;

@Injectable({ providedIn: 'root' })
export class OwnerAccountService {
  public resourceUrl = SERVER_API_URL + 'api/owner-accounts';

  constructor(private http: HttpClient) {}

  create(ownerAccount: IOwnerAccount): Observable<EntityResponseType> {
    return this.http.post<IOwnerAccount>(this.resourceUrl, ownerAccount, { observe: 'response' });
  }

  update(ownerAccount: IOwnerAccount): Observable<EntityResponseType> {
    return this.http.put<IOwnerAccount>(this.resourceUrl, ownerAccount, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IOwnerAccount>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IOwnerAccount[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<any>> {
    return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
