/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { MyapphipTestModule } from '../../../test.module';
import { PrimeACDetailComponent } from 'app/entities/prime-ac/prime-ac-detail.component';
import { PrimeAC } from 'app/shared/model/prime-ac.model';

describe('Component Tests', () => {
  describe('PrimeAC Management Detail Component', () => {
    let comp: PrimeACDetailComponent;
    let fixture: ComponentFixture<PrimeACDetailComponent>;
    const route = ({ data: of({ primeAC: new PrimeAC(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [MyapphipTestModule],
        declarations: [PrimeACDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(PrimeACDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(PrimeACDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should call load all on init', () => {
        // GIVEN

        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.primeAC).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
