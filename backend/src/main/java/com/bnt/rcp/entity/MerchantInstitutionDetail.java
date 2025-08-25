package com.bnt.rcp.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Setter
@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "merchant_institution_detail")
public class MerchantInstitutionDetail extends AuditEntity {

    @Id
    private Integer id;

    @Column(name = "address_1")
    private String address1;

    @Column(name = "address_2")
    private String address2;

    @Column(name = "city")
    private String city;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "state")
    private CountryState countryState;

    @Column(name = "postal_code")
    private String zip;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "country")
    private Country country;

    @Column(name = "phone")
    private String phone;

    @Column(name = "fax")
    private String fax;

    @Column(name = "website")
    private String website;

    @Column(name = "official_email")
    private String email;

    @MapsId
    @OneToOne
    @JoinColumn(name = "id")
    private MerchantInstitution merchantInstitution;
    
}
