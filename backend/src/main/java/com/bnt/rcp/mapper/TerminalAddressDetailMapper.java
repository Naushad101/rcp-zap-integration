package com.bnt.rcp.mapper;

import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import com.bnt.rcp.dto.TerminalAddressDetailDto;
import com.bnt.rcp.entity.TerminalAddressDetail;

@Mapper(componentModel = "spring")
public interface TerminalAddressDetailMapper {

    @Mapping(target = "address1", source = "address1")
    @Mapping(target = "address2", source = "address2")
    @Mapping(target = "city", source = "city")
    @Mapping(target = "postalCode", source = "postalCode")
    @Mapping(target = "phone", source = "phone")
    @Mapping(target = "email", source = "email")
    @Mapping(target = "latitude", source = "latitude")
    @Mapping(target = "longitude", source = "longitude")
    // Country field needs custom mapping from String to GenericWrapper
    @Mapping(target = "country", ignore = true)
    TerminalAddressDetailDto toDto(TerminalAddressDetail entity);

    @Mapping(target = "id", ignore = true) // Let Hibernate handle ID generation
    @Mapping(target = "address1", source = "address1")
    @Mapping(target = "address2", source = "address2")
    @Mapping(target = "city", source = "city")
    @Mapping(target = "postalCode", source = "postalCode")
    @Mapping(target = "phone", source = "phone")
    @Mapping(target = "email", source = "email")
    @Mapping(target = "latitude", source = "latitude")
    @Mapping(target = "longitude", source = "longitude")
    // Country field needs custom mapping from GenericWrapper to String
    @Mapping(target = "country", ignore = true)
    TerminalAddressDetail toEntity(TerminalAddressDetailDto terminalAddressDetailDto);

    // Custom mapping methods for country field
    // @AfterMapping
    // default void mapCountryToDto(@MappingTarget TerminalAddressDetailDto dto, TerminalAddressDetail entity) {
    //     if (entity.getCountry() != null) {
    //         // Create GenericWrapper for country
    //         com.bnt.rcp.dto.GenericWrapper countryWrapper = new com.bnt.rcp.dto.GenericWrapper();
    //         countryWrapper.setCode(entity.getCountry());
    //         countryWrapper.setName(entity.getCountry()); // You might want to lookup the full country name
    //         dto.setCountry(countryWrapper);
    //     }
    // }

    // @AfterMapping
    // default void mapCountryToEntity(@MappingTarget TerminalAddressDetail entity, TerminalAddressDetailDto dto) {
    //     if (dto.getCountry() != null) {
    //         entity.setCountry(dto.getCountry().getCode());
    //     }
    // }
}