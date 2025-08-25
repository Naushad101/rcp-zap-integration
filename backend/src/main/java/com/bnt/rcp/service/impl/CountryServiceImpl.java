package com.bnt.rcp.service.impl;

import com.bnt.rcp.constant.RippsRestConstant;
import com.bnt.rcp.dto.CountryDto;
import com.bnt.rcp.entity.Country;
import com.bnt.rcp.entity.Currency;
import com.bnt.rcp.exception.RippsAdminRestException;
import com.bnt.rcp.mapper.CountryMapper;
import com.bnt.rcp.repository.CountryRepository;
import com.bnt.rcp.repository.CurrencyRepository;
import com.bnt.rcp.service.CountryService;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Service
public class CountryServiceImpl implements CountryService {

    private final CountryRepository countryRepository;

    private final CurrencyRepository currencyRepository;

    public CountryServiceImpl(CountryRepository countryRepository, CurrencyRepository currencyRepository) {
        this.countryRepository = countryRepository;
        this.currencyRepository = currencyRepository;
    }

    @Override
    public Integer createCountry(CountryDto countryDto) {
        log.info("Creating country with details: {}", countryDto);

        Currency existingCurrency = currencyRepository.findById(countryDto.getCurrencyId())
        .orElseThrow(() -> {
            log.error("Currency not found with id: {}", countryDto.getCurrencyId());
            return new RippsAdminRestException(RippsRestConstant.ERROR, "Currency not found with id: " + countryDto.getCurrencyId(), null);
        });

        if (countryRepository.existsByCode(countryDto.getCode())) {
            log.error("Country code already exists: {}", countryDto.getCode());
            throw new RippsAdminRestException(RippsRestConstant.ERROR, "Country code already exists", null);
        }

        if (countryRepository.existsByIsoCode(countryDto.getIsoCode())) {
            log.error("Country iso code already exists: {}", countryDto.getIsoCode());
            throw new RippsAdminRestException(RippsRestConstant.ERROR, "Country iso code already exists", null);
        }

        Country country = CountryMapper.INSTANCE.toEntity(countryDto);
        country.setCurrency(existingCurrency);

        Country createdCountry = countryRepository.save(country);

        log.info("Successfully created country with id: {}", createdCountry.getId());
        return createdCountry.getId();
    }

    @Override
    public List<CountryDto> getAllCountries() {
        log.info("Fetching all countries");

        List<Country> existingCountries = countryRepository.findAll().stream()
            .filter(country -> country.getDeleted() != '1')
            .toList();

        List<CountryDto> countryDtoList = CountryMapper.INSTANCE.toDtoList(existingCountries);

        log.info("Successfully fetched {} countries", countryDtoList.size());
        return countryDtoList;
    }

    @Override
    public Map<Integer, String> getAllCountriesIdAndName() {
        log.info("Fetching all countries with id and name");

        List<Country> countries = countryRepository.findAllCountriesIdAndName();

        Map<Integer, String> countryMap = countries.stream()
            .collect(Collectors.toMap(Country::getId, Country::getCountryName));

        log.info("Successfully fetched all {} countries with id and name", countryMap.size());
        return countryMap;
    }

    @Override
    public Integer updateCountry(Integer id, CountryDto countryDto) {
        log.info("Updating country with id: {}", id);

        Optional<Country> existingCountryOpt = countryRepository.findById(id);

        if (existingCountryOpt.isEmpty()) {
            log.warn("Country with id: {} not found", id);
            throw new RippsAdminRestException(RippsRestConstant.ERROR, "Country not found with id: " + id, null);
        }

        Country existingCountry = existingCountryOpt.get();
        existingCountry.setActive(countryDto.getActive());

        Country updated = countryRepository.save(existingCountry);

        log.info("Successfully updated country: {}", updated);
        return updated.getId();
    }

    @Override
    public void deleteCountry(Integer id) {
        log.info("Soft deleting country with id: {}", id);

        Country existingCountry = countryRepository.findById(id).orElse(null);

        if (existingCountry == null) {
            log.warn("Country with id: {} not found", id);
            throw new RippsAdminRestException(RippsRestConstant.ERROR, "Country not found with id: " + id, null);
        }

        countryRepository.softDeleteById(id);

        log.info("Successfully soft deleted country with id: {}", id);
    }   
}
