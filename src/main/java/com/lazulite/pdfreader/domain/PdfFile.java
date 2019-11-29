package com.lazulite.pdfreader.domain;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;

/**
 * A PdfFile.
 */
@Entity
@Table(name = "pdf_file")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class PdfFile implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "url")
    private String url;

    @Column(name = "reader_url")
    private String readerUrl;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public PdfFile name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public PdfFile url(String url) {
        this.url = url;
        return this;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getReaderUrl() {
        return readerUrl;
    }

    public PdfFile readerUrl(String readerUrl) {
        this.readerUrl = readerUrl;
        return this;
    }

    public void setReaderUrl(String readerUrl) {
        this.readerUrl = readerUrl;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof PdfFile)) {
            return false;
        }
        return id != null && id.equals(((PdfFile) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "PdfFile{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", url='" + getUrl() + "'" +
            ", readerUrl='" + getReaderUrl() + "'" +
            "}";
    }
}
