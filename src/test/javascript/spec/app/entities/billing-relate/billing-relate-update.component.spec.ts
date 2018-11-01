/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { MyapphipTestModule } from '../../../test.module';
import { BillingRelateUpdateComponent } from 'app/entities/billing-relate/billing-relate-update.component';
import { BillingRelateService } from 'app/entities/billing-relate/billing-relate.service';
import { BillingRelate } from 'app/shared/model/billing-relate.model';

describe('Component Tests', () => {
  describe('BillingRelate Management Update Component', () => {
    let comp: BillingRelateUpdateComponent;
    let fixture: ComponentFixture<BillingRelateUpdateComponent>;
    let service: BillingRelateService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [MyapphipTestModule],
        declarations: [BillingRelateUpdateComponent]
      })
        .overrideTemplate(BillingRelateUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(BillingRelateUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(BillingRelateService);
    });

    describe('save', () => {
      it(
        'Should call update service on save for existing entity',
        fakeAsync(() => {
          // GIVEN
          const entity = new BillingRelate(123);
          spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
          comp.billingRelate = entity;
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
          const entity = new BillingRelate();
          spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
          comp.billingRelate = entity;
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
