import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { SERVER_API_URL } from 'app/app.constants';
import { map } from 'rxjs/operators';

import { IServerInfo } from 'app/custom/entities/server-info/server-info.model';
import { createRequestOption } from 'app/core/request/request-util';

@Injectable()
export class ServerInfoService {
  private resourceUrl = SERVER_API_URL + 'api/server/info';

  constructor(private http: HttpClient) {}

  query(req?: any): Observable<IServerInfo | null> {
    const options = createRequestOption(req);
    return this.http
      .get<IServerInfo>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: HttpResponse<IServerInfo>) => res.body));
  }
}
