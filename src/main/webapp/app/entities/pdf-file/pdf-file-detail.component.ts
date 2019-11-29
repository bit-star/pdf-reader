import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { JhiDataUtils } from 'ng-jhipster';

import { IPdfFile } from 'app/shared/model/pdf-file.model';

@Component({
  selector: 'jhi-pdf-file-detail',
  templateUrl: './pdf-file-detail.component.html'
})
export class PdfFileDetailComponent implements OnInit {
  pdfFile: IPdfFile;

  constructor(protected dataUtils: JhiDataUtils, protected activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ pdfFile }) => {
      this.pdfFile = pdfFile;
    });
  }

  byteSize(field) {
    return this.dataUtils.byteSize(field);
  }

  openFile(contentType, field) {
    return this.dataUtils.openFile(contentType, field);
  }
  previousState() {
    window.history.back();
  }
}
