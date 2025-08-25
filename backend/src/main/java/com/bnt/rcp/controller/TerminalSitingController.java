package com.bnt.rcp.controller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.bnt.rcp.dto.TerminalSitingDto;
import com.bnt.rcp.service.TerminalSitingService;

import java.util.List;

@RestController
@RequestMapping("/api/terminal-sitings")
public class TerminalSitingController {

    @Autowired
    private TerminalSitingService terminalSitingService;

    @PostMapping
    public ResponseEntity<TerminalSitingDto> createTerminalSiting(@RequestBody TerminalSitingDto terminalSitingDto) {
        TerminalSitingDto createdTerminalSiting = terminalSitingService.createTerminalSiting(terminalSitingDto);
        return new ResponseEntity<>(createdTerminalSiting, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<TerminalSitingDto> getTerminalSitingById(@PathVariable Integer id) {
        TerminalSitingDto terminalSitingDto = terminalSitingService.getTerminalSitingById(id);
        return new ResponseEntity<>(terminalSitingDto, HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<TerminalSitingDto>> getAllTerminalSitings() {
        List<TerminalSitingDto> terminalSitings = terminalSitingService.getAllTerminalSitings();
        return new ResponseEntity<>(terminalSitings, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<TerminalSitingDto> updateTerminalSiting(@PathVariable Integer id, @RequestBody TerminalSitingDto terminalSitingDto) {
        TerminalSitingDto updatedTerminalSiting = terminalSitingService.updateTerminalSiting(id, terminalSitingDto);
        return new ResponseEntity<>(updatedTerminalSiting, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTerminalSiting(@PathVariable Integer id) {
        terminalSitingService.deleteTerminalSiting(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
