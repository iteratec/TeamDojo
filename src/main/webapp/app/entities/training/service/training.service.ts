import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as dayjs from 'dayjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { ITraining, getTrainingIdentifier } from '../training.model';

export type EntityResponseType = HttpResponse<ITraining>;
export type EntityArrayResponseType = HttpResponse<ITraining[]>;

@Injectable({ providedIn: 'root' })
export class TrainingService {
  public resourceUrl = this.applicationConfigService.getEndpointFor('api/trainings');

  constructor(protected http: HttpClient, private applicationConfigService: ApplicationConfigService) {}

  create(training: ITraining): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(training);
    return this.http
      .post<ITraining>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(training: ITraining): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(training);
    return this.http
      .put<ITraining>(`${this.resourceUrl}/${getTrainingIdentifier(training) as number}`, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  partialUpdate(training: ITraining): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(training);
    return this.http
      .patch<ITraining>(`${this.resourceUrl}/${getTrainingIdentifier(training) as number}`, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<ITraining>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<ITraining[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  addTrainingToCollectionIfMissing(trainingCollection: ITraining[], ...trainingsToCheck: (ITraining | null | undefined)[]): ITraining[] {
    const trainings: ITraining[] = trainingsToCheck.filter(isPresent);
    if (trainings.length > 0) {
      const trainingCollectionIdentifiers = trainingCollection.map(trainingItem => getTrainingIdentifier(trainingItem)!);
      const trainingsToAdd = trainings.filter(trainingItem => {
        const trainingIdentifier = getTrainingIdentifier(trainingItem);
        if (trainingIdentifier == null || trainingCollectionIdentifiers.includes(trainingIdentifier)) {
          return false;
        }
        trainingCollectionIdentifiers.push(trainingIdentifier);
        return true;
      });
      return [...trainingsToAdd, ...trainingCollection];
    }
    return trainingCollection;
  }

  protected convertDateFromClient(training: ITraining): ITraining {
    return Object.assign({}, training, {
      validUntil: training.validUntil?.isValid() ? training.validUntil.toJSON() : undefined,
      createdAt: training.createdAt?.isValid() ? training.createdAt.toJSON() : undefined,
      updatedAt: training.updatedAt?.isValid() ? training.updatedAt.toJSON() : undefined,
    });
  }

  protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
    if (res.body) {
      res.body.validUntil = res.body.validUntil ? dayjs(res.body.validUntil) : undefined;
      res.body.createdAt = res.body.createdAt ? dayjs(res.body.createdAt) : undefined;
      res.body.updatedAt = res.body.updatedAt ? dayjs(res.body.updatedAt) : undefined;
    }
    return res;
  }

  protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
    if (res.body) {
      res.body.forEach((training: ITraining) => {
        training.validUntil = training.validUntil ? dayjs(training.validUntil) : undefined;
        training.createdAt = training.createdAt ? dayjs(training.createdAt) : undefined;
        training.updatedAt = training.updatedAt ? dayjs(training.updatedAt) : undefined;
      });
    }
    return res;
  }
}
