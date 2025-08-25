package com.bnt.rcp.databasesyncup.mapper;

import com.bnt.rcp.databasesyncup.dto.MerchantGroupSyncUpDto;
import com.bnt.rcp.entity.MerchantInstitution;
import org.mapstruct.*;
import org.mapstruct.factory.Mappers;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface MerchantGroupMapper {

    MerchantGroupMapper INSTANCE = Mappers.getMapper(MerchantGroupMapper.class);

    @Mapping(target = "institutionId", ignore = true)
    @Mapping(target = "acquirerId", ignore = true)
    @Mapping(target = "merchantInstitutionDetail.countryId", ignore = true)
    @Mapping(target = "merchantInstitutionDetail.state", ignore = true)
    MerchantGroupSyncUpDto toDto(MerchantInstitution entity);
}

