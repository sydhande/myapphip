/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { MyapphipTestModule } from '../../../test.module';
import { OwnerDeleteDialogComponent } from 'app/entities/owner/owner-delete-dialog.component';
import { OwnerService } from 'app/entities/owner/owner.service';

describe('Component Tests', () => {
  describe('Owner Management Delete Component', () => {
    let comp: OwnerDeleteDialogComponent;
    let fixture: ComponentFixture<OwnerDeleteDialogComponent>;
    let service: OwnerService;
    let mockEventManager: any;
    let mockActiveModal: any;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [MyapphipTestModule],
        declarations: [OwnerDeleteDialogComponent]
      })
        .overrideTemplate(OwnerDeleteDialogComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(OwnerDeleteDialogComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(OwnerService);
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
