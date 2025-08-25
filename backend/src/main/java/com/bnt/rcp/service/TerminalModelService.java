package com.bnt.rcp.service;

import java.util.List;

import com.bnt.rcp.dto.TerminalModelDto;

public interface TerminalModelService {
    TerminalModelDto createTerminalModel(TerminalModelDto terminalModelDto);
    TerminalModelDto getTerminalModelById(Integer id);
    List<TerminalModelDto> getAllTerminalModels();
    TerminalModelDto updateTerminalModel(Integer id, TerminalModelDto terminalModelDto);
    void deleteTerminalModel(Integer id);
}
