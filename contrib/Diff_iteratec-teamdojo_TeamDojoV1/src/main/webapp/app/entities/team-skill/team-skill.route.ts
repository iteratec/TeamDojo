11d10
< import { TeamSkillVoteComponent } from 'app/entities/team-skill/team-skill-vote.component';
50,60d48
<             pageTitle: 'teamdojoApp.teamSkill.home.title'
<         },
<         canActivate: [UserRouteAccessService]
<     },
<     {
<         path: ':id/vote',
<         component: TeamSkillVoteComponent,
<         resolve: {
<             teamSkill: TeamSkillResolve
<         },
<         data: {
