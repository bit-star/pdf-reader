import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager, JhiDataUtils } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IPdfFile } from 'app/shared/model/pdf-file.model';
import { PdfFileService } from './pdf-file.service';
import { PdfFileDeleteDialogComponent } from './pdf-file-delete-dialog.component';

@Component({
  selector: 'jhi-pdf-file',
  templateUrl: './pdf-file.component.html'
})
export class PdfFileComponent implements OnInit, OnDestroy {
  pdfFiles: IPdfFile[];
  eventSubscriber: Subscription;

  constructor(
    protected pdfFileService: PdfFileService,
    protected dataUtils: JhiDataUtils,
    protected eventManager: JhiEventManager,
    protected modalService: NgbModal
  ) {}

  loadAll() {
    this.pdfFileService.query().subscribe((res: HttpResponse<IPdfFile[]>) => {
      this.pdfFiles = res.body;
    });
  }

  ngOnInit() {
    this.loadAll();
    this.registerChangeInPdfFiles();
  }

  ngOnDestroy() {
    this.eventManager.destroy(this.eventSubscriber);
  }

  trackId(index: number, item: IPdfFile) {
    return item.id;
  }

  byteSize(field) {
    return this.dataUtils.byteSize(field);
  }

  openFile(contentType, field) {
    return this.dataUtils.openFile(contentType, field);
  }

  registerChangeInPdfFiles() {
    this.eventSubscriber = this.eventManager.subscribe('pdfFileListModification', () => this.loadAll());
  }

  delete(pdfFile: IPdfFile) {
    const modalRef = this.modalService.open(PdfFileDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.pdfFile = pdfFile;
  }
}
