package com.iteratec.teamdojo.service.dto;

import io.swagger.annotations.ApiModel;
import java.io.Serializable;
import java.time.Instant;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link com.iteratec.teamdojo.domain.Team} entity.
 */
@ApiModel(description = "This is an Team\n@author Robert Seedorff")
public class TeamDTO implements Serializable {

    private Long id;

    @NotNull
    @Size(min = 2, max = 50)
    private String title;

    @NotNull
    @Size(min = 2, max = 20)
    @Pattern(regexp = "^[a-zA-Z0-9_-]*$")
    private String shortTitle;

    @Size(max = 255)
    private String slogan;

    @Size(max = 255)
    private String contact;

    private Instant validUntil;

    @NotNull
    private Boolean pureTrainingTeam;

    @NotNull
    private Boolean official;

    @NotNull
    private Instant createdAt;

    @NotNull
    private Instant updatedAt;

    private ImageDTO image;

    private Set<DimensionDTO> participations = new HashSet<>();

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

    public String getShortTitle() {
        return shortTitle;
    }

    public void setShortTitle(String shortTitle) {
        this.shortTitle = shortTitle;
    }

    public String getSlogan() {
        return slogan;
    }

    public void setSlogan(String slogan) {
        this.slogan = slogan;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public Instant getValidUntil() {
        return validUntil;
    }

    public void setValidUntil(Instant validUntil) {
        this.validUntil = validUntil;
    }

    public Boolean getPureTrainingTeam() {
        return pureTrainingTeam;
    }

    public void setPureTrainingTeam(Boolean pureTrainingTeam) {
        this.pureTrainingTeam = pureTrainingTeam;
    }

    public Boolean getOfficial() {
        return official;
    }

    public void setOfficial(Boolean official) {
        this.official = official;
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

    public ImageDTO getImage() {
        return image;
    }

    public void setImage(ImageDTO image) {
        this.image = image;
    }

    public Set<DimensionDTO> getParticipations() {
        return participations;
    }

    public void setParticipations(Set<DimensionDTO> participations) {
        this.participations = participations;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof TeamDTO)) {
            return false;
        }

        TeamDTO teamDTO = (TeamDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, teamDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "TeamDTO{" +
            "id=" + getId() +
            ", title='" + getTitle() + "'" +
            ", shortTitle='" + getShortTitle() + "'" +
            ", slogan='" + getSlogan() + "'" +
            ", contact='" + getContact() + "'" +
            ", validUntil='" + getValidUntil() + "'" +
            ", pureTrainingTeam='" + getPureTrainingTeam() + "'" +
            ", official='" + getOfficial() + "'" +
            ", createdAt='" + getCreatedAt() + "'" +
            ", updatedAt='" + getUpdatedAt() + "'" +
            ", image=" + getImage() +
            ", participations=" + getParticipations() +
            "}";
    }
}
