package com.bnt.rcp.entity;

import java.math.BigInteger;
 
import jakarta.persistence.*;
import lombok.*;
 
@Entity
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "address")
public class Address extends RnBaseEntity {
 
    @Column(nullable = true, length = 50)
    private String address1;
 
    @Column(nullable = true, length = 50)
    private String address2;
 
    @Column(nullable = true, length = 50)
    private String city;
 
    @Column(nullable = true, length = 10)
    private String postcode;
 
    @Column(nullable = true, length = 50)
    private String phone;
 
    @Column(nullable = true, length = 50)
    private String email;
 
    @Column(nullable = true)
    private BigInteger userchange;
 
    @Column(name = "countryid", nullable = true)
    private BigInteger countryid;
 
    @Column(nullable = true)
    private float geolat;
 
    @Column(nullable = true)
    private float geolong;
 
}  
