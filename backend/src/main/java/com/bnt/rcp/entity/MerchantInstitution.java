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
@Table(name = "merchant_institution")
public class MerchantInstitution extends BaseEntity {

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "institution_id")
    private Institution institution;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "acquirer_id")
    private AcquirerIdConfig acquirer;

    @Transient
    private Integer totalMerchant;

    @Transient
    private Integer totalLocation;

    @Transient
    private Integer totalDevice;

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

    @Column(name = "deleted")
    private Character deleted;

    @Column(name = "locked")
    private Character locked;

    @OneToOne(cascade = CascadeType.ALL , mappedBy = "merchantInstitution",fetch = FetchType.EAGER)
    @JoinColumn(name = "id", insertable = true, updatable = true)
    private MerchantInstitutionDetail merchantInstitutionDetail;

}
