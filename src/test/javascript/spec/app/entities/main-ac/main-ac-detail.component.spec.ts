/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { MyapphipTestModule } from '../../../test.module';
import { MainACDetailComponent } from 'app/entities/main-ac/main-ac-detail.component';
import { MainAC } from 'app/shared/model/main-ac.model';

describe('Component Tests', () => {
  describe('MainAC Management Detail Component', () => {
    let comp: MainACDetailComponent;
    let fixture: ComponentFixture<MainACDetailComponent>;
    const route = ({ data: of({ mainAC: new MainAC(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [MyapphipTestModule],
        declarations: [MainACDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(MainACDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(MainACDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should call load all on init', () => {
        // GIVEN

        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.mainAC).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
