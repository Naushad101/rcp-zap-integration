package com.bnt.rcp.service.impl;

import com.bnt.rcp.dto.TerminalSitingDto;
import com.bnt.rcp.entity.TerminalSiting;
import com.bnt.rcp.mapper.TerminalSitingMapper;
import com.bnt.rcp.repository.TerminalSitingRepository;
import com.bnt.rcp.service.TerminalSitingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class TerminalSitingServiceImpl implements TerminalSitingService {

    @Autowired
    private TerminalSitingRepository terminalSitingRepository;

    @Autowired
    private TerminalSitingMapper terminalSitingMapper;

    @Override
    public TerminalSitingDto createTerminalSiting(TerminalSitingDto terminalSitingDto) {
        TerminalSiting entity = terminalSitingMapper.toEntity(terminalSitingDto);
        if(terminalSitingRepository.findByName(entity.getName()).isPresent()) {
            throw new RuntimeException("TerminalSiting with name " + entity.getName() + " already exists.");
        }
        TerminalSiting savedEntity = terminalSitingRepository.save(entity);
        return terminalSitingMapper.toDto(savedEntity);
    }

    @Override
    public TerminalSitingDto getTerminalSitingById(Integer id) {
        TerminalSiting entity = terminalSitingRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("TerminalSiting not found with id: " + id));
        return terminalSitingMapper.toDto(entity);
    }

    @Override
    public List<TerminalSitingDto> getAllTerminalSitings() {
        List<TerminalSiting> entities = terminalSitingRepository.findAll();
        return entities.stream()
                .map(terminalSitingMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public TerminalSitingDto updateTerminalSiting(Integer id, TerminalSitingDto terminalSitingDto) {
        TerminalSiting entity = terminalSitingRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("TerminalSiting not found with id: " + id));
        entity.setName(terminalSitingDto.getName());
        entity.setStatus(terminalSitingDto.getStatus());
        entity.setDeleted(terminalSitingDto.getDeleted());
        TerminalSiting updatedEntity = terminalSitingRepository.save(entity);
        return terminalSitingMapper.toDto(updatedEntity);
    }

    @Override
    public void deleteTerminalSiting(Integer id) {
        TerminalSiting entity = terminalSitingRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("TerminalSiting not found with id: " + id));
        terminalSitingRepository.delete(entity);
    }
}
