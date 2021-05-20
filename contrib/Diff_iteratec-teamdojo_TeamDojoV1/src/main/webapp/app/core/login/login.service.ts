2d1
< import { JhiLanguageService } from 'ng-jhipster';
11d9
<         private languageService: JhiLanguageService,
24,28d21
<                         // After the login the language will be changed to
<                         // the language selected by the user during his registration
<                         if (account !== null) {
<                             this.languageService.changeLanguage(account.langKey);
<                         }
