package com.bnt.rcp.databasesyncup.mapper;

import com.bnt.rcp.databasesyncup.dto.CurrencySyncUpDto;
import com.bnt.rcp.entity.Currency;

import org.mapstruct.*;
import org.mapstruct.factory.Mappers;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface CurrencyMapper {

    CurrencyMapper INSTANCE = Mappers.getMapper(CurrencyMapper.class);

    CurrencySyncUpDto toDto(Currency value);

}
