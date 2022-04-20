import { Route } from '@angular/router';

import { NavbarComponent } from './navbar.component';
import { TeamGroupResolve } from '../../custom/common.resolver';

export const navbarRoute: Route = {
  path: '',
  component: NavbarComponent,
  outlet: 'navbar',
  // ### Modification-Start ###
  resolve: {
    teamGroup: TeamGroupResolve,
  },
  // ### Modification-End ###
};
