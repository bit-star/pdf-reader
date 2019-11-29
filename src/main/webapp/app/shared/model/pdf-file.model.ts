export interface IPdfFile {
  id?: number;
  name?: string;
  url?: string;
  readerUrl?: string;
}

export class PdfFile implements IPdfFile {
  constructor(public id?: number, public name?: string, public url?: string, public readerUrl?: string) {}
}
