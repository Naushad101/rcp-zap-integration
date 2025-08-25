package com.bnt.rcp.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bnt.rcp.dto.TerminalVendorDto;
import com.bnt.rcp.entity.TerminalVendor;
import com.bnt.rcp.mapper.TerminalVendorMapper;
import com.bnt.rcp.repository.TerminalVendorRepository;
import com.bnt.rcp.service.TerminalVendorService;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class TerminalVendorServiceImpl implements TerminalVendorService {

    @Autowired
    private TerminalVendorRepository terminalVendorRepository;

    @Autowired
    private TerminalVendorMapper terminalVendorMapper;

    @Override
    public TerminalVendorDto createTerminalVendor(TerminalVendorDto terminalVendorDto) {
        TerminalVendor entity = terminalVendorMapper.toEntity(terminalVendorDto);
        if (terminalVendorRepository.findByName(entity.getName()).isPresent()) {
            throw new RuntimeException("TerminalVendor with name " + entity.getName() + " already exists.");
            
        }
        TerminalVendor savedEntity = terminalVendorRepository.save(entity);
        return terminalVendorMapper.toDto(savedEntity);
    }

    @Override
    public TerminalVendorDto getTerminalVendorById(Integer id) {
        TerminalVendor entity = terminalVendorRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("TerminalVendor not found with id: " + id));
        return terminalVendorMapper.toDto(entity);
    }

    @Override
    public List<TerminalVendorDto> getAllTerminalVendors() {
        List<TerminalVendor> entities = terminalVendorRepository.findAll();
        return entities.stream()
                .map(terminalVendorMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public TerminalVendorDto updateTerminalVendor(Integer id, TerminalVendorDto terminalVendorDto) {
        TerminalVendor entity = terminalVendorRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("TerminalVendor not found with id: " + id));
        entity.setName(terminalVendorDto.getName());
        entity.setStatus(terminalVendorDto.getStatus());
        entity.setDeleted(terminalVendorDto.getDeleted());
        TerminalVendor updatedEntity = terminalVendorRepository.save(entity);
        return terminalVendorMapper.toDto(updatedEntity);
    }

    @Override
    public void deleteTerminalVendor(Integer id) {
        TerminalVendor entity = terminalVendorRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("TerminalVendor not found with id: " + id));
        terminalVendorRepository.delete(entity);
    }
}
