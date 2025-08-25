package com.bnt.rcp.databasesyncup.mapper;

import com.bnt.rcp.databasesyncup.dto.CountrySyncUpDto;
import com.bnt.rcp.entity.Country;

import org.mapstruct.*;
import org.mapstruct.factory.Mappers;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface CountryMapper {

    CountryMapper INSTANCE = Mappers.getMapper(CountryMapper.class);

    CountrySyncUpDto toDto(Country country);

}
