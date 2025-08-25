package com.bnt.rcp.service.impl;

import com.bnt.rcp.constant.RippsRestConstant;
import com.bnt.rcp.dto.MerchantInstitutionDto;
import com.bnt.rcp.dto.MerchantInstitutionDetailDto;
import com.bnt.rcp.entity.MerchantInstitution;
import com.bnt.rcp.entity.MerchantInstitutionDetail;
import com.bnt.rcp.exception.RippsAdminRestException;
import com.bnt.rcp.mapper.MerchantInstitutionDetailMapper;
import com.bnt.rcp.mapper.MerchantInstitutionMapper;
import com.bnt.rcp.repository.InstitutionRepository;
import com.bnt.rcp.repository.MerchantInstitutionRepository;
import com.bnt.rcp.service.MerchantInstitutionService;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import com.bnt.rcp.util.RippsUtil;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Service
public class MerchantInstitutionServiceImpl implements MerchantInstitutionService {

    private final MerchantInstitutionRepository merchantInstitutionRepository;

    private final InstitutionRepository institutionRepository;

    public MerchantInstitutionServiceImpl(MerchantInstitutionRepository merchantInstitutionRepository,
            InstitutionRepository institutionRepository) {
        this.merchantInstitutionRepository = merchantInstitutionRepository;
        this.institutionRepository = institutionRepository;
    }

    @Override
    public Integer createMerchantInstitution(MerchantInstitutionDto merchantInstitutionDto) {
        log.info("Creating merchantInstitution with details: {}", merchantInstitutionDto);

        if(merchantInstitutionRepository.existsByName(merchantInstitutionDto.getName())) {
            log.error("Merchant institution name already exists: {}", merchantInstitutionDto.getName());
            throw new RippsAdminRestException(RippsRestConstant.ERROR, "Merchant institution name already exists: "+ merchantInstitutionDto.getName(), null);
        }

        MerchantInstitution merchantInstitution = MerchantInstitutionMapper.INSTANCE.toEntity(merchantInstitutionDto);

        MerchantInstitutionDetail merchantInstitutionDetail = MerchantInstitutionDetailMapper.INSTANCE.toEntity(merchantInstitutionDto.getMerchantInstitutionDetail());
        String code = RippsUtil.generateMerchantInstitutionCode();

        merchantInstitution.setInstitution(institutionRepository.findById(merchantInstitutionDto.getInstitution().getId()).orElse(null));
        merchantInstitution.setCode(code);
        merchantInstitution.setDeleted('0');

        merchantInstitutionDetail.setMerchantInstitution(merchantInstitution);
        merchantInstitution.setMerchantInstitutionDetail(merchantInstitutionDetail);

        MerchantInstitution createdMerchantInstitution = merchantInstitutionRepository.save(merchantInstitution);

        log.info("Merchant institution created with ID: {}", createdMerchantInstitution.getId());
        return createdMerchantInstitution.getId();
    }

    @Override   
    public MerchantInstitutionDto getMerchantInstitutionById(Integer id) {
        log.info("Fetching merchantInstitution with id: {}", id);

        Optional<MerchantInstitution> merchantInstitutionOpt = merchantInstitutionRepository.findById(id);

        if(merchantInstitutionOpt.isEmpty()) {
            log.warn("Merchant institution with Id: {} not found", id);
            throw new RippsAdminRestException(RippsRestConstant.ERROR, "Merchant institution not found with ID: " + id, null);
        }

        MerchantInstitution merchantInstitution = merchantInstitutionOpt.get();
        MerchantInstitutionDto merchantInstitutionDto = MerchantInstitutionMapper.INSTANCE.toDto(merchantInstitution);

        log.info("Successfully fetched merchantInstitution with id: {}", id);

        return merchantInstitutionDto;
    }

    @Override
    public List<MerchantInstitutionDto> getAllMerchantInstitutions() {
        log.info("Fetching all merchantInstitutions");

        List<MerchantInstitution> merchantInstitutions = merchantInstitutionRepository.findAll().stream()
                .filter(merchantInstitution -> merchantInstitution.getDeleted() != '1')
                .toList();

        List<MerchantInstitutionDto> merchantInstitutionDtoList = MerchantInstitutionMapper.INSTANCE.toDtoList(merchantInstitutions);

        log.info("Successfully fetched all {} merchantInstitutions", merchantInstitutionDtoList.size());
        return merchantInstitutionDtoList;
    }

    @Override
    public Map<Integer, String> getAllMerchantInstitutionsIdAndCode() {
        log.info("Fetching all merchantInstitutions with id and code");

        List<MerchantInstitution> merchantInstitutions = merchantInstitutionRepository.findAllMerchantInstitutionsIdAndName();
        Map<Integer, String> merchantInstitutionMap = merchantInstitutions.stream().collect(Collectors.toMap(MerchantInstitution::getId, MerchantInstitution::getName));

        log.info("Successfully fetched all {} merchantInstitutions with id and code", merchantInstitutionMap.size());
        return merchantInstitutionMap;
    }

    @Override
    public Integer updateMerchantInstitution(Integer id, MerchantInstitutionDto merchantInstitutionDto) {
        log.info("Updating merchantInstitution with id: {}", id);

        Optional<MerchantInstitution> existingMerchantInstitutionOpt = merchantInstitutionRepository.findById(id);

        if (existingMerchantInstitutionOpt.isEmpty()) {
            log.warn("Merchant institution with id: {} not found", id);
            throw new RippsAdminRestException(RippsRestConstant.ERROR, "Merchant institution not found with id : " + id, null);
        }

        MerchantInstitution existingMerchantInstitution = existingMerchantInstitutionOpt.get();
        updateMerchantInstitutionFields(existingMerchantInstitution, merchantInstitutionDto);
        
        if (merchantInstitutionDto.getMerchantInstitutionDetail() != null) {
            updateMerchantInstitutionDetail(existingMerchantInstitution, merchantInstitutionDto.getMerchantInstitutionDetail());
        }

        MerchantInstitution updatedMerchantInstitution = merchantInstitutionRepository.save(existingMerchantInstitution);
        log.info("Successfully updated merchantInstitution with id: {}", updatedMerchantInstitution.getId());
        return updatedMerchantInstitution.getId();
    }

    @Override
    public void deleteMerchantInstitution(Integer id) {
        log.info("Soft deleting merchantInstitution with id: {}", id);

        if (!merchantInstitutionRepository.existsById(id)) {
            log.warn("Merchant institution with id: {} not found", id);
            throw new RippsAdminRestException(RippsRestConstant.ERROR, "Merchant institution not found with id: " + id, null);
        }

        merchantInstitutionRepository.softDeleteById(id);
        log.info("Successfully soft deleted merchantInstitution with id: {}", id);
    }

    private void updateMerchantInstitutionFields(MerchantInstitution entity, MerchantInstitutionDto dto) {
        if (dto.getName() != null) entity.setName(dto.getName());
        if (dto.getCode() != null) entity.setCode(dto.getCode());
        if (dto.getDescription() != null) entity.setDescription(dto.getDescription());
        if (dto.getActivateOn() != null) entity.setActivateOn(dto.getActivateOn());
        if (dto.getExpiryOn() != null) entity.setExpiryOn(dto.getExpiryOn());
        if (dto.getLocked() != null) entity.setLocked(dto.getLocked());
        if (dto.getDeleted() != null) entity.setDeleted(dto.getDeleted());
    }

    private void updateMerchantInstitutionDetail(MerchantInstitution entity, MerchantInstitutionDetailDto detailDto) {
        MerchantInstitutionDetail detail = entity.getMerchantInstitutionDetail();
        if (detail == null) {
            detail = new MerchantInstitutionDetail();
            detail.setMerchantInstitution(entity);
        }

        if (detailDto.getAddress1() != null) detail.setAddress1(detailDto.getAddress1());
        if (detailDto.getAddress2() != null) detail.setAddress2(detailDto.getAddress2());
        if (detailDto.getCity() != null) detail.setCity(detailDto.getCity());
        if (detailDto.getZip() != null) detail.setZip(detailDto.getZip());
        if (detailDto.getPhone() != null) detail.setPhone(detailDto.getPhone());
        if (detailDto.getFax() != null) detail.setFax(detailDto.getFax());
        if (detailDto.getEmail() != null) detail.setEmail(detailDto.getEmail());

        entity.setMerchantInstitutionDetail(detail);
    }

}
