package com.bnt.rcp.databasesyncup.dto;

import lombok.*;

@Setter
@Getter
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
