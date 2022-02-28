import { Component, OnInit } from '@angular/core';
import {
  Router,
  // start
  ActivatedRoute,
  // end
} from '@angular/router';
import { TranslateService } from '@ngx-translate/core';
import { SessionStorageService } from 'ngx-webstorage';

import { VERSION } from 'app/app.constants';
import { LANGUAGES } from 'app/config/language.constants';
import { Account } from 'app/core/auth/account.model';
import { AccountService } from 'app/core/auth/account.service';
import { LoginService } from 'app/login/login.service';
import { ProfileService } from 'app/layouts/profiles/profile.service';
import { EntityNavbarItems } from 'app/entities/entity-navbar-items';
// ### Modification-Start ###
import { IBreadcrumb } from 'app/custom/entities/breadcrumb/breadcrumb.model';
// ### Modification-End ###
@Component({
  selector: 'jhi-navbar',
  templateUrl: './navbar.component.html',
  // ### Modification-Start ###
  styleUrls: ['./custom/navbar.component.scss'],
  // ### Modification-End ###
})
export class NavbarComponent implements OnInit {
  inProduction?: boolean;
  isNavbarCollapsed = true;
  languages = LANGUAGES;
  openAPIEnabled?: boolean;
  version = '';
  account: Account | null = null;
  entitiesNavbarItems: any[] = [];

  // ### Modification-Start ###
  organisationName = '';
  breadcrumbs: IBreadcrumb[] = [];
  // ### Modification-End ###

  constructor(
    private loginService: LoginService,
    private translateService: TranslateService,
    private sessionStorageService: SessionStorageService,
    private accountService: AccountService,
    private profileService: ProfileService,
    // ### Modification-Start ###
    private router: Router,
    private route: ActivatedRoute // private breadcrumbService: BreadcrumbService, // ### Modification-End ###
  ) {
    if (VERSION) {
      this.version = VERSION.toLowerCase().startsWith('v') ? VERSION : `v${VERSION}`;
    }
  }

  ngOnInit(): void {
    // ### Modification-Start ###
    // this.breadcrumbs = this.breadcrumbService.getCurrentBreadcrumb();
    /* this.breadcrumbService.breadcrumbChanged.subscribe(breadcrumb => {
      this.breadcrumbs = this.breadcrumbService.getCurrentBreadcrumb();
    });

     this.route.data.subscribe(({ organisation }) => {
      this.organisationName = organisation.name;
    }); */
    // ### Modification-End ###
    this.entitiesNavbarItems = EntityNavbarItems;
    this.profileService.getProfileInfo().subscribe(profileInfo => {
      this.inProduction = profileInfo.inProduction;
      this.openAPIEnabled = profileInfo.openAPIEnabled;
    });

    this.accountService.getAuthenticationState().subscribe(account => {
      this.account = account;
    });
  }

  changeLanguage(languageKey: string): void {
    this.sessionStorageService.store('locale', languageKey);
    this.translateService.use(languageKey);
  }

  collapseNavbar(): void {
    this.isNavbarCollapsed = true;
  }

  login(): void {
    this.loginService.login();
  }

  logout(): void {
    this.collapseNavbar();
    this.loginService.logout();
    this.router.navigate(['']);
  }

  // ### MODIFICATION-START ###
  isAuthenticated(): boolean {
    return this.accountService.isAuthenticated();
  }
  // ### MODIFICATION-END ###

  toggleNavbar(): void {
    this.isNavbarCollapsed = !this.isNavbarCollapsed;
  }
}
