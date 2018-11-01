/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { Observable, of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { MyapphipTestModule } from '../../../test.module';
import { PrimeACComponent } from 'app/entities/prime-ac/prime-ac.component';
import { PrimeACService } from 'app/entities/prime-ac/prime-ac.service';
import { PrimeAC } from 'app/shared/model/prime-ac.model';

describe('Component Tests', () => {
  describe('PrimeAC Management Component', () => {
    let comp: PrimeACComponent;
    let fixture: ComponentFixture<PrimeACComponent>;
    let service: PrimeACService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [MyapphipTestModule],
        declarations: [PrimeACComponent],
        providers: []
      })
        .overrideTemplate(PrimeACComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(PrimeACComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(PrimeACService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new PrimeAC(123)],
            headers
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.primeACS[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
