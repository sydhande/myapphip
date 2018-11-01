/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { MyapphipTestModule } from '../../../test.module';
import { BillingRelateDeleteDialogComponent } from 'app/entities/billing-relate/billing-relate-delete-dialog.component';
import { BillingRelateService } from 'app/entities/billing-relate/billing-relate.service';

describe('Component Tests', () => {
  describe('BillingRelate Management Delete Component', () => {
    let comp: BillingRelateDeleteDialogComponent;
    let fixture: ComponentFixture<BillingRelateDeleteDialogComponent>;
    let service: BillingRelateService;
    let mockEventManager: any;
    let mockActiveModal: any;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [MyapphipTestModule],
        declarations: [BillingRelateDeleteDialogComponent]
      })
        .overrideTemplate(BillingRelateDeleteDialogComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(BillingRelateDeleteDialogComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(BillingRelateService);
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
