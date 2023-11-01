import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { map } from 'rxjs/operators';

import dayjs from 'dayjs/esm';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IActivity, NewActivity } from '../activity.model';

export type PartialUpdateActivity = Partial<IActivity> & Pick<IActivity, 'id'>;

type RestOf<T extends IActivity | NewActivity> = Omit<T, 'createdAt' | 'updatedAt'> & {
  createdAt?: string | null;
  updatedAt?: string | null;
};

export type RestActivity = RestOf<IActivity>;

export type NewRestActivity = RestOf<NewActivity>;

export type PartialUpdateRestActivity = RestOf<PartialUpdateActivity>;

export type EntityResponseType = HttpResponse<IActivity>;
export type EntityArrayResponseType = HttpResponse<IActivity[]>;

@Injectable({ providedIn: 'root' })
export class ActivityService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/activities');

  constructor(
    protected http: HttpClient,
    protected applicationConfigService: ApplicationConfigService,
  ) {}

  create(activity: NewActivity): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(activity);
    return this.http
      .post<RestActivity>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  update(activity: IActivity): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(activity);
    return this.http
      .put<RestActivity>(`${this.resourceUrl}/${this.getActivityIdentifier(activity)}`, copy, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  partialUpdate(activity: PartialUpdateActivity): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(activity);
    return this.http
      .patch<RestActivity>(`${this.resourceUrl}/${this.getActivityIdentifier(activity)}`, copy, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<RestActivity>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<RestActivity[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map(res => this.convertResponseArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getActivityIdentifier(activity: Pick<IActivity, 'id'>): number {
    return activity.id;
  }

  compareActivity(o1: Pick<IActivity, 'id'> | null, o2: Pick<IActivity, 'id'> | null): boolean {
    return o1 && o2 ? this.getActivityIdentifier(o1) === this.getActivityIdentifier(o2) : o1 === o2;
  }

  addActivityToCollectionIfMissing<Type extends Pick<IActivity, 'id'>>(
    activityCollection: Type[],
    ...activitiesToCheck: (Type | null | undefined)[]
  ): Type[] {
    const activities: Type[] = activitiesToCheck.filter(isPresent);
    if (activities.length > 0) {
      const activityCollectionIdentifiers = activityCollection.map(activityItem => this.getActivityIdentifier(activityItem)!);
      const activitiesToAdd = activities.filter(activityItem => {
        const activityIdentifier = this.getActivityIdentifier(activityItem);
        if (activityCollectionIdentifiers.includes(activityIdentifier)) {
          return false;
        }
        activityCollectionIdentifiers.push(activityIdentifier);
        return true;
      });
      return [...activitiesToAdd, ...activityCollection];
    }
    return activityCollection;
  }

  protected convertDateFromClient<T extends IActivity | NewActivity | PartialUpdateActivity>(activity: T): RestOf<T> {
    return {
      ...activity,
      createdAt: activity.createdAt?.toJSON() ?? null,
      updatedAt: activity.updatedAt?.toJSON() ?? null,
    };
  }

  protected convertDateFromServer(restActivity: RestActivity): IActivity {
    return {
      ...restActivity,
      createdAt: restActivity.createdAt ? dayjs(restActivity.createdAt) : undefined,
      updatedAt: restActivity.updatedAt ? dayjs(restActivity.updatedAt) : undefined,
    };
  }

  protected convertResponseFromServer(res: HttpResponse<RestActivity>): HttpResponse<IActivity> {
    return res.clone({
      body: res.body ? this.convertDateFromServer(res.body) : null,
    });
  }

  protected convertResponseArrayFromServer(res: HttpResponse<RestActivity[]>): HttpResponse<IActivity[]> {
    return res.clone({
      body: res.body ? res.body.map(item => this.convertDateFromServer(item)) : null,
    });
  }
}
