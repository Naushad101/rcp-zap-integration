package com.bnt.rcp.entity;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.*;

import java.sql.Timestamp;

@Setter
@Getter
@MappedSuperclass
@EntityListeners(AuditListener.class)
public class AuditEntity {

    @Column(name = "created_by")
    @CreatedBy
    private Integer createdBy;

    @Column(name = "updated_by")
    @LastModifiedBy
    private Integer updatedBy;

    @Column(name = "created_on")
    @CreatedDate
    private Timestamp createdOn;

    @Column(name = "updated_on")
    @LastModifiedDate
    private Timestamp updatedOn;
}
