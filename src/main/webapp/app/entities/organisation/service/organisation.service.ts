import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as dayjs from 'dayjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IOrganisation, getOrganisationIdentifier } from '../organisation.model';

export type EntityResponseType = HttpResponse<IOrganisation>;
export type EntityArrayResponseType = HttpResponse<IOrganisation[]>;

@Injectable({ providedIn: 'root' })
export class OrganisationService {
  public resourceUrl = this.applicationConfigService.getEndpointFor('api/organisations');

  constructor(protected http: HttpClient, private applicationConfigService: ApplicationConfigService) {}

  create(organisation: IOrganisation): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(organisation);
    return this.http
      .post<IOrganisation>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(organisation: IOrganisation): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(organisation);
    return this.http
      .put<IOrganisation>(`${this.resourceUrl}/${getOrganisationIdentifier(organisation) as number}`, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  partialUpdate(organisation: IOrganisation): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(organisation);
    return this.http
      .patch<IOrganisation>(`${this.resourceUrl}/${getOrganisationIdentifier(organisation) as number}`, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IOrganisation>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IOrganisation[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  addOrganisationToCollectionIfMissing(
    organisationCollection: IOrganisation[],
    ...organisationsToCheck: (IOrganisation | null | undefined)[]
  ): IOrganisation[] {
    const organisations: IOrganisation[] = organisationsToCheck.filter(isPresent);
    if (organisations.length > 0) {
      const organisationCollectionIdentifiers = organisationCollection.map(
        organisationItem => getOrganisationIdentifier(organisationItem)!
      );
      const organisationsToAdd = organisations.filter(organisationItem => {
        const organisationIdentifier = getOrganisationIdentifier(organisationItem);
        if (organisationIdentifier == null || organisationCollectionIdentifiers.includes(organisationIdentifier)) {
          return false;
        }
        organisationCollectionIdentifiers.push(organisationIdentifier);
        return true;
      });
      return [...organisationsToAdd, ...organisationCollection];
    }
    return organisationCollection;
  }

  protected convertDateFromClient(organisation: IOrganisation): IOrganisation {
    return Object.assign({}, organisation, {
      createdAt: organisation.createdAt?.isValid() ? organisation.createdAt.toJSON() : undefined,
      updatedAt: organisation.updatedAt?.isValid() ? organisation.updatedAt.toJSON() : undefined,
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
      res.body.forEach((organisation: IOrganisation) => {
        organisation.createdAt = organisation.createdAt ? dayjs(organisation.createdAt) : undefined;
        organisation.updatedAt = organisation.updatedAt ? dayjs(organisation.updatedAt) : undefined;
      });
    }
    return res;
  }
}
