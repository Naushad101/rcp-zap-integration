package com.bnt.rcp.dto;

import lombok.*;

@Setter
@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class CountryStateDto extends BaseDto {

    private String code;

    private String stateName;

    private CountryDto country;
    
    private Integer countryId;

    private Character active;

}
