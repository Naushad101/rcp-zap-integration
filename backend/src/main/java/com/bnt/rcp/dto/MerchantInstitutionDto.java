package com.bnt.rcp.dto;

import java.sql.Timestamp;
import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.validation.Valid;
import lombok.*;

@Setter
@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class MerchantInstitutionDto extends BaseDto {

    private GenericWrapper institution;

    private AcquirerIdConfigDto acquirer;

    private String code;

    private String name;

    private String description;

    private Timestamp activateOn;
    
    private Timestamp expiryOn;

    private Integer totalMerchant;

    private Integer totalLocation;

    private Integer totalDevice;

    private boolean expried;

    @Valid
    private MerchantInstitutionDetailDto merchantInstitutionDetail;

    private String message;

    private Character locked;

    @JsonIgnore
    private Character deleted;

    @JsonIgnore
    private String menuId;

}
