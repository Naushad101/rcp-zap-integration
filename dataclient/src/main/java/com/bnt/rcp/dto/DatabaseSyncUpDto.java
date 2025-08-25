package com.bnt.rcp.dto;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.*;

@Setter
@Getter
@ToString
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class DatabaseSyncUpDto {

    @JsonProperty("merchantis")
    List<MerchantGroupSyncUpDto> merchantis;

    @JsonProperty("currencies")
    List<CurrencySyncUpDto> currencies;

    @JsonProperty("countries")
    List<CountrySyncUpDto> countries;

    @JsonProperty("countryStates")
    List<CountryStateSyncUpDto> countryStates;

    @JsonProperty("acquirerIdConfigs")
    List<AcquirerIdConfigSyncUpDto> acquirerIdConfigs;
}
