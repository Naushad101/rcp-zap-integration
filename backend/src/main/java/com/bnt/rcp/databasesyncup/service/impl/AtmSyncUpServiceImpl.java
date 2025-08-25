package com.bnt.rcp.databasesyncup.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.bnt.rcp.databasesyncup.dto.DatabaseSyncUpDto;
import com.bnt.rcp.databasesyncup.dto.FilterDataDto;
import com.bnt.rcp.databasesyncup.dto.MerchantGroupSyncUpDto;
import com.bnt.rcp.databasesyncup.mapper.MerchantGroupMapper;
import com.bnt.rcp.databasesyncup.repository.AtmSyncUpRepository;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class AtmSyncUpServiceImpl {

    private final AtmSyncUpRepository atmSyncUpRepository;

    public AtmSyncUpServiceImpl(AtmSyncUpRepository atmSyncUpRepository) {
        this.atmSyncUpRepository = atmSyncUpRepository;
    }

    public DatabaseSyncUpDto getDataFromAtm(FilterDataDto filterDataDto) {
        log.info("Filtering data from ATM with filter: {}", filterDataDto);
        DatabaseSyncUpDto databaseSyncUpDto = new DatabaseSyncUpDto();

        databaseSyncUpDto.setMerchantis(getMerchantGroupData(filterDataDto));
        log.info("Successfully filtered data from ATM");
        return databaseSyncUpDto;
    }

    private List<MerchantGroupSyncUpDto> getMerchantGroupData(FilterDataDto filterDataDto) {
        return atmSyncUpRepository.getMerchantInstitutionData(filterDataDto).stream().map(value -> {
            MerchantGroupSyncUpDto dto = MerchantGroupMapper.INSTANCE.toDto(value);

            dto.setInstitutionId(value.getInstitution().getId());
            dto.setAcquirerId(value.getAcquirer() != null ? value.getAcquirer().getId() : null);
            dto.getMerchantInstitutionDetail().setCountryId(value.getMerchantInstitutionDetail().getCountry().getId());
            dto.getMerchantInstitutionDetail().setState(value.getMerchantInstitutionDetail().getCountryState().getId());

            return dto;
        }).toList();
    }

}
