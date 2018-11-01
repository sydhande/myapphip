/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { MyapphipTestModule } from '../../../test.module';
import { AccountCatagoryDetailComponent } from 'app/entities/account-catagory/account-catagory-detail.component';
import { AccountCatagory } from 'app/shared/model/account-catagory.model';

describe('Component Tests', () => {
  describe('AccountCatagory Management Detail Component', () => {
    let comp: AccountCatagoryDetailComponent;
    let fixture: ComponentFixture<AccountCatagoryDetailComponent>;
    const route = ({ data: of({ accountCatagory: new AccountCatagory(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [MyapphipTestModule],
        declarations: [AccountCatagoryDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(AccountCatagoryDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(AccountCatagoryDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should call load all on init', () => {
        // GIVEN

        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.accountCatagory).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
