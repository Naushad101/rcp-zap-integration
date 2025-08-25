package com.bnt.rcp.mapper;

import com.bnt.rcp.dto.CurrencyDto;
import com.bnt.rcp.entity.Currency;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;


@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface CurrencyMapper {

    CurrencyMapper INSTANCE = Mappers.getMapper(CurrencyMapper.class);

    Currency toEntity(CurrencyDto currencyDto);

    List<CurrencyDto> toDtoList(List<Currency> currencyList);

}
