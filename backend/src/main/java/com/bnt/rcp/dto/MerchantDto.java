package com.bnt.rcp.dto;

import java.sql.Timestamp;

import jakarta.validation.Valid;
import lombok.*;

@Setter
@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class MerchantDto extends BaseDto {

    private GenericWrapper merchantInstitution;

    private String code;

    private String name;

    private String description;

    private Timestamp activateOn;

    private Timestamp expiryOn;

    private Integer totalLocation;

    private Integer totalDevice;

    private Character locked;

    private Character posSafetyFlag;

    private boolean expired;

    private String message;

    private String reversalTimeout;

    private Character deleted;

    @Valid
    private MerchantProfileDto merchantProfile;

    private AtmOptionDto atmOption;

    private Integer acquirerId;

    @Valid
    private MerchantDetailDto merchantDetail;

    private GenericWrapper currency;

    private String bankName;

    private String accountNumber;

}
