package com.bnt.rcp.controller;

import com.bnt.rcp.constant.RippsRestConstant;
import com.bnt.rcp.dto.LocationDto;
import com.bnt.rcp.dto.ResponseEntityData;
import com.bnt.rcp.service.LocationService;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/locations")
public class LocationController {

    private final LocationService locationService;

    public LocationController(LocationService locationService) {
        this.locationService = locationService;
    }

    @PostMapping
    public ResponseEntity<ResponseEntityData> addLocation(@RequestBody LocationDto locationDto) {
        log.info("Received request to create location");

        LocationDto savedLocation = locationService.addLocation(locationDto);

        log.info("Successfully created location with id: {}", savedLocation.getId());
        return ResponseEntity.ok(new ResponseEntityData(RippsRestConstant.SUCCESS, "Location created successfully", savedLocation));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResponseEntityData> getLocationById(@PathVariable Integer id) {
        log.info("Received request to fetch location with id: {}", id);

        LocationDto location = locationService.getLocationById(id);

        log.info("Successfully fetched location with id: {}", id);
        return ResponseEntity.ok(new ResponseEntityData(RippsRestConstant.SUCCESS, "Location fetched successfully", location));
    }

    @GetMapping
    public ResponseEntity<ResponseEntityData> getAllLocations() {
        log.info("Received request to fetch all locations");

        List<LocationDto> locations = locationService.getAllLocations();

        log.info("Successfully fetched {} locations", locations.size());
        return ResponseEntity.ok(new ResponseEntityData(RippsRestConstant.SUCCESS, "Locations fetched successfully", locations));
    }

    @GetMapping("/merchant/{merchantId}")
    public ResponseEntity<ResponseEntityData> getLocationsByMerchantId(@PathVariable Integer merchantId) {
        log.info("Received request to fetch locations for merchant id: {}", merchantId);

        List<LocationDto> locations = locationService.getLocationsByMerchantId(merchantId);

        log.info("Successfully fetched {} locations for merchant id: {}", locations.size(), merchantId);
        return ResponseEntity.ok(new ResponseEntityData(RippsRestConstant.SUCCESS, "Locations fetched successfully for merchant", locations));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ResponseEntityData> updateLocation(@PathVariable Integer id, @RequestBody LocationDto locationDto) {
        log.info("Received request to update location with id: {}", id);

        LocationDto updatedLocation = locationService.updateLocation(id, locationDto);

        log.info("Successfully updated location with id: {}", id);
        return ResponseEntity.ok(new ResponseEntityData(RippsRestConstant.SUCCESS, "Location updated successfully", updatedLocation));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseEntityData> deleteLocation(@PathVariable Integer id) {
        log.info("Received request to delete location with id: {}", id);

        locationService.deleteLocation(id);

        log.info("Successfully deleted location with id: {}", id);
        return ResponseEntity.ok(new ResponseEntityData(RippsRestConstant.SUCCESS, "Location deleted successfully", null));
    }

    @PatchMapping("/{id}/soft-delete")
    public ResponseEntity<ResponseEntityData> softDeleteLocation(@PathVariable Integer id) {
        log.info("Received request to soft delete location with id: {}", id);

        locationService.softDeleteLocation(id);

        log.info("Successfully soft deleted location with id: {}", id);
        return ResponseEntity.ok(new ResponseEntityData(RippsRestConstant.SUCCESS, "Location soft deleted successfully", null));
    }

    @PatchMapping("/{id}/lock")
    public ResponseEntity<ResponseEntityData> lockLocation(@PathVariable Integer id) {
        log.info("Received request to lock location with id: {}", id);

        locationService.lockLocation(id);

        log.info("Successfully locked location with id: {}", id);
        return ResponseEntity.ok(new ResponseEntityData(RippsRestConstant.SUCCESS, "Location locked successfully", null));
    }

    @PatchMapping("/{id}/unlock")
    public ResponseEntity<ResponseEntityData> unlockLocation(@PathVariable Integer id) {
        log.info("Received request to unlock location with id: {}", id);

        locationService.unlockLocation(id);

        log.info("Successfully unlocked location with id: {}", id);
        return ResponseEntity.ok(new ResponseEntityData(RippsRestConstant.SUCCESS, "Location unlocked successfully", null));
    }
}