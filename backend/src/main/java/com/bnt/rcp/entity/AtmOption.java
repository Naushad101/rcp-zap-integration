package com.bnt.rcp.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Table(name = "atm_option")
public class AtmOption {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "dccflag")
    private Boolean dccFlag;

    @Column(name = "currency")
    private String currency;

    @Column(name = "masterkeyexpirytimeunit")
    private String masterKeyExpiryTimeUnit;

    @Column(name = "pinkeyexpirytimeunit")
    private String pinKeyExpiryTimeUnit;

    @Column(name = "masterkeyexpiryvalue")
    private Integer masterKeyExpiryValue;

    @Column(name = "pinkeyexpiryperiod")
    private Integer pinKeyExpiryPeriod;

    @Column(name = "merchant_id")
    private Integer merchantId;
}

