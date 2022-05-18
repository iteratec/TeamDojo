package com.iteratec.teamdojo.service.dto;

import com.iteratec.teamdojo.GeneratedByJHipster;
import java.io.Serializable;
import java.time.Instant;
import java.util.Objects;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link com.iteratec.teamdojo.domain.Dimension} entity.
 */
@GeneratedByJHipster
// ### MODIFICATION-START ###
public class DimensionDTO implements Serializable, com.iteratec.teamdojo.service.dto.custom.AuditableData {

    // ### MODIFICATION-END ###

    private Long id;

    @NotNull
    @Size(min = 1, max = 50)
    private String titleEN;

    @Size(min = 1, max = 50)
    private String titleDE;

    @Size(max = 4096)
    private String descriptionEN;

    @Size(max = 4096)
    private String descriptionDE;

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

    public String getTitleEN() {
        return titleEN;
    }

    public void setTitleEN(String titleEN) {
        this.titleEN = titleEN;
    }

    public String getTitleDE() {
        return titleDE;
    }

    public void setTitleDE(String titleDE) {
        this.titleDE = titleDE;
    }

    public String getDescriptionEN() {
        return descriptionEN;
    }

    public void setDescriptionEN(String descriptionEN) {
        this.descriptionEN = descriptionEN;
    }

    public String getDescriptionDE() {
        return descriptionDE;
    }

    public void setDescriptionDE(String descriptionDE) {
        this.descriptionDE = descriptionDE;
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
        if (!(o instanceof DimensionDTO)) {
            return false;
        }

        DimensionDTO dimensionDTO = (DimensionDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, dimensionDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "DimensionDTO{" +
            "id=" + getId() +
            ", titleEN='" + getTitleEN() + "'" +
            ", titleDE='" + getTitleDE() + "'" +
            ", descriptionEN='" + getDescriptionEN() + "'" +
            ", descriptionDE='" + getDescriptionDE() + "'" +
            ", createdAt='" + getCreatedAt() + "'" +
            ", updatedAt='" + getUpdatedAt() + "'" +
            "}";
    }
}
