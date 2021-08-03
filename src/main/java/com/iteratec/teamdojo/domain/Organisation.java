package com.iteratec.teamdojo.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.iteratec.teamdojo.domain.enumeration.ApplicationMode;
import java.io.Serializable;
import java.time.Instant;
import javax.persistence.*;
import javax.validation.constraints.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Organisation.
 */
@Entity
@Table(name = "organisation")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Organisation implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Size(min = 1, max = 50)
    @Column(name = "title", length = 50, nullable = false)
    private String title;

    @Size(max = 4096)
    @Column(name = "description", length = 4096)
    private String description;

    @Column(name = "level_up_score")
    private Integer levelUpScore;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "application_mode", nullable = false)
    private ApplicationMode applicationMode;

    @Min(value = 0)
    @Column(name = "count_of_confirmations")
    private Integer countOfConfirmations;

    @NotNull
    @Column(name = "created_at", nullable = false)
    private Instant createdAt;

    @NotNull
    @Column(name = "updated_at", nullable = false)
    private Instant updatedAt;

    @ManyToOne
    @JsonIgnoreProperties(value = { "parent" }, allowSetters = true)
    private Organisation parent;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Organisation id(Long id) {
        this.id = id;
        return this;
    }

    public String getTitle() {
        return this.title;
    }

    public Organisation title(String title) {
        this.title = title;
        return this;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return this.description;
    }

    public Organisation description(String description) {
        this.description = description;
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getLevelUpScore() {
        return this.levelUpScore;
    }

    public Organisation levelUpScore(Integer levelUpScore) {
        this.levelUpScore = levelUpScore;
        return this;
    }

    public void setLevelUpScore(Integer levelUpScore) {
        this.levelUpScore = levelUpScore;
    }

    public ApplicationMode getApplicationMode() {
        return this.applicationMode;
    }

    public Organisation applicationMode(ApplicationMode applicationMode) {
        this.applicationMode = applicationMode;
        return this;
    }

    public void setApplicationMode(ApplicationMode applicationMode) {
        this.applicationMode = applicationMode;
    }

    public Integer getCountOfConfirmations() {
        return this.countOfConfirmations;
    }

    public Organisation countOfConfirmations(Integer countOfConfirmations) {
        this.countOfConfirmations = countOfConfirmations;
        return this;
    }

    public void setCountOfConfirmations(Integer countOfConfirmations) {
        this.countOfConfirmations = countOfConfirmations;
    }

    public Instant getCreatedAt() {
        return this.createdAt;
    }

    public Organisation createdAt(Instant createdAt) {
        this.createdAt = createdAt;
        return this;
    }

    public void setCreatedAt(Instant createdAt) {
        this.createdAt = createdAt;
    }

    public Instant getUpdatedAt() {
        return this.updatedAt;
    }

    public Organisation updatedAt(Instant updatedAt) {
        this.updatedAt = updatedAt;
        return this;
    }

    public void setUpdatedAt(Instant updatedAt) {
        this.updatedAt = updatedAt;
    }

    public Organisation getParent() {
        return this.parent;
    }

    public Organisation parent(Organisation organisation) {
        this.setParent(organisation);
        return this;
    }

    public void setParent(Organisation organisation) {
        this.parent = organisation;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Organisation)) {
            return false;
        }
        return id != null && id.equals(((Organisation) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Organisation{" +
            "id=" + getId() +
            ", title='" + getTitle() + "'" +
            ", description='" + getDescription() + "'" +
            ", levelUpScore=" + getLevelUpScore() +
            ", applicationMode='" + getApplicationMode() + "'" +
            ", countOfConfirmations=" + getCountOfConfirmations() +
            ", createdAt='" + getCreatedAt() + "'" +
            ", updatedAt='" + getUpdatedAt() + "'" +
            "}";
    }
}
