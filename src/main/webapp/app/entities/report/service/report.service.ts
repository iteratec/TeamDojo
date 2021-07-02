import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as dayjs from 'dayjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IReport, getReportIdentifier } from '../report.model';

export type EntityResponseType = HttpResponse<IReport>;
export type EntityArrayResponseType = HttpResponse<IReport[]>;

@Injectable({ providedIn: 'root' })
export class ReportService {
  public resourceUrl = this.applicationConfigService.getEndpointFor('api/reports');

  constructor(protected http: HttpClient, private applicationConfigService: ApplicationConfigService) {}

  create(report: IReport): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(report);
    return this.http
      .post<IReport>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(report: IReport): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(report);
    return this.http
      .put<IReport>(`${this.resourceUrl}/${getReportIdentifier(report) as number}`, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  partialUpdate(report: IReport): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(report);
    return this.http
      .patch<IReport>(`${this.resourceUrl}/${getReportIdentifier(report) as number}`, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IReport>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IReport[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  addReportToCollectionIfMissing(reportCollection: IReport[], ...reportsToCheck: (IReport | null | undefined)[]): IReport[] {
    const reports: IReport[] = reportsToCheck.filter(isPresent);
    if (reports.length > 0) {
      const reportCollectionIdentifiers = reportCollection.map(reportItem => getReportIdentifier(reportItem)!);
      const reportsToAdd = reports.filter(reportItem => {
        const reportIdentifier = getReportIdentifier(reportItem);
        if (reportIdentifier == null || reportCollectionIdentifiers.includes(reportIdentifier)) {
          return false;
        }
        reportCollectionIdentifiers.push(reportIdentifier);
        return true;
      });
      return [...reportsToAdd, ...reportCollection];
    }
    return reportCollection;
  }

  protected convertDateFromClient(report: IReport): IReport {
    return Object.assign({}, report, {
      createdAt: report.createdAt?.isValid() ? report.createdAt.toJSON() : undefined,
      updatedAt: report.updatedAt?.isValid() ? report.updatedAt.toJSON() : undefined,
    });
  }

  protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
    if (res.body) {
      res.body.createdAt = res.body.createdAt ? dayjs(res.body.createdAt) : undefined;
      res.body.updatedAt = res.body.updatedAt ? dayjs(res.body.updatedAt) : undefined;
    }
    return res;
  }

  protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
    if (res.body) {
      res.body.forEach((report: IReport) => {
        report.createdAt = report.createdAt ? dayjs(report.createdAt) : undefined;
        report.updatedAt = report.updatedAt ? dayjs(report.updatedAt) : undefined;
      });
    }
    return res;
  }
}
