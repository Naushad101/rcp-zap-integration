package com.bnt.rcp.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Setter
@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "merchant_detail")
public class MerchantDetail extends AuditEntity {

    @Id
    private Integer id;

    @Column(name = "address_1")
    private String address1;

    @Column(name = "address_2")
    private String address2;

    @Column(name = "city")
    private String city;

    @Column(name = "postal_code")
    private String zip;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "state", insertable = true)
    private CountryState countryState;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "country")
    private Country country;

    @Column(name = "phone")
    private String phone;

    @Column(name = "fax")
    private String fax;

    @Column(name = "website")
    private String webSite;

    @Column(name = "official_email")
    private String email;

    @OneToOne(fetch = FetchType.LAZY)
    @MapsId
    @JoinColumn(name = "id")
    private Merchant merchant;

}
