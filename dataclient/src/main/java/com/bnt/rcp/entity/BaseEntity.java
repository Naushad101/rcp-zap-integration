package com.bnt.rcp.entity;

import jakarta.persistence.*;

import lombok.*;

@MappedSuperclass
@Setter
@Getter
public class BaseEntity extends AuditEntity {

    @Id
    private int id;

}
