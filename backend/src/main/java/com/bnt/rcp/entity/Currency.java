package com.bnt.rcp.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Setter
@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "currency")
public class Currency extends BaseEntity {

    @Column(name = "code")
    private String code;

    @Column(name = "iso_code")
    private String isoCode;
 
    @Column(name = "display_name")
    private String currencyName;

    @Column(name = "currency_minor_unit")
    private String currencyMinorUnit;

    @Column(name = "active")
    private Character active;

    @Column(name = "deleted", nullable = false)
    private Character deleted = '0';
    
}
