/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { MyapphipTestModule } from '../../../test.module';
import { AllAccountCodeUpdateComponent } from 'app/entities/all-account-code/all-account-code-update.component';
import { AllAccountCodeService } from 'app/entities/all-account-code/all-account-code.service';
import { AllAccountCode } from 'app/shared/model/all-account-code.model';

describe('Component Tests', () => {
  describe('AllAccountCode Management Update Component', () => {
    let comp: AllAccountCodeUpdateComponent;
    let fixture: ComponentFixture<AllAccountCodeUpdateComponent>;
    let service: AllAccountCodeService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [MyapphipTestModule],
        declarations: [AllAccountCodeUpdateComponent]
      })
        .overrideTemplate(AllAccountCodeUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(AllAccountCodeUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(AllAccountCodeService);
    });

    describe('save', () => {
      it(
        'Should call update service on save for existing entity',
        fakeAsync(() => {
          // GIVEN
          const entity = new AllAccountCode(123);
          spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
          comp.allAccountCode = entity;
          // WHEN
          comp.save();
          tick(); // simulate async

          // THEN
          expect(service.update).toHaveBeenCalledWith(entity);
          expect(comp.isSaving).toEqual(false);
        })
      );

      it(
        'Should call create service on save for new entity',
        fakeAsync(() => {
          // GIVEN
          const entity = new AllAccountCode();
          spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
          comp.allAccountCode = entity;
          // WHEN
          comp.save();
          tick(); // simulate async

          // THEN
          expect(service.create).toHaveBeenCalledWith(entity);
          expect(comp.isSaving).toEqual(false);
        })
      );
    });
  });
});
