/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { Observable, of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { MyapphipTestModule } from '../../../test.module';
import { VendorAccountComponent } from 'app/entities/vendor-account/vendor-account.component';
import { VendorAccountService } from 'app/entities/vendor-account/vendor-account.service';
import { VendorAccount } from 'app/shared/model/vendor-account.model';

describe('Component Tests', () => {
  describe('VendorAccount Management Component', () => {
    let comp: VendorAccountComponent;
    let fixture: ComponentFixture<VendorAccountComponent>;
    let service: VendorAccountService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [MyapphipTestModule],
        declarations: [VendorAccountComponent],
        providers: []
      })
        .overrideTemplate(VendorAccountComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(VendorAccountComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(VendorAccountService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new VendorAccount(123)],
            headers
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.vendorAccounts[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
