/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { Observable, of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { MyapphipTestModule } from '../../../test.module';
import { AccountCatagoryComponent } from 'app/entities/account-catagory/account-catagory.component';
import { AccountCatagoryService } from 'app/entities/account-catagory/account-catagory.service';
import { AccountCatagory } from 'app/shared/model/account-catagory.model';

describe('Component Tests', () => {
  describe('AccountCatagory Management Component', () => {
    let comp: AccountCatagoryComponent;
    let fixture: ComponentFixture<AccountCatagoryComponent>;
    let service: AccountCatagoryService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [MyapphipTestModule],
        declarations: [AccountCatagoryComponent],
        providers: []
      })
        .overrideTemplate(AccountCatagoryComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(AccountCatagoryComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(AccountCatagoryService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new AccountCatagory(123)],
            headers
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.accountCatagories[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
