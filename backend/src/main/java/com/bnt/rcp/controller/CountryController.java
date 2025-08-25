package com.bnt.rcp.controller;

import com.bnt.rcp.constant.RippsRestConstant;
import com.bnt.rcp.dto.CountryDto;
import com.bnt.rcp.dto.ResponseEntityData;
import com.bnt.rcp.service.CountryService;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/api/countries")
public class CountryController {

    private final CountryService countryService;

    public CountryController(CountryService countryService) {
        this.countryService = countryService;
    }

    @PostMapping
    public ResponseEntity<ResponseEntityData> createCountry(@RequestBody CountryDto countryDto) {
        log.info("Received request to create country");

        Integer countryId = countryService.createCountry(countryDto);

        log.info("Successfully created country with id: {}", countryId);
        return ResponseEntity.ok(new ResponseEntityData(RippsRestConstant.SUCCESS, "Country created successfully", countryId)
        );
    }

    @GetMapping
    public ResponseEntity<ResponseEntityData> getAllCountries() {
        log.info("Received request to fetch all countries");

        List<CountryDto> countries = countryService.getAllCountries();

        log.info("Successfully fetched {} countries", countries.size());
        return ResponseEntity.ok(new ResponseEntityData(RippsRestConstant.SUCCESS, "Countries fetched successfully", countries)
        );
    }

    @GetMapping("/filter")
    public ResponseEntity<ResponseEntityData> getAllCountriesIdAndName() {
        log.info("Received request to fetch all countries with id and name");

        Map<Integer, String> countryMap = countryService.getAllCountriesIdAndName();

        log.info("Successfully fetched {} countries with id and name", countryMap.size());
        return ResponseEntity.ok(new ResponseEntityData(RippsRestConstant.SUCCESS, "Countries fetched successfully with id and Code", countryMap)
        );
    }

    @PutMapping("/{id}")
    public ResponseEntity<ResponseEntityData> updateCountry(@PathVariable Integer id, @RequestBody CountryDto countryDto) {
        log.info("Received request to update country with id: {}", id);

        Integer updatedId = countryService.updateCountry(id, countryDto);

        log.info("Successfully updated country with id: {}", updatedId);
        return ResponseEntity.ok(new ResponseEntityData(RippsRestConstant.SUCCESS, "Country updated successfully", updatedId)
        );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseEntityData> deleteCountry(@PathVariable Integer id) {
        log.info("Received request to delete country with id: {}", id);

        countryService.deleteCountry(id);  
        log.info("Successfully deleted country with id: {}", id);
        return ResponseEntity.ok(new ResponseEntityData(RippsRestConstant.SUCCESS, "Country deleted successfully", null)
    );
    }
}
