package com.bnt.rcp.dto;

import lombok.*;

import java.sql.Timestamp;
import java.time.LocalDateTime;

import com.bnt.rcp.entity.Location;
import com.bnt.rcp.entity.LocationDetail;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LocationDto {
    private Integer id;
    private String code;
    private String name;
    private String description;
    private Timestamp activateOn;
    private Timestamp expiryOn;
    private Character deleted;
    private Character locked;
    private Character posSafetyFlag;
    private String reversalTimeout;
    private String additionalAttribute;
    private Double latitude;
    private Double longitude;
    
   
    private Integer merchantId;
    private String merchantName;
    private String merchantCode;
    
 
    private Integer locationDetailId;
    private String address1;
    private String address2;
    private String city;
    private String zip;
    private String phone;
    private String fax;
    private String website;
    private String email;
    
   
    private Integer countryId;
    private String countryName;
    private String countryCode;
    private Integer stateId;
    private String stateName;
    private String stateCode;
    

    private Integer region;
    private Integer locationId;
    private Character atmDeleted;
    private Character atmLocked;
    private Boolean sublocation;
    private Integer parentLocationId; 
}