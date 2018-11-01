/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { MyapphipTestModule } from '../../../test.module';
import { OwnerAccountUpdateComponent } from 'app/entities/owner-account/owner-account-update.component';
import { OwnerAccountService } from 'app/entities/owner-account/owner-account.service';
import { OwnerAccount } from 'app/shared/model/owner-account.model';

describe('Component Tests', () => {
  describe('OwnerAccount Management Update Component', () => {
    let comp: OwnerAccountUpdateComponent;
    let fixture: ComponentFixture<OwnerAccountUpdateComponent>;
    let service: OwnerAccountService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [MyapphipTestModule],
        declarations: [OwnerAccountUpdateComponent]
      })
        .overrideTemplate(OwnerAccountUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(OwnerAccountUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(OwnerAccountService);
    });

    describe('save', () => {
      it(
        'Should call update service on save for existing entity',
        fakeAsync(() => {
          // GIVEN
          const entity = new OwnerAccount(123);
          spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
          comp.ownerAccount = entity;
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
          const entity = new OwnerAccount();
          spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
          comp.ownerAccount = entity;
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
