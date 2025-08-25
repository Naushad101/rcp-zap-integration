package com.bnt.rcp.controller;

import com.bnt.rcp.constant.RippsRestConstant;
import com.bnt.rcp.dto.ResponseEntityData;
import com.bnt.rcp.dto.TerminalDto;
import com.bnt.rcp.service.TerminalService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/api/terminals")
public class TerminalController {

    private final TerminalService terminalService;

    public TerminalController(TerminalService terminalService) {
        this.terminalService = terminalService;
    }

    @PostMapping
    public ResponseEntity<ResponseEntityData> createTerminal(@RequestBody TerminalDto terminalDto) {
        log.info("Received request to create terminal");

        String id = terminalService.createTerminal(terminalDto);

        log.info("Successfully created terminal with id: {}", id);
        return ResponseEntity.ok(new ResponseEntityData(RippsRestConstant.SUCCESS, "Terminal created successfully", id));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResponseEntityData> getTerminalById(@PathVariable String id) {
        log.info("Received request to fetch terminal with id: {}", id);

        TerminalDto terminal = terminalService.getTerminalById(id);

        log.info("Successfully fetched terminal with id: {}", id);
        return ResponseEntity.ok(new ResponseEntityData(RippsRestConstant.SUCCESS, "Terminal fetched successfully", terminal));
    }

    @GetMapping
    public ResponseEntity<ResponseEntityData> getAllTerminals() {
        log.info("Received request to fetch all terminals");

        List<TerminalDto> terminals = terminalService.getAllTerminals();

        log.info("Successfully fetched {} terminals", terminals.size());
        return ResponseEntity.ok(new ResponseEntityData(RippsRestConstant.SUCCESS, "All terminals fetched successfully", terminals));
    }

    @GetMapping("/filter")
    public ResponseEntity<ResponseEntityData> getAllTerminalIdAndCode() {
        log.info("Received request to fetch terminals with id and code");

        Map<String, String> terminalMap = terminalService.getAllTerminalsIdAndCode();

        log.info("Successfully fetched {} terminals with id and code", terminalMap.size());
        return ResponseEntity.ok(new ResponseEntityData(RippsRestConstant.SUCCESS, "Terminals fetched successfully with id and code", terminalMap));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ResponseEntityData> updateTerminal(@PathVariable String id, @RequestBody TerminalDto terminalDto) {
        log.info("Received request to update terminal with id: {}", id);

        String updatedId = terminalService.updateTerminal(id, terminalDto);

        log.info("Successfully updated terminal with id: {}", updatedId);
        return ResponseEntity.ok(new ResponseEntityData(RippsRestConstant.SUCCESS, "Terminal updated successfully", updatedId));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseEntityData> deleteTerminal(@PathVariable String id) {
        log.info("Received request to delete terminal with id: {}", id);

        terminalService.deleteTerminal(id);

        log.info("Successfully deleted terminal with id: {}", id);
        return ResponseEntity.ok(new ResponseEntityData(RippsRestConstant.SUCCESS, "Terminal deleted successfully", id));
    }
}