import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { of } from 'rxjs';

import { ReportService } from '../service/report.service';

import { ReportComponent } from './report.component';

describe('Component Tests', () => {
  describe('Report Management Component', () => {
    let comp: ReportComponent;
    let fixture: ComponentFixture<ReportComponent>;
    let service: ReportService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
        declarations: [ReportComponent],
      })
        .overrideTemplate(ReportComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(ReportComponent);
      comp = fixture.componentInstance;
      service = TestBed.inject(ReportService);

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
      expect(comp.reports?.[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
