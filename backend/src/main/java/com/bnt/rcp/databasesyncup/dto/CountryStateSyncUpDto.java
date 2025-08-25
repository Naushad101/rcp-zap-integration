package com.bnt.rcp.databasesyncup.dto;

import lombok.*;

@Setter
@Getter
public class CountryStateSyncUpDto extends BaseDto {

    private String code;

    private String stateName;

    private CountrySyncUpDto country;
    
    private Integer countryId;

    private Character active;
}
