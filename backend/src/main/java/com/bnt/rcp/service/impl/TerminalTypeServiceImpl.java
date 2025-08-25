package com.bnt.rcp.service.impl;

import org.springframework.stereotype.Service;

import com.bnt.rcp.dto.TerminalTypeDto;
import com.bnt.rcp.entity.TerminalTypeEntity;
import com.bnt.rcp.mapper.TerminalTypeMapper;
import com.bnt.rcp.repository.TerminalTypeRepository;
import com.bnt.rcp.service.TerminalTypeService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class TerminalTypeServiceImpl implements TerminalTypeService {

    @Autowired
    private TerminalTypeRepository terminalTypeRepository;

    @Autowired
    private TerminalTypeMapper terminalTypeMapper;

    @Override
    public TerminalTypeDto createTerminalType(TerminalTypeDto terminalTypeDto) {
        TerminalTypeEntity entity = terminalTypeMapper.toEntity(terminalTypeDto);
        if(terminalTypeRepository.findByType(entity.getType()).isPresent()) {
            throw new RuntimeException("TerminalType with type " + entity.getType() + " already exists.");
        }
        TerminalTypeEntity savedEntity = terminalTypeRepository.save(entity);
        return terminalTypeMapper.toDto(savedEntity);
    }

    @Override
    public TerminalTypeDto getTerminalTypeById(Integer id) {
        TerminalTypeEntity entity = terminalTypeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("TerminalType not found with id: " + id));
        return terminalTypeMapper.toDto(entity);
    }

    @Override
    public List<TerminalTypeDto> getAllTerminalTypes() {
        List<TerminalTypeEntity> entities = terminalTypeRepository.findAll();
        return entities.stream()
                .map(terminalTypeMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public TerminalTypeDto updateTerminalType(Integer id, TerminalTypeDto terminalTypeDto) {
        TerminalTypeEntity entity = terminalTypeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("TerminalType not found with id: " + id));
        entity.setType(terminalTypeDto.getType());
        entity.setStatus(terminalTypeDto.getStatus());
        entity.setDeleted(terminalTypeDto.getDeleted());
        TerminalTypeEntity updatedEntity = terminalTypeRepository.save(entity);
        return terminalTypeMapper.toDto(updatedEntity);
    }

    @Override
    public void deleteTerminalType(Integer id) {
        TerminalTypeEntity entity = terminalTypeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("TerminalType not found with id: " + id));
        terminalTypeRepository.delete(entity);
    }
}
