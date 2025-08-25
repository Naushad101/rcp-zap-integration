package com.bnt.rcp.service;

import com.bnt.rcp.dto.TerminalTypeDto;
import java.util.List;

public interface TerminalTypeService {
    TerminalTypeDto createTerminalType(TerminalTypeDto terminalTypeDto);
    TerminalTypeDto getTerminalTypeById(Integer id);
    List<TerminalTypeDto> getAllTerminalTypes();
    TerminalTypeDto updateTerminalType(Integer id, TerminalTypeDto terminalTypeDto);
    void deleteTerminalType(Integer id);
}
