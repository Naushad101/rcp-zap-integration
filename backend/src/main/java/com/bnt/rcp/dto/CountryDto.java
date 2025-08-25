package com.bnt.rcp.dto;

import lombok.*;

@Setter
@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class CountryDto extends BaseDto {

    private String code;
    
    private String countryName;

    private Integer currencyId;

    private CurrencyDto currency;

    private String isoCode;

    private String shortCode;

    private String isdCode;
    
    private Character active;

}
