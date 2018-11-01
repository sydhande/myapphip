import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IBillingAC } from 'app/shared/model/billing-ac.model';

type EntityResponseType = HttpResponse<IBillingAC>;
type EntityArrayResponseType = HttpResponse<IBillingAC[]>;

@Injectable({ providedIn: 'root' })
export class BillingACService {
  public resourceUrl = SERVER_API_URL + 'api/billing-acs';

  constructor(private http: HttpClient) {}

  create(billingAC: IBillingAC): Observable<EntityResponseType> {
    return this.http.post<IBillingAC>(this.resourceUrl, billingAC, { observe: 'response' });
  }

  update(billingAC: IBillingAC): Observable<EntityResponseType> {
    return this.http.put<IBillingAC>(this.resourceUrl, billingAC, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IBillingAC>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IBillingAC[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<any>> {
    return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
