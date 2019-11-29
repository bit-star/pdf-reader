export interface IPdfFile {
  id?: number;
  name?: string;
  fileContentType?: string;
  file?: any;
  readerUrl?: string;
}

export class PdfFile implements IPdfFile {
  constructor(public id?: number, public name?: string, public fileContentType?: string, public file?: any, public readerUrl?: string) {}
}
