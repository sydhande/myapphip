/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { MyapphipTestModule } from '../../../test.module';
import { MainACUpdateComponent } from 'app/entities/main-ac/main-ac-update.component';
import { MainACService } from 'app/entities/main-ac/main-ac.service';
import { MainAC } from 'app/shared/model/main-ac.model';

describe('Component Tests', () => {
  describe('MainAC Management Update Component', () => {
    let comp: MainACUpdateComponent;
    let fixture: ComponentFixture<MainACUpdateComponent>;
    let service: MainACService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [MyapphipTestModule],
        declarations: [MainACUpdateComponent]
      })
        .overrideTemplate(MainACUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(MainACUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(MainACService);
    });

    describe('save', () => {
      it(
        'Should call update service on save for existing entity',
        fakeAsync(() => {
          // GIVEN
          const entity = new MainAC(123);
          spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
          comp.mainAC = entity;
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
          const entity = new MainAC();
          spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
          comp.mainAC = entity;
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
