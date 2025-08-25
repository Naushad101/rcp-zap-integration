package com.bnt.rcp.dto;

import lombok.*;

@Setter
@Getter
@ToString
public class DetailSyncUpDto extends BaseDto {

    private String address1;

    private String address2;

    private String city;
    
    private Integer state;
    
    private String zip;
    
    private Integer countryId;
    
    private String phone;
    
    private String fax;
    
    private String website;
    
    private String email;
    
    private String countryCode;
    
    private String latitude;
    
    private String longitude;
    
}
