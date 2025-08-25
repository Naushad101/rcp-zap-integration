package com.bnt.rcp.service.impl;



import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bnt.rcp.dto.TerminalModelDto;
import com.bnt.rcp.dto.TerminalSitingDto;
import com.bnt.rcp.dto.TerminalTypeDto;
import com.bnt.rcp.dto.TerminalVendorDto;
import com.bnt.rcp.entity.TerminalModel;
import com.bnt.rcp.entity.TerminalSiting;
import com.bnt.rcp.entity.TerminalTypeEntity;
import com.bnt.rcp.entity.TerminalVendor;
import com.bnt.rcp.mapper.TerminalModelMapper;
import com.bnt.rcp.repository.TerminalModelRepository;
import com.bnt.rcp.repository.TerminalSitingRepository;
import com.bnt.rcp.repository.TerminalTypeRepository;
import com.bnt.rcp.repository.TerminalVendorRepository;
import com.bnt.rcp.service.TerminalModelService;

import jakarta.transaction.Transactional;

import com.bnt.rcp.mapper.TerminalTypeMapper;
import com.bnt.rcp.mapper.TerminalVendorMapper;
import com.bnt.rcp.mapper.TerminalSitingMapper;


@Service
public class TerminalModelServiceImpl implements TerminalModelService {

    @Autowired
    private TerminalModelRepository terminalModelRepository;
    
    @Autowired
    private TerminalTypeRepository terminalTypeRepository;
    
    @Autowired
    private TerminalVendorRepository terminalVendorRepository;
    
    @Autowired
    private TerminalSitingRepository terminalSitingRepository;

    @Autowired
    private TerminalModelMapper terminalModelMapper;

    @Autowired
    private TerminalTypeMapper terminalTypeMapper;

    @Autowired
    private TerminalVendorMapper terminalVendorMapper;

    @Autowired
    private TerminalSitingMapper terminalSitingMapper;

    @Override
    @Transactional
    public TerminalModelDto createTerminalModel(TerminalModelDto terminalModelDto) {

        System.out.println("================================================");
        System.out.println("The value of siting:" + terminalModelDto.getSiting());
        System.out.println("================================================");

        TerminalTypeEntity type = findOrCreateTerminalType(terminalModelDto.getType());
        TerminalVendor vendor = findOrCreateTerminalVendor(terminalModelDto.getVendor());
        TerminalSiting siting = findOrCreateTerminalSiting(terminalModelDto.getSiting());
        
        
        TerminalModel entity = terminalModelMapper.toEntity(terminalModelDto);
        entity.setType(type);
        entity.setVendor(vendor);
        entity.setSiting(siting);
        
        TerminalModel savedEntity = terminalModelRepository.save(entity);
        return terminalModelMapper.toDto(savedEntity);
    }
    
    private TerminalTypeEntity findOrCreateTerminalType(TerminalTypeDto typeDto) {
        if (typeDto == null) {
            return null;
        }
        
        Optional<TerminalTypeEntity> existingType = terminalTypeRepository.findByType(typeDto.getType());
        
        if (existingType.isPresent()) {
            return existingType.get();
        } else {
    
            TerminalTypeEntity newType = new TerminalTypeEntity();
            newType.setType(typeDto.getType());
            newType.setStatus(typeDto.getStatus());
            newType.setDeleted(typeDto.getDeleted());
            return terminalTypeRepository.save(newType);
        }
    }
    
    private TerminalVendor findOrCreateTerminalVendor(TerminalVendorDto vendorDto) {
        if (vendorDto == null) {
            return null;
        }
        
        Optional<TerminalVendor> existingVendor = terminalVendorRepository.findByName(vendorDto.getName());
        
        if (existingVendor.isPresent()) {
            return existingVendor.get();
        } else {
            TerminalVendor newVendor = new TerminalVendor();
            newVendor.setName(vendorDto.getName());
            newVendor.setStatus(vendorDto.getStatus());
            newVendor.setDeleted(vendorDto.getDeleted());
            return terminalVendorRepository.save(newVendor);
        }
    }
    
    private TerminalSiting findOrCreateTerminalSiting(TerminalSitingDto sitingDto) {
        if (sitingDto == null) {
            System.out.println("TerminalSitingDto is null, returning null");
            return null;
        }

        System.out.println("Finding or creating TerminalSiting for: " + sitingDto.getName());
        
        Optional<TerminalSiting> existingSiting = terminalSitingRepository.findByName(sitingDto.getName());

        System.out.println("Existing siting found: " + existingSiting.isPresent());
        
        if (existingSiting.isPresent()) {
            return existingSiting.get();
        } else {
    
            TerminalSiting newSiting = new TerminalSiting();
            newSiting.setName(sitingDto.getName());
            newSiting.setStatus(sitingDto.getStatus());
            newSiting.setDeleted(sitingDto.getDeleted());
            return terminalSitingRepository.save(newSiting);
        }
    }
    @Override
    public TerminalModelDto getTerminalModelById(Integer id) {
        TerminalModel entity = terminalModelRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("TerminalModel not found with id: " + id));
        return terminalModelMapper.toDto(entity);
    }

    @Override
    public List<TerminalModelDto> getAllTerminalModels() {
        List<TerminalModel> entities = terminalModelRepository.findAll();
        return entities.stream()
                .map(terminalModelMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public TerminalModelDto updateTerminalModel(Integer id, TerminalModelDto terminalModelDto) {
        TerminalModel entity = terminalModelRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("TerminalModel not found with id: " + id));
        entity.setType(findOrCreateTerminalType(terminalModelDto.getType()));
        entity.setVendor(findOrCreateTerminalVendor(terminalModelDto.getVendor()));
        entity.setSiting(findOrCreateTerminalSiting(terminalModelDto.getSiting()));
        entity.setModelname(terminalModelDto.getModelname());
        entity.setStatus(terminalModelDto.getStatus());
        entity.setDeleted(terminalModelDto.getDeleted());
        TerminalModel updatedEntity = terminalModelRepository.save(entity);
        return terminalModelMapper.toDto(updatedEntity);
    }

    @Override
    public void deleteTerminalModel(Integer id) {
        TerminalModel entity = terminalModelRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("TerminalModel not found with id: " + id));
        terminalModelRepository.delete(entity);
    }
}