package com.bnt.rcp.service;

import com.bnt.rcp.dto.CurrencyDto;

import java.util.List;
import java.util.Map;

public interface CurrencyService {

    Integer createCurrency(CurrencyDto currencyDto);

    List<CurrencyDto> getAllCurrencies();

    Map<Integer, String> getAllCurrenciesIdAndCode();

    Integer updateCurrency(Integer id, CurrencyDto currencyDto);

    void deleteCurrency(Integer id);
}
