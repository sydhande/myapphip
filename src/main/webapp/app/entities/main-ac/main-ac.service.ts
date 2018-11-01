import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IMainAC } from 'app/shared/model/main-ac.model';

type EntityResponseType = HttpResponse<IMainAC>;
type EntityArrayResponseType = HttpResponse<IMainAC[]>;

@Injectable({ providedIn: 'root' })
export class MainACService {
  public resourceUrl = SERVER_API_URL + 'api/main-acs';

  constructor(private http: HttpClient) {}

  create(mainAC: IMainAC): Observable<EntityResponseType> {
    return this.http.post<IMainAC>(this.resourceUrl, mainAC, { observe: 'response' });
  }

  update(mainAC: IMainAC): Observable<EntityResponseType> {
    return this.http.put<IMainAC>(this.resourceUrl, mainAC, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IMainAC>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IMainAC[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<any>> {
    return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
