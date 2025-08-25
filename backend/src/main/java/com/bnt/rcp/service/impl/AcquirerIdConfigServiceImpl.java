package com.bnt.rcp.service.impl;

import com.bnt.rcp.constant.RippsRestConstant;
import com.bnt.rcp.dto.AcquirerIdConfigDto;
import com.bnt.rcp.entity.AcquirerIdConfig;
import com.bnt.rcp.entity.Country;
import com.bnt.rcp.exception.RippsAdminRestException;
import com.bnt.rcp.mapper.AcquirerIdConfigMapper;
import com.bnt.rcp.repository.AcquirerIdConfigRepository;
import com.bnt.rcp.repository.CountryRepository;
import com.bnt.rcp.service.AcquirerIdConfigService;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Service
public class AcquirerIdConfigServiceImpl implements AcquirerIdConfigService {

    private final AcquirerIdConfigRepository acquirerIdConfigRepository;
    private final CountryRepository countryRepository;

    public AcquirerIdConfigServiceImpl(AcquirerIdConfigRepository acquirerIdConfigRepository,
            CountryRepository countryRepository) {
        this.acquirerIdConfigRepository = acquirerIdConfigRepository;
        this.countryRepository = countryRepository;
    }

    @Override
    public Integer createAcquirerIdConfig(AcquirerIdConfigDto acquirerIdConfigDto) {
        log.info("Creating acquirerIdConfig with details: {}", acquirerIdConfigDto);

        Country country = countryRepository.findById(acquirerIdConfigDto.getCountryId())
                .orElseThrow(() -> {
                    log.error("Country not found with id: {}", acquirerIdConfigDto.getCountryId());
                    return new RippsAdminRestException(RippsRestConstant.ERROR,
                            "Country not found with ID: " + acquirerIdConfigDto.getCountryId(), null);
                });

        if (acquirerIdConfigRepository.existsByName(acquirerIdConfigDto.getName())) {
            log.error("AcquirerIdConfig name already exists: {}", acquirerIdConfigDto.getName());
            throw new RippsAdminRestException(RippsRestConstant.ERROR, "AcquirerIdConfig name already exists",
                    null);
        }

        if (acquirerIdConfigRepository.existsByCode(acquirerIdConfigDto.getCode())) {
            log.error("AcquirerIdConfig code already exists: {}", acquirerIdConfigDto.getCode());
            throw new RippsAdminRestException(RippsRestConstant.ERROR, "AcquirerIdConfig code already exists",
                    null);
        }

        AcquirerIdConfig acquirerIdConfig = AcquirerIdConfigMapper.INSTANCE.toEntity(acquirerIdConfigDto);

        acquirerIdConfig.setCountry(country);

        AcquirerIdConfig createdAcquirerIdConfig = acquirerIdConfigRepository.save(acquirerIdConfig);

        log.info("Successfully created acquirerIdConfig with id: {}", createdAcquirerIdConfig.getId());
        return createdAcquirerIdConfig.getId();

    }

    @Override
    public List<AcquirerIdConfigDto> getAllAcquirerIdConfigs() {
        log.info("Fetching all acquirerIdConfigs");

        List<AcquirerIdConfig> existingAcquirerIdConfigs = acquirerIdConfigRepository.findAll().stream()
                .filter(acquirerIdConfig -> acquirerIdConfig.getDeleted() != '1')
                .toList();
                
        List<AcquirerIdConfigDto> acquirerIdConfigDtoList = AcquirerIdConfigMapper.INSTANCE.toDtoList(existingAcquirerIdConfigs);

        log.info("Successfully fetched all {} acquirerIdConfigs", acquirerIdConfigDtoList.size());
        return acquirerIdConfigDtoList;
    }

    @Override
    public Map<Integer, String> getAllAcquirerIdConfigsIdAndName() {
        log.info("Fetching all acquirerIdConfigs with id and Name");

        List<AcquirerIdConfig> existingAcquirerIdConfigs = acquirerIdConfigRepository.findAllAcquirerIdConfigsIdAndName();
        Map<Integer, String> acquirerMap = existingAcquirerIdConfigs.stream().collect(Collectors.toMap(AcquirerIdConfig::getId, AcquirerIdConfig::getName));

        log.info("Successfully fetched {} acquirerIdConfigs with id and Name", acquirerMap.size());
        return acquirerMap;
    }

    @Override
    public AcquirerIdConfigDto getAcquirerIdConfig(Integer id) {
        log.info("Fetching acquirerIdConfig by id: {}", id);

        Optional<AcquirerIdConfig> acquirerIdConfigOpt = acquirerIdConfigRepository.findById(id);

        if (acquirerIdConfigOpt.isEmpty()) {
            log.error("AcquirerIdConfig not found with id : {}", id);
            throw new RippsAdminRestException(RippsRestConstant.ERROR, "AcquirerIdConfig not found with id : " + id,
                    null);
        }
        AcquirerIdConfig acquirerIdConfig = acquirerIdConfigOpt.get();

        AcquirerIdConfigDto acquirerIdConfigDto = AcquirerIdConfigMapper.INSTANCE.toDto(acquirerIdConfig);

        log.info("Successfully fetched acquirerIdConfig with id: {}", id);
        return acquirerIdConfigDto;

    }

    @Override
    public Integer updateAcquirerIdConfig(Integer id, AcquirerIdConfigDto dto) {
        log.info("Updating acquirerIdConfig with id: {}", id);

        Optional<AcquirerIdConfig> existingOpt = acquirerIdConfigRepository.findById(id);

        if (existingOpt.isEmpty()) {
            log.error("AcquirerIdConfig not found with id: {}", id);
            throw new RippsAdminRestException(RippsRestConstant.ERROR, "AcquirerIdConfig not found with id: " + id,
                    null);
        }

        AcquirerIdConfig existing = existingOpt.get();

        existing.setId(id);
        existing.setName(dto.getName());
        existing.setActive(dto.getActive());
        existing.setAdviceMatch(dto.getAdviceMatch());
        existing.setCode(dto.getCode());
        existing.setDescription(dto.getDescription());
        existing.setDeleted(dto.getDeleted());
        existing.setName(dto.getName());
        existing.setOnusValidate(dto.getOnusValidate());
        existing.setRefundOffline(dto.getRefundOffline());
        existing.setPosSms(dto.getPosSms());
        existing.setPosDms(dto.getPosDms());
        existing.setTxtnypeSms(dto.getTxtnypeSms());
        existing.setTxtnypeDms(dto.getTxtnypeDms());
        existing.setAccounttypeSms(dto.getAccounttypeSms());
        existing.setAccounttypeDms(dto.getAccounttypeDms());

        AcquirerIdConfig updated = acquirerIdConfigRepository.save(existing);

        log.info("Successfully updated acquirerIdConfig with id: {}", updated.getId());
        return updated.getId();
    }

    @Override
    public void deleteAcquirerIdConfig(Integer id) {
        log.info("Deleting acquirerIdConfig with id: {}", id);

        if (!acquirerIdConfigRepository.existsById(id)) {
            log.error("AcquirerIdConfig not found with id : {}", id);
            throw new RippsAdminRestException(RippsRestConstant.ERROR, "AcquirerIdConfig not found with id : " + id,
                    null);
        }

        acquirerIdConfigRepository.softDeleteById(id);

        log.info("Successfully deleted acquirerIdConfig with id: {}", id);
    }

}
