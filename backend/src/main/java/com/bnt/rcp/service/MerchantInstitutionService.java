package com.bnt.rcp.service;

import java.util.List;
import java.util.Map;

import com.bnt.rcp.dto.MerchantInstitutionDto;

public interface MerchantInstitutionService {

    Integer createMerchantInstitution(MerchantInstitutionDto merchantInstitutionDto);

    MerchantInstitutionDto getMerchantInstitutionById(Integer id);  

    List<MerchantInstitutionDto> getAllMerchantInstitutions();

    Map<Integer, String> getAllMerchantInstitutionsIdAndCode();

    Integer updateMerchantInstitution(Integer id, MerchantInstitutionDto merchantInstitutionDto);

    void deleteMerchantInstitution(Integer id);

}
