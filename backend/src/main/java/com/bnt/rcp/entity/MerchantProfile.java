package com.bnt.rcp.entity;

import jakarta.persistence.*;
import lombok.*;

import com.querydsl.core.annotations.QueryInit;

@Entity
@Setter
@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "merchant_profile")
public class MerchantProfile extends AuditEntity {

    @Id
    private Integer id;

    @Column(name = "partial_auth")
    private String partialAuth;

    @Column(name = "velocity")
    private String velocity;

    @Column(name = "merchant_category_code")
    private String categoryCode;

    @Column(name = "services")
    private String services;

    @Transient
    private String additionalServices;

    @OneToOne(fetch = FetchType.LAZY)
    @MapsId
    @JoinColumn(name = "id")
    @QueryInit("*")
    private Merchant merchant;
}
