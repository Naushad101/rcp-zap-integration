package com.bnt.rcp.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

import com.bnt.rcp.dto.MerchantInstitutionDetailDto;
import com.bnt.rcp.entity.MerchantInstitutionDetail;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface MerchantInstitutionDetailMapper {

    MerchantInstitutionDetailMapper INSTANCE = Mappers.getMapper(MerchantInstitutionDetailMapper.class);

    MerchantInstitutionDetail toEntity(MerchantInstitutionDetailDto merchantInstitutionDetailDto);

    List<MerchantInstitutionDetailDto> toDtoList(List<MerchantInstitutionDetail> merchantInstitutionDetailList);

}
