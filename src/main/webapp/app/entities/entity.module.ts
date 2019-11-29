import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

@NgModule({
  imports: [
    RouterModule.forChild([
      {
        path: 'pdf-file',
        loadChildren: () => import('./pdf-file/pdf-file.module').then(m => m.PdfReaderPdfFileModule)
      }
      /* jhipster-needle-add-entity-route - JHipster will add entity modules routes here */
    ])
  ]
})
export class PdfReaderEntityModule {}
