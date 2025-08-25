package com.bnt.rcp.mapper;

import com.bnt.rcp.dto.AcquirerIdConfigDto;
import com.bnt.rcp.entity.AcquirerIdConfig;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface AcquirerIdConfigMapper {

    AcquirerIdConfigMapper INSTANCE = Mappers.getMapper(AcquirerIdConfigMapper.class);
    
    AcquirerIdConfig toEntity(AcquirerIdConfigDto acquirerIdConfigDto);

    AcquirerIdConfigDto toDto(AcquirerIdConfig acquirerIdConfig);

    List<AcquirerIdConfigDto> toDtoList (List<AcquirerIdConfig> acquirerIdConfigList);

}

