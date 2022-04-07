import { Route } from '@angular/router';

import { NavbarComponent } from './navbar.component';
// ### MODIFICATION-START ###
import { OrganisationResolve } from '../../custom/common.resolver';
// ### MODIFICATION-END ###

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
