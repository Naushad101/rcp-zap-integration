package com.bnt.rcp.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

import com.bnt.rcp.dto.MerchantDto;
import com.bnt.rcp.entity.Merchant;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface MerchantMapper {

    MerchantMapper INSTANCE = Mappers.getMapper(MerchantMapper.class);

    Merchant toEntity(MerchantDto merchantDto);
    
    MerchantDto toDto(Merchant merchant);

    List<MerchantDto> toDtoList(List<Merchant> merchantList);

}
