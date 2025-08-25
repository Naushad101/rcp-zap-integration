package com.bnt.rcp.mapper;

import com.bnt.rcp.dto.TerminalDto;
import com.bnt.rcp.entity.Terminal;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface TerminalMapper {

    @Mapping(target = "terminalId", source = "terminalId")
    // @Mapping(target = "name", source = "name")
    // @Mapping(target = "code", source = "code")
    @Mapping(target = "macAddress", source = "macAddress")
    @Mapping(target = "eppSerialNumber", source = "eppSerialNumber")
    @Mapping(target = "description", source = "description")
    @Mapping(target = "branchSortCode", source = "branchSortCode")
    // @Mapping(target = "additionalAttribute", source = "additionalAttribute")
    @Mapping(target = "locationInherit", source = "inheritAddress")
    @Mapping(target = "rhJFlag", source = "rkIFlag")
    @Mapping(target = "locked", source = "deleted")
    // Complex mappings that need custom logic
    @Mapping(target = "terminalModel", ignore = true) // Needs custom mapping from TerminalModel to GenericWrapper
    @Mapping(target = "addressDetail", ignore = true) // Needs custom mapping due to different field types
    @Mapping(target = "institution", ignore = true) // Needs custom logic
    @Mapping(target = "institutionGroup", ignore = true) // Needs custom mapping from institutionGroup field
    @Mapping(target = "location", ignore = true) // Needs custom logic
    @Mapping(target = "id", ignore = true) // BaseDto field, needs custom handling
    TerminalDto toDto(Terminal entity);

    @Mapping(target = "terminalId", source = "terminalId")
    // @Mapping(target = "name", source = "name")
    // @Mapping(target = "code", source = "code")
    @Mapping(target = "macAddress", source = "macAddress")
    @Mapping(target = "eppSerialNumber", source = "eppSerialNumber")
    @Mapping(target = "description", source = "description")
    @Mapping(target = "branchSortCode", source = "branchSortCode")
    // @Mapping(target = "additionalAttribute", source = "additionalAttribute")
    @Mapping(target = "inheritAddress", source = "locationInherit")
    @Mapping(target = "rkIFlag", source = "rhJFlag")
    @Mapping(target = "deleted", source = "locked")
    // Complex mappings that need custom logic
    @Mapping(target = "terminalModel", ignore = true) // Needs custom mapping from GenericWrapper to TerminalModel
    @Mapping(target = "addressDetail", ignore = true) // Needs custom mapping due to different field types
    @Mapping(target = "institutionGroup", ignore = true) // Needs custom mapping to Integer from GenericWrapper
    Terminal toEntity(TerminalDto terminalDto);
}