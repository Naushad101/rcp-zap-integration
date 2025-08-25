package com.bnt.rcp.dto;

import lombok.*;

@Setter
@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class CurrencyDto extends BaseDto {

    private String code;

    private String isoCode;

    private String currencyName;

    private String currencyMinorUnit;

    private Character active;

}

