package com.iteratec.teamdojo.service.dto;

import java.io.Serializable;
import java.time.Instant;
import java.util.Objects;
import javax.persistence.Lob;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link com.iteratec.teamdojo.domain.Image} entity.
 */
// ### MODIFICATION-START ###
public class ImageDTO implements Serializable, com.iteratec.teamdojo.service.dto.custom.AuditableData {

    // ### MODIFICATION-END ###
    private Long id;

    @NotNull
    @Size(min = 1, max = 50)
    private String title;

    @Lob
    private byte[] small;

    private String smallContentType;

    @Lob
    private byte[] medium;

    private String mediumContentType;

    @Lob
    private byte[] large;

    private String largeContentType;

    @Size(max = 32)
    private String hash;

    // ### MODIFICATION-START ###
    private Instant createdAt;
    // ### MODIFICATION-END ###

    // ### MODIFICATION-START ###
    private Instant updatedAt;

    // ### MODIFICATION-END ###

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public byte[] getSmall() {
        return small;
    }

    public void setSmall(byte[] small) {
        this.small = small;
    }

    public String getSmallContentType() {
        return smallContentType;
    }

    public void setSmallContentType(String smallContentType) {
        this.smallContentType = smallContentType;
    }

    public byte[] getMedium() {
        return medium;
    }

    public void setMedium(byte[] medium) {
        this.medium = medium;
    }

    public String getMediumContentType() {
        return mediumContentType;
    }

    public void setMediumContentType(String mediumContentType) {
        this.mediumContentType = mediumContentType;
    }

    public byte[] getLarge() {
        return large;
    }

    public void setLarge(byte[] large) {
        this.large = large;
    }

    public String getLargeContentType() {
        return largeContentType;
    }

    public void setLargeContentType(String largeContentType) {
        this.largeContentType = largeContentType;
    }

    public String getHash() {
        return hash;
    }

    public void setHash(String hash) {
        this.hash = hash;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Instant createdAt) {
        this.createdAt = createdAt;
    }

    public Instant getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Instant updatedAt) {
        this.updatedAt = updatedAt;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ImageDTO)) {
            return false;
        }

        ImageDTO imageDTO = (ImageDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, imageDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ImageDTO{" +
            "id=" + getId() +
            ", title='" + getTitle() + "'" +
            ", small='" + getSmall() + "'" +
            ", medium='" + getMedium() + "'" +
            ", large='" + getLarge() + "'" +
            ", hash='" + getHash() + "'" +
            ", createdAt='" + getCreatedAt() + "'" +
            ", updatedAt='" + getUpdatedAt() + "'" +
            "}";
    }
}
