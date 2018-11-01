/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { MyapphipTestModule } from '../../../test.module';
import { VendorAccountDeleteDialogComponent } from 'app/entities/vendor-account/vendor-account-delete-dialog.component';
import { VendorAccountService } from 'app/entities/vendor-account/vendor-account.service';

describe('Component Tests', () => {
  describe('VendorAccount Management Delete Component', () => {
    let comp: VendorAccountDeleteDialogComponent;
    let fixture: ComponentFixture<VendorAccountDeleteDialogComponent>;
    let service: VendorAccountService;
    let mockEventManager: any;
    let mockActiveModal: any;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [MyapphipTestModule],
        declarations: [VendorAccountDeleteDialogComponent]
      })
        .overrideTemplate(VendorAccountDeleteDialogComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(VendorAccountDeleteDialogComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(VendorAccountService);
      mockEventManager = fixture.debugElement.injector.get(JhiEventManager);
      mockActiveModal = fixture.debugElement.injector.get(NgbActiveModal);
    });

    describe('confirmDelete', () => {
      it('Should call delete service on confirmDelete', inject(
        [],
        fakeAsync(() => {
          // GIVEN
          spyOn(service, 'delete').and.returnValue(of({}));

          // WHEN
          comp.confirmDelete(123);
          tick();

          // THEN
          expect(service.delete).toHaveBeenCalledWith(123);
          expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
          expect(mockEventManager.broadcastSpy).toHaveBeenCalled();
        })
      ));
    });
  });
});
