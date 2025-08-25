package com.bnt.rcp.controller;

import com.bnt.rcp.constant.RippsRestConstant;
import com.bnt.rcp.dto.MerchantDto;
import com.bnt.rcp.dto.ResponseEntityData;
import com.bnt.rcp.service.MerchantService;
import lombok.extern.slf4j.Slf4j;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/api/merchants")
public class MerchantController {

    private final MerchantService merchantService;

    public MerchantController(MerchantService merchantService) {
        this.merchantService = merchantService;
    }

    @PostMapping
    public ResponseEntity<ResponseEntityData> createMerchant(@RequestBody MerchantDto merchantDto) {
        log.info("Received request to create merchant");

        Integer id = merchantService.createMerchant(merchantDto);

        log.info("Successfully created merchant with id: {}", id);
        return ResponseEntity.ok(new ResponseEntityData(RippsRestConstant.SUCCESS, "Merchant created successfully", id));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResponseEntityData> getMerchantById(@PathVariable Integer id) {
        log.info("Received request to fetch merchant with id: {}", id);

        MerchantDto merchant = merchantService.getMerchantById(id);

        log.info("Successfully fetched merchant with id: {}", id);
        return ResponseEntity.ok(new ResponseEntityData(RippsRestConstant.SUCCESS, "Merchant fetched successfully", merchant));
    }

    @GetMapping
    public ResponseEntity<ResponseEntityData> getAllMerchants() {
        log.info("Received request to fetch all merchants");

        List<MerchantDto> merchants = merchantService.getAllMerchants();

        log.info("Successfully fetched {} merchants", merchants.size());
        return ResponseEntity.ok(new ResponseEntityData(RippsRestConstant.SUCCESS, "All merchants fetched successfully", merchants));
    }

    @GetMapping("/filter")
    public ResponseEntity<ResponseEntityData> getAllMerchantIdAndCode() {
        log.info("Received request to fetch merchants with id and code");

        Map<Integer, String> merchantMap = merchantService.getAllMerchantsIdAndCode();

        log.info("Successfully fetched {} merchants with id and code", merchantMap.size());
        return ResponseEntity.ok(new ResponseEntityData(RippsRestConstant.SUCCESS, "Merchants fetched successfully with id and code", merchantMap));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ResponseEntityData> updateMerchant(@PathVariable Integer id, @RequestBody MerchantDto merchantDto) {
        log.info("Received request to update merchant with id: {}", id);

        Integer updatedId = merchantService.updateMerchant(id, merchantDto);

        log.info("Successfully updated merchant with id: {}", updatedId);
        return ResponseEntity.ok(new ResponseEntityData(RippsRestConstant.SUCCESS, "Merchant updated successfully", updatedId));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseEntityData> deleteMerchant(@PathVariable Integer id) {
        log.info("Received request to delete merchant with id: {}", id);

        merchantService.deleteMerchant(id);

        log.info("Successfully deleted merchant with id: {}", id);
        return ResponseEntity.ok(new ResponseEntityData(RippsRestConstant.SUCCESS, "Merchant deleted successfully", id));
    }

    @GetMapping("/category-codes")
    public ResponseEntity<ResponseEntityData> getAllMerchantCategoryCodes() {
        log.info("Received request to fetch all merchant category codes");

        Map<Integer, String> categoryCodes = merchantService.getAllMerchantCategoryCodes();

        log.info("Successfully fetched {} merchant category codes", categoryCodes.size());
        return ResponseEntity.ok(new ResponseEntityData(RippsRestConstant.SUCCESS, "Merchant category codes fetched successfully", categoryCodes));
    }
    
}
