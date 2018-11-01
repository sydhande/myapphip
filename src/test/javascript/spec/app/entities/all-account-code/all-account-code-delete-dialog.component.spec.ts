/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { MyapphipTestModule } from '../../../test.module';
import { AllAccountCodeDeleteDialogComponent } from 'app/entities/all-account-code/all-account-code-delete-dialog.component';
import { AllAccountCodeService } from 'app/entities/all-account-code/all-account-code.service';

describe('Component Tests', () => {
  describe('AllAccountCode Management Delete Component', () => {
    let comp: AllAccountCodeDeleteDialogComponent;
    let fixture: ComponentFixture<AllAccountCodeDeleteDialogComponent>;
    let service: AllAccountCodeService;
    let mockEventManager: any;
    let mockActiveModal: any;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [MyapphipTestModule],
        declarations: [AllAccountCodeDeleteDialogComponent]
      })
        .overrideTemplate(AllAccountCodeDeleteDialogComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(AllAccountCodeDeleteDialogComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(AllAccountCodeService);
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
