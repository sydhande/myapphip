import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IBillingRelate } from 'app/shared/model/billing-relate.model';

type EntityResponseType = HttpResponse<IBillingRelate>;
type EntityArrayResponseType = HttpResponse<IBillingRelate[]>;

@Injectable({ providedIn: 'root' })
export class BillingRelateService {
  public resourceUrl = SERVER_API_URL + 'api/billing-relates';

  constructor(private http: HttpClient) {}

  create(billingRelate: IBillingRelate): Observable<EntityResponseType> {
    return this.http.post<IBillingRelate>(this.resourceUrl, billingRelate, { observe: 'response' });
  }

  update(billingRelate: IBillingRelate): Observable<EntityResponseType> {
    return this.http.put<IBillingRelate>(this.resourceUrl, billingRelate, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IBillingRelate>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IBillingRelate[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<any>> {
    return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
