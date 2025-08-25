package com.bnt.rcp.databasesyncup.contoller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.bnt.rcp.databasesyncup.dto.DataClientResponse;
import com.bnt.rcp.databasesyncup.dto.DatabaseSyncUpDto;
import com.bnt.rcp.databasesyncup.dto.FilterDataDto;
import com.bnt.rcp.databasesyncup.service.DatabaseSyncUpService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/database-sync-up")
public class DatabaseSyncUpController {

    private DatabaseSyncUpService databaseSynUpService;

    public DatabaseSyncUpController(@Autowired DatabaseSyncUpService databaseSynUpService) {
        this.databaseSynUpService = databaseSynUpService;
    }

    @GetMapping(value = "/atm")
    public ResponseEntity<DataClientResponse> getDataFromAtm(@RequestParam String period) {
        log.info("Received request to get ATM data for period: {}", period);
        FilterDataDto filterDataDto = null;
        if (!period.isEmpty()) {
            String[] splitString = period.split("-");
            filterDataDto = new FilterDataDto(splitString[0], splitString[1]);
            log.debug("Created FilterDataDto with startDate: {} and endDate: {}", splitString[0], splitString[1]);
        }

        try {
            DatabaseSyncUpDto databaseSyncUpDto = databaseSynUpService.getDataFromAtm(filterDataDto);
            log.info("Successfully retrieved ATM data");
            return new ResponseEntity<>(new DataClientResponse("", databaseSyncUpDto), HttpStatus.OK);
        } catch (Exception e) {
            log.error("Error occurred while fetching ATM data: {}", e.getMessage(), e);
            return new ResponseEntity<>(new DataClientResponse(e.getMessage(), null), HttpStatus.EXPECTATION_FAILED);
        }
    }

    @GetMapping(value = "/switch")
    public ResponseEntity<DataClientResponse> getDataFromSwitch(@RequestParam String period) {
        log.info("Received request to get Switch data for period: {}", period);
        FilterDataDto filterDataDto = null;
        if (!period.isEmpty()) {
            String[] splitString = period.split("-");
            filterDataDto = new FilterDataDto(splitString[0], splitString[1]);
            log.debug("Created FilterDataDto with startDate: {} and endDate: {}", splitString[0], splitString[1]);
        }

        try {
            DatabaseSyncUpDto databaseSyncUpDto = databaseSynUpService.getDataFromSwitch(filterDataDto);
            log.info("Successfully retrieved Switch data");
            return new ResponseEntity<>(new DataClientResponse("", databaseSyncUpDto), HttpStatus.OK);
        } catch (Exception e) {
            log.error("Error occurred while fetching Switch data: {}", e.getMessage(), e);
            return new ResponseEntity<>(new DataClientResponse(e.getMessage(), null), HttpStatus.EXPECTATION_FAILED);
        }
    }


}

