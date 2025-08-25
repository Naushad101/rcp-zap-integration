package com.bnt.rcp.service;

import com.bnt.rcp.dto.CountryDto;

import java.util.List;
import java.util.Map;

public interface CountryService {

    Integer createCountry(CountryDto countryDto);

    List<CountryDto> getAllCountries();

    Map<Integer, String> getAllCountriesIdAndName();

    Integer updateCountry(Integer id, CountryDto countryDto);

    void deleteCountry(Integer id);

}
