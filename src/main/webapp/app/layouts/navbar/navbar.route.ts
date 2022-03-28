import { Route } from '@angular/router';

import { NavbarComponent } from './navbar.component';
import { OrganisationResolve } from '../../custom/common.resolver';

export const navbarRoute: Route = {
  path: '',
  component: NavbarComponent,
  outlet: 'navbar',
  // ### Modification-Start ###
  resolve: {
    organisation: OrganisationResolve,
  },
  // ### Modification-End ###
};
