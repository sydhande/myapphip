import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IAccountCatagory } from 'app/shared/model/account-catagory.model';

type EntityResponseType = HttpResponse<IAccountCatagory>;
type EntityArrayResponseType = HttpResponse<IAccountCatagory[]>;

@Injectable({ providedIn: 'root' })
export class AccountCatagoryService {
  public resourceUrl = SERVER_API_URL + 'api/account-catagories';

  constructor(private http: HttpClient) {}

  create(accountCatagory: IAccountCatagory): Observable<EntityResponseType> {
    return this.http.post<IAccountCatagory>(this.resourceUrl, accountCatagory, { observe: 'response' });
  }

  update(accountCatagory: IAccountCatagory): Observable<EntityResponseType> {
    return this.http.put<IAccountCatagory>(this.resourceUrl, accountCatagory, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IAccountCatagory>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IAccountCatagory[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<any>> {
    return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
