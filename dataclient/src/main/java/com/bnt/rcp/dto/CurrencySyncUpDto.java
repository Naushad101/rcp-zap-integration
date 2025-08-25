package com.bnt.rcp.dto;

import lombok.*;

@Setter
@Getter
@ToString
public class CurrencySyncUpDto extends BaseDto {

    private String code;

    private String isoCode;

    private String currencyName;

    private String currencyMinorUnit;

    private Character active;

}
