package com.bnt.rcp.dto;

import lombok.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class MerchantProfileDto extends BaseDto {

    private String partialAuth;

    private String velocity;

    private String categoryCode;

    private String services;

    private String additionalServices;

}
