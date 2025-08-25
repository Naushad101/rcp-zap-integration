package com.bnt.rcp.databasesyncup.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.bnt.rcp.databasesyncup.dto.*;
import com.bnt.rcp.databasesyncup.mapper.*;
import com.bnt.rcp.databasesyncup.repository.SwitchSyncUpRepository;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class SwitchSyncUpServiceImpl {

    private final SwitchSyncUpRepository switchSyncUpRepository;

    public SwitchSyncUpServiceImpl(SwitchSyncUpRepository switchSyncUpRepository) {
        this.switchSyncUpRepository = switchSyncUpRepository;
    }

    public DatabaseSyncUpDto getDataFromSwitch(FilterDataDto filterDataDto) {
        log.info("Filtering data from Switch with filter: {}", filterDataDto);
        DatabaseSyncUpDto databaseSyncUpDto = new DatabaseSyncUpDto();

        databaseSyncUpDto.setCurrencies(getCurrencyData(filterDataDto));
        databaseSyncUpDto.setCountries(getCountryData(filterDataDto));
        databaseSyncUpDto.setCountryStates(getCountryStateData(filterDataDto));
        databaseSyncUpDto.setAcquirerIdConfigs(getAcquirerIdConfigData(filterDataDto));

        log.info("Successfully filtered from Switch");
        return databaseSyncUpDto;
    }

    private List<CurrencySyncUpDto> getCurrencyData(FilterDataDto filterDataDto) {
        return switchSyncUpRepository.getCurrencyData(filterDataDto).stream()
            .map(CurrencyMapper.INSTANCE::toDto)
            .toList();
    }

    private List<CountrySyncUpDto> getCountryData(FilterDataDto filterDataDto) {
        return switchSyncUpRepository.getCountryData(filterDataDto).stream()
            .map(CountryMapper.INSTANCE::toDto)
            .toList();
    }

    private List<CountryStateSyncUpDto> getCountryStateData(FilterDataDto filterDataDto) {
        return switchSyncUpRepository.getCountryStateData(filterDataDto).stream()
            .map(CountryStateMapper.INSTANCE::toDto)
            .toList();
    }

    private List<AcquirerIdConfigSyncUpDto> getAcquirerIdConfigData(FilterDataDto filterDataDto) {
        return switchSyncUpRepository.getAcquirerIdConfigData(filterDataDto).stream()
            .map(AcquirerIdConfigMapper.INSTANCE::toDto)
            .toList();
    }

}
