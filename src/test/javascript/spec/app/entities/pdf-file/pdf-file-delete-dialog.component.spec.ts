import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { PdfReaderTestModule } from '../../../test.module';
import { PdfFileDeleteDialogComponent } from 'app/entities/pdf-file/pdf-file-delete-dialog.component';
import { PdfFileService } from 'app/entities/pdf-file/pdf-file.service';

describe('Component Tests', () => {
  describe('PdfFile Management Delete Component', () => {
    let comp: PdfFileDeleteDialogComponent;
    let fixture: ComponentFixture<PdfFileDeleteDialogComponent>;
    let service: PdfFileService;
    let mockEventManager: any;
    let mockActiveModal: any;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [PdfReaderTestModule],
        declarations: [PdfFileDeleteDialogComponent]
      })
        .overrideTemplate(PdfFileDeleteDialogComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(PdfFileDeleteDialogComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(PdfFileService);
      mockEventManager = fixture.debugElement.injector.get(JhiEventManager);
      mockActiveModal = fixture.debugElement.injector.get(NgbActiveModal);
    });

    describe('confirmDelete', () => {
      it('Should call delete service on confirmDelete', inject(
        [],
        fakeAsync(() => {
          // GIVEN
          spyOn(service, 'delete').and.returnValue(of({}));

          // WHEN
          comp.confirmDelete(123);
          tick();

          // THEN
          expect(service.delete).toHaveBeenCalledWith(123);
          expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
          expect(mockEventManager.broadcastSpy).toHaveBeenCalled();
        })
      ));
    });
  });
});
