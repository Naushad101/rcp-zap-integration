package com.bnt.rcp.dto;

import lombok.*;

@Setter
@Getter
public class AcquirerIdConfigSyncUpDto extends BaseDto {

    private String name;

    private String code;
    
    private String description;

    private Integer countryId;
     
    private CountrySyncUpDto country;
    
    private Integer totalMerchantGroup;
    
    private Character onusValidate;
    
    private Character refundOffline;
    
    private Boolean adviceMatch;
    
    private String posSms;
    
    private String posDms;
    
    private String txtnypeSms;
    
    private String txtnypeDms;
    
    private String accounttypeSms;
    
    private String accounttypeDms;

    private Character deleted;

    private Character active;
}
