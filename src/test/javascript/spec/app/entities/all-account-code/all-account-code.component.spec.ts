/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { Observable, of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { MyapphipTestModule } from '../../../test.module';
import { AllAccountCodeComponent } from 'app/entities/all-account-code/all-account-code.component';
import { AllAccountCodeService } from 'app/entities/all-account-code/all-account-code.service';
import { AllAccountCode } from 'app/shared/model/all-account-code.model';

describe('Component Tests', () => {
  describe('AllAccountCode Management Component', () => {
    let comp: AllAccountCodeComponent;
    let fixture: ComponentFixture<AllAccountCodeComponent>;
    let service: AllAccountCodeService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [MyapphipTestModule],
        declarations: [AllAccountCodeComponent],
        providers: []
      })
        .overrideTemplate(AllAccountCodeComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(AllAccountCodeComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(AllAccountCodeService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new AllAccountCode(123)],
            headers
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.allAccountCodes[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
