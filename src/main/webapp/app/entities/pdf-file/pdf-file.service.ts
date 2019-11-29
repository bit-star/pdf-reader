import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IPdfFile } from 'app/shared/model/pdf-file.model';

type EntityResponseType = HttpResponse<IPdfFile>;
type EntityArrayResponseType = HttpResponse<IPdfFile[]>;

@Injectable({ providedIn: 'root' })
export class PdfFileService {
  public resourceUrl = SERVER_API_URL + 'api/pdf-files';

  constructor(protected http: HttpClient) {}

  create(pdfFile: IPdfFile): Observable<EntityResponseType> {
    return this.http.post<IPdfFile>(this.resourceUrl, pdfFile, { observe: 'response' });
  }

  update(pdfFile: IPdfFile): Observable<EntityResponseType> {
    return this.http.put<IPdfFile>(this.resourceUrl, pdfFile, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IPdfFile>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IPdfFile[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<any>> {
    return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
