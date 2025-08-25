package com.bnt.rcp.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.*;

@Setter
@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class AtmOptionDto {

    private Integer id;

    @JsonProperty("dccflag")
    private Boolean dccFlag;

    @JsonProperty("currency")
    private String currency;

    @JsonProperty("masterkeyexpirytimeunit")
    private String masterKeyExpiryTimeUnit;

    @JsonProperty("pinkeyexpirytimeunit")
    private String pinKeyExpiryTimeUnit;

    @JsonProperty("masterkeyexpiryvalue")
    private Integer masterKeyExpiryValue;

    @JsonProperty("pinkeyexpiryperiod")
    private Integer pinKeyExpiryPeriod;

}
