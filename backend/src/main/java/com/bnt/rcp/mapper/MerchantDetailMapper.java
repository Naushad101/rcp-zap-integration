package com.bnt.rcp.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

import com.bnt.rcp.dto.MerchantDetailDto;
import com.bnt.rcp.entity.MerchantDetail;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface MerchantDetailMapper {

    MerchantDetailMapper INSTANCE = Mappers.getMapper(MerchantDetailMapper.class);

    MerchantDetail toEntity(MerchantDetailDto merchantDetailDto);
    
    MerchantDetailDto toDto(MerchantDetail merchantDetail);

    List<MerchantDetailDto> toDtoList(List<MerchantDetail> merchantDetailList);

}
