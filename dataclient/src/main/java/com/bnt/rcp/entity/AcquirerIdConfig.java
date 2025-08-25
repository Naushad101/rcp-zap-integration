package com.bnt.rcp.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Setter
@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "acquirer_id_config")
public class AcquirerIdConfig extends BaseEntity {

    @Column(name = "name")
    private String name;

    @Column(name = "code")
    private String code;

    @Column(name = "description")
    private String description;

    @OneToOne
    @JoinColumn(name = "country_id")
    private Country country;

    @Transient
    private Integer totalMerchantGroup;

    @Column(name = "onus_validate")
    private Character onusValidate;

    @Column(name = "refund_offline")
    private Character refundOffline;

    @Column(name = "advice_match")
    private Boolean adviceMatch;

    @Column(name = "pos_sms")
    private String posSms;

    @Column(name = "pos_dms")
    private String posDms;

    @Column(name = "txntype_sms")
    private String txtnypeSms;

    @Column(name = "txntype_dms")
    private String txtnypeDms;
    
    @Column(name = "accounttype_sms")
    private String accounttypeSms;

    @Column(name = "accounttype_dms")
    private String accounttypeDms;

    @Column(name = "deleted")
    private Character deleted;

    @Column(name = "active")
    private Boolean active;

}

