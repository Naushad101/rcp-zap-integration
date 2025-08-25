package com.bnt.rcp.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import lombok.*;

@Setter
@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class MerchantDetailDto {

    @NotEmpty(message = "Address1 should not be empty")
    private String address1;
 
    private String address2;
 
    private String city;
 
    private String zip;
 
    private CountryWrapperDto country;
 
    private CountryStateDto countryState;
 
    private String phone;
 
    private String fax;
 
    @Pattern(regexp = ".+@.+\\.[a-z]+")
    @NotEmpty(message = "email must not be empty")
    private String email;

}
