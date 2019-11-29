import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IPdfFile } from 'app/shared/model/pdf-file.model';

@Component({
  selector: 'jhi-pdf-file-detail',
  templateUrl: './pdf-file-detail.component.html'
})
export class PdfFileDetailComponent implements OnInit {
  pdfFile: IPdfFile;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ pdfFile }) => {
      this.pdfFile = pdfFile;
    });
  }

  previousState() {
    window.history.back();
  }
}
