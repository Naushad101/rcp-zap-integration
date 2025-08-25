package com.bnt.rcp.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bnt.rcp.dto.TerminalAddressDetailDto;
import com.bnt.rcp.entity.TerminalAddressDetail;
import com.bnt.rcp.mapper.TerminalAddressDetailMapper;
import com.bnt.rcp.repository.TerminalAddressDetailRepository;
import com.bnt.rcp.service.TerminalAddressDetailService;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class TerminalAddressDetailServiceImpl implements TerminalAddressDetailService {

    private final TerminalAddressDetailRepository terminalAddressDetailRepository;
    private final TerminalAddressDetailMapper terminalAddressDetailMapper;

    @Override
    public Long createTerminalAddressDetail(TerminalAddressDetailDto terminalAddressDetailDto) {
        log.debug("Creating terminal address detail with data: {}", terminalAddressDetailDto);
        
        try {
            // Convert DTO to entity
            TerminalAddressDetail terminalAddressDetail = terminalAddressDetailMapper.toEntity(terminalAddressDetailDto);
            
            // Save the terminal address detail
            TerminalAddressDetail savedTerminalAddressDetail = terminalAddressDetailRepository.save(terminalAddressDetail);
            
            log.debug("Terminal address detail created successfully with ID: {}", savedTerminalAddressDetail.getId());
            return savedTerminalAddressDetail.getId();
            
        } catch (Exception e) {
            log.error("Error creating terminal address detail: {}", e.getMessage(), e);
            throw new RuntimeException("Failed to create terminal address detail: " + e.getMessage(), e);
        }
    }

    @Override
    @Transactional(readOnly = true)
    public TerminalAddressDetailDto getTerminalAddressDetailById(Long id) {
        log.debug("Fetching terminal address detail with ID: {}", id);
        
        try {
            Optional<TerminalAddressDetail> terminalAddressDetailOpt = terminalAddressDetailRepository.findById(id);
            
            if (terminalAddressDetailOpt.isEmpty()) {
                log.warn("Terminal address detail with ID {} not found", id);
                throw new RuntimeException("Terminal address detail not found with ID: " + id);
            }
            
            TerminalAddressDetail terminalAddressDetail = terminalAddressDetailOpt.get();
            TerminalAddressDetailDto terminalAddressDetailDto = terminalAddressDetailMapper.toDto(terminalAddressDetail);
            
            log.debug("Terminal address detail fetched successfully with ID: {}", id);
            return terminalAddressDetailDto;
            
        } catch (Exception e) {
            log.error("Error fetching terminal address detail with ID {}: {}", id, e.getMessage(), e);
            throw new RuntimeException("Failed to fetch terminal address detail: " + e.getMessage(), e);
        }
    }

    @Override
    @Transactional(readOnly = true)
    public List<TerminalAddressDetailDto> getAllTerminalAddressDetails() {
        log.debug("Fetching all terminal address details");
        
        try {
            List<TerminalAddressDetail> terminalAddressDetails = terminalAddressDetailRepository.findAll();
            
            List<TerminalAddressDetailDto> terminalAddressDetailDtos = terminalAddressDetails.stream()
                    .map(terminalAddressDetailMapper::toDto)
                    .collect(Collectors.toList());
            
            log.debug("Fetched {} terminal address details successfully", terminalAddressDetailDtos.size());
            return terminalAddressDetailDtos;
            
        } catch (Exception e) {
            log.error("Error fetching all terminal address details: {}", e.getMessage(), e);
            throw new RuntimeException("Failed to fetch terminal address details: " + e.getMessage(), e);
        }
    }

    @Override
    public Long updateTerminalAddressDetail(Long id, TerminalAddressDetailDto terminalAddressDetailDto) {
        log.debug("Updating terminal address detail with ID: {}", id);
        
        try {
            // Check if terminal address detail exists
            Optional<TerminalAddressDetail> existingTerminalAddressDetailOpt = terminalAddressDetailRepository.findById(id);
            
            if (existingTerminalAddressDetailOpt.isEmpty()) {
                log.warn("Terminal address detail with ID {} not found for update", id);
                throw new RuntimeException("Terminal address detail not found with ID: " + id);
            }
            
            // Convert DTO to entity
            TerminalAddressDetail updatedTerminalAddressDetail = terminalAddressDetailMapper.toEntity(terminalAddressDetailDto);
            updatedTerminalAddressDetail.setId(id); // Ensure ID remains the same
            
            // Save the updated terminal address detail
            TerminalAddressDetail savedTerminalAddressDetail = terminalAddressDetailRepository.save(updatedTerminalAddressDetail);
            
            log.debug("Terminal address detail updated successfully with ID: {}", savedTerminalAddressDetail.getId());
            return savedTerminalAddressDetail.getId();
            
        } catch (Exception e) {
            log.error("Error updating terminal address detail with ID {}: {}", id, e.getMessage(), e);
            throw new RuntimeException("Failed to update terminal address detail: " + e.getMessage(), e);
        }
    }

    @Override
    public void deleteTerminalAddressDetail(Long id) {
        log.debug("Deleting terminal address detail with ID: {}", id);
        
        try {
            // Check if terminal address detail exists
            Optional<TerminalAddressDetail> terminalAddressDetailOpt = terminalAddressDetailRepository.findById(id);
            
            if (terminalAddressDetailOpt.isEmpty()) {
                log.warn("Terminal address detail with ID {} not found for deletion", id);
                throw new RuntimeException("Terminal address detail not found with ID: " + id);
            }
            
            // Hard delete the terminal address detail
            terminalAddressDetailRepository.deleteById(id);
            
            log.debug("Terminal address detail deleted successfully with ID: {}", id);
            
        } catch (Exception e) {
            log.error("Error deleting terminal address detail with ID {}: {}", id, e.getMessage(), e);
            throw new RuntimeException("Failed to delete terminal address detail: " + e.getMessage(), e);
        }
    }
}