/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { Observable, of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { MyapphipTestModule } from '../../../test.module';
import { BillingACComponent } from 'app/entities/billing-ac/billing-ac.component';
import { BillingACService } from 'app/entities/billing-ac/billing-ac.service';
import { BillingAC } from 'app/shared/model/billing-ac.model';

describe('Component Tests', () => {
  describe('BillingAC Management Component', () => {
    let comp: BillingACComponent;
    let fixture: ComponentFixture<BillingACComponent>;
    let service: BillingACService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [MyapphipTestModule],
        declarations: [BillingACComponent],
        providers: []
      })
        .overrideTemplate(BillingACComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(BillingACComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(BillingACService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new BillingAC(123)],
            headers
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.billingACS[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
