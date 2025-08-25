package com.bnt.rcp.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.bnt.rcp.dto.TerminalTypeDto;
import com.bnt.rcp.entity.TerminalTypeEntity;

@Mapper(componentModel = "spring")
public interface TerminalTypeMapper {

    @Mapping(target = "id", source = "id")
    @Mapping(target = "type", source = "type")
    @Mapping(target = "status", source = "status")
    @Mapping(target = "deleted", source = "deleted")
    TerminalTypeDto toDto(TerminalTypeEntity entity);

    @Mapping(target = "id", source = "id")
    @Mapping(target = "type", source = "type")
    @Mapping(target = "status", source = "status")
    @Mapping(target = "deleted", source = "deleted")
    @Mapping(target = "createdBy", ignore = true)
    @Mapping(target = "updatedBy", ignore = true)
    @Mapping(target = "createdOn", ignore = true)
    @Mapping(target = "updatedOn", ignore = true)
    TerminalTypeEntity toEntity(TerminalTypeDto dto);
}
