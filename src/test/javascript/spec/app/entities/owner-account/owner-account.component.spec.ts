/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { Observable, of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { MyapphipTestModule } from '../../../test.module';
import { OwnerAccountComponent } from 'app/entities/owner-account/owner-account.component';
import { OwnerAccountService } from 'app/entities/owner-account/owner-account.service';
import { OwnerAccount } from 'app/shared/model/owner-account.model';

describe('Component Tests', () => {
  describe('OwnerAccount Management Component', () => {
    let comp: OwnerAccountComponent;
    let fixture: ComponentFixture<OwnerAccountComponent>;
    let service: OwnerAccountService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [MyapphipTestModule],
        declarations: [OwnerAccountComponent],
        providers: []
      })
        .overrideTemplate(OwnerAccountComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(OwnerAccountComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(OwnerAccountService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new OwnerAccount(123)],
            headers
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.ownerAccounts[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
