package com.bnt.rcp.entity;

import jakarta.persistence.*;
import lombok.*;

import java.sql.Timestamp;

@Entity
@Setter
@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "merchant")
public class Merchant extends BaseEntity {

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "merchant_institution_id")
    private MerchantInstitution merchantInstitution;

    @Column(name = "code")
    private String code;

    @Column(name = "name")
    private String name;

    @Column(name = "description")
    private String description;

    @Column(name = "activate_on")
    private Timestamp activateOn;

    @Column(name = "expiry_on")
    private Timestamp expiryOn;

    @Column(name = "locked")
    private Character locked;

    @Column(name = "deleted")
    private Character deleted;

    @Column(name = "additional_attributes")
    private String additionalAttribute;

    @OneToOne(cascade = CascadeType.ALL, mappedBy = "merchant", orphanRemoval = true, fetch = FetchType.EAGER)
    @JoinColumn(name = "id", insertable = true, updatable = true)
    private MerchantDetail merchantDetail;

    @OneToOne(cascade = CascadeType.ALL, mappedBy = "merchant", fetch = FetchType.EAGER)
    @JoinColumn(name = "id", insertable = true, updatable = true)
    private MerchantProfile merchantProfile;

    @Column(name = "total_location")
    @Transient
    private Integer totalLocation;

    @Transient
    @Column(name = "total_device")
    private Integer totalDevice;

    @Column(name = "pos_safety_flag")
    private Character posSafetyFlag;

    @Transient
    @Column(name = "reversal_timeout")
    private String reversalTimeout;

}
