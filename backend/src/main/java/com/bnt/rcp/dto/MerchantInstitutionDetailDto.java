package com.bnt.rcp.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.*;

@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class MerchantInstitutionDetailDto extends BaseDto {

    @NotNull
    @Size(min = 2, max = 256)
    private String address1;

    private String address2;

    @NotNull
    @Size(min = 2, max = 50)
    private String city;

    private String zip;

    private CountryWrapperDto country;

    private CountryStateDto countryState;

    private String phone;

    private String fax;

    @Pattern(regexp = ".+@.+\\..+")
    private String email;

}

