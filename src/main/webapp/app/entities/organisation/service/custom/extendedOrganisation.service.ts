import { Injectable } from '@angular/core';
import { HttpResponse, HttpClient } from '@angular/common/http';

import { Observable } from 'rxjs';

import { IOrganisation } from '../../organisation.model';
import { OrganisationService } from 'app/entities/organisation/service/organisation.service';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { LocalStorageService } from 'ngx-webstorage';

export type EntityResponseType = HttpResponse<IOrganisation>;
export type EntityArrayResponseType = HttpResponse<IOrganisation[]>;

const COUNT_OF_CONFIRMATIONS_STORAGE_KEY = 'countOfConfirmations';

@Injectable({ providedIn: 'root' })
export class ExtendedOrganisationService extends OrganisationService {
  constructor(protected http: HttpClient, applicationConfigService: ApplicationConfigService, private storage: LocalStorageService) {
    super(http, applicationConfigService);
  }

  findCurrent(): Observable<EntityResponseType> {
    const result = this.http.get<IOrganisation>(`${this.resourceUrl}/current`, { observe: 'response' });

    result.subscribe(res => {
      if (res.body) {
        this.storage.store(COUNT_OF_CONFIRMATIONS_STORAGE_KEY, res.body.countOfConfirmations ?? 0);
      }
    });
    return result;
  }
}
