package com.bnt.rcp.service;

import java.util.List;

import com.bnt.rcp.dto.TerminalVendorDto;

public interface TerminalVendorService {
    TerminalVendorDto createTerminalVendor(TerminalVendorDto terminalVendorDto);
    TerminalVendorDto getTerminalVendorById(Integer id);
    List<TerminalVendorDto> getAllTerminalVendors();
    TerminalVendorDto updateTerminalVendor(Integer id, TerminalVendorDto terminalVendorDto);
    void deleteTerminalVendor(Integer id);
}
