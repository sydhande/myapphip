/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { MyapphipTestModule } from '../../../test.module';
import { VendorAccountUpdateComponent } from 'app/entities/vendor-account/vendor-account-update.component';
import { VendorAccountService } from 'app/entities/vendor-account/vendor-account.service';
import { VendorAccount } from 'app/shared/model/vendor-account.model';

describe('Component Tests', () => {
  describe('VendorAccount Management Update Component', () => {
    let comp: VendorAccountUpdateComponent;
    let fixture: ComponentFixture<VendorAccountUpdateComponent>;
    let service: VendorAccountService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [MyapphipTestModule],
        declarations: [VendorAccountUpdateComponent]
      })
        .overrideTemplate(VendorAccountUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(VendorAccountUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(VendorAccountService);
    });

    describe('save', () => {
      it(
        'Should call update service on save for existing entity',
        fakeAsync(() => {
          // GIVEN
          const entity = new VendorAccount(123);
          spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
          comp.vendorAccount = entity;
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
          const entity = new VendorAccount();
          spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
          comp.vendorAccount = entity;
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
