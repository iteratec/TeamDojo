jest.mock('@ng-bootstrap/ng-bootstrap');

import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { of } from 'rxjs';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { LevelSkillService } from '../service/level-skill.service';

import { LevelSkillDeleteDialogComponent } from './level-skill-delete-dialog.component';

describe('LevelSkill Management Delete Component', () => {
  let comp: LevelSkillDeleteDialogComponent;
  let fixture: ComponentFixture<LevelSkillDeleteDialogComponent>;
  let service: LevelSkillService;
  let mockActiveModal: NgbActiveModal;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, LevelSkillDeleteDialogComponent],
      providers: [NgbActiveModal],
    })
      .overrideTemplate(LevelSkillDeleteDialogComponent, '')
      .compileComponents();
    fixture = TestBed.createComponent(LevelSkillDeleteDialogComponent);
    comp = fixture.componentInstance;
    service = TestBed.inject(LevelSkillService);
    mockActiveModal = TestBed.inject(NgbActiveModal);
  });

  describe('confirmDelete', () => {
    it('Should call delete service on confirmDelete', inject(
      [],
      fakeAsync(() => {
        // GIVEN
        jest.spyOn(service, 'delete').mockReturnValue(of(new HttpResponse({ body: {} })));

        // WHEN
        comp.confirmDelete(123);
        tick();

        // THEN
        expect(service.delete).toHaveBeenCalledWith(123);
        expect(mockActiveModal.close).toHaveBeenCalledWith('deleted');
      }),
    ));

    it('Should not call delete service on clear', () => {
      // GIVEN
      jest.spyOn(service, 'delete');

      // WHEN
      comp.cancel();

      // THEN
      expect(service.delete).not.toHaveBeenCalled();
      expect(mockActiveModal.close).not.toHaveBeenCalled();
      expect(mockActiveModal.dismiss).toHaveBeenCalled();
    });
  });
});
