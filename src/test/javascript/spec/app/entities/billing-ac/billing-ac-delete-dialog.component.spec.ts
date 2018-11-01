/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { MyapphipTestModule } from '../../../test.module';
import { BillingACDeleteDialogComponent } from 'app/entities/billing-ac/billing-ac-delete-dialog.component';
import { BillingACService } from 'app/entities/billing-ac/billing-ac.service';

describe('Component Tests', () => {
  describe('BillingAC Management Delete Component', () => {
    let comp: BillingACDeleteDialogComponent;
    let fixture: ComponentFixture<BillingACDeleteDialogComponent>;
    let service: BillingACService;
    let mockEventManager: any;
    let mockActiveModal: any;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [MyapphipTestModule],
        declarations: [BillingACDeleteDialogComponent]
      })
        .overrideTemplate(BillingACDeleteDialogComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(BillingACDeleteDialogComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(BillingACService);
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
