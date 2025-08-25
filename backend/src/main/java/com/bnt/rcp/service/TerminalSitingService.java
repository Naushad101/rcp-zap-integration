package com.bnt.rcp.service;

import java.util.List;

import com.bnt.rcp.dto.TerminalSitingDto;

public interface TerminalSitingService {
    TerminalSitingDto createTerminalSiting(TerminalSitingDto terminalSitingDto);
    TerminalSitingDto getTerminalSitingById(Integer id);
    List<TerminalSitingDto> getAllTerminalSitings();
    TerminalSitingDto updateTerminalSiting(Integer id, TerminalSitingDto terminalSitingDto);
    void deleteTerminalSiting(Integer id);
}
