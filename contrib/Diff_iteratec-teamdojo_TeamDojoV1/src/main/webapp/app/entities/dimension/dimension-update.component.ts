9,10d8
< import { ITeam } from 'app/shared/model/team.model';
< import { TeamService } from 'app/entities/team';
12a11,12
> import { ITeam } from 'app/shared/model/team.model';
> import { TeamService } from 'app/entities/team';
22,23d21
<     teams: ITeam[];
< 
25a24,25
>     teams: ITeam[];
> 
29d28
<         protected teamService: TeamService,
30a30
>         protected teamService: TeamService,
39,45d38
<         this.teamService
<             .query()
<             .pipe(
<                 filter((mayBeOk: HttpResponse<ITeam[]>) => mayBeOk.ok),
<                 map((response: HttpResponse<ITeam[]>) => response.body)
<             )
<             .subscribe((res: ITeam[]) => (this.teams = res), (res: HttpErrorResponse) => this.onError(res.message));
52a46,52
>         this.teamService
>             .query()
>             .pipe(
>                 filter((mayBeOk: HttpResponse<ITeam[]>) => mayBeOk.ok),
>                 map((response: HttpResponse<ITeam[]>) => response.body)
>             )
>             .subscribe((res: ITeam[]) => (this.teams = res), (res: HttpErrorResponse) => this.onError(res.message));
85c85
<     trackTeamById(index: number, item: ITeam) {
---
>     trackBadgeById(index: number, item: IBadge) {
89c89
<     trackBadgeById(index: number, item: IBadge) {
---
>     trackTeamById(index: number, item: ITeam) {
