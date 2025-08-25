package com.bnt.rcp.controller;

import com.bnt.rcp.constant.RippsRestConstant;
import com.bnt.rcp.dto.CountryStateDto;
import com.bnt.rcp.dto.ResponseEntityData;
import com.bnt.rcp.service.CountryStateService;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/country-states")
public class CountryStateController {

    private final CountryStateService countryStateService;

    public CountryStateController(CountryStateService countryStateService) {
        this.countryStateService = countryStateService;
    }

    @PostMapping
    public ResponseEntity<ResponseEntityData> createCountryState(@RequestBody CountryStateDto countryStateDto) {
        log.info("Received request to create countryState");

        Integer countryStateId = countryStateService.createCountryState(countryStateDto);

        log.info("Successfully created countryState with id: {}", countryStateId);
        return ResponseEntity.ok(new ResponseEntityData(RippsRestConstant.SUCCESS, "CountryState created successfully", countryStateId));
    }

    @GetMapping
    public ResponseEntity<ResponseEntityData> getAllCountryStates() {
        log.info("Received request to fetch all countryStates");

        List<CountryStateDto> countryStates = countryStateService.getAllCountryStates();

        log.info("Successfully fetched {} countryStates", countryStates.size());
        return ResponseEntity.ok(new ResponseEntityData(RippsRestConstant.SUCCESS, "CountryStates fetched successfully", countryStates));
    }

    @GetMapping("/filter")
    public ResponseEntity<ResponseEntityData> getAllCountryStatesIdAndName() {
        log.info("Received request to fetch all countryStates with id and name");

        List<CountryStateDto> countryStates = countryStateService.getAllCountryStatesIdAndName();

        log.info("Successfully fetched {} countryStates with id and name", countryStates.size());
        return ResponseEntity.ok(new ResponseEntityData(RippsRestConstant.SUCCESS, "CountryStates fetched successfully with id and Code", countryStates));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ResponseEntityData> updateCountryState(@PathVariable Integer id, @RequestBody CountryStateDto countryStateDto) {
        log.info("Received request to update countryState with id: {}", id);

        Integer updatedId = countryStateService.updateCountryState(id, countryStateDto);

        log.info("Successfully updated countryState with id: {}", updatedId);
        return ResponseEntity.ok(new ResponseEntityData(RippsRestConstant.SUCCESS, "CountryState updated successfully", updatedId));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseEntityData> deleteCountryState(@PathVariable Integer id) {
        log.info("Received request to delete countryState with id: {}", id);

        countryStateService.deleteCountryState(id);

        log.info("Successfully deleted countryState with id: {}", id);
        return ResponseEntity.ok(new ResponseEntityData(RippsRestConstant.SUCCESS, "CountryState deleted successfully", null));
    }   
}
