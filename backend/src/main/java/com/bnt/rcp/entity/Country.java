package com.bnt.rcp.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Setter
@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "country")
public class Country extends BaseEntity {

    @Column(name = "code")
    private String code;

    @Column(name = "display_name")
    private String countryName;

    @OneToOne 
    @JoinColumn(name = "currency")
    private Currency currency;

    @Column(name = "iso_code")
    private String isoCode;

    @Column(name = "short_code")
    private String shortCode;

    @Column(name = "isd_code")
    private String isdCode;
    
    @Column(name = "active")
    private Character active;

    @Column(name = "deleted")
    private Character deleted = '0';

}
