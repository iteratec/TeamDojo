/*
 SPDX-FileCopyrightText: the TeamDojo authors
 SPDX-License-Identifier: Apache-2.0
 */
import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { map } from 'rxjs/operators';
import { IReport, Report } from 'app/entities/report/report.model';

export type EntityResponseType = HttpResponse<IReport>;
export type EntityArrayResponseType = HttpResponse<IReport[]>;

@Injectable()
export class FeedbackService {
  private resourceUrl = SERVER_API_URL + 'api/reports';

  constructor(private http: HttpClient) {}

  create(report: IReport): Observable<EntityResponseType> {
    const copy = this.convert(report);
    return this.http
      .post<IReport>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertResponse(res)));
  }

  /**
   * Convert a returned JSON object to Feedback.
   */
  private convertItemFromServer(report: IReport): IReport {
    const copy: IReport = Object.assign({}, report, {});
    return copy;
  }

  /**
   * Convert a Feedback to a JSON which can be sent to the server.
   */
  private convert(report: IReport): IReport {
    const copy: IReport = Object.assign({}, report, {});
    return copy;
  }

  private convertResponse(res: EntityResponseType): EntityResponseType {
    const body: IReport = this.convertItemFromServer(res.body ? res.body : new Report());
    return res.clone({ body });
  }
}
