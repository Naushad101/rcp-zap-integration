package com.bnt.rcp.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.bnt.rcp.dto.*;
import com.bnt.rcp.entity.*;
import com.bnt.rcp.repository.*;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class SwitchServiceImpl {

    private final CurrencyRepository currencyRepository;

    private final CountryRepository countryRepository;

    private final CountryStateRepository countryStateRepository;

    private final AcquirerIdConfigRepository acquirerIdConfigRepository;

    public SwitchServiceImpl(CurrencyRepository currencyRepository, 
                             CountryRepository countryRepository, 
                             CountryStateRepository countryStateRepository,
                             AcquirerIdConfigRepository acquirerIdConfigRepository) {
        this.currencyRepository = currencyRepository;
        this.countryRepository = countryRepository;
        this.countryStateRepository = countryStateRepository;
        this.acquirerIdConfigRepository = acquirerIdConfigRepository;
    }

    private StringBuilder successData; 

    private StringBuilder failedData;

    public StringBuilder getSuccessData() {
        return successData;
    }

    public StringBuilder getFailedData() {
        return failedData;
    }

    public void execute(DatabaseSyncUpDto syncUpDto) {
        successData = new StringBuilder();
        failedData = new StringBuilder();
        
        log.info("Starting Switch service execution with dto {}", syncUpDto);
        if (syncUpDto.getCurrencies() != null) {
            saveCurrencies(syncUpDto.getCurrencies());
        }
        if (syncUpDto.getCountries() != null) {
            saveCountries(syncUpDto.getCountries());
        }
        if (syncUpDto.getCountryStates() != null) {
            saveCountryStates(syncUpDto.getCountryStates());
        }
        if (syncUpDto.getAcquirerIdConfigs() != null) {
            saveAcquirerIdConfigs(syncUpDto.getAcquirerIdConfigs());
        }
        log.info("Switch service execution completed");
    }

    private void saveCurrencies(List<CurrencySyncUpDto> currencies) {
        log.info("Starting to save {} currencies", currencies.size());
        try {
            for (CurrencySyncUpDto currencyDto : currencies) {
                Currency currency = new Currency();
                
                currency.setId(currencyDto.getId());
                currency.setCode(currencyDto.getCode());
                currency.setIsoCode(currencyDto.getIsoCode());
                currency.setCurrencyName(currencyDto.getCurrencyName());
                currency.setCurrencyMinorUnit(currencyDto.getCurrencyMinorUnit());
                currency.setActive(currencyDto.getActive());
                currency.setCreatedBy(currencyDto.getCreatedBy());
                currency.setUpdatedBy(currencyDto.getUpdatedBy());
                currency.setCreatedOn(currencyDto.getCreatedOn());
                currency.setUpdatedOn(currencyDto.getUpdatedOn());
                currency.setDeleted('0');
                
                currencyRepository.save(currency);

            }
            successData.append("Currencies: ").append(currencies.size()).append(" ");
            log.info("Successfully saved {} currencies", currencies.size());
        } catch (Exception e) {
            log.error("Error saving currencies: {}", e.getMessage(), e);
            failedData.append("Currencies: ").append(e.getMessage()).append(" ");
        }
        log.info("Finished saving currencies");
    }

    private void saveCountries(List<CountrySyncUpDto> countries) {
        log.info("Starting to save {} countries", countries.size());
        try {
            for (CountrySyncUpDto countryDto : countries) {
                Country country = new Country();
                
                country.setId(countryDto.getId());
                country.setCode(countryDto.getCode());
                country.setCountryName(countryDto.getCountryName());
                country.setIsoCode(countryDto.getIsoCode());
               
                Currency currency = currencyRepository.findById(countryDto.getCurrency().getId())
                        .orElseThrow(() -> new RuntimeException("Currency not found for ID: " + countryDto.getCurrencyId()));

                country.setCurrency(currency);
                country.setShortCode(countryDto.getShortCode());
                country.setIsdCode(countryDto.getIsdCode());
                country.setActive(countryDto.getActive());
                country.setCreatedBy(countryDto.getCreatedBy());
                country.setUpdatedBy(countryDto.getUpdatedBy());
                country.setCreatedOn(countryDto.getCreatedOn());
                country.setUpdatedOn(countryDto.getUpdatedOn());
    
                countryRepository.save(country);
            }
            successData.append("Countries: ").append(countries.size()).append(" ");
            log.info("Successfully saved {} countries", countries.size());
        } catch (Exception e) {
            log.error("Error saving countries: {}", e.getMessage(), e);
            failedData.append("Countries: ").append(e.getMessage()).append(" ");
        }
        log.info("Finished saving countries");
    }

    private void saveCountryStates(List<CountryStateSyncUpDto> countryStates) {
        log.info("Starting to save {} country states", countryStates.size());
        try {
            for (CountryStateSyncUpDto stateDto : countryStates) {
                CountryState state = new CountryState();
                
                state.setId(stateDto.getId());
                state.setId(stateDto.getId());
                state.setStateName(stateDto.getStateName());
                state.setCode(stateDto.getCode());

                Country country = countryRepository.findById(stateDto.getCountry().getId())
                        .orElseThrow(() -> new RuntimeException("Country not found for ID: " + stateDto.getCountryId()));

                state.setCountry(country);
                state.setActive(stateDto.getActive());
                state.setCreatedBy(stateDto.getCreatedBy());
                state.setUpdatedBy(stateDto.getUpdatedBy());
                state.setCreatedOn(stateDto.getCreatedOn());
                state.setUpdatedOn(stateDto.getUpdatedOn());
                
                countryStateRepository.save(state);
            }
            successData.append("Country States: ").append(countryStates.size()).append(" ");
            log.info("Successfully saved {} country states", countryStates.size());
        } catch (Exception e) {
            log.error("Error saving country states: {}", e.getMessage(), e);
            failedData.append("Country States: ").append(e.getMessage()).append(" ");
        }
        log.info("Finished saving country states");
    }

    private void saveAcquirerIdConfigs(List<AcquirerIdConfigSyncUpDto> acquirerIdConfigs) {
        log.info("Starting to save {} acquirer ID configs", acquirerIdConfigs.size());
        try {
            for (AcquirerIdConfigSyncUpDto configDto : acquirerIdConfigs) {
                AcquirerIdConfig config = new AcquirerIdConfig();
                
                config.setId(configDto.getId());
                config.setName(configDto.getName());
                config.setCode(configDto.getCode());
                config.setDescription(configDto.getDescription());

                Country country = countryRepository.findById(configDto.getCountry().getId())
                        .orElseThrow(() -> new RuntimeException("Country not found for ID: " + configDto.getCountryId()));
                        
                config.setCountry(country);
                config.setTotalMerchantGroup(configDto.getTotalMerchantGroup());
                config.setOnusValidate(configDto.getOnusValidate());
                config.setRefundOffline(configDto.getRefundOffline());
                config.setAdviceMatch(configDto.getAdviceMatch());
                config.setPosSms(configDto.getPosSms());
                config.setPosDms(configDto.getPosDms());
                config.setTxtnypeSms(configDto.getTxtnypeSms());
                config.setTxtnypeDms(configDto.getTxtnypeDms());
                config.setAccounttypeSms(configDto.getAccounttypeSms());
                config.setAccounttypeDms(configDto.getAccounttypeDms());
                config.setDeleted(configDto.getDeleted());
                config.setActive(configDto.getActive()=='1');
                config.setCreatedBy(configDto.getCreatedBy());
                config.setUpdatedBy(configDto.getUpdatedBy());
                config.setCreatedOn(configDto.getCreatedOn());
                config.setUpdatedOn(configDto.getUpdatedOn());
                
                acquirerIdConfigRepository.save(config);
            }
            successData.append("Acquirer ID Configs: ").append(acquirerIdConfigs.size()).append(" ");
            log.info("Successfully saved {} acquirer ID configs", acquirerIdConfigs.size());
        } catch (Exception e) {
            log.error("Error saving acquirer ID configs: {}", e.getMessage(), e);
            failedData.append("Acquirer ID Configs: ").append(e.getMessage()).append(" ");
        }
        log.info("Finished saving acquirer ID configs");
    }

}
