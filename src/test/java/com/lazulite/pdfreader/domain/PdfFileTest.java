package com.lazulite.pdfreader.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.lazulite.pdfreader.web.rest.TestUtil;

public class PdfFileTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(PdfFile.class);
        PdfFile pdfFile1 = new PdfFile();
        pdfFile1.setId(1L);
        PdfFile pdfFile2 = new PdfFile();
        pdfFile2.setId(pdfFile1.getId());
        assertThat(pdfFile1).isEqualTo(pdfFile2);
        pdfFile2.setId(2L);
        assertThat(pdfFile1).isNotEqualTo(pdfFile2);
        pdfFile1.setId(null);
        assertThat(pdfFile1).isNotEqualTo(pdfFile2);
    }
}
