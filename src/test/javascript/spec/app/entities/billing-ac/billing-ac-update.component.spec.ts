/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { MyapphipTestModule } from '../../../test.module';
import { BillingACUpdateComponent } from 'app/entities/billing-ac/billing-ac-update.component';
import { BillingACService } from 'app/entities/billing-ac/billing-ac.service';
import { BillingAC } from 'app/shared/model/billing-ac.model';

describe('Component Tests', () => {
  describe('BillingAC Management Update Component', () => {
    let comp: BillingACUpdateComponent;
    let fixture: ComponentFixture<BillingACUpdateComponent>;
    let service: BillingACService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [MyapphipTestModule],
        declarations: [BillingACUpdateComponent]
      })
        .overrideTemplate(BillingACUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(BillingACUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(BillingACService);
    });

    describe('save', () => {
      it(
        'Should call update service on save for existing entity',
        fakeAsync(() => {
          // GIVEN
          const entity = new BillingAC(123);
          spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
          comp.billingAC = entity;
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
          const entity = new BillingAC();
          spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
          comp.billingAC = entity;
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
