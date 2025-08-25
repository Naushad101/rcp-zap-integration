package com.bnt.rcp.controller;

import java.util.List;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.bnt.rcp.constant.RippsRestConstant;
import com.bnt.rcp.dto.MerchantInstitutionDto;
import com.bnt.rcp.dto.ResponseEntityData;
import com.bnt.rcp.service.MerchantInstitutionService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/api/merchant-institutions")
public class MerchantInstitutionContoller {

    private final MerchantInstitutionService merchantInstitutionService;

    public MerchantInstitutionContoller(MerchantInstitutionService merchantInstitutionService) {
        this.merchantInstitutionService = merchantInstitutionService;
    }

    @PostMapping
    public ResponseEntity<ResponseEntityData> createMerchantInstitution(@RequestBody MerchantInstitutionDto merchantInstitutionDto) {
        log.info("Received request to create merchant institution");

        Integer id = merchantInstitutionService.createMerchantInstitution(merchantInstitutionDto);

        log.info("Successfully created merchant institution with id: {}", id);
        return ResponseEntity.ok(new ResponseEntityData(RippsRestConstant.SUCCESS, "Merchant institution created successfully", id));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResponseEntityData> getMerchantInstitutionById(@PathVariable Integer id) {
        log.info("Received request to fetch merchant institution with id: {}", id);

        MerchantInstitutionDto merchantInstitution = merchantInstitutionService.getMerchantInstitutionById(id);

        log.info("Successfully fetched merchant institution with id: {}", id);
        return ResponseEntity.ok(new ResponseEntityData(RippsRestConstant.SUCCESS, "Merchant institution fetched successfully", merchantInstitution));
    }

    @GetMapping
    public ResponseEntity<ResponseEntityData> getAllMerchantInstitutions() {
        log.info("Received request to fetch all merchant institutions");

        List<MerchantInstitutionDto> merchantInstitutions = merchantInstitutionService.getAllMerchantInstitutions();

        log.info("Successfully fetched {} merchant institutions", merchantInstitutions.size());
        return ResponseEntity.ok(new ResponseEntityData(RippsRestConstant.SUCCESS, "All merchant institutions fetched successfully", merchantInstitutions));
    }

    @GetMapping("/filter")
    public ResponseEntity<ResponseEntityData> getAllMerchantInstitutionsIdAndName() {
        log.info("Received request to fetch merchant institutions with id and code");

        Map<Integer, String> merchantInstitutionMap = merchantInstitutionService.getAllMerchantInstitutionsIdAndCode();

        log.info("Successfully fetched {} merchant institutions with id and code", merchantInstitutionMap.size());
        return ResponseEntity.ok(new ResponseEntityData(RippsRestConstant.SUCCESS, "Merchant institutions fetched successfully with id and code", merchantInstitutionMap));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ResponseEntityData> updateMerchantInstitution(@PathVariable Integer id, @RequestBody MerchantInstitutionDto merchantInstitutionDto) {
        log.info("Received request to update merchant institution with id: {}", id);

        Integer updatedId = merchantInstitutionService.updateMerchantInstitution(id, merchantInstitutionDto);

        log.info("Successfully updated merchant institution with id: {}", updatedId);
        return ResponseEntity.ok(new ResponseEntityData(RippsRestConstant.SUCCESS, "Merchant institution updated successfully", updatedId));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseEntityData> deleteMerchantInstitution(@PathVariable Integer id) {
        log.info("Received request to delete merchant institution with id: {}", id);

        merchantInstitutionService.deleteMerchantInstitution(id);

        log.info("Successfully deleted merchant institution with id: {}", id);
        return ResponseEntity.ok(new ResponseEntityData(RippsRestConstant.SUCCESS, "Merchant institution deleted successfully", id));
    }
}
