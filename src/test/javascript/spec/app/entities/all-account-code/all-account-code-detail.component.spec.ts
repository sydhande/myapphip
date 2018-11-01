/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { MyapphipTestModule } from '../../../test.module';
import { AllAccountCodeDetailComponent } from 'app/entities/all-account-code/all-account-code-detail.component';
import { AllAccountCode } from 'app/shared/model/all-account-code.model';

describe('Component Tests', () => {
  describe('AllAccountCode Management Detail Component', () => {
    let comp: AllAccountCodeDetailComponent;
    let fixture: ComponentFixture<AllAccountCodeDetailComponent>;
    const route = ({ data: of({ allAccountCode: new AllAccountCode(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [MyapphipTestModule],
        declarations: [AllAccountCodeDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(AllAccountCodeDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(AllAccountCodeDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should call load all on init', () => {
        // GIVEN

        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.allAccountCode).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
