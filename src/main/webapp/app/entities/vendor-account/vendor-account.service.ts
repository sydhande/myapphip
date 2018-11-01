import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IVendorAccount } from 'app/shared/model/vendor-account.model';

type EntityResponseType = HttpResponse<IVendorAccount>;
type EntityArrayResponseType = HttpResponse<IVendorAccount[]>;

@Injectable({ providedIn: 'root' })
export class VendorAccountService {
  public resourceUrl = SERVER_API_URL + 'api/vendor-accounts';

  constructor(private http: HttpClient) {}

  create(vendorAccount: IVendorAccount): Observable<EntityResponseType> {
    return this.http.post<IVendorAccount>(this.resourceUrl, vendorAccount, { observe: 'response' });
  }

  update(vendorAccount: IVendorAccount): Observable<EntityResponseType> {
    return this.http.put<IVendorAccount>(this.resourceUrl, vendorAccount, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IVendorAccount>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IVendorAccount[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<any>> {
    return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
