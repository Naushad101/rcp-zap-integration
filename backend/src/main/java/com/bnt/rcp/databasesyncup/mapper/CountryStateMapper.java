package com.bnt.rcp.databasesyncup.mapper;

import com.bnt.rcp.databasesyncup.dto.CountryStateSyncUpDto;
import com.bnt.rcp.entity.CountryState;

import org.mapstruct.*;
import org.mapstruct.factory.Mappers;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface CountryStateMapper {
    
    CountryStateMapper INSTANCE = Mappers.getMapper(CountryStateMapper.class);

    CountryStateSyncUpDto toDto(CountryState countryState);

} 