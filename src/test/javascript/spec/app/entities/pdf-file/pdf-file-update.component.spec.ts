import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { PdfReaderTestModule } from '../../../test.module';
import { PdfFileUpdateComponent } from 'app/entities/pdf-file/pdf-file-update.component';
import { PdfFileService } from 'app/entities/pdf-file/pdf-file.service';
import { PdfFile } from 'app/shared/model/pdf-file.model';

describe('Component Tests', () => {
  describe('PdfFile Management Update Component', () => {
    let comp: PdfFileUpdateComponent;
    let fixture: ComponentFixture<PdfFileUpdateComponent>;
    let service: PdfFileService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [PdfReaderTestModule],
        declarations: [PdfFileUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(PdfFileUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(PdfFileUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(PdfFileService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new PdfFile(123);
        spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
        comp.updateForm(entity);
        // WHEN
        comp.save();
        tick(); // simulate async

        // THEN
        expect(service.update).toHaveBeenCalledWith(entity);
        expect(comp.isSaving).toEqual(false);
      }));

      it('Should call create service on save for new entity', fakeAsync(() => {
        // GIVEN
        const entity = new PdfFile();
        spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
        comp.updateForm(entity);
        // WHEN
        comp.save();
        tick(); // simulate async

        // THEN
        expect(service.create).toHaveBeenCalledWith(entity);
        expect(comp.isSaving).toEqual(false);
      }));
    });
  });
});
