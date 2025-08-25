package com.bnt.rcp.mapper;

import com.bnt.rcp.dto.CountryDto;
import com.bnt.rcp.entity.Country;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface CountryMapper {

    CountryMapper INSTANCE = Mappers.getMapper(CountryMapper.class);

    Country toEntity(CountryDto dto);

    List<CountryDto> toDtoList(List<Country> entityList);
}
