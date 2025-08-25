package com.bnt.rcp.dto;

import lombok.*;

@Setter
@Getter
@ToString
public class CountrySyncUpDto extends BaseDto {

    private String code;
    
    private String countryName;

    private Integer currencyId;

    private CurrencySyncUpDto currency;

    private String isoCode;

    private String shortCode;

    private String isdCode;
    
    private Character active;
}
