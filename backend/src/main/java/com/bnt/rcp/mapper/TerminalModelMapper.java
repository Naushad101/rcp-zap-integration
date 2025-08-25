package com.bnt.rcp.mapper;

import com.bnt.rcp.dto.TerminalModelDto;
import com.bnt.rcp.dto.TerminalSitingDto;
import com.bnt.rcp.entity.TerminalModel;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = {TerminalTypeMapper.class, TerminalVendorMapper.class, TerminalSitingMapper.class})
public interface TerminalModelMapper {

    @Mapping(target = "id", source = "id")
    @Mapping(target = "type", source = "type")
    @Mapping(target = "vendor", source = "vendor")
    @Mapping(target = "siting", source = "siting")
    @Mapping(target = "modelname", source = "modelname")
    @Mapping(target = "status", source = "status")
    @Mapping(target = "deleted", source = "deleted")
    TerminalModelDto toDto(TerminalModel entity);

    @Mapping(target = "id", source = "id")
    @Mapping(target = "type", source = "type")
    @Mapping(target = "vendor", source = "vendor")
    @Mapping(target = "siting", source = "siting")
    @Mapping(target = "modelname", source = "modelname")
    @Mapping(target = "status", source = "status")
    @Mapping(target = "deleted", source = "deleted")
    @Mapping(target = "createdBy", ignore = true)
    @Mapping(target = "updatedBy", ignore = true)
    @Mapping(target = "createdOn", ignore = true)
    @Mapping(target = "updatedOn", ignore = true)
    TerminalModel toEntity(TerminalModelDto terminalModelDto);
}