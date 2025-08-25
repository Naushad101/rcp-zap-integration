package com.bnt.rcp.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

import com.bnt.rcp.dto.MerchantProfileDto;
import com.bnt.rcp.entity.MerchantProfile;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface MerchantProfileMapper {

    MerchantProfileMapper INSTANCE = Mappers.getMapper(MerchantProfileMapper.class);

    MerchantProfile toEntity(MerchantProfileDto merchantProfileDto);

    MerchantProfileDto toDto(MerchantProfile merchantProfile);

    List<MerchantProfileDto> toDtoList(List<MerchantProfile> merchantProfileList);

}
