package com.bnt.rcp.service;

import com.bnt.rcp.dto.AcquirerIdConfigDto;

import java.util.List;
import java.util.Map;

public interface AcquirerIdConfigService {

    Integer createAcquirerIdConfig(AcquirerIdConfigDto dto);

    AcquirerIdConfigDto getAcquirerIdConfig(Integer id);

    List<AcquirerIdConfigDto> getAllAcquirerIdConfigs();

    Map<Integer, String> getAllAcquirerIdConfigsIdAndName();

    Integer updateAcquirerIdConfig(Integer id, AcquirerIdConfigDto dto);

    void deleteAcquirerIdConfig(Integer id);
}

