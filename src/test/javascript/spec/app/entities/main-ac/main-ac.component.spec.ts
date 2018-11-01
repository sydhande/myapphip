/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { Observable, of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { MyapphipTestModule } from '../../../test.module';
import { MainACComponent } from 'app/entities/main-ac/main-ac.component';
import { MainACService } from 'app/entities/main-ac/main-ac.service';
import { MainAC } from 'app/shared/model/main-ac.model';

describe('Component Tests', () => {
  describe('MainAC Management Component', () => {
    let comp: MainACComponent;
    let fixture: ComponentFixture<MainACComponent>;
    let service: MainACService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [MyapphipTestModule],
        declarations: [MainACComponent],
        providers: []
      })
        .overrideTemplate(MainACComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(MainACComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(MainACService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new MainAC(123)],
            headers
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.mainACS[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
