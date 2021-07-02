import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { of } from 'rxjs';

import { OrganisationService } from '../service/organisation.service';

import { OrganisationComponent } from './organisation.component';

describe('Component Tests', () => {
  describe('Organisation Management Component', () => {
    let comp: OrganisationComponent;
    let fixture: ComponentFixture<OrganisationComponent>;
    let service: OrganisationService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
        declarations: [OrganisationComponent],
      })
        .overrideTemplate(OrganisationComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(OrganisationComponent);
      comp = fixture.componentInstance;
      service = TestBed.inject(OrganisationService);

      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [{ id: 123 }],
            headers,
          })
        )
      );
    });

    it('Should call load all on init', () => {
      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.organisations?.[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
