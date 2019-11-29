import { ComponentFixture, TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { PdfReaderTestModule } from '../../../test.module';
import { PdfFileComponent } from 'app/entities/pdf-file/pdf-file.component';
import { PdfFileService } from 'app/entities/pdf-file/pdf-file.service';
import { PdfFile } from 'app/shared/model/pdf-file.model';

describe('Component Tests', () => {
  describe('PdfFile Management Component', () => {
    let comp: PdfFileComponent;
    let fixture: ComponentFixture<PdfFileComponent>;
    let service: PdfFileService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [PdfReaderTestModule],
        declarations: [PdfFileComponent],
        providers: []
      })
        .overrideTemplate(PdfFileComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(PdfFileComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(PdfFileService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new PdfFile(123)],
            headers
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.pdfFiles[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
