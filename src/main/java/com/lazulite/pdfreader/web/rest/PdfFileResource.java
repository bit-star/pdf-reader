package com.lazulite.pdfreader.web.rest;

import com.lazulite.pdfreader.domain.PdfFile;
import com.lazulite.pdfreader.service.PdfFileService;
import com.lazulite.pdfreader.web.rest.errors.BadRequestAlertException;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link com.lazulite.pdfreader.domain.PdfFile}.
 */
@RestController
@RequestMapping("/api")
public class PdfFileResource {

    private final Logger log = LoggerFactory.getLogger(PdfFileResource.class);

    private static final String ENTITY_NAME = "pdfFile";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final PdfFileService pdfFileService;

    public PdfFileResource(PdfFileService pdfFileService) {
        this.pdfFileService = pdfFileService;
    }

    /**
     * {@code POST  /pdf-files} : Create a new pdfFile.
     *
     * @param pdfFile the pdfFile to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new pdfFile, or with status {@code 400 (Bad Request)} if the pdfFile has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/pdf-files")
    public ResponseEntity<PdfFile> createPdfFile(@RequestBody PdfFile pdfFile) throws URISyntaxException {
        log.debug("REST request to save PdfFile : {}", pdfFile);
        if (pdfFile.getId() != null) {
            throw new BadRequestAlertException("A new pdfFile cannot already have an ID", ENTITY_NAME, "idexists");
        }
        PdfFile result = pdfFileService.save(pdfFile);
        return ResponseEntity.created(new URI("/api/pdf-files/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /pdf-files} : Updates an existing pdfFile.
     *
     * @param pdfFile the pdfFile to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated pdfFile,
     * or with status {@code 400 (Bad Request)} if the pdfFile is not valid,
     * or with status {@code 500 (Internal Server Error)} if the pdfFile couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/pdf-files")
    public ResponseEntity<PdfFile> updatePdfFile(@RequestBody PdfFile pdfFile) throws URISyntaxException {
        log.debug("REST request to update PdfFile : {}", pdfFile);
        if (pdfFile.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        PdfFile result = pdfFileService.save(pdfFile);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, pdfFile.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /pdf-files} : get all the pdfFiles.
     *

     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of pdfFiles in body.
     */
    @GetMapping("/pdf-files")
    public List<PdfFile> getAllPdfFiles() {
        log.debug("REST request to get all PdfFiles");
        return pdfFileService.findAll();
    }

    /**
     * {@code GET  /pdf-files/:id} : get the "id" pdfFile.
     *
     * @param id the id of the pdfFile to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the pdfFile, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/pdf-files/{id}")
    public ResponseEntity<PdfFile> getPdfFile(@PathVariable Long id) {
        log.debug("REST request to get PdfFile : {}", id);
        Optional<PdfFile> pdfFile = pdfFileService.findOne(id);
        return ResponseUtil.wrapOrNotFound(pdfFile);
    }

    /**
     * {@code DELETE  /pdf-files/:id} : delete the "id" pdfFile.
     *
     * @param id the id of the pdfFile to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/pdf-files/{id}")
    public ResponseEntity<Void> deletePdfFile(@PathVariable Long id) {
        log.debug("REST request to delete PdfFile : {}", id);
        pdfFileService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
