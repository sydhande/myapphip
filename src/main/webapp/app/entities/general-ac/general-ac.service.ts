import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IGeneralAC } from 'app/shared/model/general-ac.model';

type EntityResponseType = HttpResponse<IGeneralAC>;
type EntityArrayResponseType = HttpResponse<IGeneralAC[]>;

@Injectable({ providedIn: 'root' })
export class GeneralACService {
  public resourceUrl = SERVER_API_URL + 'api/general-acs';

  constructor(private http: HttpClient) {}

  create(generalAC: IGeneralAC): Observable<EntityResponseType> {
    return this.http.post<IGeneralAC>(this.resourceUrl, generalAC, { observe: 'response' });
  }

  update(generalAC: IGeneralAC): Observable<EntityResponseType> {
    return this.http.put<IGeneralAC>(this.resourceUrl, generalAC, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IGeneralAC>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IGeneralAC[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<any>> {
    return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
