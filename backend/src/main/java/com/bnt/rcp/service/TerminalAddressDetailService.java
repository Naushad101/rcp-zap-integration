package com.bnt.rcp.service;

import java.util.List;

import com.bnt.rcp.dto.TerminalAddressDetailDto;


public interface TerminalAddressDetailService {
    Long createTerminalAddressDetail(TerminalAddressDetailDto terminalAddressDetailDto);
    TerminalAddressDetailDto getTerminalAddressDetailById(Long id);
    List<TerminalAddressDetailDto> getAllTerminalAddressDetails();
    Long updateTerminalAddressDetail(Long id, TerminalAddressDetailDto terminalAddressDetailDto);
    void deleteTerminalAddressDetail(Long id);
}
