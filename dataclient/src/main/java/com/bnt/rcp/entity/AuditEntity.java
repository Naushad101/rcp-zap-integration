package com.bnt.rcp.entity;

import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;

import lombok.*;

import java.sql.Timestamp;

@Setter
@Getter
@MappedSuperclass
public class AuditEntity {

    @Column(name = "created_by")
    private Integer createdBy;

    @Column(name = "updated_by")
    private Integer updatedBy;

    @Column(name = "created_on")
    private Timestamp createdOn;

    @Column(name = "updated_on")
    private Timestamp updatedOn;
}
