package com.bnt.rcp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.bnt.rcp.dto.TerminalTypeDto;
import com.bnt.rcp.service.TerminalTypeService;

import java.util.List;

@RestController
@RequestMapping("/api/terminal-types")
public class TerminalTypeController {

    @Autowired
    private TerminalTypeService terminalTypeService;

    @PostMapping
    public ResponseEntity<TerminalTypeDto> createTerminalType(@RequestBody TerminalTypeDto terminalTypeDto) {
        TerminalTypeDto createdTerminalType = terminalTypeService.createTerminalType(terminalTypeDto);
        return new ResponseEntity<>(createdTerminalType, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<TerminalTypeDto> getTerminalTypeById(@PathVariable Integer id) {
        TerminalTypeDto terminalTypeDto = terminalTypeService.getTerminalTypeById(id);
        return new ResponseEntity<>(terminalTypeDto, HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<TerminalTypeDto>> getAllTerminalTypes() {
        List<TerminalTypeDto> terminalTypes = terminalTypeService.getAllTerminalTypes();
        return new ResponseEntity<>(terminalTypes, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<TerminalTypeDto> updateTerminalType(@PathVariable Integer id, @RequestBody TerminalTypeDto terminalTypeDto) {
        TerminalTypeDto updatedTerminalType = terminalTypeService.updateTerminalType(id, terminalTypeDto);
        return new ResponseEntity<>(updatedTerminalType, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTerminalType(@PathVariable Integer id) {
        terminalTypeService.deleteTerminalType(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
