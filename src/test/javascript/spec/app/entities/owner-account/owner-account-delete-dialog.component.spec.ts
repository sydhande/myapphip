/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { MyapphipTestModule } from '../../../test.module';
import { OwnerAccountDeleteDialogComponent } from 'app/entities/owner-account/owner-account-delete-dialog.component';
import { OwnerAccountService } from 'app/entities/owner-account/owner-account.service';

describe('Component Tests', () => {
  describe('OwnerAccount Management Delete Component', () => {
    let comp: OwnerAccountDeleteDialogComponent;
    let fixture: ComponentFixture<OwnerAccountDeleteDialogComponent>;
    let service: OwnerAccountService;
    let mockEventManager: any;
    let mockActiveModal: any;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [MyapphipTestModule],
        declarations: [OwnerAccountDeleteDialogComponent]
      })
        .overrideTemplate(OwnerAccountDeleteDialogComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(OwnerAccountDeleteDialogComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(OwnerAccountService);
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
