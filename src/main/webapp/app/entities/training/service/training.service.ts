import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import dayjs from 'dayjs/esm';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { ITraining, NewTraining } from '../training.model';

export type PartialUpdateTraining = Partial<ITraining> & Pick<ITraining, 'id'>;

type RestOf<T extends ITraining | NewTraining> = Omit<T, 'validUntil' | 'createdAt' | 'updatedAt'> & {
  validUntil?: string | null;
  createdAt?: string | null;
  updatedAt?: string | null;
};

export type RestTraining = RestOf<ITraining>;

export type NewRestTraining = RestOf<NewTraining>;

export type PartialUpdateRestTraining = RestOf<PartialUpdateTraining>;

export type EntityResponseType = HttpResponse<ITraining>;
export type EntityArrayResponseType = HttpResponse<ITraining[]>;

@Injectable({ providedIn: 'root' })
export class TrainingService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/trainings');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(training: NewTraining): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(training);
    return this.http
      .post<RestTraining>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  update(training: ITraining): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(training);
    return this.http
      .put<RestTraining>(`${this.resourceUrl}/${this.getTrainingIdentifier(training)}`, copy, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  partialUpdate(training: PartialUpdateTraining): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(training);
    return this.http
      .patch<RestTraining>(`${this.resourceUrl}/${this.getTrainingIdentifier(training)}`, copy, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<RestTraining>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<RestTraining[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map(res => this.convertResponseArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getTrainingIdentifier(training: Pick<ITraining, 'id'>): number {
    return training.id;
  }

  compareTraining(o1: Pick<ITraining, 'id'> | null, o2: Pick<ITraining, 'id'> | null): boolean {
    return o1 && o2 ? this.getTrainingIdentifier(o1) === this.getTrainingIdentifier(o2) : o1 === o2;
  }

  addTrainingToCollectionIfMissing<Type extends Pick<ITraining, 'id'>>(
    trainingCollection: Type[],
    ...trainingsToCheck: (Type | null | undefined)[]
  ): Type[] {
    const trainings: Type[] = trainingsToCheck.filter(isPresent);
    if (trainings.length > 0) {
      const trainingCollectionIdentifiers = trainingCollection.map(trainingItem => this.getTrainingIdentifier(trainingItem)!);
      const trainingsToAdd = trainings.filter(trainingItem => {
        const trainingIdentifier = this.getTrainingIdentifier(trainingItem);
        if (trainingCollectionIdentifiers.includes(trainingIdentifier)) {
          return false;
        }
        trainingCollectionIdentifiers.push(trainingIdentifier);
        return true;
      });
      return [...trainingsToAdd, ...trainingCollection];
    }
    return trainingCollection;
  }

  protected convertDateFromClient<T extends ITraining | NewTraining | PartialUpdateTraining>(training: T): RestOf<T> {
    return {
      ...training,
      validUntil: training.validUntil?.toJSON() ?? null,
      createdAt: training.createdAt?.toJSON() ?? null,
      updatedAt: training.updatedAt?.toJSON() ?? null,
    };
  }

  protected convertDateFromServer(restTraining: RestTraining): ITraining {
    return {
      ...restTraining,
      validUntil: restTraining.validUntil ? dayjs(restTraining.validUntil) : undefined,
      createdAt: restTraining.createdAt ? dayjs(restTraining.createdAt) : undefined,
      updatedAt: restTraining.updatedAt ? dayjs(restTraining.updatedAt) : undefined,
    };
  }

  protected convertResponseFromServer(res: HttpResponse<RestTraining>): HttpResponse<ITraining> {
    return res.clone({
      body: res.body ? this.convertDateFromServer(res.body) : null,
    });
  }

  protected convertResponseArrayFromServer(res: HttpResponse<RestTraining[]>): HttpResponse<ITraining[]> {
    return res.clone({
      body: res.body ? res.body.map(item => this.convertDateFromServer(item)) : null,
    });
  }
}
