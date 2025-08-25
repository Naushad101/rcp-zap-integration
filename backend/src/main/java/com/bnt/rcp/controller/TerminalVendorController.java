package com.bnt.rcp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.bnt.rcp.dto.TerminalVendorDto;
import com.bnt.rcp.service.TerminalVendorService;

import java.util.List;

@RestController
@RequestMapping("/api/terminal-vendors")
public class TerminalVendorController {

    @Autowired
    private TerminalVendorService terminalVendorService;

    @PostMapping
    public ResponseEntity<TerminalVendorDto> createTerminalVendor(@RequestBody TerminalVendorDto terminalVendorDto) {
        TerminalVendorDto createdTerminalVendor = terminalVendorService.createTerminalVendor(terminalVendorDto);
        return new ResponseEntity<>(createdTerminalVendor, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<TerminalVendorDto> getTerminalVendorById(@PathVariable Integer id) {
        TerminalVendorDto terminalVendorDto = terminalVendorService.getTerminalVendorById(id);
        return new ResponseEntity<>(terminalVendorDto, HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<TerminalVendorDto>> getAllTerminalVendors() {
        List<TerminalVendorDto> terminalVendors = terminalVendorService.getAllTerminalVendors();
        return new ResponseEntity<>(terminalVendors, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<TerminalVendorDto> updateTerminalVendor(@PathVariable Integer id, @RequestBody TerminalVendorDto terminalVendorDto) {
        TerminalVendorDto updatedTerminalVendor = terminalVendorService.updateTerminalVendor(id, terminalVendorDto);
        return new ResponseEntity<>(updatedTerminalVendor, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTerminalVendor(@PathVariable Integer id) {
        terminalVendorService.deleteTerminalVendor(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}