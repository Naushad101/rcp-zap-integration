package com.bnt.rcp.dto;

import lombok.*;

@Setter
@Getter
@ToString
public class CountryStateSyncUpDto extends BaseDto {

    private String code;

    private String stateName;

    private CountrySyncUpDto country;
    
    private Integer countryId;

    private Character active;
}
