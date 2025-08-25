package com.bnt.rcp.mapper;

import com.bnt.rcp.dto.CountryStateDto;
import com.bnt.rcp.entity.CountryState;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface CountryStateMapper {

    CountryStateMapper INSTANCE = Mappers.getMapper(CountryStateMapper.class);

    CountryState toEntity(CountryStateDto stateDto);

    List<CountryStateDto> toDtoList(List<CountryState> stateList);

}

