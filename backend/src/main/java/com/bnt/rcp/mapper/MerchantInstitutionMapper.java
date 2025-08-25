package com.bnt.rcp.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

import com.bnt.rcp.dto.MerchantInstitutionDto;
import com.bnt.rcp.entity.MerchantInstitution;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface MerchantInstitutionMapper {

    MerchantInstitutionMapper INSTANCE = Mappers.getMapper(MerchantInstitutionMapper.class);

    MerchantInstitution toEntity(MerchantInstitutionDto merchantInstitutionDto);

    MerchantInstitutionDto toDto(MerchantInstitution merchantInstitution);

    List<MerchantInstitutionDto> toDtoList(List<MerchantInstitution> merchantInstitutionList);

}
