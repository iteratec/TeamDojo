9,10d8
< import { StarRatingModule } from 'angular-star-rating';
< import { MarkdownModule, MarkedOptions } from 'ngx-markdown';
22,24d19
< import { OverviewModule } from 'app/overview';
< import { TeamsModule } from './teams/teams.module';
< import { FeedbackModule } from './feedback/feedback.module';
26,27c21
< 
< import { ActiveMenuDirective, ErrorComponent, FooterComponent, JhiMainComponent, NavbarComponent, PageRibbonComponent } from './layouts';
---
> import { JhiMainComponent, NavbarComponent, FooterComponent, PageRibbonComponent, ActiveMenuDirective, ErrorComponent } from './layouts';
31d24
<         StarRatingModule.forRoot(),
41,49d33
<         MarkdownModule.forRoot({
<             markedOptions: {
<                 provide: MarkedOptions,
<                 useValue: {
<                     breaks: true,
<                     sanitize: true
<                 }
<             }
<         }),
52,54d35
<         OverviewModule,
<         TeamsModule,
<         FeedbackModule,
