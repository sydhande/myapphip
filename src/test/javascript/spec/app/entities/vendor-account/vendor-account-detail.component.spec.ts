/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { MyapphipTestModule } from '../../../test.module';
import { VendorAccountDetailComponent } from 'app/entities/vendor-account/vendor-account-detail.component';
import { VendorAccount } from 'app/shared/model/vendor-account.model';

describe('Component Tests', () => {
  describe('VendorAccount Management Detail Component', () => {
    let comp: VendorAccountDetailComponent;
    let fixture: ComponentFixture<VendorAccountDetailComponent>;
    const route = ({ data: of({ vendorAccount: new VendorAccount(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [MyapphipTestModule],
        declarations: [VendorAccountDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(VendorAccountDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(VendorAccountDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should call load all on init', () => {
        // GIVEN

        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.vendorAccount).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
