package com.bnt.rcp.dto;

import java.sql.Timestamp;
import lombok.*;

@Setter
@Getter
@ToString
public class MerchantGroupSyncUpDto extends BaseDto {

    private Integer institutionId;
    
    private Integer acquirerId;
    
    private String code;
    
    private String name;
    
    private String description;
    
    private Timestamp activateOn;
    
    private Timestamp expiryOn;
    
    private Character locked;
    
    private Character deleted;
    
    private DetailSyncUpDto merchantInstitutionDetail;
    
}

