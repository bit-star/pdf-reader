import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { PdfReaderTestModule } from '../../../test.module';
import { PdfFileDetailComponent } from 'app/entities/pdf-file/pdf-file-detail.component';
import { PdfFile } from 'app/shared/model/pdf-file.model';

describe('Component Tests', () => {
  describe('PdfFile Management Detail Component', () => {
    let comp: PdfFileDetailComponent;
    let fixture: ComponentFixture<PdfFileDetailComponent>;
    const route = ({ data: of({ pdfFile: new PdfFile(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [PdfReaderTestModule],
        declarations: [PdfFileDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(PdfFileDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(PdfFileDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should call load all on init', () => {
        // GIVEN

        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.pdfFile).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
