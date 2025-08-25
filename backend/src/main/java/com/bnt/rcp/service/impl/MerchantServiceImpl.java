package com.bnt.rcp.service.impl;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.bnt.rcp.constant.RippsRestConstant;
import com.bnt.rcp.dto.MerchantDto;
import com.bnt.rcp.entity.AtmOption;
import com.bnt.rcp.entity.Country;
import com.bnt.rcp.entity.CountryState;
import com.bnt.rcp.entity.Merchant;
import com.bnt.rcp.entity.MerchantDetail;
import com.bnt.rcp.entity.MerchantInstitution;
import com.bnt.rcp.entity.MerchantProfile;
import com.bnt.rcp.exception.RippsAdminRestException;
import com.bnt.rcp.mapper.AtmOptionMapper;
import com.bnt.rcp.mapper.MerchantDetailMapper;
import com.bnt.rcp.mapper.MerchantMapper;
import com.bnt.rcp.mapper.MerchantProfileMapper;
import com.bnt.rcp.repository.AtmOptionRepository;
import com.bnt.rcp.repository.CountryRepository;
import com.bnt.rcp.repository.CountryStateRepository;
import com.bnt.rcp.repository.MerchantInstitutionRepository;
import com.bnt.rcp.repository.MerchantRepository;
import com.bnt.rcp.service.MerchantService;
import com.bnt.rcp.util.RippsUtil;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class MerchantServiceImpl implements MerchantService {

    private final MerchantRepository merchantRepository;

    private final MerchantInstitutionRepository merchantInstitutionRepository;

    private final CountryRepository countryRepository;

    private final CountryStateRepository countryStateRepository;

    private final AtmOptionRepository atmOptionRepository;

    public MerchantServiceImpl(MerchantRepository merchantRepository,
            MerchantInstitutionRepository merchantInstitutionRepository,
            CountryRepository countryRepository,
            CountryStateRepository countryStateRepository,
            AtmOptionRepository atmOptionRepository) {
        this.merchantRepository = merchantRepository;
        this.merchantInstitutionRepository = merchantInstitutionRepository;
        this.countryRepository = countryRepository;
        this.countryStateRepository = countryStateRepository;
        this.atmOptionRepository = atmOptionRepository;
    }

    @Override
    public Integer createMerchant(MerchantDto merchantDto) {

        log.info("Creating merchant with details: {}", merchantDto);

        if (merchantRepository.existsByName(merchantDto.getName())) {
            log.error("Merchant name already exists: {}", merchantDto.getName());
            throw new RippsAdminRestException(RippsRestConstant.ERROR,
                    "Merchant name already exists: " + merchantDto.getName(), null);
        }

        Merchant merchant = MerchantMapper.INSTANCE.toEntity(merchantDto);

        MerchantDetail merchantDetail = MerchantDetailMapper.INSTANCE.toEntity(merchantDto.getMerchantDetail());
        MerchantProfile merchantProfile = MerchantProfileMapper.INSTANCE.toEntity(merchantDto.getMerchantProfile());

        MerchantInstitution merchantInstitution = merchantInstitutionRepository
                .findById(merchantDto.getMerchantInstitution().getId()).orElse(null);

        Country country = countryRepository.findById(merchantDto.getMerchantDetail().getCountry().getId()).orElse(null);
        CountryState countryState = countryStateRepository
                .findById(merchantDto.getMerchantDetail().getCountryState().getId()).orElse(null);

        String code = RippsUtil.generateMerchantInstitutionCode();

        merchantProfile.setMerchant(merchant);

        merchantDetail.setCountry(country);
        merchantDetail.setCountryState(countryState);
        merchantDetail.setMerchant(merchant);

        merchant.setCode(code);
        merchant.setMerchantInstitution(merchantInstitution);
        merchant.setMerchantDetail(merchantDetail);
        merchant.setMerchantProfile(merchantProfile);
        merchant.setDeleted('0');

        Merchant createdMerchant = merchantRepository.save(merchant);

        log.info("Merchant created with ID: {}", createdMerchant.getId());

        Integer createdMerchantId = createdMerchant.getId();

        log.info("Creating ATM Option for Merchant ID: {}", createdMerchantId);

        AtmOption atmOption = AtmOptionMapper.INSTANCE.toEntity(merchantDto.getAtmOption());
        atmOption.setMerchantId(createdMerchantId);

        atmOptionRepository.save(atmOption);
        log.info("ATM Option created for Merchant ID: {}", createdMerchantId);

        return createdMerchantId;
    }

    @Override
    public MerchantDto getMerchantById(Integer id) {
        log.info("Fetching merchant with id: {}", id);

        Merchant merchant = merchantRepository.findById(id)
                .orElseThrow(() -> {
                    log.warn("Merchant not found with id: {}", id);
                    return new RippsAdminRestException(RippsRestConstant.ERROR, "Merchant not found with Id: " + id,
                            null);
                });

        MerchantDto merchantDto = MerchantMapper.INSTANCE.toDto(merchant);
        log.info("Successfully fetched merchant with id: {}", id);

        return merchantDto;
    }

    @Override
    public List<MerchantDto> getAllMerchants() {
        log.info("Fetching all merchants");

        List<Merchant> merchants = merchantRepository.findAll().stream()
                .filter(merchant -> merchant.getDeleted() != '1')
                .toList();

        List<MerchantDto> merchantDtoList = MerchantMapper.INSTANCE.toDtoList(merchants);

        log.info("Successfully fetched all {} merchants", merchantDtoList.size());
        return merchantDtoList;
    }

    @Override
    public Map<Integer, String> getAllMerchantsIdAndCode() {
        log.info("Fetching all merchants with id and code");

        List<Merchant> merchants = merchantRepository.findAllMerchantsIdAndCode();

        Map<Integer, String> merchantMap = merchants.stream()
                .collect(Collectors.toMap(Merchant::getId, Merchant::getCode));

        log.info("Successfully fetched {} merchants with id and code", merchantMap.size());
        return merchantMap;
    }

    @Override
    public Integer updateMerchant(Integer id, MerchantDto merchantDto) {
        log.info("Updating merchant with id: {}", id);

        Merchant existingMerchant = merchantRepository.findById(id)
                .orElseThrow(() -> {
                    log.warn("Merchant not found with id: {}", id);
                    return new RippsAdminRestException(RippsRestConstant.ERROR, "Merchant not found with id: " + id,
                            null);
                });

        updateMerchantBasicFields(existingMerchant, merchantDto);
        updateMerchantDetail(existingMerchant, merchantDto);
        updateMerchantProfile(existingMerchant, merchantDto);
        updateMerchantInstitution(existingMerchant, merchantDto);

        Merchant updatedMerchant = merchantRepository.save(existingMerchant);
        log.info("Successfully updated merchant with id: {}", updatedMerchant.getId());
        return updatedMerchant.getId();
    }

    @Override
    public void deleteMerchant(Integer id) {
        log.info("Soft deleting merchant with id: {}", id);

        if (!merchantRepository.existsById(id)) {
            log.warn("Merchant with id: {} not found", id);
            throw new RippsAdminRestException(RippsRestConstant.ERROR, "Merchant not found with id: " + id, null);
        }

        merchantRepository.softDeleteById(id);
        log.info("Successfully soft deleted merchant with id: {}", id);
    }

    @Override
    public Map<Integer, String> getAllMerchantCategoryCodes() {
        log.info("Fetching all merchant institution category codes");

        List<Object[]> categoryCodes = merchantRepository.findAllMerchantCategoryCodes();

        Map<Integer, String> categoryCodeMap = categoryCodes.stream().collect(Collectors.toMap(
                        code -> (Integer) code[0],
                        code -> (String) code[1]));
                                                                                
        log.info("Successfully fetched all {} merchant institution category codes", categoryCodeMap.size());
        return categoryCodeMap;
    }

    private void updateMerchantBasicFields(Merchant existingMerchant, MerchantDto merchantDto) {
        if (merchantDto.getName() != null)
            existingMerchant.setName(merchantDto.getName());
        if (merchantDto.getCode() != null)
            existingMerchant.setCode(merchantDto.getCode());
        if (merchantDto.getActivateOn() != null)
            existingMerchant.setActivateOn(merchantDto.getActivateOn());
        if (merchantDto.getExpiryOn() != null)
            existingMerchant.setExpiryOn(merchantDto.getExpiryOn());
        if (merchantDto.getLocked() != null)
            existingMerchant.setLocked(merchantDto.getLocked());
        if (merchantDto.getDeleted() != null)
            existingMerchant.setDeleted(merchantDto.getDeleted());
        existingMerchant.setPosSafetyFlag(merchantDto.getPosSafetyFlag());
    }

    private void updateMerchantDetail(Merchant existingMerchant, MerchantDto merchantDto) {
        if (merchantDto.getMerchantDetail() == null) {
            return;
        }
        MerchantDetail detail = existingMerchant.getMerchantDetail();
        if (detail == null) {
            detail = new MerchantDetail();
            detail.setMerchant(existingMerchant);
        }
        if (merchantDto.getMerchantDetail().getAddress1() != null)
            detail.setAddress1(merchantDto.getMerchantDetail().getAddress1());
        if (merchantDto.getMerchantDetail().getAddress2() != null)
            detail.setAddress2(merchantDto.getMerchantDetail().getAddress2());
        if (merchantDto.getMerchantDetail().getCity() != null)
            detail.setCity(merchantDto.getMerchantDetail().getCity());
        if (merchantDto.getMerchantDetail().getZip() != null)
            detail.setZip(merchantDto.getMerchantDetail().getZip());
        if (merchantDto.getMerchantDetail().getPhone() != null)
            detail.setPhone(merchantDto.getMerchantDetail().getPhone());
        if (merchantDto.getMerchantDetail().getEmail() != null)
            detail.setEmail(merchantDto.getMerchantDetail().getEmail());
        if (merchantDto.getMerchantDetail().getFax() != null)
            detail.setFax(merchantDto.getMerchantDetail().getFax());

        if (merchantDto.getMerchantDetail().getCountry() != null) {
            Country country = countryRepository.findById(merchantDto.getMerchantDetail().getCountry().getId())
                    .orElse(null);
            detail.setCountry(country);
        }

        if (merchantDto.getMerchantDetail().getCountryState() != null) {
            CountryState state = countryStateRepository
                    .findById(merchantDto.getMerchantDetail().getCountryState().getId()).orElse(null);
            detail.setCountryState(state);
        }

        existingMerchant.setMerchantDetail(detail);
    }

    private void updateMerchantProfile(Merchant existingMerchant, MerchantDto merchantDto) {
        if (merchantDto.getMerchantProfile() == null) {
            return;
        }
        MerchantProfile profile = existingMerchant.getMerchantProfile();
        if (profile == null) {
            profile = new MerchantProfile();
            profile.setMerchant(existingMerchant);
        }

        if (merchantDto.getMerchantProfile().getPartialAuth() != null)
            profile.setPartialAuth(merchantDto.getMerchantProfile().getPartialAuth());
        if (merchantDto.getMerchantProfile().getVelocity() != null)
            profile.setVelocity(merchantDto.getMerchantProfile().getVelocity());
        if (merchantDto.getMerchantProfile().getCategoryCode() != null)
            profile.setCategoryCode(merchantDto.getMerchantProfile().getCategoryCode());
        if (merchantDto.getMerchantProfile().getServices() != null)
            profile.setServices(merchantDto.getMerchantProfile().getServices());

        existingMerchant.setMerchantProfile(profile);
    }

    private void updateMerchantInstitution(Merchant existingMerchant, MerchantDto merchantDto) {
        if (merchantDto.getMerchantInstitution() != null && merchantDto.getMerchantInstitution().getId() != null) {
            MerchantInstitution inst = merchantInstitutionRepository
                    .findById(merchantDto.getMerchantInstitution().getId()).orElse(null);
            existingMerchant.setMerchantInstitution(inst);
        }
    }

}
