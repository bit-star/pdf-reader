import { Component, OnInit } from '@angular/core';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { IPdfFile, PdfFile } from 'app/shared/model/pdf-file.model';
import { PdfFileService } from './pdf-file.service';

@Component({
  selector: 'jhi-pdf-file-update',
  templateUrl: './pdf-file-update.component.html'
})
export class PdfFileUpdateComponent implements OnInit {
  isSaving: boolean;

  editForm = this.fb.group({
    id: [],
    name: [],
    url: [],
    readerUrl: []
  });

  constructor(protected pdfFileService: PdfFileService, protected activatedRoute: ActivatedRoute, private fb: FormBuilder) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ pdfFile }) => {
      this.updateForm(pdfFile);
    });
  }

  updateForm(pdfFile: IPdfFile) {
    this.editForm.patchValue({
      id: pdfFile.id,
      name: pdfFile.name,
      url: pdfFile.url,
      readerUrl: pdfFile.readerUrl
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const pdfFile = this.createFromForm();
    if (pdfFile.id !== undefined) {
      this.subscribeToSaveResponse(this.pdfFileService.update(pdfFile));
    } else {
      this.subscribeToSaveResponse(this.pdfFileService.create(pdfFile));
    }
  }

  private createFromForm(): IPdfFile {
    return {
      ...new PdfFile(),
      id: this.editForm.get(['id']).value,
      name: this.editForm.get(['name']).value,
      url: this.editForm.get(['url']).value,
      readerUrl: this.editForm.get(['readerUrl']).value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IPdfFile>>) {
    result.subscribe(() => this.onSaveSuccess(), () => this.onSaveError());
  }

  protected onSaveSuccess() {
    this.isSaving = false;
    this.previousState();
  }

  protected onSaveError() {
    this.isSaving = false;
  }
}
