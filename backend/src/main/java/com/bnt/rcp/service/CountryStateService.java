package com.bnt.rcp.service;

import com.bnt.rcp.dto.CountryStateDto;

import java.util.List;

public interface CountryStateService {

    Integer createCountryState(CountryStateDto countryStateDto);

    List<CountryStateDto> getAllCountryStates();

    List<CountryStateDto> getAllCountryStatesIdAndName();

    Integer updateCountryState(Integer id, CountryStateDto countryStateDto);

    void deleteCountryState(Integer id);
}

