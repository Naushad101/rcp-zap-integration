package com.bnt.rcp.databasesyncup.mapper;

import com.bnt.rcp.databasesyncup.dto.AcquirerIdConfigSyncUpDto;
import com.bnt.rcp.entity.AcquirerIdConfig;

import org.mapstruct.*;
import org.mapstruct.factory.Mappers;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface AcquirerIdConfigMapper {

    AcquirerIdConfigMapper INSTANCE = Mappers.getMapper(AcquirerIdConfigMapper.class);

    AcquirerIdConfigSyncUpDto toDto(AcquirerIdConfig acquirerIdConfig);

}
