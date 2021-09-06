import { Directive, ElementRef, Input, OnChanges, OnInit } from '@angular/core';
import { TranslateService } from '@ngx-translate/core';
import { JhiConfigService } from 'ng-jhipster';
import { OrganisationService } from 'app/entities/organisation/service/organisation.service';
import { ApplicationMode } from 'app/entities/enumerations/application-mode.model';

/* tslint:disable directive-selector */
@Directive({
  selector: '[jhiDojoTranslate]',
})
export class DojoTranslateDirective implements OnChanges, OnInit {
  @Input() dojoTranslate: string;
  @Input() translateValues: any;

  constructor(
    private configService: JhiConfigService,
    private el: ElementRef,
    private translateService: TranslateService,
    private organisationService: OrganisationService
  ) {
    this.dojoTranslate = '';
  }

  ngOnInit(): void {
    const enabled = this.configService.getConfig().i18nEnabled;
    if (enabled) {
      this.translateService.onLangChange.subscribe(() => {
        this.getTranslation();
      });
    }
  }

  ngOnChanges(): void {
    const enabled = this.configService.getConfig().i18nEnabled;

    if (enabled) {
      this.getTranslation();
    }
  }

  private getTranslation(): void {
    /*if (this.organisationService.getCurrentUserMode() === ApplicationMode.PERSON) {
      const personTranslateKey = this.dojoTranslate.replace('teamdojoApp', 'persondojoApp');

      this.translateService.get(personTranslateKey, this.translateValues).subscribe(personValue => {
        if (!personValue.includes(personTranslateKey)) {
          this.el.nativeElement.innerHTML = personValue;
        } else {
          this.translateService.get(this.dojoTranslate, this.translateValues).subscribe(teamValue => {
            this.el.nativeElement.innerHTML = teamValue;
          });
        }
      });
    } else {
      this.translateService.get(this.dojoTranslate, this.translateValues).subscribe(teamValue => {
        this.el.nativeElement.innerHTML = teamValue;
      });
    }*/
  }
}
