/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { MyapphipTestModule } from '../../../test.module';
import { OwnerAccountDetailComponent } from 'app/entities/owner-account/owner-account-detail.component';
import { OwnerAccount } from 'app/shared/model/owner-account.model';

describe('Component Tests', () => {
  describe('OwnerAccount Management Detail Component', () => {
    let comp: OwnerAccountDetailComponent;
    let fixture: ComponentFixture<OwnerAccountDetailComponent>;
    const route = ({ data: of({ ownerAccount: new OwnerAccount(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [MyapphipTestModule],
        declarations: [OwnerAccountDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(OwnerAccountDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(OwnerAccountDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should call load all on init', () => {
        // GIVEN

        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.ownerAccount).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
