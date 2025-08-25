package com.bnt.rcp.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

import com.bnt.rcp.dto.AtmOptionDto;
import com.bnt.rcp.entity.AtmOption;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface AtmOptionMapper {

    AtmOptionMapper INSTANCE = Mappers.getMapper(AtmOptionMapper.class);

    AtmOptionDto toDto(AtmOption entity);

    AtmOption toEntity(AtmOptionDto dto);

    List<AtmOptionDto> toDtoList(List<AtmOption> entityList);

}
