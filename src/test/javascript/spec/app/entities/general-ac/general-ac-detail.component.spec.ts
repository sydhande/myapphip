/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { MyapphipTestModule } from '../../../test.module';
import { GeneralACDetailComponent } from 'app/entities/general-ac/general-ac-detail.component';
import { GeneralAC } from 'app/shared/model/general-ac.model';

describe('Component Tests', () => {
  describe('GeneralAC Management Detail Component', () => {
    let comp: GeneralACDetailComponent;
    let fixture: ComponentFixture<GeneralACDetailComponent>;
    const route = ({ data: of({ generalAC: new GeneralAC(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [MyapphipTestModule],
        declarations: [GeneralACDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(GeneralACDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(GeneralACDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should call load all on init', () => {
        // GIVEN

        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.generalAC).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
