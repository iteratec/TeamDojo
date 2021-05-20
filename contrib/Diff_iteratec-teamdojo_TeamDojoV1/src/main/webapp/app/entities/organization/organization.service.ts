7,8c7
< import { IOrganization, UserMode } from 'app/shared/model/organization.model';
< import { LocalStorageService } from 'ngx-webstorage';
---
> import { IOrganization } from 'app/shared/model/organization.model';
13,15d11
< const USER_MODE_STORAGE_KEY = 'userMode';
< const COUNT_OF_CONFIRMATIONS_STORAGE_KEY = 'countOfConfirmations';
< 
20c16
<     constructor(protected http: HttpClient, private storage: LocalStorageService) {}
---
>     constructor(protected http: HttpClient) {}
41,57d36
<     }
< 
<     findCurrent(): Observable<EntityResponseType> {
<         const result = this.http.get<IOrganization>(`${this.resourceUrl}/current`, { observe: 'response' });
<         result.subscribe(res => {
<             this.storage.store(USER_MODE_STORAGE_KEY, res.body.userMode || UserMode.TEAM);
<             this.storage.store(COUNT_OF_CONFIRMATIONS_STORAGE_KEY, res.body.countOfConfirmations || 0);
<         });
<         return result;
<     }
< 
<     getCurrentUserMode(): UserMode {
<         return this.storage.retrieve(USER_MODE_STORAGE_KEY) || UserMode.TEAM;
<     }
< 
<     getCurrentCountOfConfirmations(): number {
<         return this.storage.retrieve(COUNT_OF_CONFIRMATIONS_STORAGE_KEY) || 0;
