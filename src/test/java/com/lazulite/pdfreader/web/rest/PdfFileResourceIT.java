package com.lazulite.pdfreader.web.rest;

import com.lazulite.pdfreader.PdfReaderApp;
import com.lazulite.pdfreader.domain.PdfFile;
import com.lazulite.pdfreader.repository.PdfFileRepository;
import com.lazulite.pdfreader.service.PdfFileService;
import com.lazulite.pdfreader.web.rest.errors.ExceptionTranslator;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Base64Utils;
import org.springframework.validation.Validator;

import javax.persistence.EntityManager;
import java.util.List;

import static com.lazulite.pdfreader.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link PdfFileResource} REST controller.
 */
@SpringBootTest(classes = PdfReaderApp.class)
public class PdfFileResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final byte[] DEFAULT_FILE = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_FILE = TestUtil.createByteArray(1, "1");
    private static final String DEFAULT_FILE_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_FILE_CONTENT_TYPE = "image/png";

    private static final String DEFAULT_READER_URL = "AAAAAAAAAA";
    private static final String UPDATED_READER_URL = "BBBBBBBBBB";

    @Autowired
    private PdfFileRepository pdfFileRepository;

    @Autowired
    private PdfFileService pdfFileService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    @Autowired
    private Validator validator;

    private MockMvc restPdfFileMockMvc;

    private PdfFile pdfFile;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final PdfFileResource pdfFileResource = new PdfFileResource(pdfFileService);
        this.restPdfFileMockMvc = MockMvcBuilders.standaloneSetup(pdfFileResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter)
            .setValidator(validator).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static PdfFile createEntity(EntityManager em) {
        PdfFile pdfFile = new PdfFile()
            .name(DEFAULT_NAME)
            .file(DEFAULT_FILE)
            .fileContentType(DEFAULT_FILE_CONTENT_TYPE)
            .readerUrl(DEFAULT_READER_URL);
        return pdfFile;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static PdfFile createUpdatedEntity(EntityManager em) {
        PdfFile pdfFile = new PdfFile()
            .name(UPDATED_NAME)
            .file(UPDATED_FILE)
            .fileContentType(UPDATED_FILE_CONTENT_TYPE)
            .readerUrl(UPDATED_READER_URL);
        return pdfFile;
    }

    @BeforeEach
    public void initTest() {
        pdfFile = createEntity(em);
    }

    @Test
    @Transactional
    public void createPdfFile() throws Exception {
        int databaseSizeBeforeCreate = pdfFileRepository.findAll().size();

        // Create the PdfFile
        restPdfFileMockMvc.perform(post("/api/pdf-files")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(pdfFile)))
            .andExpect(status().isCreated());

        // Validate the PdfFile in the database
        List<PdfFile> pdfFileList = pdfFileRepository.findAll();
        assertThat(pdfFileList).hasSize(databaseSizeBeforeCreate + 1);
        PdfFile testPdfFile = pdfFileList.get(pdfFileList.size() - 1);
        assertThat(testPdfFile.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testPdfFile.getFile()).isEqualTo(DEFAULT_FILE);
        assertThat(testPdfFile.getFileContentType()).isEqualTo(DEFAULT_FILE_CONTENT_TYPE);
        assertThat(testPdfFile.getReaderUrl()).isEqualTo(DEFAULT_READER_URL);
    }

    @Test
    @Transactional
    public void createPdfFileWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = pdfFileRepository.findAll().size();

        // Create the PdfFile with an existing ID
        pdfFile.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restPdfFileMockMvc.perform(post("/api/pdf-files")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(pdfFile)))
            .andExpect(status().isBadRequest());

        // Validate the PdfFile in the database
        List<PdfFile> pdfFileList = pdfFileRepository.findAll();
        assertThat(pdfFileList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllPdfFiles() throws Exception {
        // Initialize the database
        pdfFileRepository.saveAndFlush(pdfFile);

        // Get all the pdfFileList
        restPdfFileMockMvc.perform(get("/api/pdf-files?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(pdfFile.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].fileContentType").value(hasItem(DEFAULT_FILE_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].file").value(hasItem(Base64Utils.encodeToString(DEFAULT_FILE))))
            .andExpect(jsonPath("$.[*].readerUrl").value(hasItem(DEFAULT_READER_URL)));
    }
    
    @Test
    @Transactional
    public void getPdfFile() throws Exception {
        // Initialize the database
        pdfFileRepository.saveAndFlush(pdfFile);

        // Get the pdfFile
        restPdfFileMockMvc.perform(get("/api/pdf-files/{id}", pdfFile.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(pdfFile.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.fileContentType").value(DEFAULT_FILE_CONTENT_TYPE))
            .andExpect(jsonPath("$.file").value(Base64Utils.encodeToString(DEFAULT_FILE)))
            .andExpect(jsonPath("$.readerUrl").value(DEFAULT_READER_URL));
    }

    @Test
    @Transactional
    public void getNonExistingPdfFile() throws Exception {
        // Get the pdfFile
        restPdfFileMockMvc.perform(get("/api/pdf-files/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updatePdfFile() throws Exception {
        // Initialize the database
        pdfFileService.save(pdfFile);

        int databaseSizeBeforeUpdate = pdfFileRepository.findAll().size();

        // Update the pdfFile
        PdfFile updatedPdfFile = pdfFileRepository.findById(pdfFile.getId()).get();
        // Disconnect from session so that the updates on updatedPdfFile are not directly saved in db
        em.detach(updatedPdfFile);
        updatedPdfFile
            .name(UPDATED_NAME)
            .file(UPDATED_FILE)
            .fileContentType(UPDATED_FILE_CONTENT_TYPE)
            .readerUrl(UPDATED_READER_URL);

        restPdfFileMockMvc.perform(put("/api/pdf-files")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedPdfFile)))
            .andExpect(status().isOk());

        // Validate the PdfFile in the database
        List<PdfFile> pdfFileList = pdfFileRepository.findAll();
        assertThat(pdfFileList).hasSize(databaseSizeBeforeUpdate);
        PdfFile testPdfFile = pdfFileList.get(pdfFileList.size() - 1);
        assertThat(testPdfFile.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testPdfFile.getFile()).isEqualTo(UPDATED_FILE);
        assertThat(testPdfFile.getFileContentType()).isEqualTo(UPDATED_FILE_CONTENT_TYPE);
        assertThat(testPdfFile.getReaderUrl()).isEqualTo(UPDATED_READER_URL);
    }

    @Test
    @Transactional
    public void updateNonExistingPdfFile() throws Exception {
        int databaseSizeBeforeUpdate = pdfFileRepository.findAll().size();

        // Create the PdfFile

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPdfFileMockMvc.perform(put("/api/pdf-files")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(pdfFile)))
            .andExpect(status().isBadRequest());

        // Validate the PdfFile in the database
        List<PdfFile> pdfFileList = pdfFileRepository.findAll();
        assertThat(pdfFileList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deletePdfFile() throws Exception {
        // Initialize the database
        pdfFileService.save(pdfFile);

        int databaseSizeBeforeDelete = pdfFileRepository.findAll().size();

        // Delete the pdfFile
        restPdfFileMockMvc.perform(delete("/api/pdf-files/{id}", pdfFile.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<PdfFile> pdfFileList = pdfFileRepository.findAll();
        assertThat(pdfFileList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
