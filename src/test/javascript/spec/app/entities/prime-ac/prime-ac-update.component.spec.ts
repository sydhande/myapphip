/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { MyapphipTestModule } from '../../../test.module';
import { PrimeACUpdateComponent } from 'app/entities/prime-ac/prime-ac-update.component';
import { PrimeACService } from 'app/entities/prime-ac/prime-ac.service';
import { PrimeAC } from 'app/shared/model/prime-ac.model';

describe('Component Tests', () => {
  describe('PrimeAC Management Update Component', () => {
    let comp: PrimeACUpdateComponent;
    let fixture: ComponentFixture<PrimeACUpdateComponent>;
    let service: PrimeACService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [MyapphipTestModule],
        declarations: [PrimeACUpdateComponent]
      })
        .overrideTemplate(PrimeACUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(PrimeACUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(PrimeACService);
    });

    describe('save', () => {
      it(
        'Should call update service on save for existing entity',
        fakeAsync(() => {
          // GIVEN
          const entity = new PrimeAC(123);
          spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
          comp.primeAC = entity;
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
          const entity = new PrimeAC();
          spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
          comp.primeAC = entity;
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
