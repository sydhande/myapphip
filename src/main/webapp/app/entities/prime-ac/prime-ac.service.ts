import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IPrimeAC } from 'app/shared/model/prime-ac.model';

type EntityResponseType = HttpResponse<IPrimeAC>;
type EntityArrayResponseType = HttpResponse<IPrimeAC[]>;

@Injectable({ providedIn: 'root' })
export class PrimeACService {
  public resourceUrl = SERVER_API_URL + 'api/prime-acs';

  constructor(private http: HttpClient) {}

  create(primeAC: IPrimeAC): Observable<EntityResponseType> {
    return this.http.post<IPrimeAC>(this.resourceUrl, primeAC, { observe: 'response' });
  }

  update(primeAC: IPrimeAC): Observable<EntityResponseType> {
    return this.http.put<IPrimeAC>(this.resourceUrl, primeAC, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IPrimeAC>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IPrimeAC[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<any>> {
    return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
