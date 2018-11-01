/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { MyapphipTestModule } from '../../../test.module';
import { BillingRelateDetailComponent } from 'app/entities/billing-relate/billing-relate-detail.component';
import { BillingRelate } from 'app/shared/model/billing-relate.model';

describe('Component Tests', () => {
  describe('BillingRelate Management Detail Component', () => {
    let comp: BillingRelateDetailComponent;
    let fixture: ComponentFixture<BillingRelateDetailComponent>;
    const route = ({ data: of({ billingRelate: new BillingRelate(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [MyapphipTestModule],
        declarations: [BillingRelateDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(BillingRelateDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(BillingRelateDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should call load all on init', () => {
        // GIVEN

        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.billingRelate).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
