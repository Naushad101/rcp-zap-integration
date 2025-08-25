package com.bnt.rcp.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.bnt.rcp.dto.TerminalModelDto;
import com.bnt.rcp.service.TerminalModelService;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@RestController
@RequestMapping("/api/terminal-models")
public class TerminalModelController {

    @Autowired
    private TerminalModelService terminalModelService;

    @PostMapping
    public ResponseEntity<TerminalModelDto> createTerminalModel(@RequestBody TerminalModelDto terminalModelDto) {
        TerminalModelDto createdTerminalModel = terminalModelService.createTerminalModel(terminalModelDto);
        return new ResponseEntity<>(createdTerminalModel, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<TerminalModelDto> getTerminalModelById(@PathVariable Integer id) {
        TerminalModelDto terminalModelDto = terminalModelService.getTerminalModelById(id);
        return new ResponseEntity<>(terminalModelDto, HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<TerminalModelDto>> getAllTerminalModels() {
        List<TerminalModelDto> terminalModels = terminalModelService.getAllTerminalModels();
        return new ResponseEntity<>(terminalModels, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<TerminalModelDto> updateTerminalModel(@PathVariable Integer id, @RequestBody TerminalModelDto terminalModelDto) {
        TerminalModelDto updatedTerminalModel = terminalModelService.updateTerminalModel(id, terminalModelDto);
        return new ResponseEntity<>(updatedTerminalModel, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTerminalModel(@PathVariable Integer id) {
        terminalModelService.deleteTerminalModel(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}