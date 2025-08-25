package com.bnt.rcp.service.impl;

import org.springframework.stereotype.Service;

import com.bnt.rcp.dto.TerminalDto;
import com.bnt.rcp.entity.Terminal;
import com.bnt.rcp.mapper.TerminalMapper;
import com.bnt.rcp.repository.TerminalRepository;
import com.bnt.rcp.service.TerminalService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class TerminalServiceImpl implements TerminalService {

    private final TerminalRepository terminalRepository;
    private final TerminalMapper terminalMapper;

    @Override
    public String createTerminal(TerminalDto terminalDto) {
        log.debug("Creating terminal with data: {}", terminalDto);
        
        try {
            // Convert DTO to entity
            Terminal terminal = terminalMapper.toEntity(terminalDto);
            
            // Set default values if needed
            if (terminal.getDeleted() == null) {
                terminal.setDeleted('N');
            }
            
            // Save the terminal
            Terminal savedTerminal = terminalRepository.save(terminal);
            
            log.debug("Terminal created successfully with ID: {}", savedTerminal.getTerminalId());
            return savedTerminal.getTerminalId();
            
        } catch (Exception e) {
            log.error("Error creating terminal: {}", e.getMessage(), e);
            throw new RuntimeException("Failed to create terminal: " + e.getMessage(), e);
        }
    }

    @Override
    @Transactional(readOnly = true)
    public TerminalDto getTerminalById(String id) {
        log.debug("Fetching terminal with ID: {}", id);
        
        try {
            Optional<Terminal> terminalOpt = terminalRepository.findByTerminalId(id);
            
            if (terminalOpt.isEmpty()) {
                log.warn("Terminal with ID {} not found", id);
                throw new RuntimeException("Terminal not found with ID: " + id);
            }
            
            Terminal terminal = terminalOpt.get();
            TerminalDto terminalDto = terminalMapper.toDto(terminal);
            
            log.debug("Terminal fetched successfully with ID: {}", id);
            return terminalDto;
            
        } catch (Exception e) {
            log.error("Error fetching terminal with ID {}: {}", id, e.getMessage(), e);
            throw new RuntimeException("Failed to fetch terminal: " + e.getMessage(), e);
        }
    }

    @Override
    @Transactional(readOnly = true)
    public List<TerminalDto> getAllTerminals() {
        log.debug("Fetching all terminals");
        
        try {
            List<Terminal> terminals = terminalRepository.findAllByDeletedNot('Y');
            
            List<TerminalDto> terminalDtos = terminals.stream()
                    .map(terminalMapper::toDto)
                    .collect(Collectors.toList());
            
            log.debug("Fetched {} terminals successfully", terminalDtos.size());
            return terminalDtos;
            
        } catch (Exception e) {
            log.error("Error fetching all terminals: {}", e.getMessage(), e);
            throw new RuntimeException("Failed to fetch terminals: " + e.getMessage(), e);
        }
    }

    @Override
    @Transactional(readOnly = true)
    public Map<String, String> getAllTerminalsIdAndCode() {
        log.debug("Fetching all terminals with ID and code only");
        
        try {
            List<Terminal> terminals = terminalRepository.findAllByDeletedNot('Y');
            
            // Map<String, String> terminalMap = terminals.stream()
            //         .collect(Collectors.toMap(
            //                 Terminal::getTerminalId,
            //                 terminal -> terminal.getCode() != null ? terminal.getCode() : "",
            //                 (existing, replacement) -> existing // Handle duplicate keys
            //         ));
            
            // log.debug("Fetched {} terminals with ID and code successfully", terminalMap.size());
            // return terminalMap;
            return null;
            
        } catch (Exception e) {
            log.error("Error fetching terminals with ID and code: {}", e.getMessage(), e);
            throw new RuntimeException("Failed to fetch terminals: " + e.getMessage(), e);
        }
    }

    @Override
    public String updateTerminal(String id, TerminalDto terminalDto) {
        log.debug("Updating terminal with ID: {}", id);
        
        try {
            // Check if terminal exists
            Optional<Terminal> existingTerminalOpt = terminalRepository.findByTerminalId(id);
            
            if (existingTerminalOpt.isEmpty()) {
                log.warn("Terminal with ID {} not found for update", id);
                throw new RuntimeException("Terminal not found with ID: " + id);
            }
            
            // Convert DTO to entity
            Terminal updatedTerminal = terminalMapper.toEntity(terminalDto);
            updatedTerminal.setTerminalId(id); // Ensure ID remains the same
            
            // Preserve existing values for fields that shouldn't be overwritten
            Terminal existingTerminal = existingTerminalOpt.get();
            if (updatedTerminal.getDeleted() == null) {
                updatedTerminal.setDeleted(existingTerminal.getDeleted());
            }
            
            // Save the updated terminal
            Terminal savedTerminal = terminalRepository.save(updatedTerminal);
            
            log.debug("Terminal updated successfully with ID: {}", savedTerminal.getTerminalId());
            return savedTerminal.getTerminalId();
            
        } catch (Exception e) {
            log.error("Error updating terminal with ID {}: {}", id, e.getMessage(), e);
            throw new RuntimeException("Failed to update terminal: " + e.getMessage(), e);
        }
    }

    @Override
    public void deleteTerminal(String id) {
        log.debug("Deleting terminal with ID: {}", id);
        
        try {
            // Check if terminal exists
            Optional<Terminal> terminalOpt = terminalRepository.findByTerminalId(id);
            
            if (terminalOpt.isEmpty()) {
                log.warn("Terminal with ID {} not found for deletion", id);
                throw new RuntimeException("Terminal not found with ID: " + id);
            }
            
            Terminal terminal = terminalOpt.get();
            
            // Soft delete by setting deleted flag
            terminal.setDeleted('Y');
            terminalRepository.save(terminal);
            
            log.debug("Terminal soft deleted successfully with ID: {}", id);
            
        } catch (Exception e) {
            log.error("Error deleting terminal with ID {}: {}", id, e.getMessage(), e);
            throw new RuntimeException("Failed to delete terminal: " + e.getMessage(), e);
        }
    }
}