import { Component, OnInit } from '@angular/core';
import {
  Router,
  // start
  ActivatedRoute,
  //end
} from '@angular/router';
import { TranslateService } from '@ngx-translate/core';
import { SessionStorageService } from 'ngx-webstorage';

import { VERSION } from 'app/app.constants';
import { LANGUAGES } from 'app/config/language.constants';
import { AccountService } from 'app/core/auth/account.service';
import { Account } from 'app/core/auth/account.model';
import { LoginService } from 'app/login/login.service';
import { ProfileService } from 'app/layouts/profiles/profile.service';
import { IBreadcrumb } from 'app/custom/entities/breadcrumb/breadcrumb.model';
import { BreadcrumbService } from 'app/custom/layouts/navbar/breadcrumb.service';

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

  // ### Modification-Start ###
  organisationName = '';
  breadcrumbs: IBreadcrumb[] = [];
  account: Account | null = null;
  // ### Modification-End ###

  constructor(
    private loginService: LoginService,
    private translateService: TranslateService,
    private sessionStorage: SessionStorageService,
    private accountService: AccountService,
    private profileService: ProfileService,
    private router: Router // ### Modification-End ### // ### Modification-Start ###
  ) /*private breadcrumbService: BreadcrumbService,
    private route: ActivatedRoute*/
  {
    if (VERSION) {
      this.version = VERSION.toLowerCase().startsWith('v') ? VERSION : 'v' + VERSION;
    }
  }

  ngOnInit(): void {
    // ### Modification-Start ###
    //this.breadcrumbs = this.breadcrumbService.getCurrentBreadcrumb();
    /*this.breadcrumbService.breadcrumbChanged.subscribe(breadcrumb => {
      this.breadcrumbs = this.breadcrumbService.getCurrentBreadcrumb();
    });

     this.route.data.subscribe(({ organisation }) => {
      this.organisationName = organisation.name;
    }); */

    this.accountService.identity().subscribe(account => (this.account = account));
    // ### Modification-End ###

    this.profileService.getProfileInfo().subscribe(profileInfo => {
      this.inProduction = profileInfo.inProduction;
      this.openAPIEnabled = profileInfo.openAPIEnabled;
    });
  }

  changeLanguage(languageKey: string): void {
    this.sessionStorage.store('locale', languageKey);
    this.translateService.use(languageKey);
  }

  collapseNavbar(): void {
    this.isNavbarCollapsed = true;
  }

  isAuthenticated(): boolean {
    return this.accountService.isAuthenticated();
  }

  login(): void {
    this.loginService.login();
  }

  logout(): void {
    this.collapseNavbar();
    this.loginService.logout();
    this.router.navigate(['']);
  }

  toggleNavbar(): void {
    this.isNavbarCollapsed = !this.isNavbarCollapsed;
  }

  getImageUrl(): string {
    return this.isAuthenticated() ? this.accountService.getImageUrl() : '';
  }
}
