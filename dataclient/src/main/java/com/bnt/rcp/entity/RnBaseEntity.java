package com.bnt.rcp.entity;

import java.math.BigInteger;
import java.sql.Timestamp;

import jakarta.persistence.*;
import lombok.*;

@Setter
@Getter
@MappedSuperclass
public class RnBaseEntity {
 
    @Id
    @Column(unique = true,nullable = false)
    protected BigInteger id;
 
    @Column(nullable = true)
    private Short deleted;
 
    @Column(name="createdby",nullable = true,updatable = false)
    protected BigInteger createdby;
 
    @Column(name="createdon",nullable = true,updatable = false)
    protected Timestamp createdon;
 
    @Column(name="timestampchange", nullable = true)
    protected Timestamp timestampchange;
 
}
