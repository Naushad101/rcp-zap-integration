package com.bnt.rcp.controller;

import com.bnt.rcp.constant.RippsRestConstant;
import com.bnt.rcp.dto.CurrencyDto;
import com.bnt.rcp.dto.ResponseEntityData;
import com.bnt.rcp.service.CurrencyService;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/api/currencies")
public class CurrencyController {

    private final CurrencyService currencyService;

    public CurrencyController(CurrencyService currencyService) {
        this.currencyService = currencyService;
    }

    @PostMapping
    public ResponseEntity<ResponseEntityData> createCurrency(@RequestBody CurrencyDto currencyDto) {
        log.info("Received request to create currency");

        Integer currencyId = currencyService.createCurrency(currencyDto);

        log.info("Successfully created currency with id: {}", currencyId);
        return ResponseEntity.ok(new ResponseEntityData(RippsRestConstant.SUCCESS, "Currency created successfully", currencyId)
        );
    }

    @GetMapping
    public ResponseEntity<ResponseEntityData> getAllCurrencies() {
        log.info("Received request to fetch currencies");

        List<CurrencyDto> currencies = currencyService.getAllCurrencies();

        log.info("Successfully fetched {} currencies", currencies.size());
        return ResponseEntity.ok(new ResponseEntityData(RippsRestConstant.SUCCESS, "Currencies fetched successfully", currencies)
        );
    }

    @GetMapping("/filter")
    public ResponseEntity<ResponseEntityData> getAllCurrenciesIdAndCode() {
        log.info("Received request to fetch currencies with id and code");

        Map<Integer, String> currencyMap = currencyService.getAllCurrenciesIdAndCode();

        log.info("Successfully fetched {} currencies with id and code", currencyMap.size());
        return ResponseEntity.ok(new ResponseEntityData(RippsRestConstant.SUCCESS, "Currencies fetched successfully with id and Code", currencyMap)
        );
    }

    @PutMapping("/{id}")
    public ResponseEntity<ResponseEntityData> updateCurrency(@PathVariable Integer id, @RequestBody CurrencyDto currencyDto) {
        log.info("Received request to update currency with id: {}", id);

        Integer updatedId = currencyService.updateCurrency(id, currencyDto);

        log.info("Successfully updated currency with id: {}", updatedId);
        return ResponseEntity.ok(new ResponseEntityData(RippsRestConstant.SUCCESS, "Currency updated successfully", updatedId)
        );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseEntityData> deleteCurrency(@PathVariable Integer id) {
        log.info("Received request to delete currency with id: {}", id);

        currencyService.deleteCurrency(id); 
        log.info("Successfully deleted currency with id: {}", id);
        return ResponseEntity.ok(new ResponseEntityData(RippsRestConstant.SUCCESS, "Currency deleted successfully", null)
        );
    }
}
