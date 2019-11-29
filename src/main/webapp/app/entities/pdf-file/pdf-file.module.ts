import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { PdfReaderSharedModule } from 'app/shared/shared.module';
import { PdfFileComponent } from './pdf-file.component';
import { PdfFileDetailComponent } from './pdf-file-detail.component';
import { PdfFileUpdateComponent } from './pdf-file-update.component';
import { PdfFileDeleteDialogComponent } from './pdf-file-delete-dialog.component';
import { pdfFileRoute } from './pdf-file.route';

@NgModule({
  imports: [PdfReaderSharedModule, RouterModule.forChild(pdfFileRoute)],
  declarations: [PdfFileComponent, PdfFileDetailComponent, PdfFileUpdateComponent, PdfFileDeleteDialogComponent],
  entryComponents: [PdfFileDeleteDialogComponent]
})
export class PdfReaderPdfFileModule {}
