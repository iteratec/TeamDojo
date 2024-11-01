package com.iteratec.teamdojo.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.iteratec.teamdojo.GeneratedByJHipster;
import java.io.Serializable;
import javax.persistence.*;
import javax.validation.constraints.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A PersistentAuditEventData.
 */
@Entity
@Table(name = "persistent_audit_event_data")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@GeneratedByJHipster
public class PersistentAuditEventData implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @NotNull
    @Size(max = 255)
    @Column(name = "name", length = 255, nullable = false)
    private String name;

    @NotNull
    @Size(max = 255)
    @Column(name = "value", length = 255, nullable = false)
    private String value;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties(value = { "data" }, allowSetters = true)
    private PersistentAuditEvent event;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public PersistentAuditEventData id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public PersistentAuditEventData name(String name) {
        this.setName(name);
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getValue() {
        return this.value;
    }

    public PersistentAuditEventData value(String value) {
        this.setValue(value);
        return this;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public PersistentAuditEvent getEvent() {
        return this.event;
    }

    public void setEvent(PersistentAuditEvent persistentAuditEvent) {
        this.event = persistentAuditEvent;
    }

    public PersistentAuditEventData event(PersistentAuditEvent persistentAuditEvent) {
        this.setEvent(persistentAuditEvent);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof PersistentAuditEventData)) {
            return false;
        }
        return id != null && id.equals(((PersistentAuditEventData) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "PersistentAuditEventData{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", value='" + getValue() + "'" +
            "}";
    }
}
