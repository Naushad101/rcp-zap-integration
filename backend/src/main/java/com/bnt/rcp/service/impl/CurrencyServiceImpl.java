package com.bnt.rcp.service.impl;

import com.bnt.rcp.constant.RippsRestConstant;
import com.bnt.rcp.dto.CurrencyDto;
import com.bnt.rcp.entity.Currency;
import com.bnt.rcp.exception.RippsAdminRestException;
import com.bnt.rcp.mapper.CurrencyMapper;
import com.bnt.rcp.repository.CurrencyRepository;
import com.bnt.rcp.service.CurrencyService;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Service
public class CurrencyServiceImpl implements CurrencyService {

    private final CurrencyRepository currencyRepository;

    public CurrencyServiceImpl(CurrencyRepository currencyRepository) {
        this.currencyRepository = currencyRepository;
    }

    @Override
    public Integer createCurrency(CurrencyDto currencyDto) {
        log.info("Creating Currency with details: {}", currencyDto);

        if (currencyRepository.existsByCode(currencyDto.getCode())) {
            log.error("Currency code already exists: {}", currencyDto.getCode());
            throw new RippsAdminRestException(RippsRestConstant.ERROR, "Currency code already exists", null);
        }

        if (currencyRepository.existsByIsoCode(currencyDto.getIsoCode())) {
            log.error("Currency ISO code already exists: {}", currencyDto.getIsoCode());
            throw new RippsAdminRestException(RippsRestConstant.ERROR, "Currency ISO code already exists", null);
        }

        Currency currency = CurrencyMapper.INSTANCE.toEntity(currencyDto);
        
        Currency createdCurrency = currencyRepository.save(currency);

        log.info("Successfully created Currency with id: {}", createdCurrency.getId());
        return createdCurrency.getId();
    }

    @Override
    public List<CurrencyDto> getAllCurrencies() {
        log.info("Fetching all currencies");

        List<Currency> currencies = currencyRepository.findAll().stream()
            .filter(currency -> currency.getDeleted() != '1')
            .toList();

        List<CurrencyDto> currencyDtoList = CurrencyMapper.INSTANCE.toDtoList(currencies);

        log.info("Successfully fetched all {} currencies", currencyDtoList.size());
        return currencyDtoList;
    }

    @Override
    public Map<Integer, String> getAllCurrenciesIdAndCode() {
        log.info("Fetching all currencies with id and code");

        List<Currency> currencies = currencyRepository.findAllCurrenciesIdAndCode();
        Map<Integer, String> currencyMap = currencies.stream().collect(Collectors.toMap(Currency::getId, Currency::getCode));

        log.info("Successfully fetched all {} currencies with id and code", currencyMap.size());
        return currencyMap;
    }

    @Override
    public Integer updateCurrency(Integer id, CurrencyDto currencyDto) {
        log.info("Updating Currency with id: {}", id);

        Optional<Currency> existingCurrencyOpt = currencyRepository.findById(id);

        if (existingCurrencyOpt.isEmpty()) {
            log.warn("Currency with id: {} not found", id);
            throw new RippsAdminRestException(RippsRestConstant.ERROR, "Currency not found with id: " + id, null);
        }

        Currency existingCurrency = existingCurrencyOpt.get();
        existingCurrency.setActive(currencyDto.getActive());

        Currency updatedCurrency = currencyRepository.save(existingCurrency);

        log.info("Successfully updated Currency: {}", updatedCurrency);
        return updatedCurrency.getId();
    }

    @Override
    public void deleteCurrency(Integer id) {
        log.info("Soft deleting Currency with id: {}", id);

        Currency existingCurrency = currencyRepository.findById(id).orElse(null);
        if (existingCurrency == null) {
            log.warn("Currency with id: {} not found", id);
            throw new RippsAdminRestException(RippsRestConstant.ERROR, "Currency not found with id: " + id, null);
        }

        currencyRepository.softDeleteById(id);
        log.info("Successfully soft deleted Currency with id: {}", id);
    }
}
