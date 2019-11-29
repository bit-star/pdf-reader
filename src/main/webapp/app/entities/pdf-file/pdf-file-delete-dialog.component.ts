import { Component } from '@angular/core';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IPdfFile } from 'app/shared/model/pdf-file.model';
import { PdfFileService } from './pdf-file.service';

@Component({
  templateUrl: './pdf-file-delete-dialog.component.html'
})
export class PdfFileDeleteDialogComponent {
  pdfFile: IPdfFile;

  constructor(protected pdfFileService: PdfFileService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.pdfFileService.delete(id).subscribe(() => {
      this.eventManager.broadcast({
        name: 'pdfFileListModification',
        content: 'Deleted an pdfFile'
      });
      this.activeModal.dismiss(true);
    });
  }
}
