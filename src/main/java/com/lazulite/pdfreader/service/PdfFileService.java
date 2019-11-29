package com.lazulite.pdfreader.service;

import com.lazulite.pdfreader.domain.PdfFile;
import com.lazulite.pdfreader.repository.PdfFileRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * Service Implementation for managing {@link PdfFile}.
 */
@Service
@Transactional
public class PdfFileService {

    private final Logger log = LoggerFactory.getLogger(PdfFileService.class);

    private final PdfFileRepository pdfFileRepository;

    public PdfFileService(PdfFileRepository pdfFileRepository) {
        this.pdfFileRepository = pdfFileRepository;
    }

    /**
     * Save a pdfFile.
     *
     * @param pdfFile the entity to save.
     * @return the persisted entity.
     */
    public PdfFile save(PdfFile pdfFile) {
        log.debug("Request to save PdfFile : {}", pdfFile);
        return pdfFileRepository.save(pdfFile);
    }

    /**
     * Get all the pdfFiles.
     *
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<PdfFile> findAll() {
        log.debug("Request to get all PdfFiles");
        return pdfFileRepository.findAll();
    }


    /**
     * Get one pdfFile by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<PdfFile> findOne(Long id) {
        log.debug("Request to get PdfFile : {}", id);
        return pdfFileRepository.findById(id);
    }

    /**
     * Delete the pdfFile by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete PdfFile : {}", id);
        pdfFileRepository.deleteById(id);
    }
}
