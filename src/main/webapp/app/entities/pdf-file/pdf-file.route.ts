import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { Observable, of } from 'rxjs';
import { map } from 'rxjs/operators';
import { PdfFile } from 'app/shared/model/pdf-file.model';
import { PdfFileService } from './pdf-file.service';
import { PdfFileComponent } from './pdf-file.component';
import { PdfFileDetailComponent } from './pdf-file-detail.component';
import { PdfFileUpdateComponent } from './pdf-file-update.component';
import { IPdfFile } from 'app/shared/model/pdf-file.model';

@Injectable({ providedIn: 'root' })
export class PdfFileResolve implements Resolve<IPdfFile> {
  constructor(private service: PdfFileService) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IPdfFile> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(map((pdfFile: HttpResponse<PdfFile>) => pdfFile.body));
    }
    return of(new PdfFile());
  }
}

export const pdfFileRoute: Routes = [
  {
    path: '',
    component: PdfFileComponent,
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'pdfReaderApp.pdfFile.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: PdfFileDetailComponent,
    resolve: {
      pdfFile: PdfFileResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'pdfReaderApp.pdfFile.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: PdfFileUpdateComponent,
    resolve: {
      pdfFile: PdfFileResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'pdfReaderApp.pdfFile.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: PdfFileUpdateComponent,
    resolve: {
      pdfFile: PdfFileResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'pdfReaderApp.pdfFile.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];
