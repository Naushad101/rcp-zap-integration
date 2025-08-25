package com.bnt.rcp.controller;

import com.bnt.rcp.constant.RippsRestConstant;
import com.bnt.rcp.dto.AcquirerIdConfigDto;
import com.bnt.rcp.dto.ResponseEntityData;
import com.bnt.rcp.service.AcquirerIdConfigService;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/api/acquirer-id-configs")
public class AcquirerIdConfigController {

    private final AcquirerIdConfigService acquirerIdConfigService;

    public AcquirerIdConfigController(AcquirerIdConfigService acquirerIdConfigService) {
        this.acquirerIdConfigService = acquirerIdConfigService;
    }

    @PostMapping
    public ResponseEntity<ResponseEntityData> createAcquirerIdConfig(@RequestBody AcquirerIdConfigDto dto) {
        log.info("Received request to create acquirerIdConfig");

        Integer acquirerId = acquirerIdConfigService.createAcquirerIdConfig(dto);

        log.info("Successfully created acquirerIdConfig with id: {}", acquirerId);
        return ResponseEntity.ok(new ResponseEntityData(RippsRestConstant.SUCCESS, "AcquirerIdConfig created successfully", acquirerId)
        );
    }

    @GetMapping
    public ResponseEntity<ResponseEntityData> getAllAcquirerIdConfigs() {
        log.info("Received request to fetch all acquirerIdConfigs");

        List<AcquirerIdConfigDto> acquirers = acquirerIdConfigService.getAllAcquirerIdConfigs();

        log.info("Successfully fetched all {} acquirerIdConfigs", acquirers.size());
        return ResponseEntity.ok(new ResponseEntityData(RippsRestConstant.SUCCESS, "All AcquirerIdConfigs fetched successfully", acquirers)
        );
    }

    @GetMapping("/filter")
    public ResponseEntity<ResponseEntityData> getAllAcquirerIdConfigsIdAndName() {
        log.info("Received request to fetch all acquirerIdConfigs with id and name");

        Map<Integer, String> acquirerMap = acquirerIdConfigService.getAllAcquirerIdConfigsIdAndName();

        log.info("Successfully fetched all {} acquirerIdConfigs with id and name", acquirerMap.size());
        return ResponseEntity.ok(new ResponseEntityData(RippsRestConstant.SUCCESS, "All AcquirerIdConfigs fetched successfully with id and Code", acquirerMap)
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResponseEntityData> getAcquirerIdConfig(@PathVariable Integer id) {
        log.info("Received request to fetch acquirerIdConfig with id: {}", id);

        AcquirerIdConfigDto dto = acquirerIdConfigService.getAcquirerIdConfig(id);

        log.info("Successfully fetched acquirerIdConfig with id: {}", id);
        return ResponseEntity.ok(new ResponseEntityData(RippsRestConstant.SUCCESS, "AcquirerIdConfig fetched successfully", dto)
        );
    }

    @PutMapping("/{id}")
    public ResponseEntity<ResponseEntityData> updateAcquirerIdConfig(@PathVariable Integer id, @RequestBody AcquirerIdConfigDto dto) {

        log.info("Received request to update acquirerIdConfig with id: {}", id);

        Integer updatedId = acquirerIdConfigService.updateAcquirerIdConfig(id, dto);

        log.info("Successfully updated acquirerIdConfig with id: {}", updatedId);
        return ResponseEntity.ok(new ResponseEntityData(RippsRestConstant.SUCCESS, "AcquirerIdConfig updated successfully", updatedId)
        );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseEntityData> deleteAcquirerIdConfig(@PathVariable Integer id) {
        log.info("Received request to delete acquirerIdConfig with id: {}", id);

        acquirerIdConfigService.deleteAcquirerIdConfig(id);

        log.info("Successfully deleted acquirerIdConfig with id: {}", id);
        return ResponseEntity.ok( new ResponseEntityData(RippsRestConstant.SUCCESS, "AcquirerIdConfig deleted successfully", id)
        );
    }
}
