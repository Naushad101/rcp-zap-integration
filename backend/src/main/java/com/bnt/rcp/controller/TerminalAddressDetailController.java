package com.bnt.rcp.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.bnt.rcp.constant.RippsRestConstant;
import com.bnt.rcp.dto.ResponseEntityData;
import com.bnt.rcp.dto.TerminalAddressDetailDto;
import com.bnt.rcp.service.TerminalAddressDetailService;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/terminal-address-details")
public class TerminalAddressDetailController {

    private final TerminalAddressDetailService terminalAddressDetailService;

    public TerminalAddressDetailController(TerminalAddressDetailService terminalAddressDetailService) {
        this.terminalAddressDetailService = terminalAddressDetailService;
    }

    @PostMapping
    public ResponseEntity<ResponseEntityData> createTerminalAddressDetail(@RequestBody TerminalAddressDetailDto terminalAddressDetailDto) {
        log.info("Received request to create terminal address detail");

        Long id = terminalAddressDetailService.createTerminalAddressDetail(terminalAddressDetailDto);

        log.info("Successfully created terminal address detail with id: {}", id);
        return ResponseEntity.ok(new ResponseEntityData(RippsRestConstant.SUCCESS, "Terminal address detail created successfully", id));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResponseEntityData> getTerminalAddressDetailById(@PathVariable Long id) {
        log.info("Received request to fetch terminal address detail with id: {}", id);

        TerminalAddressDetailDto terminalAddressDetail = terminalAddressDetailService.getTerminalAddressDetailById(id);

        log.info("Successfully fetched terminal address detail with id: {}", id);
        return ResponseEntity.ok(new ResponseEntityData(RippsRestConstant.SUCCESS, "Terminal address detail fetched successfully", terminalAddressDetail));
    }

    @GetMapping
    public ResponseEntity<ResponseEntityData> getAllTerminalAddressDetails() {
        log.info("Received request to fetch all terminal address details");

        List<TerminalAddressDetailDto> terminalAddressDetails = terminalAddressDetailService.getAllTerminalAddressDetails();

        log.info("Successfully fetched {} terminal address details", terminalAddressDetails.size());
        return ResponseEntity.ok(new ResponseEntityData(RippsRestConstant.SUCCESS, "All terminal address details fetched successfully", terminalAddressDetails));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ResponseEntityData> updateTerminalAddressDetail(@PathVariable Long id, @RequestBody TerminalAddressDetailDto terminalAddressDetailDto) {
        log.info("Received request to update terminal address detail with id: {}", id);

        Long updatedId = terminalAddressDetailService.updateTerminalAddressDetail(id, terminalAddressDetailDto);

        log.info("Successfully updated terminal address detail with id: {}", updatedId);
        return ResponseEntity.ok(new ResponseEntityData(RippsRestConstant.SUCCESS, "Terminal address detail updated successfully", updatedId));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseEntityData> deleteTerminalAddressDetail(@PathVariable Long id) {
        log.info("Received request to delete terminal address detail with id: {}", id);

        terminalAddressDetailService.deleteTerminalAddressDetail(id);

        log.info("Successfully deleted terminal address detail with id: {}", id);
        return ResponseEntity.ok(new ResponseEntityData(RippsRestConstant.SUCCESS, "Terminal address detail deleted successfully", id));
    }
}