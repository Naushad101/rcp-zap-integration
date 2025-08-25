package com.bnt.rcp.databasesyncup.dto;

import lombok.*;

@Setter
@Getter
public class CurrencySyncUpDto extends BaseDto {

    private String code;

    private String isoCode;

    private String currencyName;

    private String currencyMinorUnit;

    private Character active;

}
