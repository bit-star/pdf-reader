package com.lazulite.pdfreader.repository;
import com.lazulite.pdfreader.domain.PdfFile;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Spring Data  repository for the PdfFile entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PdfFileRepository extends JpaRepository<PdfFile, Long> {

    @Query("select pdfFile from PdfFile pdfFile where pdfFile.user.login = ?#{principal.username}")
    List<PdfFile> findByUserIsCurrentUser();

}
