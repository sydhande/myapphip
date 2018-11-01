/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { Observable, of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { MyapphipTestModule } from '../../../test.module';
import { BillingRelateComponent } from 'app/entities/billing-relate/billing-relate.component';
import { BillingRelateService } from 'app/entities/billing-relate/billing-relate.service';
import { BillingRelate } from 'app/shared/model/billing-relate.model';

describe('Component Tests', () => {
  describe('BillingRelate Management Component', () => {
    let comp: BillingRelateComponent;
    let fixture: ComponentFixture<BillingRelateComponent>;
    let service: BillingRelateService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [MyapphipTestModule],
        declarations: [BillingRelateComponent],
        providers: []
      })
        .overrideTemplate(BillingRelateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(BillingRelateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(BillingRelateService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new BillingRelate(123)],
            headers
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.billingRelates[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
