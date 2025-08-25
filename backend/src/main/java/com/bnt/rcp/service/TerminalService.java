package com.bnt.rcp.service;

import java.util.List;
import java.util.Map;

import com.bnt.rcp.dto.TerminalDto;

public interface TerminalService {
    
    /**
     * Create a new terminal
     * @param terminalDto the terminal data
     * @return the created terminal ID
     */
    String createTerminal(TerminalDto terminalDto);
    
    /**
     * Get terminal by ID
     * @param id the terminal ID
     * @return the terminal data
     */
    TerminalDto getTerminalById(String id);
    
    /**
     * Get all terminals
     * @return list of all terminals
     */
    List<TerminalDto> getAllTerminals();
    
    /**
     * Get all terminals with only ID and code
     * @return map of terminal ID to code
     */
    Map<String, String> getAllTerminalsIdAndCode();
    
    /**
     * Update an existing terminal
     * @param id the terminal ID
     * @param terminalDto the updated terminal data
     * @return the updated terminal ID
     */
    String updateTerminal(String id, TerminalDto terminalDto);
    
    /**
     * Delete a terminal by ID
     * @param id the terminal ID
     */
    void deleteTerminal(String id);
}
