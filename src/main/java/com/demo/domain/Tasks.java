package com.demo.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.Objects;
import java.time.Instant;

/**
 * A Tasks.
 */
@Entity
@Table(name = "tasks")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Tasks implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "user_id", nullable = true)
    private Long userId;

    @Column(name = "creared_date")
    private Instant crearedDate;

    @Column(name = "due_date")
    private Instant dueDate;

    @Column(name = "importance")
    private Boolean importance;

    @Column(name = "status")
    private Integer status;

    @Column(name = "noi_dung")
    private String noiDung;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserId() {
        return userId;
    }

    public Tasks userId(Long userId) {
        this.userId = userId;
        return this;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Instant getCrearedDate() {
        return crearedDate;
    }

    public Tasks crearedDate(Instant crearedDate) {
        this.crearedDate = crearedDate;
        return this;
    }

    public void setCrearedDate(Instant crearedDate) {
        this.crearedDate = crearedDate;
    }

    public Instant getDueDate() {
        return dueDate;
    }

    public Tasks dueDate(Instant dueDate) {
        this.dueDate = dueDate;
        return this;
    }

    public void setDueDate(Instant dueDate) {
        this.dueDate = dueDate;
    }

    public Boolean isImportance() {
        return importance;
    }

    public Tasks importance(Boolean importance) {
        this.importance = importance;
        return this;
    }

    public void setImportance(Boolean importance) {
        this.importance = importance;
    }

    public Integer getStatus() {
        return status;
    }

    public Tasks status(Integer status) {
        this.status = status;
        return this;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getNoiDung() {
        return noiDung;
    }

    public Tasks noiDung(String noiDung) {
        this.noiDung = noiDung;
        return this;
    }

    public void setNoiDung(String noiDung) {
        this.noiDung = noiDung;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Tasks)) {
            return false;
        }
        return id != null && id.equals(((Tasks) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Tasks{" +
            "id=" + getId() +
            ", userId=" + getUserId() +
            ", crearedDate='" + getCrearedDate() + "'" +
            ", dueDate='" + getDueDate() + "'" +
            ", importance='" + isImportance() + "'" +
            ", status=" + getStatus() +
            ", noiDung='" + getNoiDung() + "'" +
            "}";
    }
}
