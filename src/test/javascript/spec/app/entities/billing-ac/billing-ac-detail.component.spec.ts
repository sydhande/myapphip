/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { MyapphipTestModule } from '../../../test.module';
import { BillingACDetailComponent } from 'app/entities/billing-ac/billing-ac-detail.component';
import { BillingAC } from 'app/shared/model/billing-ac.model';

describe('Component Tests', () => {
  describe('BillingAC Management Detail Component', () => {
    let comp: BillingACDetailComponent;
    let fixture: ComponentFixture<BillingACDetailComponent>;
    const route = ({ data: of({ billingAC: new BillingAC(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [MyapphipTestModule],
        declarations: [BillingACDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(BillingACDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(BillingACDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should call load all on init', () => {
        // GIVEN

        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.billingAC).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
