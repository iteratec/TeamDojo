package com.iteratec.teamdojo.domain;

import java.io.Serializable;
import java.time.Instant;
import javax.persistence.*;
import javax.validation.constraints.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * This is an Image\n@author Robert Seedorff
 */
@Entity
@Table(name = "image")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Image implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Size(min = 1, max = 50)
    @Column(name = "title", length = 50, nullable = false)
    private String title;

    @Lob
    @Column(name = "small")
    private byte[] small;

    @Column(name = "small_content_type")
    private String smallContentType;

    @Lob
    @Column(name = "medium")
    private byte[] medium;

    @Column(name = "medium_content_type")
    private String mediumContentType;

    @Lob
    @Column(name = "large")
    private byte[] large;

    @Column(name = "large_content_type")
    private String largeContentType;

    @Size(max = 32)
    @Column(name = "hash", length = 32)
    private String hash;

    @NotNull
    @Column(name = "created_at", nullable = false)
    private Instant createdAt;

    @NotNull
    @Column(name = "updated_at", nullable = false)
    private Instant updatedAt;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Image id(Long id) {
        this.id = id;
        return this;
    }

    public String getTitle() {
        return this.title;
    }

    public Image title(String title) {
        this.title = title;
        return this;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public byte[] getSmall() {
        return this.small;
    }

    public Image small(byte[] small) {
        this.small = small;
        return this;
    }

    public void setSmall(byte[] small) {
        this.small = small;
    }

    public String getSmallContentType() {
        return this.smallContentType;
    }

    public Image smallContentType(String smallContentType) {
        this.smallContentType = smallContentType;
        return this;
    }

    public void setSmallContentType(String smallContentType) {
        this.smallContentType = smallContentType;
    }

    public byte[] getMedium() {
        return this.medium;
    }

    public Image medium(byte[] medium) {
        this.medium = medium;
        return this;
    }

    public void setMedium(byte[] medium) {
        this.medium = medium;
    }

    public String getMediumContentType() {
        return this.mediumContentType;
    }

    public Image mediumContentType(String mediumContentType) {
        this.mediumContentType = mediumContentType;
        return this;
    }

    public void setMediumContentType(String mediumContentType) {
        this.mediumContentType = mediumContentType;
    }

    public byte[] getLarge() {
        return this.large;
    }

    public Image large(byte[] large) {
        this.large = large;
        return this;
    }

    public void setLarge(byte[] large) {
        this.large = large;
    }

    public String getLargeContentType() {
        return this.largeContentType;
    }

    public Image largeContentType(String largeContentType) {
        this.largeContentType = largeContentType;
        return this;
    }

    public void setLargeContentType(String largeContentType) {
        this.largeContentType = largeContentType;
    }

    public String getHash() {
        return this.hash;
    }

    public Image hash(String hash) {
        this.hash = hash;
        return this;
    }

    public void setHash(String hash) {
        this.hash = hash;
    }

    public Instant getCreatedAt() {
        return this.createdAt;
    }

    public Image createdAt(Instant createdAt) {
        this.createdAt = createdAt;
        return this;
    }

    public void setCreatedAt(Instant createdAt) {
        this.createdAt = createdAt;
    }

    public Instant getUpdatedAt() {
        return this.updatedAt;
    }

    public Image updatedAt(Instant updatedAt) {
        this.updatedAt = updatedAt;
        return this;
    }

    public void setUpdatedAt(Instant updatedAt) {
        this.updatedAt = updatedAt;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Image)) {
            return false;
        }
        return id != null && id.equals(((Image) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Image{" +
            "id=" + getId() +
            ", title='" + getTitle() + "'" +
            ", small='" + getSmall() + "'" +
            ", smallContentType='" + getSmallContentType() + "'" +
            ", medium='" + getMedium() + "'" +
            ", mediumContentType='" + getMediumContentType() + "'" +
            ", large='" + getLarge() + "'" +
            ", largeContentType='" + getLargeContentType() + "'" +
            ", hash='" + getHash() + "'" +
            ", createdAt='" + getCreatedAt() + "'" +
            ", updatedAt='" + getUpdatedAt() + "'" +
            "}";
    }
}
