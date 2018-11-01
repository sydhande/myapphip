/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { MyapphipTestModule } from '../../../test.module';
import { AccountCatagoryDeleteDialogComponent } from 'app/entities/account-catagory/account-catagory-delete-dialog.component';
import { AccountCatagoryService } from 'app/entities/account-catagory/account-catagory.service';

describe('Component Tests', () => {
  describe('AccountCatagory Management Delete Component', () => {
    let comp: AccountCatagoryDeleteDialogComponent;
    let fixture: ComponentFixture<AccountCatagoryDeleteDialogComponent>;
    let service: AccountCatagoryService;
    let mockEventManager: any;
    let mockActiveModal: any;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [MyapphipTestModule],
        declarations: [AccountCatagoryDeleteDialogComponent]
      })
        .overrideTemplate(AccountCatagoryDeleteDialogComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(AccountCatagoryDeleteDialogComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(AccountCatagoryService);
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
