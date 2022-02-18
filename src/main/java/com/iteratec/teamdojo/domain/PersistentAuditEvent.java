package com.iteratec.teamdojo.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;
import javax.validation.constraints.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A PersistentAuditEvent.
 */
@Entity
@Table(name = "persistent_audit_event")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class PersistentAuditEvent implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @NotNull
    @Size(max = 255)
    @Column(name = "principal", length = 255, nullable = false)
    private String principal;

    @Column(name = "audit_event_date")
    private Instant auditEventDate;

    @Size(max = 30)
    @Column(name = "audit_event_type", length = 30)
    private String auditEventType;

    @OneToMany(mappedBy = "event")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "event" }, allowSetters = true)
    private Set<PersistentAuditEventData> data = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public PersistentAuditEvent id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPrincipal() {
        return this.principal;
    }

    public PersistentAuditEvent principal(String principal) {
        this.setPrincipal(principal);
        return this;
    }

    public void setPrincipal(String principal) {
        this.principal = principal;
    }

    public Instant getAuditEventDate() {
        return this.auditEventDate;
    }

    public PersistentAuditEvent auditEventDate(Instant auditEventDate) {
        this.setAuditEventDate(auditEventDate);
        return this;
    }

    public void setAuditEventDate(Instant auditEventDate) {
        this.auditEventDate = auditEventDate;
    }

    public String getAuditEventType() {
        return this.auditEventType;
    }

    public PersistentAuditEvent auditEventType(String auditEventType) {
        this.setAuditEventType(auditEventType);
        return this;
    }

    public void setAuditEventType(String auditEventType) {
        this.auditEventType = auditEventType;
    }

    public Set<PersistentAuditEventData> getData() {
        return this.data;
    }

    public void setData(Set<PersistentAuditEventData> persistentAuditEventData) {
        if (this.data != null) {
            this.data.forEach(i -> i.setEvent(null));
        }
        if (persistentAuditEventData != null) {
            persistentAuditEventData.forEach(i -> i.setEvent(this));
        }
        this.data = persistentAuditEventData;
    }

    public PersistentAuditEvent data(Set<PersistentAuditEventData> persistentAuditEventData) {
        this.setData(persistentAuditEventData);
        return this;
    }

    public PersistentAuditEvent addData(PersistentAuditEventData persistentAuditEventData) {
        this.data.add(persistentAuditEventData);
        persistentAuditEventData.setEvent(this);
        return this;
    }

    public PersistentAuditEvent removeData(PersistentAuditEventData persistentAuditEventData) {
        this.data.remove(persistentAuditEventData);
        persistentAuditEventData.setEvent(null);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof PersistentAuditEvent)) {
            return false;
        }
        return id != null && id.equals(((PersistentAuditEvent) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "PersistentAuditEvent{" +
            "id=" + getId() +
            ", principal='" + getPrincipal() + "'" +
            ", auditEventDate='" + getAuditEventDate() + "'" +
            ", auditEventType='" + getAuditEventType() + "'" +
            "}";
    }
}
