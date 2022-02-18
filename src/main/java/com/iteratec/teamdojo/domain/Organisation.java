package com.iteratec.teamdojo.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
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
    @Column(name = "id")
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
        return this.id;
    }

    public Organisation id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return this.title;
    }

    public Organisation title(String title) {
        this.setTitle(title);
        return this;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return this.description;
    }

    public Organisation description(String description) {
        this.setDescription(description);
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getLevelUpScore() {
        return this.levelUpScore;
    }

    public Organisation levelUpScore(Integer levelUpScore) {
        this.setLevelUpScore(levelUpScore);
        return this;
    }

    public void setLevelUpScore(Integer levelUpScore) {
        this.levelUpScore = levelUpScore;
    }

    public Integer getCountOfConfirmations() {
        return this.countOfConfirmations;
    }

    public Organisation countOfConfirmations(Integer countOfConfirmations) {
        this.setCountOfConfirmations(countOfConfirmations);
        return this;
    }

    public void setCountOfConfirmations(Integer countOfConfirmations) {
        this.countOfConfirmations = countOfConfirmations;
    }

    public Instant getCreatedAt() {
        return this.createdAt;
    }

    public Organisation createdAt(Instant createdAt) {
        this.setCreatedAt(createdAt);
        return this;
    }

    public void setCreatedAt(Instant createdAt) {
        this.createdAt = createdAt;
    }

    public Instant getUpdatedAt() {
        return this.updatedAt;
    }

    public Organisation updatedAt(Instant updatedAt) {
        this.setUpdatedAt(updatedAt);
        return this;
    }

    public void setUpdatedAt(Instant updatedAt) {
        this.updatedAt = updatedAt;
    }

    public Organisation getParent() {
        return this.parent;
    }

    public void setParent(Organisation organisation) {
        this.parent = organisation;
    }

    public Organisation parent(Organisation organisation) {
        this.setParent(organisation);
        return this;
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
            ", countOfConfirmations=" + getCountOfConfirmations() +
            ", createdAt='" + getCreatedAt() + "'" +
            ", updatedAt='" + getUpdatedAt() + "'" +
            "}";
    }
}
