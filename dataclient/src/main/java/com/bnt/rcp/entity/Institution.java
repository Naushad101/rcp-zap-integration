package com.bnt.rcp.entity;

import java.math.BigInteger;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Setter
@Getter
@Table(name="institution" )
public class Institution extends RnBaseEntity {
 
    @Column(name= "name", nullable = true, length = 50)
    private String name;
 
    @Column(name= "code",nullable = true, length = 50)
    private String code;
 
    @Column(name = "description",nullable = true, length = 50)
    private String description;
 
    @Column(name="addressid", nullable = true, length = 50)
    private BigInteger address;
 
    @Column(name="userchange",nullable = true)
    private BigInteger userchange;
 
}
