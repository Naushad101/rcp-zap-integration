package com.bnt.rcp.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.bnt.rcp.dto.TerminalSitingDto;
import com.bnt.rcp.entity.TerminalSiting;

@Mapper(componentModel = "spring")
public interface TerminalSitingMapper {

    @Mapping(target = "id", source = "id")
    @Mapping(target = "name", source = "name")
    @Mapping(target = "status", source = "status")
    @Mapping(target = "deleted", source = "deleted")
    TerminalSitingDto toDto(TerminalSiting entity);

    @Mapping(target = "id", source = "id")
    @Mapping(target = "name", source = "name")
    @Mapping(target = "status", source = "status")
    @Mapping(target = "deleted", source = "deleted")
    @Mapping(target = "createdBy", ignore = true)
    @Mapping(target = "updatedBy", ignore = true)
    @Mapping(target = "createdOn", ignore = true)
    @Mapping(target = "updatedOn", ignore = true)
    TerminalSiting toEntity(TerminalSitingDto dto);
}
