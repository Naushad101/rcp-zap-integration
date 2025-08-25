package com.bnt.rcp.service;

import java.math.BigInteger;
import java.util.List;

import org.springframework.stereotype.Service;

import com.bnt.rcp.dto.DatabaseSyncUpDto;
import com.bnt.rcp.dto.MerchantGroupSyncUpDto;
import com.bnt.rcp.entity.Address;
import com.bnt.rcp.entity.Institution;
import com.bnt.rcp.repository.AddressRepository;
import com.bnt.rcp.repository.InstitutionRepository;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class AtmServiceImpl {

    private final InstitutionRepository institutionRepository;

    private final AddressRepository addressRepository;

    public AtmServiceImpl(InstitutionRepository institutionRepository, AddressRepository addressRepository) {
        this.institutionRepository = institutionRepository;
        this.addressRepository = addressRepository;
    }

    private StringBuilder successData; 

    private StringBuilder failedData;

    public StringBuilder getSuccessData() {
        return successData;
    }

    public StringBuilder getFailedData() {
        return failedData;
    }

    public void execute(DatabaseSyncUpDto syncUpDto) {
        successData = new StringBuilder();
        failedData = new StringBuilder();
        
        log.info("Starting ATM service execution");
        if (syncUpDto.getMerchantis() != null) {
            saveMerchantis(syncUpDto.getMerchantis());
        } 
        log.info("ATM service execution completed");
    }

    private void saveMerchantis(List<MerchantGroupSyncUpDto> merchantis) {
        log.info("Starting to save {} merchant institutions", merchantis.size());
        try {
            for (MerchantGroupSyncUpDto merchantGroupSyncDto : merchantis) {
    
                Address address = new Address();
                address.setId(BigInteger.valueOf(merchantGroupSyncDto.getMerchantInstitutionDetail().getId()));
                address.setAddress1(merchantGroupSyncDto.getMerchantInstitutionDetail().getAddress1());
                address.setAddress2(merchantGroupSyncDto.getMerchantInstitutionDetail().getAddress2());
                address.setCity(merchantGroupSyncDto.getMerchantInstitutionDetail().getCity());
                address.setPostcode(merchantGroupSyncDto.getMerchantInstitutionDetail().getZip());
                address.setPhone(merchantGroupSyncDto.getMerchantInstitutionDetail().getPhone());
                address.setEmail(merchantGroupSyncDto.getMerchantInstitutionDetail().getEmail());
                address.setCountryid(BigInteger.valueOf(merchantGroupSyncDto.getMerchantInstitutionDetail().getCountryId()));
                address.setCreatedby(BigInteger.valueOf(merchantGroupSyncDto.getMerchantInstitutionDetail().getCreatedBy()));
                address.setCreatedon(merchantGroupSyncDto.getMerchantInstitutionDetail().getCreatedOn());
                address.setDeleted(merchantGroupSyncDto.getDeleted() == null ? null : Short.valueOf(String.valueOf(merchantGroupSyncDto.getDeleted())));
        
                addressRepository.save(address);

                Institution institution = new Institution();
                institution.setId(BigInteger.valueOf(merchantGroupSyncDto.getId()));
                institution.setName(merchantGroupSyncDto.getName());
                institution.setCode(merchantGroupSyncDto.getCode());
                institution.setDescription(merchantGroupSyncDto.getDescription());
                institution.setAddress(address.getId());
                institution.setCreatedby(BigInteger.valueOf(merchantGroupSyncDto.getCreatedBy()));
                institution.setCreatedon(merchantGroupSyncDto.getCreatedOn());
                institution.setDeleted(merchantGroupSyncDto.getDeleted() == null ? null : Short.valueOf(String.valueOf(merchantGroupSyncDto.getDeleted())));

                institutionRepository.save(institution);
            }

            successData.append("Merchant Group: ").append(merchantis.size()).append(" ");
            log.info("Successfully processed {} merchant institutions", merchantis.size());
        }
        catch (Exception e) {
            failedData.append("Merchant Group: ").append(e.getMessage()).append(" ");
            log.error("Error processing merchant institutions: {}", e.getMessage(), e);
        }
    }

}
