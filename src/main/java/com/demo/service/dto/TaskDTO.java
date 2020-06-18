package com.demo.service.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.Instant;

@JsonIgnoreProperties(ignoreUnknown = true)
public class TaskDTO implements Serializable, Cloneable{

    private Long id;

    private Instant crearedDate;

    private Instant dueDate;

    private Boolean importance;

    private Integer status;

    private String noiDung;

    public TaskDTO() {
    }

    public TaskDTO(Long id, Instant crearedDate, Instant dueDate, Boolean importance, Integer status, String noiDung) {
        this.id = id;
        this.crearedDate = crearedDate;
        this.dueDate = dueDate;
        this.importance = importance;
        this.status = status;
        this.noiDung = noiDung;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Instant getCrearedDate() {
        return crearedDate;
    }

    public void setCrearedDate(Instant crearedDate) {
        this.crearedDate = crearedDate;
    }

    public Instant getDueDate() {
        return dueDate;
    }

    public void setDueDate(Instant dueDate) {
        this.dueDate = dueDate;
    }

    public Boolean getImportance() {
        return importance;
    }

    public void setImportance(Boolean importance) {
        this.importance = importance;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getNoiDung() {
        return noiDung;
    }

    public void setNoiDung(String noiDung) {
        this.noiDung = noiDung;
    }
}
